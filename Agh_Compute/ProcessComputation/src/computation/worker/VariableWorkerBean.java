package computation.worker;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import computation.worker.VariableWorker.VariableWorkerLocal;
import computation.worker.VariableWorker.VariableWorkerRemote;

import core.workers.AbstractWorkerBean;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
@Local(VariableWorkerLocal.class)
@Remote(VariableWorkerRemote.class)
public class VariableWorkerBean extends AbstractWorkerBean implements VariableWorker {

	/**
	 * Constructor.
	 */
	public VariableWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public <T> T deserialize(String serialized, Class<T> clazz) {
		XStream xStream = new XStream(new DomDriver());
		Object deserialized = xStream.fromXML(serialized);
		return clazz.cast(deserialized);
	}
	
}
