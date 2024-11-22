package dao;
import entidade.Conta;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContaDAO extends GenericDAO<Conta> {
    public ContaDAO() {
        super(Conta.class);
    }
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public int contarOperacoesPorDia(Long id) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(m) FROM Movimentacao m WHERE m.conta.id = :id_conta AND DATE(m.dataTransacao) = CURRENT_DATE", Long.class)
            .setParameter("id_conta", id)
            .getSingleResult();
        em.close();
        return count.intValue();
    }
    
    public int contarPorConta(Long id) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
            .setParameter("id_cliente", id)
            .getSingleResult();
        em.close();
        return count.intValue();
    }

    public Double calcularSaldo(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
            "SELECT COALESCE(SUM(m.valorOperacao), 0.0) FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
            .setParameter("id_conta", id)
            .getSingleResult();
    }

    public void atualizarSaldoConta(Long id, Double rendimento) {
        Double saldoAtual = calcularSaldo(id);
        Double novoSaldo = saldoAtual + rendimento;
        System.out.println("Novo saldo: " + novoSaldo);
    }
    

    public Double limiteCreditoPreAprovado(Long id, Date inicio, Date fim){
		EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
        return em.createQuery(
                "SELECT COALESCE(SUM(m.valorOperacao * 0.005), 0.0) " +  
                "FROM Movimentacao m WHERE m.conta.id = :idConta " +
                "AND m.dataTransacao BETWEEN :inicio AND :fim", Double.class)
            .setParameter("idConta", id)
            .setParameter("inicio", inicio)
            .setParameter("fim", fim)
            .getSingleResult();
    }   
}
