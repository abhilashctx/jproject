package postit;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Stroke;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PostitMgr extends Window implements ActionListener{

	
	private int w,h;
	private PopupMenu pm;
	private boolean alive=true;
	private ArrayList postits;
	
	public PostitMgr() {
		super(new Frame());
		
		w = h = 30;
		
		setSize(w,h);
		setVisible(true);
		setAlwaysOnTop(true);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
		
		pm = new PopupMenu("PostitMgrMenu");
		MenuItem mi;
		mi = new MenuItem("Add Postit");
		mi.addActionListener(this);
		pm.add(mi);
		mi = new MenuItem("Exit");
		mi.addActionListener(this);
		pm.add(mi);
		add(pm);
		
		postits = new ArrayList();
	}
	
	protected void processMouseEvent(MouseEvent e) {
		if(e.isPopupTrigger()){
			pm.show(this, e.getX(), e.getY());
		}
		super.processMouseEvent(e);
	}
	
	int mx=0,my=0;
	protected void processMouseMotionEvent(MouseEvent e) {
		
		switch(e.getID()){
		case MouseEvent.MOUSE_MOVED:
			mx = e.getX();
			my = e.getY();
			//System.out.println("moved");
			break;
		case MouseEvent.MOUSE_DRAGGED:
			setLocation(getX() + e.getX() - mx, getY() + e.getY() - my);
			//System.out.println("dragged");
			break;
		}
		//super.processMouseMotionEvent(e);
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Add Postit")){
			System.out.println("Add Postit");
			JWIN postit = new JWIN(this);
			postits.add(postit);
		}else if(cmd.equals("Exit")){
			alive=false;
		}else if(cmd.equals("Delete")){ //delet postit ... tapping into the postit event que
			for(int i=0;i<postits.size();i++){
				JWIN p = (JWIN)postits.get(i);
				if(p.active){
					postits.remove(p);
					p.setVisible(false);
					p.dispose();
					break;
				}
			}
		}else{
			for(int i=0;i<postits.size();i++){
				JWIN p = (JWIN)postits.get(i);
				if(p.active){
					p.actionPerformed(e);
					break;
				}
			}
		}
	}
	
	public void update(Graphics g) {
	}
	
	public void run(){
		BufferedImage bi = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
		FontMetrics fm = g.getFontMetrics();
		Stroke s1 = g.getStroke();
		Stroke s7 = new BasicStroke(5);
		while(alive){
			g.setColor(Color.orange);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.black);
			g.drawString("Post", 1, 20);
			getGraphics().drawImage(bi, 0, 0, this);
			
			for(int i=0;i<postits.size();i++){
				JWIN p = (JWIN)postits.get(i);
				p.runner();
				try{Thread.sleep(1);}catch(Exception e){}
			}
			try{Thread.sleep(10);}catch(Exception e){}
		}
		for(int i=0;i<postits.size();i++){
			JWIN p = (JWIN)postits.get(i);
			p.setVisible(false);
			p.dispose();
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		PostitMgr mgr = new PostitMgr();
		mgr.run();
	}
}
