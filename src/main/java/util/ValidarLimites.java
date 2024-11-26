package util;

import java.time.LocalTime;

public class ValidarLimites {
    	//Limite de R$ 300,00 para operações de pix.
	public static void validarLimitePix(Double valorOperacao) {
		if (valorOperacao > 300.00) {
			throw new IllegalArgumentException("Valor ultrapassou o limite de R$300,00");
		}
	}

	//Limite diário de saques de R$ 5.000,00.
	public static void validarLimitesSaque(Double valorOperacao) {
        if (valorOperacao > 5000.00) {
            throw new IllegalArgumentException("Valor ultrapassou o limite de R$5000,00 para saques.");
        }
    }

    //As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
	public static void validarHorarioPix() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
            throw new IllegalArgumentException("Operações de Pix só podem ser realizadas entre 06:00 e 22:00.");
        }
    }



	//O saldo (produto de depósitos - saques - pagamento (boleto) - pix (só pagamento)) não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
	public static void validarSaldo(double saldo, Double valorOperacao) {
		if (saldo < valorOperacao) {
			throw new IllegalArgumentException("Saldo insuficiente para realizar a operação.");
		}
		if (saldo < 100.00) {
			throw new IllegalArgumentException("Saldo menor que R$100,00. Faça um depósito.");
		}
	}
	

	//Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
	public static void verificarAlertaSaldoBaixo(double saldo) {
        if (saldo < 100.00) {
            System.out.println("Alerta: Saldo abaixo de R$100,00. Considere fazer um depósito.");
        }
    }

    //Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
	public static void detectarFraude(Double mediaGastos, Double valorOperacao) {
        if (mediaGastos == 0) {
			return; // Não bloqueia a transação se não houver histórico
		}
		if (valorOperacao > mediaGastos * 2) {
            throw new IllegalArgumentException("Operação suspeita detectada. Transação bloqueada.");
        }
    }
}
