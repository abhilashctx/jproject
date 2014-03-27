package cmenu;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class USys {

	public static URL getResource(String name){
		return ClassLoader.getSystemResource(name);
	}
	
	public static TrayIcon getTray(String imgName, String tooltip){
		try{
			URL iconURL = USys.getResource(imgName);
			if(iconURL==null) return null;
			TrayIcon trayIcon = new TrayIcon(ImageIO.read(iconURL),tooltip);
			trayIcon.setImageAutoSize(true);
			return trayIcon;
		}catch(Exception e){}
		return null;
	}
	
	public static SystemTray getSysTray(){
		return SystemTray.getSystemTray();
	}
	
	public static void addSysTray(TrayIcon trayIcon){
		try{
			getSysTray().add(trayIcon);
		}catch(Exception e){}
	}
	
	public static void removeSysTray(TrayIcon trayIcon){
		getSysTray().remove(trayIcon);
	}
	
	public static String getTimestampName(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		String name = sdf.format(new Date());
		return name;
	}
	
	public static String getTimeImageName(){
		//return System.getProperty("user.home")+File.separator+getTimestampName()+".png";
		return "c:"+File.separator+getTimestampName()+".png";
	}
	
	public static void exit(){
		System.exit(0);
	}
}
