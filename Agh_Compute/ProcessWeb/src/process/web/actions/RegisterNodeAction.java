package process.web.actions;

import computation.model.RegistrationData;
import computation.worker.NodesWorker;

/**
 * Handles computing node registration.
 * @author  malczyk.pawel@gmail.com
 */
public class RegisterNodeAction extends AbstractProcessAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 4965680695688641156L;
	
	/**
	 * Computing node id.
	 * @uml.property  name="nodeIp"
	 */
	private String nodeIp;
	
	/**
	 * Computing node JMX port.
	 * @uml.property  name="nodeJmxPort"
	 */
	private String nodeJmxPort;
	
	/**
	 * Node operational port.
	 * @uml.property  name="operationPort"
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
	public RegisterNodeAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		locate(NodesWorker.class).registerNode(deserialize());
		return SUCCESS;
	}
	
	/**
	 * Deserializes request parameter to {@link RegistrationData}. 
	 * @return registration data object
	 */
	private RegistrationData deserialize() {
		RegistrationData registrationData = new RegistrationData();
		registrationData.setInetAddr(getNodeIp());
		registrationData.setPort(getNodeJmxPort());
		registrationData.setOperationPort(getOperationPort());
		registrationData.setSshUser(getSshUser());
		registrationData.setSshPasswd(getSshPasswd());
		registrationData.setLibLocation(getLibLocation());
		registrationData.setConfLocation(getConfLocation());
		return registrationData;
	}

	/**
	 * Getter.
	 * @return  the nodeIp
	 * @uml.property  name="nodeIp"
	 */
	public String getNodeIp() {
		return nodeIp;
	}

	/**
	 * Setter.
	 * @param nodeIp  the nodeIp to set
	 * @uml.property  name="nodeIp"
	 */
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	/**
	 * Getter.
	 * @return  the nodeJmxPort
	 * @uml.property  name="nodeJmxPort"
	 */
	public String getNodeJmxPort() {
		return nodeJmxPort;
	}

	/**
	 * Setter.
	 * @param nodeJmxPort  the nodeJmxPort to set
	 * @uml.property  name="nodeJmxPort"
	 */
	public void setNodeJmxPort(String nodeJmxPort) {
		this.nodeJmxPort = nodeJmxPort;
	}

	/**
	 * Getter.
	 * @return the operationPort
	 */
	public String getOperationPort() {
		return operationPort;
	}

	/**
	 * Setter.
	 * @param operationPort the operationPort to set
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
