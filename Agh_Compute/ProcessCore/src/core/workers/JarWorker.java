package core.workers;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Worker that operates on JAR files.
 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
 *
 */
public interface JarWorker {
	
	/**
	 * Creates Jar File from byte content.
	 * @param data representing jar file.
	 * @return jar file
	 */
	JarFile createFromBytes(byte [] data);
	
	
	/**
	 * Retrieve entry of JAR file based on name.
	 * @param data representing jar file.
	 * @param entryName name of entry being retrieved from file
	 * @return searched entry or null
	 */
	JarEntry retrieveJarEntry(byte [] data, String entryName);
	
	/**
	 * 
	 * Local interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface JarWorkerLocal extends JarWorker { }
	
	/**
	 * Remote interface.
	 * @author <a href="mailto:malczyk.pawel@gmail.com>malczyk.pawel@gmail.com</a>
	 *
	 */
	public interface JarWorkerRemote extends JarWorker { }
	
	
}
