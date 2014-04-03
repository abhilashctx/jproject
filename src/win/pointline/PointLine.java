package win.pointline;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class PointLine extends Frame {

	int width,height;
	
	float x,y;
	float x1,y1,x2,y2;
	
	boolean isRun;
	
	public void init(){
		width=height=500;
		setSize(width,height);
		setVisible(true);
		
		enableEvents(MouseEvent.MOUSE_PRESSED|MouseEvent.MOUSE_DRAGGED|WindowEvent.WINDOW_CLOSING);
		isRun=true;
	}
	
	public void run(){
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g=(Graphics2D)bi.getGraphics();
		setAntiAlias(g);
		
		x1=114;y1=193;
		x2=403;y2=321;
		
		float ax,ay,bx,by;
		//float tmp;
		
		//start,end,avg times; frame count
		long st=0,et=0,at=0,fc=0;
		
		int dpf = 1000/20 ; //delay per frame
		
		while(isRun){
			
			st=System.currentTimeMillis();
			
			g.setColor(Color.black);
			g.fillRect(0,0,width,height);
			
			//start rendering
			g.setColor(Color.yellow);
			g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
			
			g.setColor(Color.green);
			g.drawOval((int)x-3,(int)y-3,6,6);
			g.drawLine((int)x1,(int)y1,(int)x,(int)y);
			
			ax=x-x1;
			ay=y-y1;
			bx=x2-x1;
			by=y2-y1;
			float adotb=dot(ax,ay,bx,by);
			g.setColor(Color.white);
			g.drawString("a.b   = "+adotb,40,60);
			
			float axb=cross(ax,ay,bx,by);
			g.drawString("axb   = "+axb,40,80);
			//cross helps which side of line point is
			
			float bdotb=dot(bx,by,bx,by);
			float proj=(float)(adotb/bdotb);
			g.drawString("proj = "+proj,40,100);
			
			if(proj<0) proj=0;
			else if(proj>1) proj=1;
			
			float cx=x1+proj*bx;
			float cy=y1+proj*by;
			g.drawString("cx = "+cx,40,120);
			g.drawString("cy = "+cy,40,140);
			
			g.setColor(Color.red);
			g.drawLine((int)x,(int)y,(int)cx,(int)cy);
			
			rotate();
			
			//done
			getGraphics().drawImage(bi,0,0,this);
			
			et=System.currentTimeMillis();
			at += (et-st); fc++;
			
			long delay = dpf - (et-st);
			if(delay>0){
				try{Thread.sleep(delay);}catch(Exception e){}
			}
		}
		
		System.out.println("avg time per frame :"+(at/fc));
		System.exit(0);
	}
	
	protected void processEvent(AWTEvent e) {
		MouseEvent m;
		switch(e.getID()){
		case WindowEvent.WINDOW_CLOSING:
			isRun=false;
			break;
		case MouseEvent.MOUSE_DRAGGED:
			m=(MouseEvent)e;
			x=m.getX();
			y=m.getY();
			break;
		case MouseEvent.MOUSE_PRESSED:
			m=(MouseEvent)e;
			x=m.getX();
			y=m.getY();
			System.out.println("x,y="+x+","+y);
			break;
		}
	}
	
	//translate x,y using mx,my
	public void rotate(){
		float mx = (x1+x2)/2;
		float my = (y1+y2)/2;
		
		//trans to origin
		float tx1=x1-mx;
		float ty1=y1-my;
		float tx2=x2-mx;
		float ty2=y2-my;
		
		//rotate
		float rad=(float)(2*Math.PI/180);
		float cos=(float)Math.cos(rad);
		float sin=(float)Math.sin(rad);
		
		x1=tx1*cos-ty1*sin;
		x2=tx2*cos-ty2*sin;
		y1=ty1*cos+tx1*sin;
		y2=ty2*cos+tx2*sin;
		
		//translate back
		x1+=mx;
		y1+=my;
		x2+=mx;
		y2+=my;
	}
	
	public void setAntiAlias(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public float dot(float x1,float y1,float x2,float y2){
		return (x1*x2+y1*y2);
	}
	public float cross(float x1,float y1,float x2,float y2){
		return (x1*y2-x2*y1);
	}
	
	public static void main(String[] args) {
		PointLine pointLine = new PointLine();
		pointLine.init();
		pointLine.run();
	}

}
