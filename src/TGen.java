import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class TGen extends Frame implements Runnable{
	
	public static final int DIM=128;
	
	private Thread me;
	private boolean work;
	private int data[][];
	private Random rnd;

	public TGen() {
		
		data = new int[DIM][DIM];
		rnd = new Random(System.currentTimeMillis());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				me=null;
				while(work) delay(10);
				System.exit(0);
			}
		});
		
		setSize(DIM, DIM);
		setVisible(true);
		
		me = new Thread(this);
		me.start();
	}
	
	public void run() {
		delay(100);
		work = true;
		createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		while(me!=null){
			Graphics g = bs.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, DIM, DIM);
			for(int i=0;i<DIM;i++)
				for(int j=0;j<DIM;j++){
					crazyShader(i, j, g);
					//simpleShader(i, j, g);
					g.fillRect(i, j, 1, 1);
				}
			bs.show();
			delay(10);
		}
		work=false;
	}
	
	public void simpleShader(int i,int j,Graphics g){
		if(data[i][j]<50) g.setColor(Color.GRAY);
		else g.setColor(Color.LIGHT_GRAY);
	}
	public void crazyShader(int i,int j,Graphics g){
		if(data[i][j]<40) g.setColor(Color.BLUE);
		else if(data[i][j]<50) g.setColor(Color.YELLOW);
		else if(data[i][j]<70) g.setColor(Color.GREEN);
		else if(data[i][j]<80) g.setColor(Color.LIGHT_GRAY);
		else g.setColor(Color.WHITE);
	}
	
	// random terrain generation
	public void gen(){
		for(int i=0;i<DIM;i++)
			for(int j=0;j<DIM;j++){
				data[i][j]=rnd.nextInt(100);
				delay(1);
			}
	}
	
	//terrain gen using diamond-square algo
	public void gen2(){
		int step=16;
		for(int i=0;i<DIM;i+=step)
			for(int j=0;j<DIM;j+=step){
				setValue(i, j, rnd.nextInt(100));
			}
		while(step>1){
			DiaSqr(step); step/=2;
		}
	}
	
	public void setValue(int x,int y,int v){
		data[x&(DIM-1)][y&(DIM-1)]=v;
	}
	public int getValue(int x,int y){
		return data[x&(DIM-1)][y&(DIM-1)];
	}
	public void setSqrValue(int x,int y,int s,int v){
		int hs = s/2;
		int a = getValue(x-hs, y-hs);
		int b = getValue(x+hs, y-hs);
		int c = getValue(x-hs, y+hs);
		int d = getValue(x+hs, y+hs);
		setValue(x, y, v+(a+b+c+d)/4);
	}
	public void setDiaValue(int x,int y,int s,int v){
		int hs = s/2;
		int a = getValue(x-hs, y);
		int b = getValue(x+hs, y);
		int c = getValue(x, y-hs);
		int d = getValue(x, y+hs);
		setValue(x, y, v+(a+b+c+d)/4);
	}
	public void DiaSqr(int step){
		int hstep=step/2;
		for(int i=hstep;i<DIM+hstep;i+=step)
			for(int j=hstep;j<DIM+hstep;j+=step){
				setSqrValue(i, j, step, nextInt());
			}
		for(int i=0;i<DIM;i+=step)
			for(int j=0;j<DIM;j+=step){
				setDiaValue(i+hstep, j, step, nextInt());
				setDiaValue(i, j+hstep, step, nextInt());
			}
	}
	public int nextInt(){
		int s=5;
		return rnd.nextInt(s)-s/2;
	}
	
	public static void delay(long x){
		try{Thread.sleep(x);}catch(Exception e){}
	}
	
	public static void main(String[] args) {
		TGen gen = new TGen();
		//gen.gen();
		gen.gen2();
	}
}
