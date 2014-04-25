package node;

import java.io.Serializable;

/**
 * ServerData.  
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public class ServerData implements Serializable {
	
	/**
	 * Constant.
	 */
	private static final String EMPTY = " ";
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 899734214581623402L;
	/**
	 * Name of the server.
	 */
	private String serverName;
	
	/**
	 * Server address.
	 */
	private String serverAddr;
	
	/**
	 * Architecture of the server.
	 */
	private String serverArch;
	
	/**
	 * Operating system.
	 */
	private String osName;
	
	/**
	 * Processor count.
	 */
	private int processorCount;
	
	/**
	 * Current physical memory.
	 */
	private String physicalMemory;
	
	/**
	 * Free physical memory.
	 */
	private String freePhysicalMemory;
	
	/**
	 * VM name.
	 */
	private String vmName;
	
	/**
	 * VM vendor.
	 */
	private String vmVendor;
	
	/**
	 * liveThreads.
	 */
	private String lifeThreads;
	
	/**
	 * Processor load.
	 */
	private String processorLoad;
	
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
	public ServerData() { }

	/**
	 * Getter.
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Setter.
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	/** {@inheritDoc}*/
	@Override
	public String toString() {
		return serverName + EMPTY + serverArch + EMPTY + osName;
	}

	/**
	 * Getter.
	 * @return the serverArch
	 */
	public String getServerArch() {
		return serverArch;
	}

	/**
	 * Setter.
	 * @param serverArch the serverArch to set
	 */
	public void setServerArch(String serverArch) {
		this.serverArch = serverArch;
	}

	/**
	 * Getter.
	 * @return the osName
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * Setter.
	 * @param osName the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * Getter.
	 * @return the serverAddr
	 */
	public String getServerAddr() {
		return serverAddr;
	}

	/**
	 * Setter.
	 * @param serverAddr the serverAddr to set
	 */
	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	/**
	 * Getter.
	 * @return the processorCount
	 */
	public int getProcessorCount() {
		return processorCount;
	}

	/**
	 * Setter.
	 * @param processorCount the processorCount to set
	 */
	public void setProcessorCount(int processorCount) {
		this.processorCount = processorCount;
	}

	/**
	 * Getter.
	 * @return the physicalMemory
	 */
	public String getPhysicalMemory() {
		return physicalMemory;
	}

	/**
	 * Setter.
	 * @param physicalMemory the physicalMemory to set
	 */
	public void setPhysicalMemory(String physicalMemory) {
		this.physicalMemory = physicalMemory;
	}

	/**
	 * Getter.
	 * @return the freePhysicalMemory
	 */
	public String getFreePhysicalMemory() {
		return freePhysicalMemory;
	}

	/**
	 * Setter.
	 * @param freePhysicalMemory the freePhysicalMemory to set
	 */
	public void setFreePhysicalMemory(String freePhysicalMemory) {
		this.freePhysicalMemory = freePhysicalMemory;
	}

	/**
	 * Getter.
	 * @return the vmName
	 */
	public String getVmName() {
		return vmName;
	}

	/**
	 * Setter.
	 * @param vmName the vmName to set
	 */
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * Getter.
	 * @return the vmVendor
	 */
	public String getVmVendor() {
		return vmVendor;
	}

	/**
	 * Setter.
	 * @param vmVendor the vmVendor to set
	 */
	public void setVmVendor(String vmVendor) {
		this.vmVendor = vmVendor;
	}

	/**
	 * Getter.
	 * @return the lifeThreads
	 */
	public String getLifeThreads() {
		return lifeThreads;
	}

	/**
	 * Setter.
	 * @param lifeThreads the lifeThreads to set
	 */
	public void setLifeThreads(String lifeThreads) {
		this.lifeThreads = lifeThreads;
	}

	/**
	 * Getter.
	 * @return the empty
	 */
	public static String getEmpty() {
		return EMPTY;
	}

	/**
	 * Getter.
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Getter.
	 * @return the processorLoad
	 */
	public String getProcessorLoad() {
		return processorLoad;
	}

	/**
	 * Setter.
	 * @param processorLoad the processorLoad to set
	 */
	public void setProcessorLoad(String processorLoad) {
		this.processorLoad = processorLoad;
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
