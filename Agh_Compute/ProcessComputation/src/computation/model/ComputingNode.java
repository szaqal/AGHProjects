package computation.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import node.ServerData;

/**
 * Represents single computing node.
 * @author   <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>.
 */
public class ComputingNode {
	
	/**
	 * Default value;
	 */
	private static final String NO_ACCESS = "N/A";

	/**
	 * Computing node.
	 * @uml.property  name="host"
	 */
	private String host = NO_ACCESS;
	
	/**
	 * JMX port.
	 * @uml.property  name="port"
	 */
	private String port = NO_ACCESS;
	
	/** Node operational port. */
	private String operationPort = NO_ACCESS;
	
	/**
	 * Address o the computing node.
	 * @uml.property  name="inetAddr"
	 */
	private String inetAddr = NO_ACCESS;
	
	/**
	 * Node architecture.
	 * @uml.property  name="arch"
	 */
	private String arch = NO_ACCESS;
	
	/**
	 * Node os.
	 * @uml.property  name="osName"
	 */
	private String osName = NO_ACCESS;
	
	/**
	 * Current system load.
	 * @uml.property  name="currentLoad"
	 */
	private String currentLoad = NO_ACCESS;
	
	/**
	 * Processor count.
	 * @uml.property  name="processorCount"
	 */
	private int processorCount = 0;
	
	/**
	 * Current physical memory.
	 * @uml.property  name="physicalMemory"
	 */
	private String physicalMemory = NO_ACCESS;
	
	/**
	 * VM name.
	 * @uml.property  name="vmName"
	 */
	private String vmName = NO_ACCESS;
	
	/**
	 * VM vendor.
	 * @uml.property  name="vmVendor"
	 */
	private String vmVendor = NO_ACCESS;
	
	/**
	 * liveThreads.
	 * @uml.property  name="lifeThreads"
	 */
	private String lifeThreads = NO_ACCESS;
	
	/**
	 * SSH user name to access this server through SFTP.
	 */
	private String sshUser = NO_ACCESS;
	
	/**
	 * SSH password name to access this server through SFTP.
	 */
	private String sshPasswd = NO_ACCESS;
	
	/**
	 * Directory where libraries are stored.
	 */
	private String libLocation = NO_ACCESS;
	
	/**
	 * Directory where configurations are stored.
	 */
	private String confLocation;
	
	/**
	 * Free physical memory.
	 * @uml.property  name="freePhysicalMemory"
	 */
	private String freePhysicalMemory = NO_ACCESS;
	
	/**
	 * Last update date.
	 * @uml.property  name="lastUpdateDate"
	 */
	private Date lastUpdateDate;
	
	
	/**
	 * Constructor.
	 */
	private ComputingNode() { };

	/**
	 * Constructor.
	 * 
	 * @param registrationData
	 *            data needed to register
	 */
	public ComputingNode(RegistrationData registrationData) {
		this();
		host = registrationData.getHost();
		port = registrationData.getPort();
		operationPort = registrationData.getOperationPort();
		inetAddr = registrationData.getInetAddr();
		sshUser = registrationData.getSshUser();
		sshPasswd = registrationData.getSshPasswd();
		libLocation = registrationData.getLibLocation();
		confLocation = registrationData.getConfLocation();
		
	}
	
	/**
	 * Updates date of last update.
	 */
	public void updateLastUpdateDate() {
		this.lastUpdateDate = new Date();
	}
	
	/**
	 * Populates data about computing node.
	 * @param serverData serverdata recieved from MBean server
	 */
	public void populate(ServerData serverData) {
		setArch(serverData.getServerArch());
		serverData.getOsName();
	}
 

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
	 * Returns unique id.
	 * @return uniqueid
	 */
	public String getUniqueId() {
		return nodeKey();
	}


	/**
	 * Returns json representation.
	 * @return json data array
	 */
	public String[] getJsonData() {
		List<String> data = new ArrayList<String>();
		data.add(inetAddr);
		data.add(operationPort);
		data.add(port);
		data.add(osName);
		data.add(arch);
		return data.toArray(new String[data.size()]);
	}

