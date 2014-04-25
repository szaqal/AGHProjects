package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import computation.model.ComputationLogEntry;
import computation.worker.ComputationLogEntryWorker;

import core.utils.PagingInfo;

/**
 * Lists computation log.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxComputationLogAction extends AbstractAjaxPagedListAction<ComputationLogEntry> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 7734257944958719809L;
	
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ComputationLogEntry> pageContent;
	
	/**
	 * list of computation log.
	 */
	private List<ComputationLogEntry> logs;
	
	/**
	 * Performed computation id.
	 * @uml.property  name="performedComputation"
	 */
	private String performedComputation;
	
	
	/**
	 * Constructor.
	 */
	public AjaxComputationLogAction() {
		super();
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ComputationLogEntry> getPageContent() {
		return pageContent;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		logs = locate(ComputationLogEntryWorker.class).retrieveLogs(Integer.valueOf(performedComputation), paginginfo);
		

		Collection<AjaxRow<ComputationLogEntry>> rows = new ArrayList<AjaxRow<ComputationLogEntry>>();
		for(ComputationLogEntry log : logs) {
			rows.add(new AjaxRow<ComputationLogEntry>(log.getJsonData(), log.getUniqueId(), false));
		}
		AjaxRow<ComputationLogEntry> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<ComputationLogEntry>(resultRows, getPage(), paginginfo.getPageCount(getItemsCount()), getItemsCount());
		
		return SUCCESS;
	}
	
	/**
	 * Returns total count of items.
	 * @return item count.
	 */
	private Long getItemsCount() {
		return locate(ComputationLogEntryWorker.class).retrieveLogsCount(Integer.valueOf(performedComputation));
	}

	/**
	 * Getter.
	 * @return  the performedComputation
	 * @uml.property  name="performedComputation"
	 */
	public String getPerformedComputation() {
		return performedComputation;
	}

	/**
	 * Setter.
	 * @param performedComputation  the performedComputation to set
	 * @uml.property  name="performedComputation"
	 */
	public void setPerformedComputation(String performedComputation) {
		this.performedComputation = performedComputation;
	}

}
