package dao;
import entidade.Cliente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class ClienteDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente inserir(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
        return cliente;
    }

    public Cliente alterar(Cliente cliente) {
        Cliente clienteBanco = null;
        if (cliente.getId() != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            clienteBanco = buscarPorId(cliente.getId());

            if (clienteBanco != null) {
                em.merge(clienteBanco);
            }

            em.getTransaction().commit();
            em.close();
        }
        return clienteBanco;
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente != null) {
            em.remove(cliente);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Cliente> listarTodos() {
        EntityManager em = emf.createEntityManager();
        // hql: hibernate query language
        List<Cliente> movimentacaoes = em.createQuery("from Cliente", Cliente.class).getResultList();
        em.close();
        return movimentacaoes;
    }
    // buscar todas as movimentacaoes de acordo com o CPF
    // buscar todas as movimentacaoes de acordo com o tipo da transação

    public Cliente buscarPorCpf(String cpf){
		EntityManager em = emf.createEntityManager();
		//hql: hibernate query language
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfCorrentista = :cpf");
        query.setParameter("cpf", cpf);
		Cliente cliente = (Cliente) query.getSingleResult();
		em.close();
		return cliente;
	}

    public Cliente buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        em.close();
        return cliente;
    }
    
}