	/**
	 * Returns full data.
	 * @return array of items.
	 */
	@Transient
	public String [] getFullJson() {
		List<String> data = new ArrayList<String>();
		data.add(inetAddr);
		data.add(port);
		data.add(osName);
		data.add(arch);
		data.add(vmName);
		data.add(vmVendor);
		data.add(String.valueOf(processorCount));
		data.add(physicalMemory);
		data.add(freePhysicalMemory);
		data.add(lifeThreads);
		data.add(currentLoad);
		data.add(operationPort);
		return data.toArray(new String[data.size()]);
	}
	
	/**
	 * Combination of Ip address with opertation port
	 * @return node key.
	 */
	public String nodeKey() {
		return getInetAddr()+"_"+getOperationPort();
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
	 * @return  the arch
	 * @uml.property  name="arch"
	 */
	public String getArch() {
		return arch;
	}

	/**
	 * Setter.
	 * @param arch  the arch to set
	 * @uml.property  name="arch"
	 */
	public void setArch(String arch) {
		this.arch = arch;
	}

	/**
	 * Getter.
	 * @return  the osName
	 * @uml.property  name="osName"
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * Setter.
	 * @param osName  the osName to set
	 * @uml.property  name="osName"
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * Getter.
	 * @return  the processorCount
	 * @uml.property  name="processorCount"
	 */
	public int getProcessorCount() {
		return processorCount;
	}

	/**
	 * Setter.
	 * @param processorCount  the processorCount to set
	 * @uml.property  name="processorCount"
	 */
	public void setProcessorCount(int processorCount) {
		this.processorCount = processorCount;
	}

	/**
	 * Getter.
	 * @return  the physicalMemory
	 * @uml.property  name="physicalMemory"
	 */
	public String getPhysicalMemory() {
		return physicalMemory;
	}

	/**
	 * Setter.
	 * @param physicalMemory  the physicalMemory to set
	 * @uml.property  name="physicalMemory"
	 */
	public void setPhysicalMemory(String physicalMemory) {
		this.physicalMemory = physicalMemory;
	}

	/**
	 * Getter.
	 * @return  the vmName
	 * @uml.property  name="vmName"
	 */
	public String getVmName() {
		return vmName;
	}

	/**
	 * Setter.
	 * @param vmName  the vmName to set
	 * @uml.property  name="vmName"
	 */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * Getter.
	 * @return  the vmVendor
	 * @uml.property  name="vmVendor"
	 */
	public String getVmVendor() {
		return vmVendor;
	}

	/**
	 * Setter.
	 * @param vmVendor  the vmVendor to set
	 * @uml.property  name="vmVendor"
	 */
	public void setVmVendor(String vmVendor) {
		this.vmVendor = vmVendor;
	}

	/**
	 * Getter.
	 * @return  the lifeThreads
	 * @uml.property  name="lifeThreads"
	 */
	public String getLifeThreads() {
		return lifeThreads;
	}

	/**
	 * Setter.
	 * @param lifeThreads  the lifeThreads to set
	 * @uml.property  name="lifeThreads"
	 */
	public void setLifeThreads(String lifeThreads) {
		this.lifeThreads = lifeThreads;
	}

	/**
	 * Getter.
	 * @return  the freePhysicalMemory
	 * @uml.property  name="freePhysicalMemory"
	 */
	public String getFreePhysicalMemory() {
		return freePhysicalMemory;
	}

	/**
	 * Setter.
	 * @param freePhysicalMemory  the freePhysicalMemory to set
	 * @uml.property  name="freePhysicalMemory"
	 */
	public void setFreePhysicalMemory(String freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}

	/**
	 * Getter.
	 * @return  the lastUpdateDate
	 * @uml.property  name="lastUpdateDate"
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Getter.
	 * @return  the currentLoad
	 * @uml.property  name="currentLoad"
	 */
	public String getCurrentLoad() {
		return currentLoad;
	}

	/**
	 * Setter.
	 * @param currentLoad  the currentLoad to set
	 * @uml.property  name="currentLoad"
	 */
	public void setCurrentLoad(String currentLoad) {
		this.currentLoad = currentLoad;
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
	
	@Override
	public String toString() {
		return getUniqueId();
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
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		boolean ipEquals = getInetAddr().equals(((ComputingNode) obj).getInetAddr());
		boolean portEquals= getOperationPort().equals(((ComputingNode) obj).getOperationPort());
		return ipEquals && portEquals;
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return getInetAddr().hashCode()+getOperationPort().hashCode();
	}

}
