package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidade.Movimentacao;

public class MovimentacaoDAO {
	private ContaDAO contaDAO; 
	
	public MovimentacaoDAO() {
        contaDAO = new ContaDAO(); 
    }

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Movimentacao inserir(Movimentacao movimentacao) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(movimentacao); 
		em.getTransaction().commit();
		return movimentacao;
	}

	public Movimentacao alterar(Movimentacao movimentacao) {
		Movimentacao contaBanco = null;
		if (movimentacao.getId() != null) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			contaBanco = buscarPorId(movimentacao.getId());

			if (contaBanco != null) {
				contaBanco.setDescricao(movimentacao.getDescricao());
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
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		if (movimentacao != null) {
			em.remove(movimentacao);
		}
		em.getTransaction().commit();
		em.close();
	}

	public List<Movimentacao> listarTodos() {
		EntityManager em = emf.createEntityManager();
		// hql: hibernate query language
		List<Movimentacao> movimentacaoes = em.createQuery("from Movimentacao", Movimentacao.class).getResultList();
		em.close();
		return movimentacaoes;
	}
	// buscar todas as movimentacaoes de acordo com o CPF
	// buscar todas as movimentacaoes de acordo com o tipo da transação


	public Double calcularMediaGastos(Long id) {
		EntityManager em = emf.createEntityManager();
		Double mediaGastos = em.createQuery(
			"SELECT AVG(m.valorOperacao) FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
			.setParameter("id_conta", id)
			.getSingleResult();
		em.close();
		return mediaGastos != null ? mediaGastos : 0.0;
	}
	
	public Movimentacao buscarPorId(Long id) {
		EntityManager em = emf.createEntityManager();
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		em.close();
		return movimentacao;
	}

	public Double calcularSaldo(Long id) {
		return contaDAO.calcularSaldo(id);
	}
	
	public List<Movimentacao> buscarPorData(Long id, Date inicio, Date fim) {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Movimentacao> query = em.createQuery(
			"FROM Movimentacao m WHERE m.conta.id = :id_conta AND m.dataTransacao BETWEEN :inicio AND :fim",
			Movimentacao.class
		);
		query.setParameter("id_conta", id);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		List<Movimentacao> movimentacoes = query.getResultList();
		em.close();
		return movimentacoes;
	}

	public Double cashback(Long id, Date inicio, Date fim){
		EntityManager em = emf.createEntityManager();
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
