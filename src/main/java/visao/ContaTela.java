package visao;

import java.util.Date;

import controle.ContaControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.TipoConta;

public class ContaTela {
    public static void main(String[] args) {
        ContaControle controle = new ContaControle();
        Cliente cliente = new Cliente();
        Conta conta = new Conta();
        conta.setDataAbertura(new Date());
        conta.setTipoConta(TipoConta.CONTA_CORRENTE);
        cliente.setNomeCorrentista("Laryssa");
        cliente.setCpfCorrentista("03227481510");
        conta.setCliente(cliente);
        controle.inserir(conta);
    }
}
