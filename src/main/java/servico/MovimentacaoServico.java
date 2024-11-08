package servico;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import dao.MovimentacaoDAO;
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
	public Movimentacao realizarSaque(Movimentacao movimentacao) {
		validarCpf(movimentacao);
		validarLimiteOperacoes(movimentacao.getCpfCorrentista());
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(movimentacao.getCpfCorrentista());
		System.out.println("Saldo antes do saque: R$ " + saldo);
		validarSaldo(saldo, movimentacao);
		validarLimitesSaque(movimentacao);
		double tarifa = 2.00; 
		double valorFinal = movimentacao.getValorOperacao() + tarifa; // Total a debitar da conta
		movimentacao.setValorOperacao(-valorFinal); 
		Movimentacao result = inserir(movimentacao);
		saldo = dao.calcularSaldo(movimentacao.getCpfCorrentista()); // Recalcula após inserção
		System.out.println("Saldo após o saque: R$ " + saldo);
		return result;
	}
	
	public Movimentacao realizarDeposito(Movimentacao movimentacao) {
		validarCpf(movimentacao);
		validarLimiteOperacoes(movimentacao.getCpfCorrentista());
		detectarFraude(movimentacao);
		return inserir(movimentacao);
	}
	
	public Movimentacao realizarPagamento(Movimentacao movimentacao) {
		validarCpf(movimentacao);
		validarLimiteOperacoes(movimentacao.getCpfCorrentista());
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(movimentacao.getCpfCorrentista());
		validarSaldo(saldo, movimentacao);
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa)); // Torna o valor negativo
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	
	public Movimentacao realizarPix(Movimentacao movimentacao) {
		validarHorarioPix(movimentacao);
		validar(cpf);
		validarLimiteOperacoes(cpf);
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(movimentacao.getCpfCorrentista());
		validarSaldo(saldo, movimentacao);
		validarLimitePix(movimentacao);
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa)); // Torna o valor negativo
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	
	//As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public void validarHorarioPix(Movimentacao movimentacao) {
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
	

	public boolean validarLimiteOperacoes(String cpf) {
        int totalOperacoes = dao.contarOperacoesPorDia(cpf);
        if (totalOperacoes >= 10) {
            throw new IllegalArgumentException("Limite diário de operações atingido.");
        }
        return true; 
    }

	//Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
	public void verificarAlertaSaldoBaixo(double saldo) {
        if (saldo < 100.00) {
            System.out.println("Alerta: Saldo abaixo de R$100,00. Considere fazer um depósito.");
        }
    }

	//Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
	public void detectarFraude(Movimentacao movimentacao) {
        double mediaGastos = dao.calcularMediaGastos(movimentacao.getCpfCorrentista());
        if (mediaGastos == 0) {
			return; // Não bloqueia a transação se não houver histórico
		}
		if (movimentacao.getValorOperacao() > mediaGastos * 2) {
            throw new IllegalArgumentException("Operação suspeita detectada. Transação bloqueada.");
        }
    }
	
	//Permitir a consulta de extrato mensal.
	//Permitir a consulta de extrato periódico.
	public List<Movimentacao> consultarExtrato(String cpf, Date inicio, Date fim) {
		return dao.buscarPorData(cpf, inicio, fim);
	}
	
	public double consultarSaldo(String cpfCorrentista) {
		return dao.calcularSaldo(cpfCorrentista);
	}	
}	