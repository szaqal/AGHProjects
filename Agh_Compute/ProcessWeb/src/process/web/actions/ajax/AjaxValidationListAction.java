package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import computation.model.ValidationSchema;
import computation.worker.ValidationWorker;

import core.utils.PagingInfo;

/**
 * Ajax list.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxValidationListAction extends AbstractAjaxPagedListAction<ValidationSchema> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -1486222225405243689L;
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ValidationSchema> pageContent;
	
	/**
	 * List of uploaded validation schemas.
	 */
	private List<ValidationSchema> schemas;
	
	/**
	 * Constructor.
	 */
	public AjaxValidationListAction() {
		super();
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ValidationSchema> getPageContent() {
		return pageContent;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		schemas = locate(ValidationWorker.class).retrieveSchemas(paginginfo);
		
		Collection<AjaxRow<ValidationSchema>> rows = new ArrayList<AjaxRow<ValidationSchema>>();
		
		for(ValidationSchema schema : schemas) {
			rows.add(new AjaxRow<ValidationSchema>(schema.getJsonData(), schema.getUniqueId(), false));
		}
		AjaxRow<ValidationSchema> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<ValidationSchema>(resultRows, getPage(), paginginfo.getPageCount(schemas.size()), schemas.size());
		
		return SUCCESS;
	}

}
