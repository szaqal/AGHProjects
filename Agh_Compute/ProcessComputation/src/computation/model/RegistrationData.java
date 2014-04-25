package computation.model;

import java.io.Serializable;

/**
 * Contains all data required to register node, which becomes  available later on.
 * @author   <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class RegistrationData implements Serializable {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -4105006146881046439L;
	
	/**
	 * Computing node.
	 * @uml.property  name="host"
	 */
	private String host;
	
	/**
	 * JMX port.
	 * @uml.property  name="port"
	 */
	private String port;
	
	/**
	 * jmx server address.
	 * @uml.property  name="inetAddr"
	 */
	private String inetAddr;
	
	/**
	 * Port for opertion invocation.
	 */
	private String operationPort;
	
	/**
	 * SSH user name to access this server through SFTP.
	 */
	private String sshUser;
	
	/**
	 * SSH password name to access this server through SFTP.
	 */
	private String sshPasswd;
	
	/**
	 * Directory where libraries are stored.
	 */
	private String libLocation;
	
	/**
	 * Directory where configurations are stored.
	 */
	private String confLocation;
	
	/**
	 * Constructor.
	 */
	public RegistrationData() { }

	/**
	 * Getter.
	 * @return  the host
	 * @uml.property  name="host"
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Setter.
	 * @param host  the host to set
	 * @uml.property  name="host"
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Getter.
	 * @return  the port
	 * @uml.property  name="port"
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Setter.
	 * @param port  the port to set
	 * @uml.property  name="port"
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Getter.
	 * @return  the inetAddr
	 * @uml.property  name="inetAddr"
	 */
	public String getInetAddr() {
		return inetAddr;
	}

	/**
	 * Setter.
	 * @param inetAddr  the inetAddr to set
	 * @uml.property  name="inetAddr"
	 */
	public void setInetAddr(String inetAddr) {
		this.inetAddr = inetAddr;
	}

	/**
	 * Getter.
	 * @return  the operationPort
	 * @uml.property  name="operationPort"
	 */
	public String getOperationPort() {
		return operationPort;
	}

	/**
	 * Setter.
	 * @param operationPort  the operationPort to set
	 * @uml.property  name="operationPort"
	 */
	public void setOperationPort(String operationPort) {
		this.operationPort = operationPort;
	}

	/**
	 * Getter.
	 * @return the sshUser
	 */
	public String getSshUser() {
		return sshUser;
	}

	/**
	 * Setter.
	 * @param sshUser the sshUser to set
	 */
	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}

	/**
	 * Getter.
	 * @return the sshPasswd
	 */
	public String getSshPasswd() {
		return sshPasswd;
	}

	/**
	 * Setter.
	 * @param sshPasswd the sshPasswd to set
	 */
	public void setSshPasswd(String sshPasswd) {
		this.sshPasswd = sshPasswd;
	}

	/**
	 * Getter.
	 * @return the libLocation
	 */
	public String getLibLocation() {
		return libLocation;
	}

	/**
	 * Setter.
	 * @param libLocation the libLocation to set
	 */
	public void setLibLocation(String libLocation) {
		this.libLocation = libLocation;
	}

	/**
	 * Getter.
	 * @return the confLocation
	 */
	public String getConfLocation() {
		return confLocation;
	}

	/**
	 * Setter.
	 * @param confLocation the confLocation to set
	 */
	public void setConfLocation(String confLocation) {
		this.confLocation = confLocation;
	}

}
