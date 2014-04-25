package process.web.actions.ajax;

/**
 * Represents single page.
 * @author <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * @param  <T>  type of listed entity
 */
public class AjaxPage<T> {

	/**
	 * Rows in page.
	 * @uml.property  name="rows"
	 * @uml.associationEnd  multiplicity="(0 -1)"
	 */
	private AjaxRow<T> [] rows;
	
	/**
	 * Current page.
	 * @uml.property  name="page"
	 */
	private String page;
	
	/**
	 * Total pages quantity.
	 * @uml.property  name="total"
	 */
	private int total;
	
	/**
	 * Found records.
	 * @uml.property  name="records"
	 */
	private String records;
	
	/**
	 * Constructor.
	 */
	public AjaxPage() {
		
	}
	
	/**
	 * Constructor.
	 * @param arr elements on page.
	 * @param totalPages total quantity of pages
	 * @param foundRecords found record count.
	 * @param page current number page
	 */
	public AjaxPage(AjaxRow<T> [] arr, int page, int totalPages, long foundRecords) {
		rows = arr;
		total = totalPages;
		records = String.valueOf(foundRecords);
		this.page = String.valueOf(page);
	}

	/**
	 * Getter.
	 * @return  kolekcja elementow na stronie.
	 * @uml.property  name="rows"
	 */
	public final AjaxRow<T> [] getRows() {
		return rows;
	}

	/**
	 * Getter.
	 * @return  value of page
	 * @uml.property  name="page"
	 */
	public final String getPage() {
		return page;
	}

	/**
	 * Getter.
	 * @return  value of field.
	 * @uml.property  name="total"
	 */
	public final int getTotal() {
		return total;
	}

	/**
	 * Getter.
	 * @return  value of record.
	 * @uml.property  name="records"
	 */
	public final String getRecords() {
		return records;
	}
	
}
