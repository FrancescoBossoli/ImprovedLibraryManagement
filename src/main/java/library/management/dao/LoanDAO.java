package library.management.dao;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import library.management.models.Loan;
import library.management.utils.JpaUtil;

public class LoanDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanDAO.class);

	public void save(Loan l) {		
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(l);
			t.commit();
			logger.info("The Loan for the Publication '" + l.getPublication().getTitle() + "' has been successifully recorded");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error while saving the " + l.getClass().getSimpleName() + " for the User with name " + l.getUser().getName() + " " + l.getUser().getSurname());			
		} finally {
			em.close();
		}
	}
	
	public void refresh(Loan l) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			em.refresh(l);
		} finally {
			em.close();
		}
	}
	
	public Loan getById(int id) {		
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			return em.find(Loan.class, id);
		} finally {
			em.close();
		}
	}
	
	public void delete(Loan l) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(em.contains(l) ? l : em.merge(l));
			t.commit();
			logger.info("The Loan for the Publication '" + l.getPublication() + "' has been successifully removed from the catalog");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error deleting the specified: " + l.getClass().getSimpleName());
		} finally {
			em.close();
		}
	}
		
	@SuppressWarnings("unchecked")
	public List<Loan> getByCard(String c) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getByCard");
			query.setParameter("c", c);
			return query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Loan> getExpiredLoans() {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getExpiredLoans");
			query.setParameter("d", LocalDate.now());
			return query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	public void printList(List<Loan> list) {
		for(Loan l : list) logger.info(l.toString());
	}
}
