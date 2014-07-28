package win.slide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JWindow;

public class Slider extends JWindow implements ActionListener,Runnable,MouseMotionListener,MouseListener{

	private boolean activate = false; //if true slide the window out
	
	private SliderThread sliderThread = null;
	
	//private JTextArea jta;
	
	private int WIDTH  = 200;
	private int HEIGHT = 400;
	
	public static final String BCLOSE="x";
	public static final String EActive="active";
	public static final String EDActive="deactive";
	
	private SliderEvent closeHandler;
	private SliderEvent activeHandler;
	
	public Slider()
	{
		
		setContainerLayoutTo(null);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setWindowSizeAndShow(getScreenSize());
		
		addButton(BCLOSE, getWidth()-55, 5, 45, 15);
		
		Thread me = new Thread(this);
		me.start();
		
		sliderThread = new SliderThread(this);
		sliderThread.start();
	}
	
	private Dimension getScreenSize(){
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	private void setWindowSizeAndShow(Dimension screen){
		HEIGHT = screen.height/2;
		setBounds(10-WIDTH, screen.height - screen.height*3/4, WIDTH, HEIGHT);
		setVisible(true);
		setAlwaysOnTop(true);
	}
	
	private void setContainerLayoutTo(LayoutManager mgr){
		getContentPane().setLayout(mgr);
	}
	
	private JButton addButton(String btntext,int x,int y,int w,int h){
		JButton button = new JButton(btntext);
		getContentPane().add(button);
		button.addActionListener(this);
		button.setBounds(x, y, w, h);
		return button;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(BCLOSE)){
			if(closeHandler!=null) closeHandler.eventHandler(BCLOSE);
			System.exit(0);
		}
	}
	
	public void setCloseHandler(SliderEvent event){
		closeHandler = event;
	}
	public void setActiveHandler(SliderEvent event){
		activeHandler = event;
	}
	
	public void run() {
		while(true){
			repaint();
			try{Thread.sleep(200);}catch(Exception e){}
		}
	}
	
	public void update(Graphics g) {
		super.update(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
	}
	
	public boolean isActivated()
	{
		return activate;
	}
	
	/*******************************************************************/
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		//System.out.println("Enter x,y    =   "+e.getX()+","+e.getY());
		activate = true;
		if(activeHandler!=null) activeHandler.eventHandler(EActive);
	}
	
	public void mouseExited(MouseEvent e) {
		//System.out.println("x,y    =   "+e.getX()+","+e.getY()+" --- "+getY());
		//mouse co-ords are relative to window
		//that is if mouse above window in x-axis then x is negative similar to y
		if((e.getX() < 0) || (e.getX() > (getWidth())) || (e.getY() < 0) || (e.getY() > (getHeight()))){
			activate = false;
			if(activeHandler!=null) activeHandler.eventHandler(EDActive);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	/*******************************************************************/
	
	public static void main(String[] args) {
		Slider slider = new Slider();
	}
	
	public interface SliderEvent{
		void eventHandler(String event);
	}
	
	//Slider Thread
	//this is what actually slides the window
	private class SliderThread extends Thread implements SliderEvent{
		
		private Slider parent = null;
		
		private int slideAmount = 10;
		
		private boolean isActive;
		
		public SliderThread(Slider _parent) {
			parent = _parent;
			isActive=false;
			parent.setCloseHandler(this);
			parent.setActiveHandler(this);
		}
		
		public void stopIt(){
			parent = null;
		}
		
		public void run() {
			while(parent != null){
				try{
					//if(parent.isActivated()){
					if(isActive){
						if(Math.abs(parent.getX()-10) > slideAmount){
							//System.out.println("Move x+1");
							parent.setLocation(parent.getX()+slideAmount,parent.getY());
						}
					}
					else{
						if(Math.abs(parent.getX()-5) < parent.getWidth()-10){
							//System.out.println("Move x+1");
							parent.setLocation(parent.getX()-slideAmount,parent.getY());
						}
					}
				}catch(Exception e){}
				try{Thread.sleep(10);}catch(Exception e){}
			}
		}
		
		public void eventHandler(String event) {
			if(event.equals(Slider.BCLOSE)){
				stopIt();
			}else if(event.equals(Slider.EActive)){
				isActive=true;
			}else if(event.equals(Slider.EDActive)){
				isActive=false;
			}
		}
	}
	
}
