package auth.tests;

import java.util.ArrayList;
import java.util.List;

import auth.model.Group;
import auth.model.User;


/**
 * Contains helper methods used by tests.
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public final class UsersTestsHelper {
	
	/**
	 * Constructor.
	 */
	private UsersTestsHelper() { }
	
	/**
	 * list of test groups.
	 * @return groups
	 */
	static List<Group> getTestGroups() {
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group("Zwykły użytkownik"));
		groups.add(new Group("Administrator"));
		groups.add(new Group("Gosc"));
		return groups;
	}
	
	
	/**
	 * Prepares test users.
	 * @return testUsers 
	 */
	static List<User> getTestUsers() {
		List<User> result = new ArrayList<User>();
		result.add(prepare("malczyk.pawel@gmail.com", "malczyk123"));
		result.add(prepare("andrzej.lepper@testmail.pl", "lepper123"));
		result.add(prepare("zygmunt.hajzer@testmail.pl", "hajzer123"));
		return result;
	}
	
	/**
	 * Prepares single user.
	 * @param email supplied email
	 * @param passwd supplied password
	 * @return prepared user
	 */
	static  User prepare(String email, String passwd) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(passwd);
		String [] split = email.split("\\@");
		String [] split2 = split[0].split("\\.");
		user.setFirstName(split2[1]);
		user.setLastName(split2[0]);
		return user;
	}

}
