package process.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation used to enoforce permissions.
 * @author malczyk.pawel@gmail.com
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Require {
	
	/**
	 * Name of the permission.
	 */
	String permission();
}
