package servico;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import dao.MovimentacaoDAO;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoServico {
	MovimentacaoDAO dao = new MovimentacaoDAO();
	
	
	public Movimentacao inserir(Movimentacao movimentacao) {
		movimentacao.setDescricao("Operação de "+movimentacao.getTipoTransacao());
		movimentacao.setDataTransacao(new Date());
		Movimentacao movimentacaoBanco = dao.inserir(movimentacao);
		return movimentacaoBanco;
	}

//Validar o CPF no momento de fazer uma transação (saque, depósito, pagamento ou Pix).
//Tarifa de Operação: R$ 5,00 para pagamentos e pix, R$ 2,00 para saques.

public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta) {
	detectarFraude(movimentacao);
	double saldo = dao.calcularSaldo(conta.getId());
	System.out.println("Saldo antes do saque: R$ " + saldo);
	validarSaldo(saldo, movimentacao);
	validarLimitesSaque(movimentacao);
	double tarifa = 2.00;
	double valorFinal = movimentacao.getValorOperacao() + tarifa;
	movimentacao.setValorOperacao(-valorFinal);
	Movimentacao result = inserir(movimentacao);
	saldo = dao.calcularSaldo(conta.getId()); 
	System.out.println("Saldo após o saque: R$ " + saldo);
	verificarAlertaSaldoBaixo(saldo);
	return result;
}
	
	public Movimentacao realizarDeposito(Movimentacao movimentacao) {
		detectarFraude(movimentacao);
		return inserir(movimentacao);
	}
	
	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta) {
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(conta.getId());
		validarSaldo(saldo, movimentacao);
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa)); 
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	
	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta) {
		validarHorarioPix();
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(conta.getId());
		validarLimitePix(movimentacao);
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa));
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	
	
	//As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public void validarHorarioPix() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalArgumentException("Operações de Pix só podem ser realizadas entre 06:00 e 22:00.");
        }
    }

	//Limite de R$ 300,00 para operações de pix.
	public void validarLimitePix(Movimentacao movimentacao) {
		if (movimentacao.getValorOperacao() > 300.00) {
			throw new IllegalArgumentException("Valor ultrapassou o limite de R$300,00");
		}
	}


	//Limite diário de saques de R$ 5.000,00.
	public void validarLimitesSaque(Movimentacao movimentacao) {
        if (movimentacao.getValorOperacao() > 5000.00) {
            throw new IllegalArgumentException("Valor ultrapassou o limite de R$5000,00 para saques.");
        }
    }

	//O saldo (produto de depósitos - saques - pagamento (boleto) - pix (só pagamento)) não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
	public void validarSaldo(double saldo, Movimentacao movimentacao) {
		if (saldo < movimentacao.getValorOperacao()) {
			throw new IllegalArgumentException("Saldo insuficiente para realizar a operação.");
		}
		if (saldo < 100.00) {
			throw new IllegalArgumentException("Saldo menor que R$100,00. Faça um depósito.");
		}
	}
	

	//Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
	public void verificarAlertaSaldoBaixo(double saldo) {
        if (saldo < 100.00) {
            System.out.println("Alerta: Saldo abaixo de R$100,00. Considere fazer um depósito.");
        }
    }

	//Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
	public void detectarFraude(Movimentacao movimentacao) {
        double mediaGastos = dao.calcularMediaGastos(movimentacao.getId());
        if (mediaGastos == 0) {
			return; // Não bloqueia a transação se não houver histórico
		}
		if (movimentacao.getValorOperacao() > mediaGastos * 2) {
            throw new IllegalArgumentException("Operação suspeita detectada. Transação bloqueada.");
        }
    }

	public double consultarSaldo(Long id) {
		return dao.calcularSaldo(id);
	}
	
	//Permitir a consulta de extrato mensal.
	//Permitir a consulta de extrato periódico.
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
		return dao.buscarPorData(id, inicio, fim);
	}
}	