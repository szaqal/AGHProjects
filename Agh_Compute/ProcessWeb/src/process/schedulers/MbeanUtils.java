package process.schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import node.ServerData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.ComputingNode;
import computation.utils.NodesUtil;

/**
 * Temporary class.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public final class MbeanUtils {
	
	/**
	 * Message label.
	 */
	private static final String EXCEPTION = "Exception";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MbeanUtils.class);
	
	/**
	 * Konstruktor.
	 */
	public MbeanUtils() { };
	
	/**
	 * populates/refreshes additional data about node.
	 * @param computingNode computing node to populate/refresh
	 * @throws Exception exception throwed
	 */
	public void populateNodeData(ComputingNode computingNode) throws Exception {
		
		MBeanServerConnection connection = createConnection(computingNode.getInetAddr(), computingNode.getPort());
			/*TODO: do jakis constrantsow*/
		ObjectName objectName = new ObjectName(connection.getDefaultDomain(), "jmxproba", "process"); 
		ServerData serverData = (ServerData) connection.getAttribute(objectName, "ServerData"); 
		NodesUtil.serverDataToComputingNode(computingNode, serverData);
		
	}
	
	/**
	 * Creates connection to MBean server.
	 * @param address server address
	 * @param port server port
	 * @return prepared url.
	 * @throws IOException exception
	 */
	private MBeanServerConnection createConnection(String address, String port) throws IOException {
		String url = "service:jmx:rmi:///jndi/rmi://" + address + ":" + port + "/jmxrmi";
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] credentials = new String[] { "monitorRole" , "tomcat"};
		map.put("jmx.remote.credentials", credentials);
		JMXConnector jmxcon = JMXConnectorFactory.connect(new JMXServiceURL(url), map);
		MBeanServerConnection server = jmxcon.getMBeanServerConnection();
		return server;
	}
	
	
}
