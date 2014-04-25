package ewpp.web.ajax;

import ewpp.utils.PagingInfo;
import ewpp.utils.StringUtils;
import ewpp.web.AbstractEwppAction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstraktyna g��wna klasa stronicowanych list ajaxowych.
 * @author malczyk.pawel@gmail.com
 * @param <T> 
 */
public abstract class AbstractAjaxPagedListAction<T> extends AbstractEwppAction {
	
	/**
	 * Parametr requestu przecowujacy biezaca strone.
	 */
	private static final String PAGE_REQ_PARAM = "page";
	
	/**
	 * Parametr requestu przecowujacy limit wierszy na stronie.
	 */
	private static final String ROWS_REQ_PARAM = "rows";
	
	/**
	 * Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractAjaxPagedListAction.class);

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -9215025858923443374L;

	
	/**
	 * Zwraca poedyncza strone wynikow.
	 * @return pojdyncza strona do wyswietlenia.
	 */
	public abstract AjaxPage<T> getPageContent();

	/**
	 * Getter.
	 * @return numer strony
	 */
	public final int getPage() {
		return getReqAttributes().containsKey(PAGE_REQ_PARAM)?Integer.parseInt(getReqAttributes().get(PAGE_REQ_PARAM)[0]):0;
	}


	/**
	 * Getter.
	 * @return ilosc wierszy
	 */
	public final int getRows() {
		return getReqAttributes().containsKey(ROWS_REQ_PARAM)?Integer.parseInt(getReqAttributes().get(ROWS_REQ_PARAM)[0]):0;
	}
	
	/**
	 * Zwraca informacje o stronicowaniu.
	 * @return info o stronicowaniu.
	 */
	public final PagingInfo getPagingInfo() {
		return new PagingInfo(getPage(), getRows());
	}
	
	/**
	 * Tworzy dablice dla JSon z zadanego obiektu i okreslonych wlasciciwosci.
	 * @param bean bean
	 * @param props lista wlasciwosci
	 * @return tablica wartoscio dla zadanych wlasciwosci.
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
