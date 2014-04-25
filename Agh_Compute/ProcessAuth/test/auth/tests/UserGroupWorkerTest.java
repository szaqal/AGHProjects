package auth.tests;

import java.util.Collection;

import javax.naming.InitialContext;

import org.apache.cactus.ServletTestCase;

import auth.GroupsWorker;
import auth.UserGroupWorker;
import auth.UsersWorker;
import auth.model.Group;
import auth.model.User;
import auth.model.UserGroup;
import core.workers.EjbInterfaceType;

/**
 * Test for  {@linkplain UserGroupWorker} .
 * @author  malczyk.pawel@gmail.com
 */
public class UserGroupWorkerTest extends ServletTestCase {
	
	/**
	 * Tested worker.
	 * @uml.property  name="userGroupWorker"
	 * @uml.associationEnd  
	 */
	private UserGroupWorker userGroupWorker;
	
	/**
	 * Worker.
	 * @uml.property  name="usersWorker"
	 * @uml.associationEnd  
	 */
	private UsersWorker usersWorker;
	
	/**
	 * Worker.
	 * @uml.property  name="groupsWorker"
	 * @uml.associationEnd  
	 */
	private GroupsWorker groupsWorker;
	
	/**
	 * User.
	 * @uml.property  name="user"
	 * @uml.associationEnd  
	 */
	private User user;
	
	/**
	 * Group.
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
	private Group group;
	
	/**
	 * Constructor.
	 */
	public UserGroupWorkerTest() {
		super();
	}
	
	
	
	/** {@inheritDoc} */
	@Override
	protected void setUp() throws Exception {
		Class<?> workerClass = UserGroupWorker.class;
		Class<?> userWorkerClass = UsersWorker.class;
		Class<?> groupsWorkerClass = GroupsWorker.class;
		InitialContext context = new InitialContext();
		String separator = "$";
		userGroupWorker = (UserGroupWorker) context.lookup(workerClass.getName() + separator + workerClass.getSimpleName() + EjbInterfaceType.REMOTE );
		usersWorker = (UsersWorker) context.lookup(userWorkerClass.getName() + separator + userWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE);
		groupsWorker = (GroupsWorker) context.lookup(groupsWorkerClass.getName()+ separator + groupsWorkerClass.getSimpleName() + EjbInterfaceType.REMOTE);
	}
	
	/**
	 * Tests if worker could be located.
	 */
	public void testWorkersFound() {
		assertNotNull(userGroupWorker);
		assertNotNull(usersWorker);
		assertNotNull(groupsWorker);
	}
	
	/**
	 * Test adding to group.
	 */
	public void testAddToGroup() {
		Collection<User> users = usersWorker.retrieveUsers(null);
		assertEquals(2, users.size());
		user = users.iterator().next();
		Collection<Group> groups = groupsWorker.retrieveGroups(null);
		int expectedGroupCount = 2;
		assertEquals(expectedGroupCount, groups.size());
		group = groups.iterator().next();
		userGroupWorker.addUserToGroup(group.getUniqueId(), user);
		Collection<UserGroup> userGroups = userGroupWorker.retrieveByUserAndGroup(group.getUniqueId(), user.getUniqueId());	
		assertEquals(1, userGroups.size());
	}
	
	
	/**
	 * Removes user from group.
	 */
	public void testRemoveFrom() {
		Collection<User> users = usersWorker.retrieveUsers(null);
		assertEquals(2, users.size());
		user = users.iterator().next();
		Collection<Group> groups = groupsWorker.retrieveGroups(null);
		group = groups.iterator().next();
		Collection<UserGroup> userGroups = userGroupWorker.retrieveByUserAndGroup(group.getUniqueId(), user.getUniqueId());	
		assertEquals(1, userGroups.size());
		userGroupWorker.removeUserFromGroup(group.getUniqueId(), user);
		userGroups = userGroupWorker.retrieveByUserAndGroup(group.getUniqueId(), user.getUniqueId());	
		assertEquals(0, userGroups.size());
	}
	
}
