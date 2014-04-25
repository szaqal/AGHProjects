package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.model.XslTransform;
import core.utils.PagingInfo;
import core.workers.TransformWorker;

/**
 * Transforms ajax list.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxTransformListAction extends AbstractAjaxPagedListAction<XslTransform> {
	
	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 5513200685910404388L;
	
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<XslTransform> pageContent;
	
	/**
	 * List of uploaded validation schemas.
	 */
	private List<XslTransform> schemas;

	/**
	 * Constructor.
	 */
	public AjaxTransformListAction() {
		super();
	}

	/**
	 * Returns page content.
	 * @return single result page
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<XslTransform> getPageContent() {
		return pageContent;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		schemas = locate(TransformWorker.class).retrieveTransforms(paginginfo);
		
		Collection<AjaxRow<XslTransform>> rows = new ArrayList<AjaxRow<XslTransform>>();
		
		for(XslTransform schema : schemas) {
			rows.add(new AjaxRow<XslTransform>(schema.getJsonData(), schema.getUniqueId(), false));
		}
		AjaxRow<XslTransform> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<XslTransform>(resultRows, getPage(), paginginfo.getPageCount(getTransformsCount()), getTransformsCount());
		
		return SUCCESS;
	}
	
	/**
	 * Returns groups count.
	 * @return groups count.
	 */
	private long getTransformsCount() {
		return locate(TransformWorker.class).retrieveTransformCount();
	}

}
