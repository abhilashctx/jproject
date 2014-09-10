import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class FreeRam {

	public static void main(String[] args) throws Exception {
		int a[] = new int[8];
		int b[] = new int[16];
		int c[] = new int[32];
		
		for(int k=0;k<10;k++)
		for(int i=0;i<256;i++){
			int ai = ((i>>5)&0x7);
			int bi = ((i>>4)&0xF);
			int ci = ((i>>3)&0x1F);
			int r = (i*i);
			a[ai] = (a[ai]+r)/2;
			b[bi] = (b[bi]+r)/2;
			c[ci] = (c[ci]+r)/2;
		}
		
		int i = 15;
		int aj = ((i>>5)&0x7);
		int bj = ((i>>4)&0xF);
		int cj = ((i>>3)&0x1F);
		System.out.println("a :"+(a[aj]));
		System.out.println("b :"+(b[bj]));
		System.out.println("c :"+(c[cj]));
		System.out.println(" :"+(5*c[cj]+2*b[bj]+a[aj])/8);
		System.out.println(" :"+(c[cj]+c[cj+1])/2);
		
		System.out.println();
		for(i=0;i<c.length;i++)
			System.out.print(" "+c[i]);
		System.out.println();
		
		q();
		r(0,0);
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
