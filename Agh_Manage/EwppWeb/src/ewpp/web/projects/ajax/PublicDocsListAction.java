package ewpp.web.projects.ajax;


import ewpp.business.entity.File;
import ewpp.business.entity.ProjectItemContent;
import ewpp.business.workers.FilesWorker;
import ewpp.utils.PagingInfo;
import ewpp.utils.SearchParameters;
import ewpp.utils.StringUtils;
import ewpp.web.ajax.AbstractAjaxPagedListAction;
import ewpp.web.ajax.AjaxPage;
import ewpp.web.ajax.AjaxRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obsluguje ajaxowa liste publicznych dokumentow.
 * @author malczyk.pawel@gmail.com
 *
 */
public class PublicDocsListAction extends AbstractAjaxPagedListAction<File>{

	/** Stala. */
	private static final String ENTITY_ALIAS = "file";

	/** Parametr requestu. */
	private static final String TITLE = "title";

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 6449890521320194358L;
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PublicDocsListAction.class);
	
	/**
	 * Zawartosc strony.
	 */
	private AjaxPage<File> pageContent;
	
	/**
	 * Konstruktor.
	 */
	public PublicDocsListAction() { }
	
	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		LOG.debug("execute () in {}", this.getClass().getName() );	
		PagingInfo paginginfo = getPagingInfo();
		Collection<File> contents = getContents(paginginfo);
		Collection<AjaxRow<File>> rows = new ArrayList<AjaxRow<File>>();
		
		for(File file : contents ) {
			rows.add(new AjaxRow<File>(extractJson(file, TITLE, "contentType"), file.getUniqueId(), true));
		}
		AjaxRow<File> [] resultRows = rows.toArray(new AjaxRow[contents.size()]);
		pageContent = new AjaxPage<File>(resultRows, getPage(), paginginfo.getPageCount(getProjectFilesCount()), getProjectFilesCount());
		return SUCCESS;
	}

	/** {@inheritDoc} */
	@Override
	public AjaxPage<File> getPageContent() {
		return pageContent;
	}
	
	/**
	 * Zwraca ilosc plikow.
	 * @return ilosc plikow
	 */
	private long getProjectFilesCount() {
		if (StringUtils.isEmpty(getTitle())) {
			return locate(FilesWorker.class).retrieveAllProjectFilesCount();
		} else {
			return locate(FilesWorker.class).retrieveAllProjectFilesCount(extractSearchParametersFromRequest());
		}
		
	}
	
	/**
	 * Zwraca kolekcje plikow.
	 * @param paginginfo informacje o stronicowaniu
	 * @return kolekcja plikow
	 */
	private Collection<File> getContents(PagingInfo paginginfo) {
		List<File> files = new ArrayList<File>();
		List<ProjectItemContent> itemContents = Collections.emptyList();
		
		if (StringUtils.isEmpty(getTitle())) {
			itemContents = locate(FilesWorker.class).retrieveAllProjectFiles(paginginfo);
		} else {
			itemContents = locate(FilesWorker.class).retrieveAllProjectFiles(extractSearchParametersFromRequest(), paginginfo);
		}
		
		for (ProjectItemContent projectItemContent : itemContents) {
			files.add((File) projectItemContent);
		}
		return files;
	}
	
	
	/**
	 * Tworzy odpowiednie parametry wyszukiwania 
	 * z danych przychodz�cych z requestu.
	 * @return przygotowane parametry wyszukiwania.
	 */
	private SearchParameters<File> extractSearchParametersFromRequest() {
		SearchParameters<File> searchParams = new SearchParameters<File>(File.class, ENTITY_ALIAS);
		if(ewpp.utils.StringUtils.isNotEmpty(getTitle())) {
			searchParams.addLenientLikeSearchParam(TITLE, getTitle());
		}
		return searchParams;
	}
	
	/**
	 * Zwraca parmaetr title z requestu.
	 * @return wartosc parametru.
	 */
	private String getTitle() {
		String result = StringUtils.EMPTY;
		try {
			result = getReqAttributes().get(TITLE)[0];
		} catch (NullPointerException e) {
			LOG.warn("no search parameter title");
		}
		return result;
	}


}
