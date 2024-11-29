package dao;
import entidade.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class ClienteDAO extends GenericoDAO<Cliente> {
    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorCpf(String cpf){
		EntityManager em = getEntityManager();
		//hql: hibernate query language
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfCorrentista = :cpf");
        query.setParameter("cpf", cpf);
		Cliente cliente = (Cliente) query.getSingleResult();
		em.close();
		return cliente;
	}
}
