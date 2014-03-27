package cmenu;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMenuMain implements ActionListener{

	public static void main(String[] args) {
		CMenuMain cm = new CMenuMain();
		cm.cmain(args);
		//System.out.println(System.getProperties());
		//System.out.println(new SimpleDateFormat("yyyyMMddhhmm").format(new Date()));
		//System.out.println(USys.getTimeImageName());
		//System.out.println(System.getProperty("user.home"));
	}
	
	private TrayIcon trayIcon;
	
	public void cmain(String[] args) {
		trayIcon = USys.getTray("cmenu/bulb.gif", "cmenu test");
		if(trayIcon==null) return;
		
		MenuItem miScrShot = UMenu.newMenuItem("ScreenShot",this);
		MenuItem miExit = UMenu.newMenuItem("Exit",this);
		
		PopupMenu popup = new PopupMenu();
		popup.add(miScrShot);
		popup.add(miExit);
		
		trayIcon.setPopupMenu(popup);
		USys.addSysTray(trayIcon);
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals("Exit")){
			USys.removeSysTray(trayIcon);
			USys.exit();
		}else if(action.equals("ScreenShot")){
			UScreen.saveImage(USys.getTimeImageName(), UScreen.getScreenShot());
		}
	}
}
