package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import computation.model.NodeMapping;
import computation.worker.NodeMappingWorker;

import core.utils.PagingInfo;

/**
 * Ajax mappings list action.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxMappingsListAction extends AbstractAjaxPagedListAction<NodeMapping>{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 3223932072011752980L;
	
	/**
	 * Ajax page content.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<NodeMapping> pageContent;
	
	
	/**
	 * Constructor.
	 */
	public AjaxMappingsListAction() {
		super();
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		String userId = getSessionData().getUserId();
		Collection<NodeMapping> contents = locate(NodeMappingWorker.class).retrieveByOwner(Integer.valueOf(userId), null);
		
		Collection<AjaxRow<NodeMapping>> rows = new ArrayList<AjaxRow<NodeMapping>>();
		
		for(NodeMapping mapping : contents ) {
			rows.add(new AjaxRow<NodeMapping>(mapping.getJsonData(), mapping.getUniqueId(), false));
		}
		
		AjaxRow<NodeMapping> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<NodeMapping>(resultRows, getPage(), paginginfo.getPageCount(getMappingCount()), getMappingCount());
		
		return SUCCESS;
	}
	
	
	/**
	 * Returns groups count.
	 * @return groups count.
	 */
	private long getMappingCount() {
		return locate(NodeMappingWorker.class).retrieveByOwnerCount(Integer.valueOf(getSessionData().getUserId()));
	}
	

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<NodeMapping> getPageContent() {
		return pageContent;
	}

	/**
	 * Setter.
	 * @param pageContent  the pageContent to set
	 * @uml.property  name="pageContent"
	 */
	public void setPageContent(AjaxPage<NodeMapping> pageContent) {
		this.pageContent = pageContent;
	}

}
