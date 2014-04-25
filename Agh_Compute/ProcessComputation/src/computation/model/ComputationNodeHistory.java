package computation.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import core.model.AbstractEntity;

/**
 * Node history. 
 * @author  malczyk.pawel@gmail.com
 */
@Entity
@NamedQueries({
	@NamedQuery(name="retrieveByIp", 
			query="SELECT cm FROM ComputationNodeHistory AS cm WHERE cm.nodeIp =:nodeIp ORDER BY cm.recordDate DESC")
})
public class ComputationNodeHistory extends AbstractEntity {
	
	/**
	 * Constatnt, name of query.
	 */
	public static final String BY_IP = "retrieveByIp";
	
	/**
	 * Unique identifier.
	 * @uml.property  name="uniqueId"
	 */
	private int uniqueId;
	
	/**
	 * Ip of computing node.
	 * @uml.property  name="nodeIp"
	 */
	private String nodeIp;
	
	/**
	 * Node architecture.
	 * @uml.property  name="arch"
	 */
	private String arch;
	
	/**
	 * Memory free.
	 * @uml.property  name="memoryFree"
	 */
	private String memoryFree;
	
	/**
	 * Processor load.
	 * @uml.property  name="processorLoad"
	 */
	private String processorLoad;
	
	/**
	 * Date when measure was done.
	 * @uml.property  name="recordDate"
	 */
	private Date recordDate;
	
	/**
	 * Constructor.
	 */
	public ComputationNodeHistory() {
		super();
	}

	/**
	 * Getter.
	 * @return  the uniqueId
	 * @uml.property  name="uniqueId"
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Override
	public int getUniqueId() {
		return uniqueId;
	}

	/**
	 * Setter.
	 * @param uniqueId  the uniqueId to set
	 * @uml.property  name="uniqueId"
	 */
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
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
	 * @return  the memoryFree
	 * @uml.property  name="memoryFree"
	 */
	public String getMemoryFree() {
		return memoryFree;
	}

	/**
	 * Setter.
	 * @param memoryFree  the memoryFree to set
	 * @uml.property  name="memoryFree"
	 */
	public void setMemoryFree(String memoryFree) {
		this.memoryFree = memoryFree;
	}

	/**
	 * Getter.
	 * @return  the processorLoad
	 * @uml.property  name="processorLoad"
	 */
	public String getProcessorLoad() {
		return processorLoad;
	}

	/**
	 * Setter.
	 * @param processorLoad  the processorLoad to set
	 * @uml.property  name="processorLoad"
	 */
	public void setProcessorLoad(String processorLoad) {
		this.processorLoad = processorLoad;
	}

	/** {@inheritDoc} */
	@Transient
	@Override
	public String[] getJsonData() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Getter.
	 * @return  the recordDate
	 * @uml.property  name="recordDate"
	 */
	public Date getRecordDate() {
		return recordDate;
	}
	
	/**
	 * Getter.
	 * @return sting representation of date.
	 */
	@Transient
	public String getStringRecordDate() {
		DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return format.format(getRecordDate());
	}

	/**
	 * Setter.
	 * @param recordDate  the recordDate to set
	 * @uml.property  name="recordDate"
	 */
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
}
