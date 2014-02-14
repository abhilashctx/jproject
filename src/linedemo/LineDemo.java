package linedemo;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Vector;

public class LineDemo extends Frame implements MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private boolean isrun=true;
	private Vector<Line> lines;
	
	public LineDemo() {
		super("Line Seg Intersection Demo");
		log("init");
		lines = new Vector<Line>();
		
		setSize(400,400);
		setVisible(true);
		
		log("add listeners");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				terminate();
				System.exit(0);
			}
		});
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void run()
	{
		log("start thread");
		createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		long prevtimestamp = System.currentTimeMillis();
		long currtimestamp = prevtimestamp;
		while(isrun){
			
			//time calculation
			currtimestamp = System.currentTimeMillis();
			long tmp_ts = (currtimestamp - prevtimestamp);
			if(tmp_ts==0l){tmp_ts=1000;}
			else{tmp_ts = 1000/tmp_ts;}
			prevtimestamp = currtimestamp;
			
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			
			for(int i=0;i<lines.size();i++){
				Line line = lines.get(i);
				intersection_detection(i,g);
				g.setColor(Color.WHITE);
				g.drawLine((int)line.x1,(int)line.y1,(int)line.x2,(int)line.y2);
				g.drawOval((int)line.x1-2,(int)line.y1-2,4,4);
				g.drawOval((int)line.x2-2,(int)line.y2-2,4,4);
			}
			
			g.drawString("FPS:"+tmp_ts,200,50);
			
			bs.show();
			delay(10);
		}
		isrun=true;
	}
	
	private void terminate()
	{
		isrun = false;
		while(!isrun){
			delay(100);
		}
		log("done");
	}
	
	private void intersection_detection(int idx_line1,Graphics2D g)
	{
		spring_force(idx_line1);
		for(int i=0;i<lines.size();i++){
			if(i==idx_line1) continue;
			Line line1 = lines.get(idx_line1);
			Line line2 = lines.get(i);
			float minx1=0,maxx1=0;
			float miny1=0,maxy1=0;
			if(line1.x1<line1.x2){
				minx1 = line1.x1;maxx1 = line1.x2;
			}else{
				minx1 = line1.x2;maxx1 = line1.x1;
			}
			if(line1.y1<line1.y2){
				miny1 = line1.y1;maxy1 = line1.y2;
			}else{
				miny1 = line1.y2;maxy1 = line1.y1;
			}
			float minx2=0,maxx2=0;
			float miny2=0,maxy2=0;
			if(line2.x1<line2.x2){
				minx2 = line2.x1;maxx2 = line2.x2;
			}else{
				minx2 = line2.x2;maxx2 = line2.x1;
			}
			if(line2.y1<line2.y2){
				miny2 = line2.y1;maxy2 = line2.y2;
			}else{
				miny2 = line2.y2;maxy2 = line2.y1;
			}
			
			//g.drawRect(minx1,miny1,(int)Math.sqrt(sqr(maxx1-minx1)),(int)Math.sqrt(sqr(maxy1-miny1)));
			//g.drawRect(minx2,miny2,(int)Math.sqrt(sqr(maxx2-minx2)),(int)Math.sqrt(sqr(maxy2-miny2)));
			//log(">"+minx1+","+miny1+","+maxx1+","+maxy1);
			//log(" "+minx2+","+miny2+","+maxx2+","+maxy2);
			if(minx1>maxx2);
			else if(maxx1<minx2);
			else if(miny1>maxy2);
			else if(maxy1<miny2);
			else { //log("interseted!!!");
				float A1 = line1.y2-line1.y1;
				float B1 = line1.x1-line1.x2;
				float C1 = A1*line1.x1+B1*line1.y1;
				float A2 = line2.y2-line2.y1;
				float B2 = line2.x1-line2.x2;
				float C2 = A2*line2.x1+B2*line2.y1;
				float det = A1*B2-A2*B1;
				if(det == 0) continue;// log("lines are parallel");
				int x = (int)((B2*C1-B1*C2)/det);
				int y = (int)((A1*C2-A2*C1)/det);
				//log("x,y="+x+","+y);
				if((minx1<=x && x<=maxx1) && (miny1<=y && y<=maxy1) && 
						(minx2<=x && x<=maxx2) && (miny2<=y && y<=maxy2)){
					//log("intersect");
					g.setColor(Color.RED);
					g.fillOval(x-5,y-5,10,10);
				}
			}
		}
	}
	
	private void spring_force(int idx_line)
	{
		Line line = lines.get(idx_line);
		float dx = line.x2 - line.x1;
		float dy = line.y2 - line.y1;
		float len = (float)Math.sqrt(dx*dx+dy*dy);
		float f = (line.len-len)/len*2;
		dx=Math.abs(f)*dx;
		dy=Math.abs(f)*dy;
		dx = Math.max(-1,Math.min(1,dx));
		dy = Math.max(-1,Math.min(1,dy));
		line.x1+=dx;line.y1+=dy;
		line.x2-=dx;line.y2-=dy;
		//line.x1+=f;line.y1+=f;
		//line.x2+=f;line.y2+=f;
		log("f,dx,dy="+f+","+dx+","+dy);
	}
	
	private void addLine(int _x1,int _y1,int _x2,int _y2)
	{
		lines.add(new Line(_x1,_y1,_x2,_y2));
	}
	
	private Line mouseHold = null;
	private boolean isfirstpoint;
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		for(int i=0;i<lines.size();i++){
			Line line = lines.get(i);
			float dx1 = Math.abs(line.x1-e.getX());
			float dy1 = Math.abs(line.y1-e.getY());
			float dx2 = Math.abs(line.x2-e.getX());
			float dy2 = Math.abs(line.y2-e.getY());
			if(dx1 < 4 && dy1 < 4){
				mouseHold = line;
				isfirstpoint=true;
			}
			if(dx2 < 4 && dy2 < 4){
				mouseHold = line;
				isfirstpoint=false;
			}
		}
	}
	public void mouseReleased(MouseEvent e) {
		mouseHold=null;
	}
	public void mouseDragged(MouseEvent e) {
		Line tmp = mouseHold;
		if(tmp!=null){
			if(isfirstpoint){
				tmp.x1=e.getX();
				tmp.y1=e.getY();
			}
			else{
				tmp.x2=e.getX();
				tmp.y2=e.getY();
			}
		}
	}
	public void mouseMoved(MouseEvent e) {}
	
	//learning concept of line intersection
	public static void main(String[] args) {
		Random rnd = new Random(System.currentTimeMillis());
		LineDemo demo = new LineDemo();
		demo.addLine(100,100,200,200);
		demo.addLine(150,110,190,200);
		for(int i=0;i<5;i++)
		demo.addLine(rnd.nextInt(300)+50,rnd.nextInt(300)+50,rnd.nextInt(300)+50,rnd.nextInt(300)+50);
		demo.run();
	}

	public static void log(String msg)
	{
		System.out.println(msg);
	}
	public static void delay(int v)
	{
		try{Thread.sleep(v);}catch(Exception e){}
	}
	public static int sqr(int v)
	{
		return v*v;
	}

class Line{
	float x1,y1,x2,y2,len,dx,dy;
	public Line(int _x1,int _y1,int _x2,int _y2) {
		x1 = _x1;y1 = _y1;
		x2 = _x2;y2 = _y2;
		float dx = x2-x1;
		float dy = y2-y1;
		len = (int)Math.sqrt(dx*dx+dy*dy);
	}
}
}