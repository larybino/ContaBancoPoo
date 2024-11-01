package dao;

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
		em.close();
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


	public Movimentacao buscarPorId(Long id) {
		EntityManager em = emf.createEntityManager();
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		em.close();
		return movimentacao;
		// return em.find(Movimentacao.class, id);
	}
}
