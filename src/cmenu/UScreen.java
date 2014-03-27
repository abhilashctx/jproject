package cmenu;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class UScreen {

	public static BufferedImage getScreenShot(){
		BufferedImage bimg=null;
		try{
			Robot robot = new Robot();
			bimg = robot.createScreenCapture(getScreenSize());
		}catch(Exception e){}
		return bimg;
	}
	
	public static BufferedImage getScreenShot(Rectangle screenArea){
		BufferedImage bimg=null;
		try{
			Robot robot = new Robot();
			bimg = robot.createScreenCapture(screenArea);
		}catch(Exception e){}
		return bimg;
	}
	
	public static Rectangle getScreenSize(){
		return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
	public static void saveImage(String iname, BufferedImage bimg){
		try{
			if(iname==null){System.out.println("saveImage: name is null");return;}
			if(bimg==null){System.out.println("saveImage: image is null");return;}
			ImageIO.write(bimg, "png", new File(iname));
		}catch(Exception e){}
	}
}
