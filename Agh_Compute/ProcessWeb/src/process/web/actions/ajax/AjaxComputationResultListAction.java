package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import computation.model.ComputationResult;
import computation.worker.ComputationResultWorker;

import core.utils.PagingInfo;

/**
 * Action lists computation results.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxComputationResultListAction extends AbstractAjaxPagedListAction<ComputationResult> {

	/**
	 * Serial. 
	 */
	private static final long serialVersionUID = -6203689706011837049L;
	
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ComputationResult> pageContent;
	
	/**
	 * Constructor.
	 */
	public AjaxComputationResultListAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		Collection<ComputationResult> contents = getContents(paginginfo);
		Collection<AjaxRow<ComputationResult>> rows = new ArrayList<AjaxRow<ComputationResult>>();
	
		for(ComputationResult computationResult : contents ) {
			rows.add(new AjaxRow<ComputationResult>(computationResult.getJsonData(), computationResult.getUniqueId(), false));
		}
		
		AjaxRow<ComputationResult> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<ComputationResult>(resultRows, getPage(), paginginfo.getPageCount(getCount()), getCount());

		return SUCCESS;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ComputationResult> getPageContent() {
		return pageContent;
	}
	
	
	
	/**
	 * Returns groups count.
	 * @return groups count.
	 */
	private long getCount() {
		return locate(ComputationResultWorker.class).resultsCount(Integer.valueOf(getSessionData().getUserId()));
	}
	
	/**
	 * Returns data.
	 * @param pagingInfo info about paging
	 * @return collection of users
	 */
	protected Collection<ComputationResult> getContents(PagingInfo pagingInfo) {
		return locate(ComputationResultWorker.class).retrieveResults(pagingInfo, Integer.valueOf(getSessionData().getUserId()));
	}

}
