package servico;


import java.util.Calendar;
import java.util.Date;

import dao.ContaDAO;
import entidade.Conta;
import entidade.TipoConta;

public class ContaServico {
    ContaDAO dao = new ContaDAO();

    public Conta inserir(Conta conta) {
        return dao.inserir(conta);
    }

    public void excluir(Conta conta) {
        if (dao.buscarPorId(conta.getId()) == null) {
            throw new IllegalArgumentException("Conta não encontrada para exclusão.");
        }
        dao.excluir(conta.getId());
    }

    public boolean validarLimiteOperacoes(Long id) {
        int totalOperacoes = dao.contarOperacoesPorDia(id);
        if (totalOperacoes >= 10) {
            throw new IllegalArgumentException("Limite diário de operações atingido.");
        }
        return true; 
    }

    public boolean adicionarConta(Long id) {
        int totalContas = dao.contarPorConta(id);
        if (totalContas >= 3) {
            throw new IllegalArgumentException("Limite de contas atingido.");
        }
        return true;
    }

    public Conta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    
    public Conta tresMesesContaCorrente(Conta conta){
        if (TipoConta.CONTA_CORRENTE.equals(conta.getTipoConta())){
            Long idConta = conta.getId();
            Calendar cal = Calendar.getInstance();
            Date fim = cal.getTime();
            cal.add(Calendar.MONTH, -3);
            Date inicio = cal.getTime();
            dao.limiteCreditoPreAprovado(idConta, inicio, fim);
            System.out.println("ContaCorrente");
            return conta;
        }
        return conta;
    }

    public Conta calcularRendimentoPoupanca(Conta conta) {
        if (TipoConta.CONTA_POUPANÇA.equals(conta.getTipoConta())) {
            Long idConta = conta.getId();
            Calendar cal = Calendar.getInstance();
            Date fim = cal.getTime();  
            cal.add(Calendar.MONTH, -1); 
            Date inicio = cal.getTime();  
            Double rendimento = dao.calcularRendimentoPoupanca(idConta, inicio, fim);
            System.out.println("Rendimento da Conta Poupança: " + rendimento);
            dao.atualizarSaldoConta(idConta, rendimento);  
            return conta; 
        }
        return conta;
    }
    
    
}
