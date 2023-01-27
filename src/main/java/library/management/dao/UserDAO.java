package library.management.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import library.management.models.User;
import library.management.utils.JpaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public void save(User u) {			
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			u.setCard(generateLibraryCard(u));
			t.begin();
			em.persist(u);
			t.commit();
			logger.info("The User named '" + u.getName() + " " + u.getSurname() +"' has been successifully added to the catalog");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error while saving the " + u.getClass().getSimpleName() + " with name " + u.getName() + " " + u.getSurname());			
		} finally {
			em.close();
		}
	}
	
	public void refresh(User u) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			em.refresh(u);
		} finally {
			em.close();
		}
	}
	
	public User getById(int id) {		
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			return em.find(User.class, id);
		} finally {
			em.close();
		}
	}
	
	public void delete(User u) {
		EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			em.remove(em.contains(u) ? u : em.merge(u));
			t.commit();
			logger.info("The User namer '" + u.getName() + " " + u.getSurname() + "' has been successifully removed from the catalog");
		} catch (Exception ex) {
			em.getTransaction().rollback();
			logger.error("Error deleting the specified: " + u.getClass().getSimpleName());
		} finally {
			em.close();
		}
	}
	
	public void printList(List<User> list) {
		for(User u : list) logger.info(u.toString());
	}
	
	public String generateLibraryCard(User u) {
		Random r = new Random();
		return String.valueOf(LocalDate.now().getMonth().name().charAt(0)) + LocalDate.now().getDayOfYear() 
				+ String.valueOf(u.getSurname().charAt(0)) + u.getBirthdate().getDayOfYear() + String.valueOf(u.getName().charAt(0)) 
				+ u.getName().length() + String.valueOf(u.getSurname().charAt(2)).toUpperCase() + (1000 * r.nextInt(10) + r.nextInt(1000));
	}
}
