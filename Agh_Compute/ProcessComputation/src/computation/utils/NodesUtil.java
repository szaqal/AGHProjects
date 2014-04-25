package computation.utils;

import node.ServerData;

import computation.model.ComputingNode;



/**
 * Converts various representations of computing nodes.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 *
 */
public final class NodesUtil {
	
	/**
	 * Constructor.
	 */
	private NodesUtil() { }
	
	/**
	 * populates computing node from server data.
	 * @param computing populating computingNode
	 * @param serverData server data from MBean
	 * @return populated computing node
	 */
	public static ComputingNode serverDataToComputingNode(ComputingNode computing, ServerData serverData) {
		computing.setOsName(serverData.getOsName());
		computing.setArch(serverData.getServerArch());
		computing.setInetAddr(serverData.getServerAddr());
		computing.setLifeThreads(serverData.getLifeThreads());
		computing.setPhysicalMemory(serverData.getPhysicalMemory());
		computing.setFreePhysicalMemory(serverData.getFreePhysicalMemory());
		computing.setProcessorCount(serverData.getProcessorCount());
		computing.setVmName(serverData.getVmName());
		computing.setCurrentLoad(serverData.getProcessorLoad());
		computing.setSshUser(serverData.getSshUser());
		computing.setSshPasswd(serverData.getSshPasswd());
		computing.setLibLocation(serverData.getLibLocation());
		computing.setConfLocation(serverData.getConfLocation());
		return computing;
	}
	
}
