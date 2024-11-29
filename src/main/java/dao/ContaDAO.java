package dao;
import entidade.Conta;
import entidade.Movimentacao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ContaDAO extends GenericoDAO<Conta> {
    public ContaDAO() {
        super(Conta.class);
    }

    public int contarOperacoesPorDia(Long id) {
        EntityManager em = getEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(m) FROM Movimentacao m WHERE m.conta.id = :id_conta AND DATE(m.dataTransacao) = CURRENT_DATE", Long.class)
            .setParameter("id_conta", id)
            .getSingleResult();
        em.close();
        return count.intValue();
    }
    
    public int contarPorConta(Long id) {
        EntityManager em = getEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
            .setParameter("id_cliente", id)
            .getSingleResult();
        em.close();
        return count.intValue();
    }

    	public List<Movimentacao> buscarPorData(Long id, Date inicio, Date fim) {
		EntityManager em = getEntityManager();
		TypedQuery<Movimentacao> query = em.createQuery(
			"FROM Movimentacao m WHERE m.conta.id = :id_conta AND m.dataTransacao BETWEEN :inicio AND :fim",
			Movimentacao.class
		);
		query.setParameter("id_conta", id);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		List<Movimentacao> movimentacaos = query.getResultList();
		em.close();
		return movimentacaos;
	}

    public Double calcularMediaGastos(Long id) {
		EntityManager em = getEntityManager();
		Double mediaGastos = em.createQuery(
			"SELECT AVG(m.valorOperacao) FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
			.setParameter("id_conta", id)
			.getSingleResult();
		em.close();
		return mediaGastos != null ? mediaGastos : 0.0;
	}

    public Double calcularSaldo(Long id) {
        EntityManager em = getEntityManager();
        return em.createQuery(
            "SELECT COALESCE(SUM(m.valorOperacao), 0.0) FROM Movimentacao m WHERE m.conta.id = "+id, Double.class)
            .getSingleResult();
    }

    public void atualizarSaldoConta(Long id, Double rendimento) {
        Double saldoAtual = calcularSaldo(id);
        Double novoSaldo = saldoAtual + rendimento;
        System.out.println("Novo saldo: " + novoSaldo);
    }
    

    public Double limiteCreditoPreAprovado(Long id, Date inicio, Date fim){
		EntityManager em = getEntityManager();
        return em.createQuery(
            		"SELECT COALESCE(SUM(m.valorOperacao) / COUNT(m), 0.0) " +
                "FROM Movimentacao m WHERE m.conta.id = :idConta " +
                "AND m.dataTransacao BETWEEN :inicio AND :fim", Double.class)
            .setParameter("idConta", id)
            .setParameter("inicio", inicio)
            .setParameter("fim", fim)
            .getSingleResult();
	}

    public Double calcularRendimentoPoupanca(Long id, Date inicio, Date fim) {
        EntityManager em = getEntityManager();
        return em.createQuery(
                "SELECT COALESCE(SUM(m.valorOperacao * 0.005), 0.0) " +  
                "FROM Movimentacao m WHERE m.conta.id = :idConta " +
                "AND m.dataTransacao BETWEEN :inicio AND :fim", Double.class)
            .setParameter("idConta", id)
            .setParameter("inicio", inicio)
            .setParameter("fim", fim)
            .getSingleResult();
    }   

    public Double cashback(Long id, Date inicio, Date fim){
		EntityManager em = getEntityManager();
        return em.createQuery(
            		"SELECT COALESCE(SUM(m.valorOperacao * 0.002), 0.0) " +
                    "FROM Movimentacao m WHERE m.conta.id = :idConta " +
                    "AND m.tipoTransacao = :tipoDebito " +
                    "AND m.dataTransacao BETWEEN :inicio AND :fim", Double.class)
                .setParameter("idConta", id)
                .setParameter("tipoDebito", "débito") 
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
            .getSingleResult();
    
	}
}
