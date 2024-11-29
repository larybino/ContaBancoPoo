package controle;

import entidade.Cliente;
import servico.BaseServico;
import servico.ClienteServico;

public class ClienteControle implements BaseControle<Cliente> {
    ClienteServico servico = new ClienteServico();

    public Cliente inserir(Cliente cliente) {
		return servico.inserir(cliente);
	}

    // public void excluir(Long cliente) {
    //     servico.excluir(cliente);
    // }

    public boolean validarCliente(Cliente cliente){
        return servico.validarCliente(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return servico.buscarPorId(id);
    }

    @Override
    public BaseServico<Cliente> getServico() {
        return servico;
    }
}
