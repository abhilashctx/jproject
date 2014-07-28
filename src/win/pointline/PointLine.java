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
	
	boolean isRun;
	
	BufferedImage bi;
	Graphics2D g;
	
	public void init(){
		width=height=500;
		setSize(width,height);
		setVisible(true);
		
		enableEvents(MouseEvent.MOUSE_PRESSED|MouseEvent.MOUSE_DRAGGED|WindowEvent.WINDOW_CLOSING);
		isRun=true;
	}
	
	public void run(){
		bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		g=bi.createGraphics();
		setAntiAlias(g);
		
		x1=114;y1=193;
		x2=403;y2=321;
		
		int frames=0;
		int updates=0;
		long titleUpdTime=System.currentTimeMillis();
		long lastUpdTime=System.currentTimeMillis();
		long lastRndTime=System.currentTimeMillis();
		
		int dpf = 1000/35 ; //delay per frame
		int dpu = 1000/20 ; //delay per update
		int skip=0;
		while(isRun){
			
			long now=System.currentTimeMillis();
			skip=0;
			while((now-lastUpdTime)>dpu && skip<5){
				skip++;
				lastUpdTime+=dpu;
				update();
				updates++;
			}
			if(lastUpdTime>now){
				lastUpdTime=now;
			}
			if((now-lastRndTime)>dpf){
				double ratio = (now-lastUpdTime-dpu)/dpu;
				render(ratio);
				lastRndTime=now;
				frames++;
			}

			if((now-titleUpdTime)>1000){
				titleUpdTime=now;
				setTitle("fps:"+frames+" tps:"+updates );
				frames=0;updates=0;
			}
			
			//now=System.currentTimeMillis();
			//long upddiff = now-lastUpdTime;
			//long rnddiff = now-lastRndTime;
			//long mindiff = Math.min(upddiff, rnddiff);
			//System.out.println("mindiff:"+mindiff);
			try{Thread.sleep(1);}catch(Exception e){}
		}
		System.exit(0);
	}
	
	float adotb;
	float axb;
	float proj;
	float x1,y1,x2,y2;
	float cx,cy;
	float px1,py1,px2,py2,pcx,pcy;
	private void update(){
		float ax,ay,bx,by;
		
		px1=x1;py1=y1;px2=x2;py2=y2;pcx=cx;pcy=cy;
		
		rotate();
		
		ax=x-x1;
		ay=y-y1;
		bx=x2-x1;
		by=y2-y1;
		adotb=dot(ax,ay,bx,by);
		
		axb=cross(ax,ay,bx,by);
		
		float bdotb=dot(bx,by,bx,by);
		proj=(float)(adotb/bdotb);
		
		if(proj<0) proj=0;
		else if(proj>1) proj=1;
		
		cx=x1+proj*bx;
		cy=y1+proj*by;
		
	}
	private void render(double r){
		
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		
		//start rendering
		g.setColor(Color.yellow);
		//g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
		g.drawLine((int)(px1+r*(x1-px1)),(int)(py1+r*(y1-py1)),(int)(px2+r*(x2-px2)),(int)(py2+r*(y2-py2)));
		
		g.setColor(Color.green);
		g.drawOval((int)x-3,(int)y-3,6,6);
		//g.drawLine((int)x1,(int)y1,(int)x,(int)y);
		g.drawLine((int)(px1+r*(x1-px1)),(int)(py1+r*(y1-py1)),(int)x,(int)y);
		
		g.setColor(Color.white);
		g.drawString("a.b   = "+adotb,40,60);
		
		g.drawString("axb   = "+axb,40,80);
		
		g.drawString("proj = "+proj,40,100);
		
		g.drawString("cx = "+cx,40,120);
		g.drawString("cy = "+cy,40,140);
		
		g.setColor(Color.red);
		//g.drawLine((int)x,(int)y,(int)cx,(int)cy);
		g.drawLine((int)x,(int)y,(int)(pcx+r*(cx-pcx)),(int)(pcy+r*(cy-pcy)));
		
		getGraphics().drawImage(bi,0,0,this);
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
