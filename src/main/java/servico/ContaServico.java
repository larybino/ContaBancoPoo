package servico;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {
    ContaDAO dao = new ContaDAO();

    public Conta inserir(Conta conta) {
        conta.setCliente(conta.getCliente());
        conta.setDataAbertura(conta.getDataAbertura());
        conta.setTipoConta(null);
        Conta contaBanco = dao.inserir(conta);
        return contaBanco;
    }
}
