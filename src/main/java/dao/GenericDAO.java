package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GenericDAO<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass; 
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public T inserir(T entidade) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
            return entidade;
        } finally {
            em.close();        
        }
    }

    public T alterar(T entidade) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            T enidadeMerge = em.merge(entidade);
            em.getTransaction().commit();
            return enidadeMerge;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entidade = em.find(entityClass, id);
            if (entidade != null) {
                em.remove(entidade);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }        
    }

    public List<T> listarTodos() {
        EntityManager em = emf.createEntityManager();
        // hql: hibernate query language
        List<T> lista = em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        em.close();
        return lista;
    }
    // buscar todas as movimentacaoes de acordo com o CPF
    // buscar todas as movimentacaoes de acordo com o tipo da transação

    public T buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        T entidade = em.find(entityClass, id);
        em.close();
        return entidade;
    }
}
