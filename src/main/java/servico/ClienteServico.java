package servico;

import entidade.Cliente;
import dao.ClienteDAO;
import dao.GenericoDAO;
import util.ValidarCpf;

public class ClienteServico implements BaseServico<Cliente> {
    ClienteDAO dao = new ClienteDAO();

    public Cliente inserir(Cliente cliente) {
        if(!ValidarCpf.validar(cliente.getCpfCorrentista())){
            throw new IllegalArgumentException("CPF inválido");
        } 
        return dao.inserir(cliente);
    }

    // public void excluir(Cliente cliente){
    //     dao.excluir(cliente.getId());
    // }

    public boolean validarCliente(Cliente cliente){
        Cliente clienteValido= dao.buscarPorCpf(cliente.getCpfCorrentista());
        if (clienteValido == null || !clienteValido.getCpfCorrentista().equals(cliente.getCpfCorrentista())) {
            return false;
        }
        return true;
    }

    public Cliente buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    @Override
    public GenericoDAO<Cliente> getDAO() {
        return dao;
    }

    @Override
    public void excluir(Long id) {
        dao.excluir(id);
    }

    @Override
    public Cliente alterar(Cliente entidade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }
}
