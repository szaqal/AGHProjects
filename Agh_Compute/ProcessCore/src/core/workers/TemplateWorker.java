package core.workers;

import java.util.Map;

/**
 * Worker that deals with Velocity templates. 
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
public interface TemplateWorker {
	
	
	/**
	 * Populates template with parameters.
	 * 
	 * @param params template variable values
	 * @param templateName populating template name
	 * @return filled template.
	 */
	String create(Map<String, String> params, String templateName);
	
	/**
	 * 
	 * Local interface.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface TemplateWorkerLocal extends TemplateWorker { }
	
	/**
	 * Remote interface.
	 * 
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface TemplateWorkerRemote extends TemplateWorker { }
}
