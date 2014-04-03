package cmenu;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CView extends Window implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		//CView cView = new CView();
		new CView();
	}
	
	private boolean active;
	private Thread me;
	private BufferedImage backImage;
	private int backX,backY;
	private int cwidth,cheight;
	public CView() {
		super(new Frame());
		backImage = UScreen.getScreenShot();
		Rectangle screen = UScreen.getScreenSize();//System.out.println("screen:"+screen);
		cwidth = 200;
		cheight = 200;
		setSize(cwidth, cheight);
		backX=screen.width/2-cwidth/2;
		backY=screen.height/2-cheight/2;
		setLocation(backX,backY);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		active=true;
		me = new Thread(this);
		me.start();
	}
	protected void processKeyEvent(KeyEvent e) {
		System.out.println("keyevent");
		if(e.getID()==KeyEvent.KEY_PRESSED){
			System.out.println("key pressed");
		}
		super.processKeyEvent(e);
	}
	protected void processMouseEvent(MouseEvent e) {
		//System.out.println("mouse");
		switch(e.getID()){
		case MouseEvent.MOUSE_ENTERED:
			System.out.println("enter");
			break;
		case MouseEvent.MOUSE_EXITED:
			System.out.println("exit");
			active=false;
			break;
		case MouseEvent.MOUSE_CLICKED:
			if(e.getButton()==MouseEvent.BUTTON3){
				//USys.exit();
				active=false;
			}
			break;
		}
		super.processMouseEvent(e);
	}
	public void update(Graphics g) {
		//super.update(g);
	}
	public void run(){
		try{Thread.sleep(500);}catch(Exception e){}
		setVisible(true);
		
		int cx=cwidth/2;
		int cy=cheight/2;
		
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("name");list.add("parent");
		list.add("menu1");list.add("menu2");list.add("menu3");
		list.add("menu4");list.add("menu5");list.add("menu6");
		//list.add("menu7");list.add("menu8");list.add("menu9");
		
		double dangle = (360.0/(list.size()-2));
		double drad = (dangle * Math.PI)/180;
		double rad=0;
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i=2;i<list.size();i++){
			int x=(int)(40 * Math.cos(rad) + cx);
			int y=(int)(40 * Math.sin(rad) + cy);
			points.add(new Point(x,y));
			rad+=drad;
		}
		
		createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		
		Color colorBlackT = new Color(0, 0, 0, 20);
		
		while(active){
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			drawTransparentLayer(g);
			drawMenuAreaLayer(g, colorBlackT, cx, cy);
			drawMenuLayer(g, cx, cy, points,list);
			
			g.dispose();
			bs.show();
			try{Thread.sleep(30);}catch(Exception e){}
		}
		USys.exit();
	}
	
	private void drawTransparentLayer(Graphics2D g){
		g.drawImage(backImage,0,0,cwidth,cheight,backX,backY,backX+cwidth,backY+cheight, this);
	}
	
	private void drawMenuAreaLayer(Graphics2D g,Color colorBlackT,int cx,int cy){
		g.setColor(colorBlackT);
		g.fillOval(cx-50, cy-50, 100, 100);
	}
	
	private void drawMenuLayer(Graphics2D g, int cx,int cy,ArrayList<Point> points,ArrayList<Object> list){
		
		g.setColor(Color.GRAY);
		g.fillOval(cx-10, cy-10, 20, 20);
		String root = (String)list.get(0);
		g.setColor(Color.WHITE);
		g.drawString(root, cx-10, cy-10);
		
		g.setColor(Color.RED);
		for(int i=0;i<points.size();i++){
			Point p = points.get(i);
			g.fillOval(p.x-5, p.y-5, 10, 10);
			g.setColor(Color.BLACK);
		}
	}
}
