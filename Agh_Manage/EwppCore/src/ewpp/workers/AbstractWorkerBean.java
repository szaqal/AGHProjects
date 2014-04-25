package ewpp.workers;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ewpp.workers.IdGeneratorWorker.IdGeneratorWorkerLocal;

/**
 * Abstrakcyjny worker.
 * @author malczyk.pawel@gmail.com
 *
 */
public abstract class AbstractWorkerBean {
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractWorkerBean.class);

	/** Entity manager. */
	@PersistenceContext(unitName="ewpp" )
	private EntityManager entityManager;

	/** Worker identyfikatorow. */
	@EJB
	private IdGeneratorWorkerLocal idGenerator;

	/**
	 * Zwraca enityManagera dla dziedziczacych.
	 *
	 * @return zwraca eneitymanagera
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/** 
	 * Pobiera encje po jej identyfikatorze.
	 * @param uniqueId identyfikator encji.
	 * @param clazz klasa encji.
	 * @param <T> typ encji.
	 * @return encja.
	 */
	public <T> T retrieveEntityId(int uniqueId, Class<T> clazz) {
		return getEntityManager().find(clazz, uniqueId);
	}
	
	/**
	 * Zwraca pojedynczy rezultat jakiegosc zapytania.
	 * @param <T> typ encji
	 * @param query przygotowane zapytanie
	 * @param clazz klasa
	 * @return znaleziony element
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Query query, Class<T> clazz) {
		Object result = query.getSingleResult();
		try {
			return (T) result;
		} catch (ClassCastException e)  {
			LOG.error("Error getting single result");
			return null;
		}
	}

	/**
	 * Zapisuje encje.
	 * @param entity zapisywana encja
	 * @param <T> typ encji. 
	 */
	public <T> void saveEntity(T entity) {
		if (getEntityManager().contains(entity)){
			getEntityManager().merge(entity);
		} else {
			getEntityManager().persist(entity);
		}

	}


	/**
	 * Zwraca typowana liste dla zapytania.
	 * @param <T>
	 * 			Typ listy
	 * @param query
	 * 			Zapytanie
	 * @param clazz
	 *			klasa obiektow
	 * @return
	 * 			zwraca typowana liste rezutlatow
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Query query, Class<T> clazz) {
		return (List<T>) query.getResultList();
	}

	/**
	 * Zwraca worker identyfikator.
	 *
	 * @return worker generujacy identyfikaotory
	 */
	public final IdGeneratorWorker getIdGenerator() {
		return idGenerator;
	}
}
