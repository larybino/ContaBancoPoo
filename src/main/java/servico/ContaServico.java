package servico;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.ContaDAO;
import dao.MovimentacaoDAO;
import entidade.Conta;
import entidade.Movimentacao;
import entidade.TipoConta;
import util.ValidarDatas;
import util.ValidarLimites;

public class ContaServico {
    ContaDAO dao = new ContaDAO();
    MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

    public Conta inserir(Conta conta) {
        return dao.inserir(conta);
    }

    public void excluir(Conta conta) {
        if (dao.buscarPorId(conta.getId()) == null) {
            throw new IllegalArgumentException("Conta não encontrada para exclusão.");
        }
        dao.excluir(conta.getId());
    }


    public Movimentacao realizarSaque(Conta conta, Movimentacao movimentacao) {
	ValidarLimites.detectarFraude(dao.calcularMediaGastos(conta.getId()), movimentacao.getValorOperacao());
	double saldo = dao.calcularSaldo(conta.getId());
	System.out.println("Saldo antes do saque: R$ " + saldo);
	ValidarLimites.validarSaldo(saldo, movimentacao.getValorOperacao());
	ValidarLimites.validarLimitesSaque(movimentacao.getValorOperacao());
	double tarifa = 2.00;
	double valorFinal = movimentacao.getValorOperacao() + tarifa;
	movimentacao.setValorOperacao(-valorFinal);
	Movimentacao result = movimentacaoDAO.inserir(movimentacao);
	saldo = dao.calcularSaldo(conta.getId()); 
	System.out.println("Saldo após o saque: R$ " + saldo);
	ValidarLimites.verificarAlertaSaldoBaixo(saldo);
	return result;
}
	
	public Movimentacao realizarDeposito(Conta conta, Movimentacao movimentacao) {
        ValidarLimites.detectarFraude(dao.calcularMediaGastos(conta.getId()), movimentacao.getValorOperacao());
		return movimentacaoDAO.inserir(movimentacao);
	}
	
	public Movimentacao realizarPagamento(Conta conta, Movimentacao movimentacao) {
        ValidarLimites.detectarFraude(dao.calcularMediaGastos(conta.getId()), movimentacao.getValorOperacao());
		double saldo = dao.calcularSaldo(conta.getId());
		ValidarLimites.validarSaldo(saldo, movimentacao.getValorOperacao());
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa)); 
		ValidarLimites.verificarAlertaSaldoBaixo(saldo);
		return movimentacaoDAO.inserir(movimentacao);
	}
	
	public Movimentacao realizarPix(Conta conta, Movimentacao movimentacao) {
		ValidarLimites.validarHorarioPix();
        ValidarLimites.detectarFraude(dao.calcularMediaGastos(conta.getId()), movimentacao.getValorOperacao());
		double saldo = dao.calcularSaldo(conta.getId());
		ValidarLimites.validarLimitePix(movimentacao.getValorOperacao());
		double tarifa = 5.00;
		movimentacao.setValorOperacao(- (movimentacao.getValorOperacao() + tarifa));
		ValidarLimites.verificarAlertaSaldoBaixo(saldo);
		return movimentacaoDAO.inserir(movimentacao);
	}

	public Movimentacao debito(Conta conta, Movimentacao movimentacao){
        ValidarLimites.detectarFraude(dao.calcularMediaGastos(conta.getId()), movimentacao.getValorOperacao());
		double saldo = dao.calcularSaldo(conta.getId());
		ValidarLimites.validarSaldo(saldo, movimentacao.getValorOperacao());
		Date inicio = ValidarDatas.getInicioMesAnterior();  
        Date fim = ValidarDatas.getFimMesAnterior();        
        double valorCashback = cashback(conta.getId(), inicio, fim);
		saldo += valorCashback;
		double valorFinal = movimentacao.getValorOperacao();
        movimentacao.setValorOperacao(-valorFinal);
        Movimentacao result = movimentacaoDAO.inserir(movimentacao);
        saldo = dao.calcularSaldo(conta.getId());
		ValidarLimites.verificarAlertaSaldoBaixo(saldo);
        return result;
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
    
    public List<Movimentacao> consultarExtrato(Long id, Date inicio, Date fim) {
		return dao.buscarPorData(id, inicio, fim);
	}

	public double cashback(Long id, Date inicio, Date fim){
		return dao.cashback(id, inicio, fim);
	}

    	
	public double consultarSaldo(Long id) {
		return dao.calcularSaldo(id);
	}
    
}
