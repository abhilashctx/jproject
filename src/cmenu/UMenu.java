package cmenu;

import java.awt.MenuItem;
import java.awt.event.ActionListener;

public class UMenu {
	
	public static MenuItem newMenuItem(String name, ActionListener al){
		MenuItem mi = new MenuItem(name);
		if(al!=null){
			mi.addActionListener(al);
		}
		return mi;
	}

}
