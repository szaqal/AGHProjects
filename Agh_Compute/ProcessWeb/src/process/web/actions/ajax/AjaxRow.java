package process.web.actions.ajax;

import core.utils.ListUtils;

/**
 * Represents single row in table.
 * @author  <a href="mailto:malczyk.pawel@gmail.com">malczyk.pawel@gmail.com</a>
 * @param  <T>  type of listed entity
 */
public class AjaxRow<T> {
	
	/**
	 * Row cells.
	 * @uml.property  name="cell"
	 */
	private String[] cell;
	
	/**
	 * Row identifier.
	 * @uml.property  name="id"
	 */
	private String id;
	
	/**
	 * Constructor.
	 */
	public AjaxRow() { }
	
	/**
	 * Constructor.
	 * @param cell single cell
	 * @param id row identifier
	 * @param appendEmpty adds empty cell 
	 * TODO: to nalezy pozniej zmienic.
	 */
	public AjaxRow(String [] cell, long id, boolean appendEmpty) {
		
		if(!appendEmpty) {
			this.cell = cell;
		} else {
			this.cell = ListUtils.shitArrayElementsRight(cell, 1);
		}
		
		this.id = String.valueOf(id);
	}
	
	/**
	 * Constructor.
	 * @param cell single cell
	 * @param id row identifier
	 * @param appendEmpty adds empty cell 
	 * TODO: to nalezy pozniej zmienic.
	 */
	public AjaxRow(String [] cell, String id, boolean appendEmpty) {
		
		if(!appendEmpty) {
			this.cell = cell;
		} else {
			this.cell = ListUtils.shitArrayElementsRight(cell, 1);
		}
		
		this.id = String.valueOf(id);
	}

	/**
	 * Getter.
	 * @return  value.
	 * @uml.property  name="cell"
	 */
	public final String[] getCell() {
		return cell;
	}

	/**
	 * Getter.
	 * @return  value
	 * @uml.property  name="id"
	 */
	public final String getId() {
		return id;
	}

}
