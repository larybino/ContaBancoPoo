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
    
}
