package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import auth.GroupsWorker;
import auth.model.Group;
import core.utils.PagingInfo;

/**
 * Ajax list of groups.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxGroupsListAction extends AbstractAjaxPagedListAction<Group> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 8394194870262927990L;
	
	/**
	 * Prepared pageContent.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<Group> pageContent;
	
	/**
	 * Constructor.
	 */
	public AjaxGroupsListAction() { }
	
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		Collection<Group> contents = getContents(paginginfo);
		Collection<AjaxRow<Group>> rows = new ArrayList<AjaxRow<Group>>();
	
		for(Group group : contents ) {
			rows.add(new AjaxRow<Group>(group.getJsonData(), group.getUniqueId(), false));
		}
		
		AjaxRow<Group> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<Group>(resultRows, getPage(), paginginfo.getPageCount(getGroupCount()), getGroupCount());

		return SUCCESS;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<Group> getPageContent() {
		return pageContent;
	}
	
	
	/**
	 * Returns groups count.
	 * @return groups count.
	 */
	private long getGroupCount() {
		return locate(GroupsWorker.class).retrieveGroupsCount();
	}
	
	/**
	 * Returns data.
	 * @param pagingInfo info about paging
	 * @return collection of users
	 */
	protected Collection<Group> getContents(PagingInfo pagingInfo) {
		return locate(GroupsWorker.class).retrieveGroups(pagingInfo);
	}

}
