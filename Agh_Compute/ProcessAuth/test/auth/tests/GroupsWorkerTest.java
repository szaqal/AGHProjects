package auth.tests;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import auth.GroupsWorker;
import auth.model.Group;
import core.workers.EjbInterfaceType;

/**
 * Test for  {@link GroupsWorker} .
 * @author  <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 */
public class GroupsWorkerTest extends ServletTestCase {
	
	/** Constant. */
	private static final int TEST_GROUPS_COUNT = 3;
	
	/**
	 * Groups worker.
	 * @uml.property  name="worker"
	 * @uml.associationEnd  
	 */
	private GroupsWorker worker;

	/**
	 * Constructor.
	 */
	public GroupsWorkerTest() {
		super();
	}
	
	/** {@inheritDoc}*/
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = GroupsWorker.class;
		InitialContext context = new InitialContext();
		worker = (GroupsWorker) context.lookup(workerClass.getName() + "$" + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkerFound() {
		assertNotNull(worker);
	}
	
	/**
	 * Tests group storing.
	 */
	public void testStoreGroup() {
		List<Group> groups = UsersTestsHelper.getTestGroups();
		for(Group group:groups) {
			int key = worker.saveGroup(group).getUniqueId();
			assertTrue(key>0);
			Group grp =worker.retrieveGroup(key);
			assertNotNull(grp);
		}
	}
	
	/**
	 * Tests retrieving groups.
	 */
	public void testGroupsCount() {
		Long groupsCount = Long.valueOf(TEST_GROUPS_COUNT);
		Long existingCount = worker.retrieveGroupsCount();
		assertEquals(groupsCount, existingCount);
	}
	
	/**
	 * Test group deletion.
	 */
	public void testGroupDelete() {
		Long countBefore = worker.retrieveGroupsCount();
		Group grp = worker.retrieveGroups(null).iterator().next();
		worker.deleteGroup(grp.getUniqueId());
		Long countAfter = worker.retrieveGroupsCount();
		assertEquals(--countBefore, countAfter);
	}
	
	/**
	 * Test group update.
	 */
	public void testGroupUpdate() {
		Group grp = worker.retrieveGroups(null).iterator().next();
		int uniqueId = grp.getUniqueId();
		String groupNameNew = "testName";
		grp.setName(groupNameNew);
		worker.saveGroup(grp);
		grp = worker.retrieveGroup(uniqueId);
		assertEquals(groupNameNew, grp.getName());
	}
}
