package servico;

import java.time.LocalTime;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {
	ContaDAO dao = new ContaDAO();
	
	
	public Conta inserir(Conta conta) {
		conta.setDescricao("Operação de "+conta.getTipoTransacao());
		conta.setDataTransacao(new Date());
		Conta contaBanco = dao.inserir(conta);
		return contaBanco;
	}

	public boolean validarCpf(String cpf) {
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
	

	public String validarHorarioPix(String tipoTransacao) {
        if (tipoTransacao.equals("Pix")) {
            LocalTime now = LocalTime.now();
            if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
                return("Operações de Pix só podem ser realizadas entre 06:00 e 22:00.");
            } 	
        } 
		return " Ok";
	}

	public String validarLimitePix(double valor){
		if(valor> 300.00){
			return("Valor ultrapassou o limite de R$300,00");
		} 
		return "Valor dentro do limite"; 
	}

	public String validarLimitesSaque(double saldo, double valor){
		if(valor>5000.){
			return "Valor de Saque inválido";
		}
		return "Saque válido";
	}

	public String validarSaldo(double saldo, double valor) {
		if (saldo < valor) {
			return "Saldo insuficiente para sacar esse valor";
		} else {
			return "Saque válido";
		}
	}


}

	


	

	