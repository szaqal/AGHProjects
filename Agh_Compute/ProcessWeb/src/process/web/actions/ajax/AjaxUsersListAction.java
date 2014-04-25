package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;

import auth.UsersWorker;
import auth.model.User;
import core.utils.PagingInfo;

/**
 * Ajax users list.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxUsersListAction extends AbstractAjaxPagedListAction<User> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1477771195468770235L;
	
	/**
	 * Prepared page content.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<User> pageContent;
	
	/**
	 * 
	 * Constructor.
	 */
	public AjaxUsersListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		Collection<User> contents = getContents(paginginfo);
		Collection<AjaxRow<User>> rows = new ArrayList<AjaxRow<User>>();
	
		for(User user : contents ) {
			rows.add(new AjaxRow<User>(user.getJsonData(), user.getUniqueId(), false));
		}
		
		AjaxRow<User> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		
		pageContent = new AjaxPage<User>(resultRows, getPage(), paginginfo.getPageCount(getUsersCount()), getUsersCount());

		return SUCCESS;
	}
	
	/**
	 * Returns data.
	 * @param pagingInfo info about paging
	 * @return collection of users
	 */
	protected Collection<User> getContents(PagingInfo pagingInfo) {
		return locate(UsersWorker.class).retrieveUsers(pagingInfo);
	}
	
	
	/**
	 * Returns users count.
	 * @return users count.
	 */
	protected long getUsersCount() {
		return locate(UsersWorker.class).retrieveUsersCount();
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<User> getPageContent() {
		return pageContent;
	}
	
	


}
