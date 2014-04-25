package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import computation.model.ComputationPackage;
import computation.worker.ComputationPackageWorker;

import core.utils.PagingInfo;


/**
 * Lists uploaded packages.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class AjaxPackagesListAction extends AbstractAjaxPagedListAction<ComputationPackage>{

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 9021905310684688971L;
	
	/**
	 * Single page of registered nodes.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ComputationPackage> pageContent;
	
	/**
	 * Computation packages list.
	 */
	private List<ComputationPackage> packages;
	
	/**
	 * 
	 * Constructor.
	 */
	public AjaxPackagesListAction() {
		super();
	}
	
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		packages = locate(ComputationPackageWorker.class).retrieveComputationPackages();
		
		Collection<AjaxRow<ComputationPackage>> rows = new ArrayList<AjaxRow<ComputationPackage>>();
		for(ComputationPackage compPackage : packages) {
			rows.add(new AjaxRow<ComputationPackage>(compPackage.getJsonData(), compPackage.getUniqueId(), false));
		}
		AjaxRow<ComputationPackage> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<ComputationPackage>(resultRows, getPage(), paginginfo.getPageCount(packages.size()), packages.size());
		
		return SUCCESS;
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ComputationPackage> getPageContent() {
		return pageContent;
	}

}
