package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import computation.model.Computation;
import computation.worker.ComputationWorker;

import core.utils.PagingInfo;

/**
 * Lists available created applications.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxComputationsListAction extends AbstractAjaxPagedListAction<Computation>{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5726228260719689702L;
	
	/**
	 * Computations on page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<Computation> pageContent;
	
	/**
	 * List of found computations.
	 */
	private List<Computation> computations;
	
	/**
	 * Constructor.
	 */
	public AjaxComputationsListAction() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		computations= locate(ComputationWorker.class).retrieveComputations(paginginfo);
		
		Collection<AjaxRow<Computation>> rows = new ArrayList<AjaxRow<Computation>>();
		for(Computation comp : computations) {
			rows.add(new AjaxRow<Computation>(comp.getJsonData(), comp.getUniqueId(), false));
		}
		AjaxRow<Computation> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<Computation>(resultRows, getPage(), paginginfo.getPageCount(computations.size()), computations.size());
		
		return SUCCESS;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<Computation> getPageContent() {
		return pageContent;
	}

	/**
	 * Setter.
	 * @param pageContent  the pageContent to set
	 * @uml.property  name="pageContent"
	 */
	public void setPageContent(AjaxPage<Computation> pageContent) {
		this.pageContent = pageContent;
	}
	

}
