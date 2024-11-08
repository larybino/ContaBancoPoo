package servico;

import entidade.Cliente;
import dao.ClienteDAO;
import util.ValidarCpf;

public class ClienteServico {
    ClienteDAO dao = new ClienteDAO();

    public Cliente inserir(Cliente cliente) {
        if(!ValidarCpf.validar(cliente.getCpfCorrentista())){
            throw new IllegalArgumentException("CPF inválido");
        } 
        return dao.inserir(cliente);
    }

    public void excluir(Cliente cliente){
        dao.excluir(cliente.getId());
    }

    
    
}
