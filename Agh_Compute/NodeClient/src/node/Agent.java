package node;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import computation.model.RegistrationData;


/**
 * Agent.
 * 
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * 
 */
public final class Agent {
	
	/**
	 * Property containing address.
	 */
	public static final String AGENT_ADDR = "process.client.inetaddr";
	
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Agent.class);
	
	
	/**
	 * Constant.
	 */
	private static final String COM_SUN_MANAGEMENT_JMXREMOTE_PORT = "com.sun.management.jmxremote.port";
	
	/**
	 * Constructor.
	 * @param sshUser - ssh user
	 * @param sshPasswd - ssh user password
	 * @param libLocation - library directory
	 * @param confLocation - configuration directory
	 */
	private Agent(String sshUser, String sshPasswd, String libLocation, String confLocation) { 
		register(sshUser, sshPasswd, libLocation, confLocation);
	}
	
	/**
	 * Registers node in the master server.
	 * @param sshUser - ssh user
	 * @param sshPasswd - ssh user password
	 * @param libLocation - library directory
	 * @param confLocation - configuration directory
	 */
	private void register(String sshUser, String sshPasswd, String libLocation, String confLocation) {
		LOG.info("Registering Node...");
		LOG.info("Listening on port:\t"
				+ System.getProperty( COM_SUN_MANAGEMENT_JMXREMOTE_PORT ) );
		
		
		String serverUrl = String.format("http://%s:%s/procc/RegisterNode", 
				Configuration.getServerIp(), Configuration.getServerPort());
		
		LOG.info("Registration at url {}", serverUrl);
		RegistrationData registrationData = createRegistrationData();
		
		HttpClient httpClient = new HttpClient();
		HttpMethod httpRegisterNodeGet = new GetMethod(serverUrl);
		httpRegisterNodeGet.setQueryString("?nodeIp="+registrationData.getInetAddr()+"&nodeJmxPort="
				+registrationData.getPort()+"&operationPort="+registrationData.getOperationPort()
				+"&sshUser="+sshUser+"&sshPasswd="+sshPasswd+"&libLocation="+libLocation+"&confLocation="+confLocation);
		try {
			httpClient.executeMethod(httpRegisterNodeGet);
		} catch (HttpException e) {
			LOG.warn("HttpException", e);
		} catch (IOException e) {
			LOG.warn("IOException", e);
		}
		httpRegisterNodeGet.releaseConnection();		
	}
	
	/**
	 * Create node registration data.
	 * @return data needed to register.
	 */
	private RegistrationData createRegistrationData() {
		RegistrationData registrationData = new RegistrationData();
		registrationData.setPort(Configuration.getJmxPort());
		registrationData.setInetAddr(Configuration.getNodeIp());
		registrationData.setOperationPort(Configuration.getOperationPort());
		return registrationData;
		
	}
	
	/**
	 * Main method.
	 * @param sshUser - ssh user
	 * @param sshPasswd - ssh user password
	 * @param libLocation - library directory
	 * @param confLocation - configuration directory
	 */
	public static void runAgnet(String sshUser, String sshPasswd, String libLocation, String confLocation){
		new Agent(sshUser, sshPasswd, libLocation, confLocation);
		try {
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName(server.getDefaultDomain(), "jmxproba", "process");
			node.jmx.Process mbean = new node.jmx.Process(sshUser, sshPasswd, libLocation, confLocation);
			if(server.isRegistered(objectName)) {
				LOG.info("MBean already registered");
				server.unregisterMBean(objectName);
			}
			server.registerMBean(mbean, objectName);
		} catch (MalformedObjectNameException e) {
			LOG.warn("Malformed object name exception");
		} catch (NullPointerException e) {
			LOG.warn("Nullpointer");
		} catch (InstanceAlreadyExistsException e) {
			LOG.warn("Instance already exists");
		} catch (MBeanRegistrationException e) {
			LOG.warn("MBean registration exception");
		} catch (NotCompliantMBeanException e) {
			LOG.warn("Not compliant MBean exception");
		} catch (InstanceNotFoundException e) {
			LOG.warn("MBean instance not found exception");
		}
	}
	
}
