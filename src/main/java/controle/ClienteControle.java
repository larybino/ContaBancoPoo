package controle;

import entidade.Cliente;
import servico.ClienteServico;

public class ClienteControle {
    ClienteServico servico = new ClienteServico();

    public Cliente inserir(Cliente cliente) {
		return servico.inserir(cliente);
	}

    public void excluir(Cliente cliente) {
        servico.excluir(cliente);
    }

    public boolean validarCliente(Cliente cliente){
        return servico.validarCliente(cliente);
    }
}
