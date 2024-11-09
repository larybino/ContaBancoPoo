package servico;


import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {
    ContaDAO dao = new ContaDAO();

    public Conta inserir(Conta conta) {
        return dao.inserir(conta);
    }

    public void excluir(Conta conta) {
        if (dao.buscarPorId(conta.getId()) == null) {
            throw new IllegalArgumentException("Conta não encontrada para exclusão.");
        }
        dao.excluir(conta.getId());
    }

    public boolean validarLimiteOperacoes(Long id) {
        int totalOperacoes = dao.contarOperacoesPorDia(id);
        if (totalOperacoes >= 10) {
            throw new IllegalArgumentException("Limite diário de operações atingido.");
        }
        return true; 
    }

    public boolean adicionarConta(Long id) {
        int totalContas = dao.contarPorConta(id);
        if (totalContas >= 3) {
            throw new IllegalArgumentException("Limite de contas atingido.");
        }
        return true;
    }

    public Conta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    
}
