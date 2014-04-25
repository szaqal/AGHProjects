package core.workers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import core.model.AbstractEntity;
import core.utils.ListUtils;

/**
 * Common base class for all workers.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public abstract class AbstractWorkerBean {

	/**
	 * Persistence unit.
	 * @uml.property  name="entityManager"
	 */
	@PersistenceContext(unitName = "procPU")
	private EntityManager entityManager;

	/**
	 * Getter.
	 * @return  the entityManager
	 * @uml.property  name="entityManager"
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Save new entity.
	 * 
	 * @param <T>
	 *            type of saved object
	 * @param obj
	 *            object
	 * @return uniqueId
	 */
	protected <T extends AbstractEntity> T save(T obj) {
		if (entityManager.contains(obj) || obj.getUniqueId() > 0) {
			obj = entityManager.merge(obj);
		} else {
			entityManager.persist(obj);

		}
		return obj;
	}

	/**
	 * Marks entiry as removed.
	 * 
	 * @param <T>
	 *            type of entity
	 * @param obj
	 *            abject being delete.
	 */
	protected <T extends AbstractEntity> void remove(T obj) {
		entityManager.remove(obj);
	}

	/**
	 * Retrieves entity by unique_id.
	 * 
	 * @param <T>
	 *            type of entity
	 * @param uniqueId
	 *            entity identifier
	 * @param clazz
	 *            class of entity
	 * @return found entity
	 */
	protected <T extends AbstractEntity> T find(int uniqueId, Class<T> clazz) {
		return getEntityManager().find(clazz, uniqueId);
	}

	/**
	 * Returns single result from query.
	 * 
	 * @param <T>
	 *            entity type
	 * @param query
	 *            prepared query
	 * @param clazz
	 *            class
	 * @return found entity
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Query query, Class<T> clazz) {
		Object result = query.getSingleResult();
		try {
			return (T) result;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns typed list.
	 * 
	 * @param <T>
	 *            list type
	 * @param query
	 *            query
	 * @param clazz
	 *            class of objects
	 * @return returns typed list
	 */
	public <T> List<T> getResultList(Query query, Class<T> clazz) {
		return ListUtils.convertRawListToGenericList(clazz, query
				.getResultList());
	}

}
