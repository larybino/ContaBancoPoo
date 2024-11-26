package controle;

import java.util.Date;
import java.util.List;

import entidade.Conta;
import entidade.Movimentacao;
import servico.ContaServico;

public class ContaControle {
    ContaServico servico = new ContaServico();

    public Conta inserir(Conta conta) {
		return servico.inserir(conta);
	}

    public void excluir(Conta conta) {
        servico.excluir(conta);
    }

    public boolean validarLimiteOperacoes(Long id){
        return servico.validarLimiteOperacoes(id);
    }
    
    public boolean adicionarConta(Long id){
        return servico.adicionarConta(id);
    }

    public Conta buscarPorId(Long id) {
        return servico.buscarPorId(id);
    }

    public Movimentacao realizarSaque(Conta conta, Movimentacao movimentacao){
		return servico.realizarSaque(conta, movimentacao);
	}

	public Movimentacao realizarDeposito(Conta conta, Movimentacao movimentacao){
		return servico.realizarDeposito(conta, movimentacao);
	}

	public Movimentacao realizarPagamento(Conta conta, Movimentacao movimentacao){
		return servico.realizarPagamento(conta, movimentacao);
	}

	public Movimentacao realizarPix(Conta conta, Movimentacao movimentacao){
		return servico.realizarPix(conta, movimentacao);
	}

	public Movimentacao debito(Conta conta, Movimentacao movimentacao){
		return servico.debito(conta, movimentacao);
	}

    public Conta tresMesesContaCorrente(Conta conta){
        return servico.tresMesesContaCorrente(conta);
    }

    public Conta calcularRendimentoPoupanca(Conta conta){
        return servico.calcularRendimentoPoupanca(conta);
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
