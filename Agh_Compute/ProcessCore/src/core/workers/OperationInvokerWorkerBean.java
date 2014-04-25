package core.workers;

import java.net.MalformedURLException;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * Worker implementation. 
 * @author malczyk.pawel@gmail.com
 *
 */
@Stateless
public class OperationInvokerWorkerBean extends AbstractWorkerBean implements OperationInvokerWorker {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OperationInvokerWorker.class);
	
	/**
	 * Constructor.
	 */
	public OperationInvokerWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public <T> T getObject(Class<T> clazz, String url) {
		HessianProxyFactory proxy = new HessianProxyFactory();
		try {
			return clazz.cast(proxy.create(clazz, url));
		} catch (MalformedURLException e) {
			LOG.warn("Exception", e);
		}
		return null;
	}

}
