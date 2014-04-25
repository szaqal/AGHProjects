package turistcompany.gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * @author malczyk.pawel@gmail.com
 *	Listbox domyslenie po zainicjowniu jest nie zmienialny !!!!!!
 *	ta klasa umozliwia dynamiczne dodawanie elementow
 */
public class MutableList extends JList {

	private static final long serialVersionUID = -8615070424814412923L;

	public MutableList(){
		super(new DefaultListModel());
	}
	
	public void addList(String[] list){
		for(String s:list){
			this.getContents().addElement(s);
		}
	}
	
    public DefaultListModel getContents() {
    	return (DefaultListModel)getModel();
    }	
	
}
