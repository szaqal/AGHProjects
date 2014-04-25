package api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Applies to collections makes computation of such value
 * partitionable an can be done on many nodes concurrently.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Partitionable {

}
