package controle;

import entidade.Movimentacao;
import servico.BaseServico;
import servico.MovimentacaoServico;

public class MovimentacaoControle implements BaseControle<Movimentacao> {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
		return servico.inserir(movimentacao);
	}

	@Override
	public BaseServico<Movimentacao> getServico() {
		return servico;
	}
}