package core.utils;

import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Logger;

/**
 * Wraps up SFTP logging.
 * 
 * @author pawel.malczyk@iloop.com
 *
 */
public class SftpLogger implements Logger {
	
	/**
	 * Logger.
	 */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SftpLogger.class);
	
	/**
	 * Constructor.
	 */
	public SftpLogger() {
		super();
	}

	/** {@inheritDoc} */
	public boolean isEnabled(int arg0) {
		return true;
	}

	/** {@inheritDoc} */
	public void log(int arg0, String arg1) {
		LOG.info("[SFTP/SSH log {}]", arg1);
	}

}
