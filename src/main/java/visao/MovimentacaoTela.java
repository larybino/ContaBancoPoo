package visao;

import java.util.Date;

import controle.MovimentacaoControle;
import entidade.Movimentacao;

public class MovimentacaoTela {

	public static void main(String[] args) {
		MovimentacaoControle controle = new MovimentacaoControle();
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setCpfCorrentista("04425225112");
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("Depósito de 500,00 no dia 03/10/24");
		movimentacao.setNomeCorrentista("José");
		movimentacao.setTipoTransacao("depósito");
		movimentacao.setValorOperacao(500.);
		
		controle.inserir(movimentacao);
	}

}
