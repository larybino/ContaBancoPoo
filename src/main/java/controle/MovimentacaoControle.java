package controle;

import java.util.Date;
import java.util.List;

import entidade.Conta;
import entidade.Movimentacao;
import servico.MovimentacaoServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
		return servico.inserir(movimentacao);
	}

	public Movimentacao realizarSaque(Movimentacao movimentacao, Conta conta){
		return servico.realizarSaque(movimentacao, conta);
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao){
		return servico.realizarDeposito(movimentacao);
	}

	public Movimentacao realizarPagamento(Movimentacao movimentacao, Conta conta){
		return servico.realizarPagamento(movimentacao, conta);
	}

	public Movimentacao realizarPix(Movimentacao movimentacao, Conta conta){
		return servico.realizarPix(movimentacao, conta);
	}
	
	public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
        try {
            return servico.consultarExtrato(id, inicio, fim);
        } catch (Exception e) {
            System.err.println("Erro ao consultar extrato: " + e.getMessage());
            return null;
        }
    }

	public double consultarSaldo(Long id) {
		return servico.consultarSaldo(id);
	}
}