package core.workers;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import core.utils.SftpLogger;
import core.utils.StringUtils;
import core.workers.SftpWorker.SftpWorkerLocal;
import core.workers.SftpWorker.SftpWorkerRemote;

/**
 * Worker implementation.
 * 
 * @author malczyk.pawel@gmail.com
 * 
 */
@Stateless
@Local(SftpWorkerLocal.class)
@Remote(SftpWorkerRemote.class)
public class SftpWorkerBean extends AbstractWorkerBean implements SftpWorker {

	/**
	 * Default SSH port.
	 */
	private static final int SSH_DEFAULT_PORT = 22;

	/**
	 * Error message prefix.
	 */
	private static final String SFTP_EXCEPTION = "SFTP Exception";
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SftpWorkerBean.class);
	
	/**
	 * Constructor.
	 */
	public SftpWorkerBean() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void uploadFile(byte[] data, String host, String targetLocation, String sftpUser,
			String sftpPassword, String fileName) {
		JSch jsch = new JSch();
		JSch.setLogger(new SftpLogger());
		Session session = null;
		try {
			targetLocation = "sftp://"+host+""+targetLocation;
			targetLocation = targetLocation.endsWith(StringUtils.SLASH) ? targetLocation
					+ fileName : targetLocation + StringUtils.SLASH + fileName;
			URI uri = new URI(targetLocation);
			LOG.info("Uploading file to {}", targetLocation);
			session = jsch.getSession(sftpUser, uri.getHost(), SSH_DEFAULT_PORT);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(sftpPassword);

			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
			sftpChannel.put(byteArrayInputStream, uri.getPath(), ChannelSftp.OVERWRITE);
			sftpChannel.exit();
			session.disconnect();

		} catch (JSchException e) {
			LOG.warn(SFTP_EXCEPTION, e);
		} catch (SftpException e) {
			LOG.warn(SFTP_EXCEPTION, e);
		} catch (URISyntaxException e) {
			LOG.warn(SFTP_EXCEPTION, e);
		}
	}

}
