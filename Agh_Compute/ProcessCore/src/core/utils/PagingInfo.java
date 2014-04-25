package core.utils;

/**
 * Contains information about paging.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 */
public class PagingInfo {

	/**
	 * Number of currentPage.
	 * @uml.property  name="currentPage"
	 */
	private int currentPage;
	
	/**
	 * Number of rows per page.
	 * @uml.property  name="rowsPerPage"
	 */
	private int rowsPerPage;
	
	/**
	 * Column for which sorting is made.
	 */
	private String sortBy;
	
	/**
	 * Sorting type asc/desc.
	 */
	private String sortType;
	
	/**
	 * Constructor.
	 */
	private PagingInfo() { }
	
	/**
	 * Constructor.
	 * @param page
	 * 		current page
	 * @param rows
	 * 		current rows count
	 * @param sortBy
	 * 		column by which sorting is made
	 * @param sortType 
	 * 		sorting type
	 */
	public PagingInfo(int page, int rows, String sortBy, String sortType){
		this();
		/*jquery numeruje strony od 1 a nie od 0*/
		currentPage = page -1; 
		rowsPerPage = rows;
		this.sortBy = sortBy;
		this.sortType = sortType;
	}

	/**
	 * Getter.
	 * @return  value.
	 * @uml.property  name="currentPage"
	 */
	public final int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Getter.
	 * @return  value.
	 * @uml.property  name="rowsPerPage"
	 */
	public final int getRowsPerPage() {
		return rowsPerPage;
	}
	
	/**
	 * Get first result for page.
	 * @return result
	 */
	public final int getFirstResult() {
		return currentPage * rowsPerPage;
	}
	
	/**
	 * Computes page count for collection size.
	 * @param collectionSize collection size
	 * @return page count
	 */
	public final int getPageCount(long collectionSize) {
		long pages = (int) (collectionSize / getRowsPerPage());
		return (int) ++pages;
	}

	/**
	 * Getter.
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * Setter.
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	/**
	 * Returns true when there are appropriate request parameter passed.
	 * @return true/false
	 */
	public boolean isSortingEnabled() {
		return getSortBy() != null && getSortType() != null;
	}

	/**
	 * Getter.
	 * @return the sortType
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * Setter.
	 * @param sortType the sortType to set
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
