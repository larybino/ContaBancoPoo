package dao;
import entidade.Conta;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContaDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Conta inserir(Conta conta) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(conta);
        em.getTransaction().commit();
        return conta;
    }

    public Conta alterar(Conta conta) {
        Conta contaBanco = null;
        if (conta.getId() != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            contaBanco = buscarPorId(conta.getId());

            if (contaBanco != null) {
                em.merge(contaBanco);
            }

            em.getTransaction().commit();
            em.close();
        }
        return contaBanco;
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Conta conta = em.find(Conta.class, id);
        if (conta != null) {
            em.remove(conta);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Conta> listarTodos() {
        EntityManager em = emf.createEntityManager();
        // hql: hibernate query language
        List<Conta> movimentacaoes = em.createQuery("from Conta", Conta.class).getResultList();
        em.close();
        return movimentacaoes;
    }
    // buscar todas as movimentacaoes de acordo com o CPF
    // buscar todas as movimentacaoes de acordo com o tipo da transação


    public Conta buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Conta conta = em.find(Conta.class, id);
        em.close();
        return conta;
    }

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
