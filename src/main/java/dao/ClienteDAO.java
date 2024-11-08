package dao;
import entidade.Cliente;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class ClienteDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente inserir(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        return cliente;
    }

    public Cliente alterar(Cliente cliente) {
        Cliente contaBanco = null;
        if (cliente.getId() != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            contaBanco = buscarPorId(cliente.getId());

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

    public List<Cliente> buscarPorCpf(String cpf) {
        EntityManager em = emf.createEntityManager();
        // Specify the type in the createQuery method
        TypedQuery<Cliente> query = em.createQuery("from Cliente where cpfCorrentista = :cpf",
                Cliente.class);
        query.setParameter("cpf", cpf);
        List<Cliente> movimentacoes = query.getResultList();
        em.close();
        return movimentacoes;
    }

    public Cliente buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        em.close();
        return cliente;
    }
    
}
