package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidade.Movimentacao;

public class MovimentacaoDAO {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

	public Movimentacao inserir(Movimentacao movimentacao) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(movimentacao); 
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

	public List<Movimentacao> buscarPorCpf(String cpf) {
		EntityManager em = emf.createEntityManager();
		// Specify the type in the createQuery method
		TypedQuery<Movimentacao> query = em.createQuery("from Movimentacao where cpfCorrentista = :cpf", Movimentacao.class);
		query.setParameter("cpf", cpf);
		List<Movimentacao> movimentacoes = query.getResultList();
		em.close();
		return movimentacoes;
	}

	public List<Double> buscarGastosPorCpf(String cpf) {
		EntityManager em = emf.createEntityManager();
		List<Double> gastos = new ArrayList<>();
		try {
			gastos = em.createQuery("SELECT valorOperacao FROM Movimentacao WHERE cpfCorrentista = :cpf", Double.class)
					.setParameter("cpf", cpf)
					.getResultList();
		} finally {
			em.close();
		}
		return gastos;
	}

	public Double calcularMediaGastos(String cpf) {
		EntityManager em = emf.createEntityManager();
		Double mediaGastos = em.createQuery(
			"SELECT AVG(valorOperacao) FROM Movimentacao WHERE cpfCorrentista = :cpf", Double.class)
			.setParameter("cpf", cpf)
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

	public List <Movimentacao> buscaTipoTransacao(String tipoTransacao){
		EntityManager em = emf.createEntityManager();
		TypedQuery<Movimentacao> query = em.createQuery("from Movimentacao where tipoTransacao = :tipo", Movimentacao.class);
		query.setParameter("tipo", tipoTransacao);
		List<Movimentacao> movimentacaos=query.getResultList();
		em.close();
		return movimentacaos;
	}

	public Double calcularSaldo(String cpf) {
		EntityManager em = emf.createEntityManager();
		Double saldo = em.createQuery("SELECT COALESCE(SUM(valorOperacao), 0.0) FROM Movimentacao WHERE cpfCorrentista = :cpf", Double.class)
						 .setParameter("cpf", cpf)
						 .getSingleResult();
		em.close();
		return saldo;
	}
	

	public List<Movimentacao> buscarPorData(String cpf, Date inicio, Date fim) {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Movimentacao> query = em.createQuery(
			"FROM Movimentacao WHERE cpfCorrentista = :cpf AND dataTransacao BETWEEN :inicio AND :fim",
			Movimentacao.class
		);
		query.setParameter("cpf", cpf);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		List<Movimentacao> movimentacoes = query.getResultList();
		em.close();
		return movimentacoes;
	}
	
	public int contarOperacoesPorDia(String cpf) {
		EntityManager em = emf.createEntityManager();
		Long count = em.createQuery("SELECT COUNT(m) FROM Movimentacao m WHERE cpfCorrentista = :cpf AND DATE(dataTransacao) = CURRENT_DATE", Long.class)
					   .setParameter("cpf", cpf)
					   .getSingleResult();
		em.close();
		return count.intValue();
	}
	
}
