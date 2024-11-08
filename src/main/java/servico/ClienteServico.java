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
        Cliente clienteBanco = dao.inserir(cliente);
        return clienteBanco;
    }

    
    
}
