package visao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controle.ContaControle;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) throws ParseException {
		ContaControle controleConta = new ContaControle();
		Conta conta= controleConta.buscarPorId(3L);
		Movimentacao movimentacao = new Movimentacao();
		double saldo = controleConta.consultarSaldo(conta.getId());
		System.out.println("Valor antes da operação: R$ " + saldo);
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("débito de 200,00");
		movimentacao.setTipoTransacao("débito");
		movimentacao.setValorOperacao(200.);
		movimentacao.setConta(conta);

		switch(movimentacao.getTipoTransacao()){
			case "saque":
				controleConta.realizarSaque(conta, movimentacao);
				break;
			case "depósito":
				controleConta.realizarDeposito(conta, movimentacao);
				break;
			case "pagamento":
				controleConta.realizarPagamento(conta, movimentacao);
				break;
			case "pix":
				controleConta.realizarPix(conta, movimentacao);
				break;
			case "débito":
				controleConta.debito(conta, movimentacao);
		}
		controleConta.tresMesesContaCorrente(conta);
        controleConta.calcularRendimentoPoupanca(conta);
		

		saldo = controleConta.consultarSaldo(conta.getId());
		System.out.println("Valor depois da operação R$ " + saldo);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date inicio = sdf.parse("01/02/2025"); 
		Date fim = sdf.parse("30/02/2025"); 

		List<Movimentacao> extrato = controleConta.consultarExtrato(conta.getId(), inicio, fim); 

		if (extrato != null && !extrato.isEmpty()) {
			System.out.println("Extrato do período de " + sdf.format(inicio) + " a " + sdf.format(fim) + ":");
			for (Movimentacao mov : extrato) {
				System.out.println("Data: " + mov.getDataTransacao() + " | Descrição: " + mov.getDescricao() + " | Valor: R$ " + mov.getValorOperacao());
			}
		} else {
			System.out.println("Nenhuma movimentação encontrada para o período especificado.");
		}

	}	
}
