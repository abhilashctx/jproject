import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class FreeRam {

	public static void main(String[] args) throws Exception {
		
		
		//q();
		//r(0,0);
		
		//for (int i=0; i<1024; ++i)
		//      System.out.println(i+" > "+(16384/(i+i+3)));
		int squash[] = new int[4096];
		int stretch[]=new int[4096];
		int sqi=0;
		for(int i=0;i<4096;i++){
			double x = ((i*17)/4096)-8;
			double e = Math.exp(x);
			int p = (int)(4096 * (e/(e+1)));
			squash[i]=p;
			stretch[i]=squash[i]-2048;
			//System.out.println("p:"+p);
		}
		//squash[0]=1;squash[4095]=4094;
		
		/*int pi=0;
		for(int x=-2047;x<=2047;x++){
			int i=squash[x+2047];
			for(int j=pi;j<=i;j++)
				stretch[j]=x;
			pi=i+1;
		}
		stretch[4095]=2047;*/
		for(int i=0;i<4096;i++) System.out.println(i+"> P:"+stretch[i]+" = "+squash[i]);
	}
	
	public static boolean xy[] = new boolean[64];
	public static void q(){
		System.out.println(xy[0]);
	}
	public static void r(int x,int y){
		final int max=8;
		if(x>=max) x=max-1;
		if(y>=max) y=max-1;
		if(xy[x*8+y]) return;
		xy[x*8+y]=true;
		int px = ((x*4+1)<<5)/(x*4+y*4+2);
		int py = ((y*4+1)<<5)/(x*4+y*4+2);
		System.out.println("px,py:"+px+","+py);
		r(x+1,(y+3)/4);
		r((x+3)/4,y+1);
	}
}
