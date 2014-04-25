package node.jmx;

import node.ServerData;


/**
 * Interface of MBean client.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public interface ProcessMBean {

	
	/**
	 * Returns server data.
	 * @return server data
	 */
	ServerData getServerData();

}
