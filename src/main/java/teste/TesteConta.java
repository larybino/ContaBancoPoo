package teste;

import entidade.Conta;
import entidade.TipoConta;

public class TesteConta {
    public static void main(String[] args){
        Conta conta= new Conta();
        conta.setTipoConta(TipoConta.CONTA_CORRENTE);
    }
}
