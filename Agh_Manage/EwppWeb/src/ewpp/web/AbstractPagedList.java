package ewpp.web;

import java.util.Collection;

/**
 * Abstrakcyjna akcja do list.
 * @author malczyk.pawel@gmail.com
 *
 * @param <T> listowany typ
 */
public abstract class AbstractPagedList<T> extends AbstractEwppAction {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = -7973521729411521400L;


	/** Biezaca strona. */
	private int page;

	/**
	 * Zwraca elementy kolekcji.
	 * @return zwarca liste elementow tabeli
	 */
	protected abstract Collection<T> getContents();

	/** {@inheritDoc} */
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**
	 * Pobiera strone listy.
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Ustawia strone.
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

}
