package analogclock;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

import sun.util.calendar.JulianCalendar;


public class AnalogClock extends JWindow implements Runnable,MouseMotionListener,FocusListener {

	private int hh;
	private int mm;
	private int ss;
	
	private Image image;
	private Graphics gr;
	private Image bgImage;
	private Random random;
	private Robot robot ;
	private int captureDelay = 5;
	
	private boolean mouseDown = false;
	
	private Thread me ;
	
	/*private class Sock extends Thread{
		public void run() {
			try {
				System.out.println("Waiting .... ");
				ServerSocket ss = new ServerSocket(5050);
				Socket s = ss.accept();
				me = null;
				s.close();
				System.out.println("Stoping Clock ....");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};*/
	
	public AnalogClock() {
		
		//super("Analog Clock v1.0 -");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		addMouseMotionListener(this);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				mouseDown = true;
			}
			public void mouseReleased(MouseEvent e) {
				mouseDown = false;
			}
		});
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println("w:"+d.width+",h:"+d.height);
		//setBounds(400,400,200,200);
		int width = 200;
		int height = 200;
		setLocation(d.width-width,0);
		setSize(width,height);
		//setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(true);
		
		initial_setup();
		me = new Thread(this);
		me.start();
		
		//Sock sock = new Sock();
		//sock.start();
	}
	
	public int getHWND()
	{
		int hwnd = 0;
		
		return 0;
	}
	
	private int getRandom(int range)
	{
		if(random == null)
			random = new Random(Calendar.getInstance().getTimeInMillis());
		random.setSeed(Calendar.getInstance().getTimeInMillis());
		return random.nextInt(range);
	}
	
	private Point moveP;
	public void mouseMoved(MouseEvent e) {
		moveP = e.getPoint();
	}
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		int x = getX()+p.x-moveP.x;
		int y = getY()+p.y-moveP.y;
		setLocation(x,y);
		update(getGraphics());
	}
	public void focusGained(FocusEvent e) {
		captureScreen();
		System.out.println("Focus Gained");
	}
	public void focusLost(FocusEvent e) {
	}
	
	private void captureScreen()
	{
		try {
		 if(!mouseDown){
			if(robot == null)
				robot = new Robot();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rect = new Rectangle(0,0,d.width,d.height);
			Point p = getLocation();
			setLocation(11111,0);
			bgImage = robot.createScreenCapture(rect);
			setLocation(p);
			Graphics g = getGraphics();
			update(g);
			
		 }
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void initial_setup() {
		Calendar cal = Calendar.getInstance();
		hh = cal.get(Calendar.HOUR);
		mm = cal.get(Calendar.MINUTE);
		ss = cal.get(Calendar.SECOND);
		
		if(robot == null)
		{
			captureScreen();			
		}
	}
	
	public void run() {
		while(me != null)
		{
			initial_setup();
			captureDelay--;
			if(captureDelay < 0) {captureScreen();captureDelay=300;}
			repaint();
			try{Thread.sleep(30);}catch(Exception e){}
		}
	}
	
	public void update(Graphics g) {
		
		if(image == null){
			image = createImage(getWidth(),getHeight());
			gr = image.getGraphics();
		}
		else if(image.getWidth(this)!=getWidth() || image.getHeight(this)!=getHeight()){
			image = createImage(getWidth(),getHeight());
			gr = image.getGraphics();			
		}
		
		Graphics2D g2d = (Graphics2D)gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		//super.update(g);
		int width  = getWidth();
		int height = getHeight();
		int cx = width/2;
		int cy = height/2+10;
		
		//g2d.setColor(Color.WHITE);
		//g2d.fillRect(0,0,width,height);
		
		//draw background image
		if(bgImage != null)
			g2d.drawImage(bgImage,0,0,getWidth(),getHeight(),
	                  getX(),getY(),getX()+getWidth(),getY()+getHeight(),this);
			//g2d.drawImage(bgImage,0,0,width,height,this);
		
		int cw = width*5/6;
		int ch = height*5/6;
		g2d.setColor(Color.BLACK);
		g2d.drawOval(cx-cw/2,cy-ch/2,cw,ch);
		g2d.drawOval(cx-cw/2+4,cy-ch/2+4,cw-8,ch-8);
		g2d.drawOval(cx-6,cy-6,12,12);
		g2d.drawOval(cx-8,cy-8,16,16);
		
		//hour
		int deg = 30;
		int rdeg = (hh) * deg-90;
		double rad = rdeg * Math.PI/180;
		int sx = (int)(cx + (cw/4)*Math.cos(rad));
		int sy = (int)(cy + (ch/4)*Math.sin(rad));
		int ccx= (int)(cx + 10*Math.cos(rad));
		int ccy= (int)(cy + 10*Math.sin(rad));
		g2d.setColor(Color.BLUE);
		g2d.drawLine(ccx,ccy,sx,sy);
		g2d.drawString(""+hh,sx,sy);
		
		//minutes
		deg = 6;
		rdeg = mm * deg-90;
		rad = rdeg * Math.PI/180;
		sx = (int)(cx + (cw/3)*Math.cos(rad));
		sy = (int)(cy + (ch/3)*Math.sin(rad));
		ccx= (int)(cx + 10*Math.cos(rad));
		ccy= (int)(cy + 10*Math.sin(rad));
		g2d.setColor(Color.BLUE);
		g2d.drawLine(ccx,ccy,sx,sy);
		g2d.drawString(""+mm,sx,sy);
		
		//seconds
		rdeg = ss * deg-90;
		rad = rdeg * Math.PI/180;	
		sx = (int)(cx + (cw/2.6)*Math.cos(rad));
		sy = (int)(cy + (ch/2.6)*Math.sin(rad));
		ccx= (int)(cx + 8*Math.cos(rad));
		ccy= (int)(cy + 8*Math.sin(rad));
		g2d.setColor(Color.RED);
		//g2d.drawLine(ccx,ccy,sx,sy);
		//g2d.drawString(""+ss,sx,sy);
	
		//draw seconds arcs
		tdeg=(tdeg+5)%360;
		int ccw = (int)(width/1.5);
		int cch = (int)(height/1.5);
		g2d.setColor(new Color(0,255,0,60));//255,0,0,60));
		g2d.fillArc(cx-ccw/2,cy-cch/2,ccw,cch,tdeg,120);
		g2d.fillArc(cx-ccw/2,cy-cch/2,ccw,cch,tdeg+180,120);
		
//		ccw = (int)(width/2);
//		cch = (int)(height/2);
//		g2d.setColor(new Color(0,255,0,60));
//		g2d.fillArc(cx-ccw/2,cy-cch/2,ccw,cch,-(rdeg+90),120);
//		g2d.fillArc(cx-ccw/2,cy-cch/2,ccw,cch,-(rdeg+270),120);
		
		g2d.setColor(Color.BLACK);
		String time = hh +":"+ mm +":"+ ss;
		g2d.drawString(time,(int)(width/2.5),height-height/6);
		
		g.drawImage(image,0,0,this);
	}
	int tdeg=0;
	public static void main(String[] args) {
		AnalogClock clock = new AnalogClock();
	}
}
