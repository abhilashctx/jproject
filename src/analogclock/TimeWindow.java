package analogclock;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
//import java.util.Calendar;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

public class TimeWindow extends JWindow implements MouseMotionListener,Runnable,ActionListener{

	private static final long serialVersionUID = 1L;
	private Thread me;
	private String date;
	//private Calendar cal;
	private Color bgColor = new Color(210,255,210);
	private Image image;
	private Graphics gr;
	private JPopupMenu popupMenu = new JPopupMenu();
	public TimeWindow()
	{
		addMouseMotionListener(this);
		setAlwaysOnTop(true);
		setLocation(500,25);
		setSize(200,20);
		setVisible(true);

		//getRootPane().setOpaque(false);
		JMenuItem jmi = new JMenuItem("what?");
		jmi.addActionListener(this);
		popupMenu.add(jmi);
		jmi = new JMenuItem("exit");
		jmi.addActionListener(this);
		popupMenu.add(jmi);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					popupMenu.show(e.getComponent(),e.getX(),e.getY());
				}
			}
		});
		
		me = new Thread(this);
		me.start();		
	}
	
	public void update(Graphics g) {
		//super.update(g);
		if(image==null){
			image=createImage(getWidth(),getHeight());
			gr=image.getGraphics();
		}
		Graphics2D g2d = (Graphics2D)gr;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(bgColor);
		g2d.fillRect(0,0,getWidth(),getHeight());
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
		if(date != null){
			FontMetrics fm = g2d.getFontMetrics();
			int x = getWidth()/2 - fm.stringWidth(date)/2;
			g2d.drawString(date,x,15);
		}
		g2d=(Graphics2D)g;
		g2d.drawImage(image,0,0,this);
	}
	
	private Point mp;
	public void mouseMoved(MouseEvent e) {
		mp = e.getPoint();
	}
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		int x = getX()+p.x-mp.x;
		int y = getY()+p.y-mp.y;
		setLocation(x,y);
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println(""+e.getActionCommand());
		String cmd = e.getActionCommand();
		if(cmd.equalsIgnoreCase("what?")){}
		else if(cmd.equalsIgnoreCase("exit"))
		{me=null;System.exit(0);}
	}
	
	public void run() {
		while(me != null){
			//cal = Calendar.getInstance();
			//Timestamp timestamp = new Timestamp(cal.getTimeInMillis());
			//date = timestamp.toString();
			Date d = new Date();
			date = d.toString();
			//repaint();
			update(getGraphics());
			try{Thread.sleep(1000);}catch(Exception e){}
		}
	}
	
	public static void main(String[] args) {
		new TimeWindow();
	}
}
