package postit;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class JWIN extends Window implements MouseMotionListener, ActionListener{

	private Image img;
	private PopupMenu pm;
	private Frame pf;
	private String note , notetime;
	private int postitw = 232;
	private int postith = 196;
	private boolean postitdone = false;
	private boolean postitpopup = false;
	private BufferedImage bi;
	Graphics2D g;
	private FontMetrics fm;
	private Stroke s1,s7;
	public boolean active=false;
	private HashMap<String, ArrayList<Object>> registry = new HashMap<String, ArrayList<Object>>();
	
	public JWIN(ActionListener actionListener) {
		super(new Frame());

		addMouseMotionListener(this);
		//img = Toolkit.getDefaultToolkit().getImage("post3.jpg");
		img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("post1.jpg"));
		setSize(postitw, postith);
		setVisible(true);
		setAlwaysOnTop(true);
		//System.out.println(img);
		createPopupMenu(actionListener);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		
		
		bi = new BufferedImage(postitw, postith,BufferedImage.TYPE_INT_RGB);
		g = bi.createGraphics();
		g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
		fm = g.getFontMetrics();
		s1 = g.getStroke();
		s7 = new BasicStroke(5);
	}
	
	public JWIN() {
		super(new Frame());
		
		addMouseMotionListener(this);
		img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("post1.jpg"));
		setSize(postitw, postith);
		setVisible(true);
		setAlwaysOnTop(true);
		createPopupMenu(this);
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		
		
		bi = new BufferedImage(postitw, postith,BufferedImage.TYPE_INT_RGB);
		g = bi.createGraphics();
		g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
		fm = g.getFontMetrics();
		s1 = g.getStroke();
		s7 = new BasicStroke(5);
	}
	
	public void register(String msg,Object obj){
		ArrayList<Object> list = registry.get(msg);
		if(list==null){
			list = new ArrayList<Object>();
			registry.put(msg, list);
		}
		for(int i=0;i<list.size();i++){
			if(list.get(i)==obj) return;
		}
		list.add(obj);
	}
	
	public void callback(String msg){
		ArrayList<Object> list = registry.get(msg);
		if(list==null) return;
		for(int i=0;i<list.size();i++){
			Object obj  = list.get(i);
			Class klass = (obj).getClass();
			System.out.println("Class Name : "+klass.getName());
			try{
				Method kallback = klass.getMethod("callback", new Class[]{String.class,Object.class});
				kallback.invoke(obj, new Object[]{msg,this});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void createPopupMenu(ActionListener actionListener){
		pm = new PopupMenu("context");
		MenuItem mi;
		mi = new MenuItem("notes");
		mi.addActionListener(actionListener);
		pm.add(mi);
		mi = new MenuItem("Done");
		mi.addActionListener(actionListener);
		pm.add(mi);
		mi = new MenuItem("OnTop");
		mi.addActionListener(actionListener);
		pm.add(mi);
		PopupMenu pmi = new PopupMenu("Paper");
		mi= new MenuItem("one");
		mi.addActionListener(actionListener);
		pmi.add(mi);
		mi= new MenuItem("two");
		mi.addActionListener(actionListener);
		pmi.add(mi);
		mi= new MenuItem("three");
		mi.addActionListener(actionListener);
		pmi.add(mi);
		pm.add(pmi);
		mi = new MenuItem("Delete");
		mi.addActionListener(actionListener);
		pm.add(mi);
		add(pm);
	}
	
	protected void processMouseEvent(MouseEvent e) {
		if(e.isPopupTrigger()){
			pm.show(this, e.getX(), e.getY());
			postitpopup=true;
		}else 
		if(e.getID()==MouseEvent.MOUSE_ENTERED){
			active=true;
		}else 
		if(e.getID()==MouseEvent.MOUSE_EXITED){
			active=false;
		}
		super.processMouseEvent(e);
	}
	
	private int px=0,py=0;
	public void mouseDragged(MouseEvent e) {
		if(!postitpopup)
		setLocation(getX()+e.getX()-px, getY()+e.getY()-py);
		postitpopup=false;
		//System.out.println("drag");
	}
	public void mouseMoved(MouseEvent e) {
		px=e.getX();py=e.getY();
		//System.out.println(px+","+py);
		//System.out.println("moved");
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Delete")){
			callback("delete");
			System.exit(0);
		}else if(cmd.equals("notes")){
			note = JOptionPane.showInputDialog(pf,"Enter Note :");
			if(note!=null){
				notetime = (new Timestamp(System.currentTimeMillis())).toString();
				callback("notes");
			}
			//System.out.println(note);
		}else if(cmd.equals("Done")){
			postitdone=true;
			callback("done");
		}else if(cmd.equals("OnTop")){
			setAlwaysOnTop(!isAlwaysOnTop());
		}else if(cmd.equals("one")){
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("post1.jpg"));
		}else if(cmd.equals("two")){
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("post2.jpg"));
		}else if(cmd.equals("three")){
			img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("post3.jpg"));
		}
	}
	
	/*public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}*/
	
	public void update(Graphics g) {
	}
	
	public void run(){
		
		/*bi = new BufferedImage(postitw, postith,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
		FontMetrics fm = g.getFontMetrics();
		Stroke s1 = g.getStroke();
		Stroke s7 = new BasicStroke(5);*/
		while(true){
			
			//g.setColor(Color.green);
			//g.fillRect(0, 0, 200, 200);
			g.drawImage(img, 0, 0, this);
			g.setColor(Color.black);
			if(note==null) note="- Empty -";
			if(notetime==null) notetime="- Time -";
			int w = fm.stringWidth(note);
			g.drawString(note, postitw/2-w/2, 100);
			w = fm.stringWidth(notetime);
			g.drawString(notetime, postitw/2-w/2, 40);
			g.setColor(Color.gray);
			g.drawString("Virtual Postit by Abhilash", 50, postith-15);
			g.setColor(Color.lightGray);
			g.setStroke(s1);
			g.drawLine(20, 45, postitw-20, 45);
			g.setStroke(s7);
			if(postitdone){
				g.setColor(Color.gray);
				g.drawLine(153, 132, 166, 145);
				g.drawLine(189, 122, 166, 145);
				g.setColor(Color.green);
				g.drawLine(151, 130, 164, 143);
				g.drawLine(187, 120, 164, 143);
			}
			else{
				g.setColor(Color.gray);
				g.drawLine(168, 123, 190, 145);
				g.drawLine(190, 123, 167, 146);
				g.setColor(Color.red);
				g.drawLine(165, 120, 187, 142);
				g.drawLine(187, 120, 164, 143);
			}
			getGraphics().drawImage(bi, 0, 0, this);
			try{Thread.sleep(10);}catch(Exception e){}
		}
	}
	
	public void runner(){

		g.drawImage(img, 0, 0, this);
		g.setColor(Color.black);
		if(note==null) note="- Empty -";
		if(notetime==null) notetime="- Time -";
		int w = fm.stringWidth(note);
		g.drawString(note, postitw/2-w/2, 100);
		w = fm.stringWidth(notetime);
		g.drawString(notetime, postitw/2-w/2, 40);
		g.setColor(Color.gray);
		g.drawString("Virtual Postit by Abhilash", 50, postith-15);
		g.setColor(Color.lightGray);
		g.setStroke(s1);
		g.drawLine(20, 45, postitw-20, 45);
		g.setStroke(s7);
		if(postitdone){
			g.setColor(Color.gray);
			g.drawLine(153, 132, 166, 145);
			g.drawLine(189, 122, 166, 145);
			g.setColor(Color.green);
			g.drawLine(151, 130, 164, 143);
			g.drawLine(187, 120, 164, 143);
		}
		else{
			g.setColor(Color.gray);
			g.drawLine(168, 123, 190, 145);
			g.drawLine(190, 123, 167, 146);
			g.setColor(Color.red);
			g.drawLine(165, 120, 187, 142);
			g.drawLine(187, 120, 164, 143);
		}
		getGraphics().drawImage(bi, 0, 0, this);
	
	}
	
	public static void main(String[] args) {
		
		JWIN jwin = new JWIN();
		jwin.run();
		/*Window w = new Window(new Frame());
		w.setSize(200, 200);
		w.setVisible(true);*/
		
	}
}
