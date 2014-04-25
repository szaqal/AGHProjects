package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import computation.model.PerformedComputation;
import computation.worker.PerformedComputationWorker;

import core.utils.PagingInfo;

/**
 * Action that lists performed/performing computations.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxPerformedComputationsList extends AbstractAjaxPagedListAction<PerformedComputation> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5545180744776896761L;
	
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<PerformedComputation> pageContent;
	
	
	/**
	 * Constructor.
	 */
	public AjaxPerformedComputationsList() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		Collection<PerformedComputation> contents = getContents(paginginfo);
		Collection<AjaxRow<PerformedComputation>> rows = new ArrayList<AjaxRow<PerformedComputation>>();
	
		for(PerformedComputation group : contents ) {
			rows.add(new AjaxRow<PerformedComputation>(group.getJsonData(), group.getUniqueId(), false));
		}
		
		AjaxRow<PerformedComputation> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<PerformedComputation>(resultRows, getPage(), paginginfo.getPageCount(getCount()), getCount());

		return SUCCESS;

	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<PerformedComputation> getPageContent() {
		return pageContent;
	}
	
	
	/**
	 * Returns groups count.
	 * @return groups count.
	 */
	private long getCount() {
		return locate(PerformedComputationWorker.class).retrievePerformedComputationCount();
	}
	
	/**
	 * Returns data.
	 * @param pagingInfo info about paging
	 * @return collection of users
	 */
	protected Collection<PerformedComputation> getContents(PagingInfo pagingInfo) {
		return locate(PerformedComputationWorker.class).retrievePerformedComputations(pagingInfo);
	}

}
