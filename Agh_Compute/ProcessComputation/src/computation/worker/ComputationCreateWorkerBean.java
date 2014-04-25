package computation.worker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import computation.exceptions.NoValidConfigFileException;
import computation.model.Computation;
import computation.utils.DigesterRulesBuilder;
import computation.worker.ComputationCreateWorker.ComputationCreateWorkerLocal;
import computation.worker.ComputationCreateWorker.ComputationCreateWorkerRemote;

import core.workers.AbstractWorkerBean;


/**
 * Worker implementation.
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(ComputationCreateWorkerLocal.class)
@Remote(ComputationCreateWorkerRemote.class)
public class ComputationCreateWorkerBean extends AbstractWorkerBean implements ComputationCreateWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ComputationCreateWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public ComputationCreateWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public Computation processConfigFile(JarEntry configEntry, JarFile jarFile) {
		
		
		try {
				
			if(configEntry == null) {
				throw new NoValidConfigFileException();
			}
			
			ZipEntry configFileZipEntry = jarFile.getEntry(configEntry.getName());
			InputStream is = jarFile.getInputStream(configFileZipEntry); 
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line= null;
			while ((line = reader.readLine())!=null){
				LOG.info(line);
			}
			is.close();
		
	
			is = jarFile.getInputStream(configFileZipEntry); 
			return createComputation(is);
			
		} catch (SAXException e) {
			LOG.warn("SAX exception", e);
		} catch (IOException e) {
			LOG.warn("IO exception", e);
		} catch (NoValidConfigFileException e) {
			LOG.warn("NoValidConfigFileException exception", e);
		}
		return null;
	}
	
	/**
	 * Creates computation form xml file stream.
	 * @param xmlInputStream xml stream.
	 * @throws SAXException sax exception
	 * @return created computation
	 * @throws IOException io exception
	 */
	public Computation createComputation(InputStream xmlInputStream) throws SAXException, IOException {
		Digester digester = DigesterRulesBuilder.createComputationImport();
		Computation result = (Computation) digester.parse(xmlInputStream);
		xmlInputStream.close();
		
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Computation createComputation(byte[] dataFile) {
		InputStream inputStream = new ByteArrayInputStream(dataFile);
		Computation createdComputation = null;
		try {
			createdComputation = createComputation(inputStream);
		} catch (SAXException e) {
			LOG.warn("SAX exception thrown", e);
		} catch (IOException e) {
			LOG.warn("IO Exceprion", e);
		}
		return createdComputation;
	}

}
