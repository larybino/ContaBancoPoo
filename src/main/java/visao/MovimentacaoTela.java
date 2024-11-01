package visao;

import java.util.Date;

import controle.MovimentacaoControle;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {
		MovimentacaoControle controle = new MovimentacaoControle();
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setCpfCorrentista("03227481510");
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("pagamento de 100,00 no dia 03/10/24");
		movimentacao.setNomeCorrentista("Laryssa");
		movimentacao.setTipoTransacao("pagamento");
		movimentacao.setValorOperacao(100.);	

		double saldo = controle.consultarSaldo(movimentacao.getCpfCorrentista());
		System.out.println(": R$ " + saldo);


		switch(movimentacao.getTipoTransacao()){
			case "saque":
				controle.realizarSaque(movimentacao);
				break;
			case "depósito":
				controle.realizarDeposito(movimentacao);
				break;
			case "pagamento":
				controle.realizarPagamento(movimentacao);
				break;
			case "pix":
				controle.realizarPix(movimentacao);
				break;
		}
	}

	
}
