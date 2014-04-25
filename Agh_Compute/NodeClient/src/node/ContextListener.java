package node;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listens for context events.
 * @author malczyk.pawel@gmail.com
 *
 */
public class ContextListener implements ServletContextListener{
	
	/**
	 * {@link ServerDataRunner}.
	 */
	private static final ServerDataRunner RUNNER = new ServerDataRunner();

	/** {@inheritDoc} */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		RUNNER.setStop(true);
	}
	
	/**
	 * Constructor.
	 */
	public ContextListener() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext = arg0.getServletContext();
		String sshUser = servletContext.getInitParameter("sshUser");
		String sshPasswd = servletContext.getInitParameter("sshPassword");
		String libLocation = servletContext.getInitParameter("libLocation");
		String confLocation = servletContext.getInitParameter("confLocation");
		RUNNER.setSshUser(sshUser);
		RUNNER.setSshPasswd(sshPasswd);
		RUNNER.setLibLocation(libLocation);
		RUNNER.setConfLocation(confLocation);
		(new Thread(RUNNER)).start();
	}

}
