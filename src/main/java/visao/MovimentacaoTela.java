package visao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controle.ContaControle;
import controle.MovimentacaoControle;
import entidade.Conta;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) throws ParseException {
		MovimentacaoControle controle = new MovimentacaoControle();
		ContaControle controleConta = new ContaControle();
		Conta conta= controleConta.buscarPorId(1L);
		Movimentacao movimentacao = new Movimentacao();
		double saldo = controle.consultarSaldo(conta.getId());
		System.out.println("Valor antes da operação: R$ " + saldo);
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("debito de 10,00");
		movimentacao.setTipoTransacao("débito");
		movimentacao.setValorOperacao(10.);
		movimentacao.setConta(conta);

		switch(movimentacao.getTipoTransacao()){
			case "saque":
				controle.realizarSaque(movimentacao, conta);
				break;
			case "depósito":
				controle.realizarDeposito(movimentacao);
				break;
			case "pagamento":
				controle.realizarPagamento(movimentacao, conta);
				break;
			case "pix":
				controle.realizarPix(movimentacao, conta);
				break;
			case "débito":
				controle.debito(movimentacao, conta);
		}

		saldo = controle.consultarSaldo(conta.getId());
		System.out.println("Valor depois da operação R$ " + saldo);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date inicio = sdf.parse("01/11/2024"); 
		Date fim = sdf.parse("31/11/2024");    
		List<Movimentacao> extrato = controle.consultarExtrato(conta.getId(), inicio, fim);
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
