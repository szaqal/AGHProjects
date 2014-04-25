package node.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import node.Agent;
import node.ServerData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class that implements mbean client.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public class Process implements ProcessMBean {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Process.class);
	
	/**
	 * @see http://download.oracle.com/javase/1.5.0/docs/guide/management/overview.html
	 * MBean object name.
	 */
	private static final String OS_OBJECT_NAME = "java.lang:type=OperatingSystem";
	
	/**
	 * @see http://download.oracle.com/javase/1.5.0/docs/guide/management/overview.html
	 * MBean object name.
	 */
	private static final String RUNTIME_OBJECT_NAME = "java.lang:type=Runtime";
	
	/**
	 * @see http://download.oracle.com/javase/1.5.0/docs/guide/management/overview.html
	 * MBean object name.
	 */
	private static final String THREADING_OBJECT_NAME = "java.lang:type=Threading";
	
	/**
	 * Server Data.
	 */
	private ServerData serverData;
	
	/**
	 * Constructor.
	 * @param sshUser ssh user
	 * @param sshPasswd ssh password
	 * @param libLocation library location
	 * @param confLocation configuration file storage location
	 */
	public Process(String sshUser, String sshPasswd, String libLocation, String confLocation) { 
		try {
			String osName = (String) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(OS_OBJECT_NAME), "Name");
			String totalPhysicalMemory = String.valueOf((Long) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(OS_OBJECT_NAME), 
					"TotalPhysicalMemorySize"));
			String freePhysicalMemory = String.valueOf((Long) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(OS_OBJECT_NAME), 
					"FreePhysicalMemorySize"));
			String vmName = (String) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(RUNTIME_OBJECT_NAME), "VmName");
			String vmVendor = (String) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(RUNTIME_OBJECT_NAME), "VmVendor");
			String liveThreads = String.valueOf((Integer) ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName(THREADING_OBJECT_NAME), 
					"ThreadCount"));
			
			OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
			serverData = new ServerData();
			serverData.setOsName(osName + " "+operatingSystemMXBean.getVersion());
			serverData.setServerArch(operatingSystemMXBean.getArch());
			serverData.setServerName(operatingSystemMXBean.getName() );
			serverData.setProcessorCount(operatingSystemMXBean.getAvailableProcessors());
			serverData.setPhysicalMemory(totalPhysicalMemory);
			serverData.setFreePhysicalMemory(freePhysicalMemory);
			serverData.setVmName(vmName);
			serverData.setVmVendor(vmVendor);
			serverData.setLifeThreads(liveThreads);
			serverData.setProcessorLoad(String.valueOf(operatingSystemMXBean.getSystemLoadAverage()));
			serverData.setSshUser(sshUser);
			serverData.setSshPasswd(sshPasswd);
			serverData.setLibLocation(libLocation);
			serverData.setConfLocation(confLocation);
		} catch (AttributeNotFoundException e) {
			LOG.warn("Attribute not found");
		} catch (InstanceNotFoundException e) {
			LOG.warn("Instance not found");
		} catch (MalformedObjectNameException e) {
			LOG.warn("Malformed name ");
		} catch (MBeanException e) {
			LOG.warn("MBean exception");
		} catch (ReflectionException e) {
			LOG.warn("Reflection exception");
		} catch (NullPointerException e) {
			LOG.warn("Nullpointer");
		}

		
		
		serverData.setServerAddr(System.getProperty(Agent.AGENT_ADDR));
	}

	/** {@inheritDoc} */
	@Override
	public ServerData getServerData() {
		return serverData;
	}

}
