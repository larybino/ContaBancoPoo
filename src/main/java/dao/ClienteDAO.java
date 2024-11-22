package dao;
import entidade.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class ClienteDAO extends GenericDAO<Cliente> {
    public ClienteDAO() {
        super(Cliente.class);
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente buscarPorCpf(String cpf){
		EntityManager em = emf.createEntityManager();
		//hql: hibernate query language
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfCorrentista = :cpf");
        query.setParameter("cpf", cpf);
		Cliente cliente = (Cliente) query.getSingleResult();
		em.close();
		return cliente;
	}
}
