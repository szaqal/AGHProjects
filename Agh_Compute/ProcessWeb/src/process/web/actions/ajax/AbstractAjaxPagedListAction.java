package process.web.actions.ajax;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import process.web.actions.AbstractProcessAction;
import core.utils.PagingInfo;
import core.utils.StringUtils;

/**
 * Base class for all ajax paged list.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * @param <T> type of listed entity
 */
public abstract class AbstractAjaxPagedListAction<T> extends AbstractProcessAction {

	/**
	 * Request parameter containing current page.
	 */
	private static final String PAGE_REQ_PARAM = "page";
	
	/**
	 * Request parameter containing rows.
	 */
	private static final String ROWS_REQ_PARAM = "rows";
	
	/**
	 * Sort by request parameter.
	 */
	private static final String SORT_BY = "sidx";
	
	/**
	 * Sort type request parameter.
	 */
	private static final String SORT_TYPE = "sord";
	
	
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -5462767930862652160L;
	
	/**
	 * Constructor.
	 */
	public AbstractAjaxPagedListAction() {
		super();
	}
	
	/**
	 * Returns content for page.
	 * @return single page for content.
	 */
	public abstract AjaxPage<T> getPageContent();
	
	/**
	 * Getter.
	 * @return page number
	 */
	public final int getPage() {
		return getReqAttributes().containsKey(PAGE_REQ_PARAM)?Integer.parseInt(getReqAttributes().get(PAGE_REQ_PARAM)[0]):0;
	}


	/**
	 * Getter.
	 * @return row count
	 */
	public final int getRows() {
		return getReqAttributes().containsKey(ROWS_REQ_PARAM)?Integer.parseInt(getReqAttributes().get(ROWS_REQ_PARAM)[0]):0;
	}
	
	/**
	 * Returns sort by request parameter.
	 * @return parameter value
	 */
	public final String getSortBy() {
		return getReqAttributes().containsKey(SORT_BY) ? getReqAttributes().get(SORT_BY)[0]:null;
	}
	
	/**
	 * Returns sort type request parameter.
	 * @return parameter value
	 */
	public final String getSortType() {
		return getReqAttributes().containsKey(SORT_TYPE) ? getReqAttributes().get(SORT_TYPE)[0]:null;
	}
	
	/**
	 * Returns paging info.
	 * @return paging info.
	 */
	public final PagingInfo getPagingInfo() {
		return new PagingInfo(getPage(), getRows(), getSortBy(), getSortType());
	}
	
	/**
	 * Creates JSON for selected object properties.
	 * @param bean bean
	 * @param props properties
	 * @return table properties as json.
	 */
	protected String [] extractJson( T bean, String... props) {
		List<String> result = new ArrayList<String>();
		for(String property : props) {
			try {
				result.add(BeanUtils.getProperty(bean, property));
			} catch (IllegalAccessException e) {
				result.add(StringUtils.EMPTY);
				LOG.error(e.getMessage());
			} catch (InvocationTargetException e) {
				result.add(StringUtils.EMPTY);
				LOG.error(e.getMessage());
			} catch (NoSuchMethodException e) {
				result.add(StringUtils.EMPTY);
				LOG.error(e.getMessage());
			}
		}
		return result.toArray(new String[result.size()]);
	}


}
