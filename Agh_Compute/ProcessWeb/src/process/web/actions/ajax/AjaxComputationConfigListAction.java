package process.web.actions.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import computation.model.ComputationConfiguration;
import computation.worker.ComputationConfgurationWorker;

import core.utils.PagingInfo;

/**
 * Ajax list of computation configs.
 * @author  malczyk.pawel@gmail.com
 */
public class AjaxComputationConfigListAction extends AbstractAjaxPagedListAction<ComputationConfiguration> {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -3746335046989154599L;
	
	/**
	 * Single page.
	 * @uml.property  name="pageContent"
	 * @uml.associationEnd  
	 */
	private AjaxPage<ComputationConfiguration> pageContent;
	
	/**
	 * List of computation configurations.
	 */
	private List<ComputationConfiguration> configurations;
	
	/**
	 * Constructor.
	 */
	public AjaxComputationConfigListAction() {
		super();
	}

	/**
	 * {@inheritDoc} 
	 * @uml.property  name="pageContent"
	 */
	@Override
	public AjaxPage<ComputationConfiguration> getPageContent() {
		return pageContent;
	}
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		PagingInfo paginginfo = getPagingInfo();
		configurations = locate(ComputationConfgurationWorker.class).retrieveComputationConfigurations();
		
		Collection<AjaxRow<ComputationConfiguration>> rows = new ArrayList<AjaxRow<ComputationConfiguration>>();
		for(ComputationConfiguration config : configurations) {
			rows.add(new AjaxRow<ComputationConfiguration>(config.getJsonData(), config.getUniqueId(), false));
		}
		AjaxRow<ComputationConfiguration> [] resultRows = rows.toArray(new AjaxRow [rows.size()]);
		pageContent = new AjaxPage<ComputationConfiguration>(resultRows, getPage(), paginginfo.getPageCount(configurations.size()), configurations.size());
		
		return SUCCESS;
	}

}
