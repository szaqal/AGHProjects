package grid.client.panels;

import grid.client.logic.LogicImpl;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class AccountsCombo extends JComboBox {
	
	private static final long serialVersionUID = 8116454343419298623L;
	
	

	public AccountsCombo() {
		super(data());
		setSize(150, 30);
	}
	
	private static String[] data() {
		
		String accounts = new LogicImpl().listAccount();
		String [] temp=accounts.split("#");
		String data[] = new String[temp.length];
		for(int i=0;i<temp.length;i++) {
			data[i] = temp[i].split(":")[0];
		}
		return data;
	}
	
	
	
	public void refresh() {
		
		((DefaultComboBoxModel)getModel()).removeAllElements();
		for(String s : data()) {
			((DefaultComboBoxModel)getModel()).addElement(s);
		}
	}
}
