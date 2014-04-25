package api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation is use to describe computable task.
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Computable {
	
	/** Task name. */
	String taskName();
}
