package j4k.beez;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Beez extends Frame {

	private byte hsp[];
	private float px1,py1,px2,py2,fx[],fy[],fbx[],fby[],aax[],aay[],adx[],ady[],aaa[];
	float data[][];
	//px[],py[],,vx[],vy[],ax[],ay[]
	//f = flower fx,fy , fa = age
	//fh,bh = flower/bee nectar
	//hx,hy,hh,hs = hives x,y,nec,size
	//tf = target flower/hive, fa = flower state
	//tt = target type for bees
	private int lvl=0,bs=30,fs=50,ht=500,mx,my,hx,hy,hh,fa[],tf[],bh[],fh[],tt[],aas=5;
	private Color fc[];
	private Random rnd;
	private boolean done,mpress=false;
	private GeneralPath gp;
	
	public Beez() {
		
		rnd = new Random(System.currentTimeMillis());
		
		enableEvents(WindowEvent.WINDOW_CLOSING|MouseEvent.MOUSE_DRAGGED|MouseEvent.MOUSE_MOVED|MouseEvent.MOUSE_PRESSED|MouseEvent.MOUSE_RELEASED);
		
		setSize(500,500);
		setVisible(true);
		
		data = new float[bs][6];
		
		tt = new int[bs];
		tf = new int[bs];
		bh = new int[bs];
		//px = new float[bs];
		//py = new float[bs];
		//vx = new float[bs];
		//vy = new float[bs];
		//ax = new float[bs];
		//ay = new float[bs];
		fx = new float[fs];
		fy = new float[fs];
		fbx = new float[fs];
		fby = new float[fs];
		fh = new int[fs];
		fa = new int[fs];
		fc = new Color[fs];
		aax = new float[aas];
		aay = new float[aas];
		adx = new float[aas];
		ady = new float[aas];
		aaa = new float[aas];
		
		gp = new GeneralPath();
		gp.reset();
		//gp.moveTo(50,50);
		//gp.lineTo(50,90);
		//gp.lineTo(100,90);
		gp.moveTo(50,50);
		gp.quadTo(70,70,50,90);
		gp.quadTo(70,70,100,90);
		gp.quadTo(100,70,50,50);
		//gp.closePath();
	}
	private int mmx=0,mmy=0;
	protected void processEvent(AWTEvent e) {
		
		switch(e.getID()){
		case WindowEvent.WINDOW_CLOSING:
			System.exit(0);
			break;
		case MouseEvent.MOUSE_DRAGGED:
		case MouseEvent.MOUSE_MOVED:
			mx = ((MouseEvent)e).getX();
			my = ((MouseEvent)e).getY();
			break;
		case MouseEvent.MOUSE_PRESSED:
			mmx = ((MouseEvent)e).getX();
			mmy = ((MouseEvent)e).getY();
			//System.out.println(mmx+","+mmy);
			mpress=true;
			break;
		case MouseEvent.MOUSE_RELEASED:
			mpress=false;
			int dx=((MouseEvent)e).getX()-mmx;
			int dy=((MouseEvent)e).getY()-mmy;
			int dd=(int)Math.sqrt(dx*dx+dy*dy)/4;
			//System.out.println(dx+","+dy+"="+mmx+","+mmy);
			//System.out.println(dd);
			dd = (dd<8) ? 8 : dd;
			dd = (dd>30) ? 30 : dd;
			addAA(dd);
			break;
		}
	}
	
	private void init(){

	}
	
	public void run()
	{
		BufferedImage bi = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)bi.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		int w=500,h=500;
		Color brown = new Color(143,94,0);
		while(true){
			lvl++;
			if(lvl>5) break;
			ht=lvl==1 ? 50 : lvl*100;
			fs=lvl*8;
			bs=lvl*6;
			px1=30;py1=295;
			px2=40;py2=295;
			done=false;
			
			//bees
			for(int i=0;i<bs;i++){
				tt[i]=-1;
				bh[i]=0;
				tf[i]=-1;
				data[i][0] = rnd.nextInt(400)+50;
				data[i][1] = rnd.nextInt(400)+50;
				data[i][2] = rnd.nextInt(2)==0 ? (rnd.nextInt(4)+1) : -(rnd.nextInt(4)+1);
				data[i][3] = rnd.nextInt(2)==0 ? (rnd.nextInt(4)+1) : -(rnd.nextInt(4)+1);
			}
			
			//flowers
			for(int i=0;i<fs;i++){
				fh[i]=10;
				fbx[i]=fx[i]=rnd.nextInt(350)+100;
				fby[i]=rnd.nextInt(50)+400;
				fy[i]=fby[i]-fh[i];
				fa[i]=0;
				fc[i]=getFlowerColor();
			}
			
			//hive
			hx=rnd.nextInt(400)+30;
			hy=rnd.nextInt(50)+40;
			hh=0;
			
			while(!done){
				g.setColor(Color.BLACK);
				g.fillRect(0,0,w,h);
				//land
				g.setColor(brown);
				g.fillRect(0,400,w,h);
				//platform
				g.fillRect(10,300,40,6);
				if(mpress){
					float dx=mx-mmx;
					float dy=my-mmy;
					float dd=(float)Math.sqrt(dx*dx+dy*dy)/4;
					dd=(dd>30? 30 : (dd<8 ? 8 : dd));
					g.setColor(Color.WHITE);
					g.drawString("POW:"+(int)dd,10,330);
				}
				//draw hive
				g.setColor(Color.YELLOW);
				g.fillOval(hx,hy,60,100);
				g.setColor(Color.BLACK);
				g.fillRect(hx,hy,60,100*(ht-hh)/ht);
				g.setColor(brown);
				g.drawOval(hx,hy,60,100);
				
				for(int i=0;i<fs;i++){
					
					if(fa[i]==-1){
						//if(rnd.nextInt(50)==0) addFlower(i);
						continue;
					}
					if(fh[i]<1) fa[i]=-1;
					g.setColor(Color.GREEN);
					g.drawLine((int)fbx[i],(int)fby[i],(int)fx[i],(int)fy[i]);
					if((fby[i]-fy[i])<20 && rnd.nextInt(1000)==0) {
						fh[i]++;
						fy[i]--;
					}
					g.setColor(fc[i]);
					int ffs = 3;
					int ffss = ffs*2;
					g.fillOval((int)fx[i]-ffs-5,(int)fy[i]-ffs,ffss,ffss);
					g.fillOval((int)fx[i]-ffs+5,(int)fy[i]-ffs,ffss,ffss);
					g.fillOval((int)fx[i]-ffs,(int)fy[i]-ffs-5,ffss,ffss);
					g.fillOval((int)fx[i]-ffs,(int)fy[i]-ffs+5,ffss,ffss);
					g.setColor(Color.YELLOW);
					g.fillOval((int)fx[i]-2,(int)fy[i]-2,4,4);
				}
				
				for(int i=0;i<bs;i++){
					if(bh[i]>0){
						g.setColor(Color.YELLOW);
						g.fillOval((int)data[i][0]-2,(int)data[i][1]-2,4,4);
					}
					g.setColor(Color.RED);
					g.drawOval((int)data[i][0]-2,(int)data[i][1]-2,4,4);
					update(i);
				}
				
				//calc hmmmm
				float dx = mx - px1;
				float dy = my - py1;
				float dd = (float)Math.sqrt(dx*dx+dy*dy);
				px2=px1+dx/dd*10;py2=py1+dy/dd*10;
				
				//hmmmm
				g.setColor(Color.WHITE);
//				g.drawLine((int)px1,(int)py1,(int)px2,(int)py2);
				g.fillOval((int)px2-2,(int)py2-2,4,4);
				
				//drawAA
				for(int i=0;i<aas;i++){
					if(aax[i]==0 && aay[i]==0) continue;
					g.drawLine((int)aax[i],(int)aay[i],(int)(aax[i]-adx[i]*10),(int)(aay[i]-ady[i]*10));
					g.fillOval((int)aax[i]-1,(int)aay[i]-1,3,3);
					aax[i]+=adx[i]*aaa[i];aay[i]+=ady[i]*aaa[i];
					if(aaa[i]<0) aaa[i]+=0.1;
					else if(aaa[i]>0) aaa[i]-=0.1;
//					if(adx[i]<0) adx[i]+=0.1;
//					else if(adx[i]>0) adx[i]-=0.1;
					ady[i]+=0.1;
					if(aax[i]<0 || aax[i]>480 || aay[i]<0 || aay[i]>480)
						aax[i]=aay[i]=0;
				}
				
				if(hh>=ht) done=true;
				
				g.setColor(Color.RED);
				g.fill(gp);
				g.setColor(Color.YELLOW);
				g.draw(gp);
				
				getGraphics().drawImage(bi,0,0,this);
				try{Thread.sleep(50);}catch(Exception e){}
			}
		}
		//game over msg :D
	}
	private void update(int idx){
		if(tf[idx]==-1){
			int fi = rnd.nextInt(fs);
			if(fa[fi]!=-1){
				tf[idx]=fi;
			}
		}
		float dx=0,dy=0;
		if(tf[idx]==-2 || tf[idx]==-1){
			dx = hx+30 - data[idx][0];
			dy = hy+50 - data[idx][1];
		}
		else if(tf[idx]!=-1){
			dx = fx[tf[idx]] - data[idx][0];
			dy = fy[tf[idx]] - data[idx][1];
		}
		float ds = (float)Math.sqrt(dx*dx + dy*dy)+1;
		if(tf[idx]!=-2 && tf[idx]!=-1 && ds<10 && fh[tf[idx]]>0 && bh[idx]<2 && rnd.nextInt(4)==0){
			bh[idx]++;
			fh[tf[idx]]--;
			fy[tf[idx]]++;
			if(fh[tf[idx]]==0) tf[idx]=-2;
		}
		else if(tf[idx]==-2 && ds<10 && bh[idx]>0){
			bh[idx]--;hh++;
			if(bh[idx]==0) tf[idx]=-1;
		}
		else if(bh[idx]>=2){
			tf[idx]=-2; // go to hive
		}
		else if(rnd.nextInt(200)==0){
			tf[idx]=-1; //find new flower
		}
		data[idx][4] += dx/ds ;//+ rx;
		data[idx][5] += dy/ds ;//+ ry;
		data[idx][2] += data[idx][4];
		data[idx][3] += data[idx][5];
		data[idx][0] += data[idx][2];
		data[idx][1] += data[idx][3];
		if(data[idx][4]>0) data[idx][4]-=1;
		else if(data[idx][4]<0) data[idx][4]+=1;
		if(data[idx][5]>0) data[idx][5]-=1;
		else if(data[idx][5]<0) data[idx][5]+=1;
		int vl=6;
		if(data[idx][2]>vl) data[idx][2]=vl;
		else if(data[idx][2]<-vl) data[idx][2]=-vl;
		if(data[idx][3]>vl) data[idx][3]=vl;
		else if(data[idx][3]<-vl) data[idx][3]=-vl;
	}
	
	/*private void addHive(){
				
	}
	private void newFlower(){
		for(int i=0;i<fs;i++){
			if(fa[i]==-1) {
				addFlower(i);
				break;
			}
		}
	}
	private void addFlower(int idx){
		fh[idx]=10;//20+rnd.nextInt(20);
		fbx[idx]=fx[idx]=rnd.nextInt(400)+50;
		fby[idx]=rnd.nextInt(50)+400;
		fy[idx]=fby[idx]-fh[idx];
		fa[idx]=0;//800+rnd.nextInt(200);
		fc[idx]=getFlowerColor();
	}*/
	
	private Color getFlowerColor()
	{
		int r = rnd.nextInt(5);
		switch(r){
		case 0: return Color.PINK;
		case 1: return Color.CYAN;
		case 2: return Color.WHITE;
		case 3: return Color.ORANGE;
		case 4: return Color.GREEN;
		}
		return Color.PINK;
	}
	
	private void addAA(int _dd){
		for(int i=0;i<aas;i++){
			if(aax[i]==0 && aay[i]==0){
				float dx = mx - px1;
				float dy = my - py1;
				float dd = (float)Math.sqrt(dx*dx+dy*dy);
				aax[i]=px2;aay[i]=py2;
				adx[i]=dx/dd;ady[i]=dy/dd;
				aaa[i]=_dd;
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Beez md = new Beez();
		md.run();
	}

}
