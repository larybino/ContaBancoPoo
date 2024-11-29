package servico;

import java.util.Date;

import dao.GenericoDAO;
import dao.MovimentacaoDAO;
import entidade.Movimentacao;

public class MovimentacaoServico  implements BaseServico<Movimentacao> {
	MovimentacaoDAO dao = new MovimentacaoDAO();
	
	@Override
	public Movimentacao inserir(Movimentacao movimentacao) {
		movimentacao.setDescricao("Operação de "+movimentacao.getTipoTransacao());
		movimentacao.setDataTransacao(new Date());
		Movimentacao movimentacaoBanco = dao.inserir(movimentacao);
		return movimentacaoBanco;
	}


	@Override
	public GenericoDAO<Movimentacao> getDAO() {
		return dao;
	}


	@Override
	public Movimentacao alterar(Movimentacao entidade) {
		throw new UnsupportedOperationException("Unimplemented method 'alterar'");
	}


	@Override
	public void excluir(Long id) {
		throw new UnsupportedOperationException("Unimplemented method 'excluir'");
	}
}	