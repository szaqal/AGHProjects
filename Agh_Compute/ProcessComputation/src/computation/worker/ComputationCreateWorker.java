package computation.worker;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.xml.sax.SAXException;

import computation.model.Computation;

/**
 * Handles process creation.
 * @author malczyk.pawel@gmail.com
 *
 */
public interface ComputationCreateWorker {
	
	/**
	 * Creates computation from package.
	 * @param configEntry configuration file
	 * @param jarFile package file
	 * @return created computation
	 */
	Computation processConfigFile(JarEntry configEntry, JarFile jarFile);
	
	/**
	 * Creates computation from inputs stream.
	 * @param xmlInputStream xmlfile input stream
	 * @return created computation.
	 * @throws SAXException exception that my be thrown
	 * @throws IOException exception that my be thrown
	 */
	Computation createComputation(InputStream xmlInputStream) throws SAXException, IOException;
	
	/**
	 * Creates computation from raw bytes.
	 * @param dataFile data file
	 * @return created computation.
	 */
	Computation createComputation(byte [] dataFile);
	
	/**
	 * Local interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationCreateWorkerLocal extends ComputationCreateWorker { }
	
	
	/**
	 * Remote interface.
	 * @author malczyk.pawel@gmail.com
	 *
	 */
	public interface ComputationCreateWorkerRemote extends ComputationCreateWorker { }

}
