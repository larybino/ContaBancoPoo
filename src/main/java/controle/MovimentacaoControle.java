package controle;

import java.util.Date;
import java.util.List;

import entidade.Movimentacao;
import servico.MovimentacaoServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
		return servico.inserir(movimentacao);
	}

	public Movimentacao realizarSaque(Movimentacao movimentacao){
		return servico.realizarSaque(movimentacao);
	}

	public Movimentacao realizarDeposito(Movimentacao movimentacao){
		return servico.realizarDeposito(movimentacao);
	}

	public Movimentacao realizarPagamento(Movimentacao movimentacao){
		return servico.realizarPagamento(movimentacao);
	}

	public Movimentacao realizarPix(Movimentacao movimentacao){
		return servico.realizarPix(movimentacao);
	}
	
	public List<Movimentacao> consultarExtrato(String cpf, Date inicio, Date fim) {
        try {
            return servico.consultarExtrato(cpf, inicio, fim);
        } catch (Exception e) {
            System.err.println("Erro ao consultar extrato: " + e.getMessage());
            return null;
        }
    }

	public double consultarSaldo(String cpfCorrentista) {
		return servico.consultarSaldo(cpfCorrentista);
	}
}