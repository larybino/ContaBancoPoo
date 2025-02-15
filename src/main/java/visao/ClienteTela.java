package visao;


import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        ClienteControle controle = new ClienteControle();
        cliente.setNomeCorrentista("Mayra");
        cliente.setCpfCorrentista("24745903820");
        controle.inserir(cliente);
    }
    
}
