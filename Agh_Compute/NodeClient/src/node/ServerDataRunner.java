package node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates information about data periodically.
 * @author malczyk.pawel@gmail.com
 *
 */
public final class ServerDataRunner implements Runnable {
	
	/** Refresh interval. */
	private static final int INTERVAL = 60000;

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ServerDataRunner.class);
	
	/**
	 * Stop.
	 */
	private boolean stop;
	
	/**
	 * Ssh user used to login (Slave node account).
	 */
	private String sshUser;
	
	/**
	 * Ssh user password used to login (Slave node account).
	 */
	private String sshPasswd;
	
	/**
	 * Directory storing libraries.
	 */
	private String libLocation;
	
	/**
	 * Directory storing configurations.
	 */
	private String confLocation;
	
	
	/**
	 * Constructor.
	 */
	public ServerDataRunner() {
	}
	
	/** {@inheritDoc} */
	@Override
	public void run() {
		
		LOG.debug("Node ip address {}  JMX port {}", Configuration.getNodeIp(), Configuration.getJmxPort());
		
		while(!stop) {
			LOG.debug("Logging");
			Agent.runAgnet(sshUser, sshPasswd, libLocation, confLocation);
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				LOG.warn("Interrupt exception", e);
			}
		}
			
	}

	/**
	 * Setter.
	 * @param stop the stop to set
	 */
	public void setStop(boolean stop) {
		this.stop = stop;
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
