package visao;

import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {
    public static void main(String[] args) {
        ClienteControle controle = new ClienteControle();

        Cliente cliente = new Cliente();
        cliente.setNomeCorrentista("Laryssa");
        cliente.setCpfCorrentista("03227481510");
        controle.inserir(cliente);
        
    }
    
    
}
