package grid.client;

import grid.client.panels.AccountsCombo;

import java.util.ArrayList;
import java.util.List;

public final class Comboboxes {
	private static List<AccountsCombo> instances = new ArrayList<AccountsCombo>();
	
	public static void add(AccountsCombo combo) {
		instances.add(combo);
	}
	
	public static void refreshAll() {
		System.out.println(instances);
		for(AccountsCombo acount:instances) {
			acount.refresh();
		}
	}
}
