package library.management.dao;

import library.management.models.Publication;
import library.management.utils.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicationDAO {

	private static final Logger logger = LoggerFactory.getLogger(PublicationDAO.class);
	
	public void save(Publication p) {		
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.persist(p);
			t.commit();
			logger.info("The Publication named '" + p.getTitle() + "' has been successifully added to the catalog");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error saving a " + p.getClass().getSimpleName() + " containing ISBN Code " + p.getISBNCode() + ". It's probably already in the catalog.");			
		} finally {
			em.close();
		}
	}
	
	public void delete(Publication p) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(em.contains(p) ? p : em.merge(p));
			t.commit();
			logger.info("The Publication named '" + p.getTitle() + "' has been successifully removed from the catalog");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error while deleting the " + p.getClass().getSimpleName() + " named '" + p.getTitle() + "'");
		} finally {
			em.close();
		}
	}
	
	public Publication getByISBNCode(String s) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getByISBNCode");
			query.setParameter("p", s);
			return (Publication)query.getResultList().get(0);			
		} finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Publication> getByYear(int i) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getByYear");
			query.setParameter("p", i);
			return (List<Publication>)query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	//it works with partials also
	@SuppressWarnings("unchecked")
	public List<Publication> getByAuthor(String s) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getByAuthor");
			query.setParameter("p", s.toLowerCase());
			return (List<Publication>)query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	//it works with partials also
	@SuppressWarnings("unchecked")
	public List<Publication> getByTitle(String s) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getByTitle");
			query.setParameter("p", s.toLowerCase());
			return (List<Publication>)query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	public void deleteByISBNCode(String s) {
		delete(getByISBNCode(s));
	}
	
	public void printList(List<Publication> list) {
		for(Publication p : list) logger.info(p.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Publication> getCatalog() {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			Query query = em.createNamedQuery("getCatalog");
			return (List<Publication>)query.getResultList();			
		} finally {
			em.close();
		}
	}
	
	
	
}
