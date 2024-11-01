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
		validarSaldo(saldo, movimentacao);
		validarLimitesSaque(saldo, movimentacao);
		double tarifa = 2.00;
		movimentacao.setValorOperacao(movimentacao.getValorOperacao() + tarifa);
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
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
		movimentacao.setValorOperacao(movimentacao.getValorOperacao() + tarifa);
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	
	public Movimentacao realizarPix(Movimentacao movimentacao) {
		validarHorarioPix(movimentacao);
		validarCpf(movimentacao);
		validarLimiteOperacoes(movimentacao.getCpfCorrentista());
		detectarFraude(movimentacao);
		double saldo = dao.calcularSaldo(movimentacao.getCpfCorrentista());
		validarSaldo(saldo, movimentacao);
		validarLimitePix(movimentacao);
		double tarifa = 5.00;
		movimentacao.setValorOperacao(movimentacao.getValorOperacao() + tarifa);
		verificarAlertaSaldoBaixo(saldo);
		return inserir(movimentacao);
	}
	


	public boolean validarCpf(Movimentacao movimentacao) {
		String cpf = movimentacao.getCpfCorrentista();
		if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
			return false;
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += (cpf.charAt(i) - '0') * (10 - i);
		}
		int dig10 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += (cpf.charAt(i) - '0') * (11 - i);
		}
		int dig11 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
		return (dig10 == (cpf.charAt(9) - '0')) && (dig11 == (cpf.charAt(10) - '0'));
	}
	
	//As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public void validarHorarioPix(Movimentacao movimentacao) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalArgumentException("Operações de Pix só podem ser realizadas entre 06:00 e 22:00.");
        }
    }

	//Limite de R$ 300,00 para operações de pix.
	public String validarLimitePix(Movimentacao movimentacao){
		if(movimentacao.getValorOperacao()> 300.00){
			return "Valor ultrapassou o limite de R$300,00";
		} 
		return "Valor dentro do limite"; 
	}

	//Limite diário de saques de R$ 5.000,00.
	public String validarLimitesSaque(double saldo, Movimentacao movimentacao){
		if(movimentacao.getValorOperacao()>5000.){
			return "movimentacao.getValorOperacao() de Saque inválido";
		} else if (saldo < movimentacao.getValorOperacao()) {
			return "Saldo insuficiente para sacar esse valor";
		}
		return "Saque válido";
	}

	//O saldo (produto de depósitos - saques - pagamento (boleto) - pix (só pagamento)) não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
	public String validarSaldo(double saldo, Movimentacao movimentacao) {
		if (saldo < movimentacao.getValorOperacao()) {
			return "Saldo insuficiente para sacar esse valor";
		} else if(saldo<100.){
			return "Saldo menor que 100 reais. Faça um depósito";
		}else {
			return "Saque válido";
		}
	}

	public boolean validarLimiteOperacoes(String cpf) {
		int totalOperacoes = dao.contarOperacoesPorDia(cpf);
		return totalOperacoes < 10; // Retorna true se ainda houver espaço para mais operações
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
		if (movimentacao.getValorOperacao() > mediaGastos * 2) {
			throw new IllegalArgumentException("Operação suspeita detectada. Transação bloqueada.");
		}
	}
	
	//Permitir a consulta de extrato mensal.
	//Permitir a consulta de extrato periódico.
	public List<Movimentacao> consultarExtrato(String cpf, Date inicio, Date fim) {
		return dao.buscarPorData(cpf, inicio, fim);
	}
	
}

	


	

	