import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FreeRam {

	public static void main(String[] args) throws Exception {
		
		
		//q();
		//r(0,0);
		
		/*byte b[] = new byte[10000000];
		try{
			FileInputStream fis = new FileInputStream("E:/Abhilash/testarea/enwik8");
			fis.read(b);
			fis.close();
			fis=null;
			
			FileOutputStream fos = new FileOutputStream("E:/Abhilash/testarea/enwik7");
			fos.write(b);
			fos.close();
			fos=null;
		}catch(Exception e){}*/
		System.out.println();
		byte a[]={'a','a','b','b',(byte)150,(byte)150,(byte)150};
		for(int i=0;i<a.length;i++) System.out.print(a[i]+" "); System.out.println();
		a=dc(a);
		for(int i=0;i<a.length;i++) System.out.print(a[i]+" "); System.out.println();
		a=ddc(a);
		for(int i=0;i<a.length;i++) System.out.print(a[i]+" "); System.out.println();
		
		
		int f[]= {10,4,1,1};
		int fz[]={10,4,1,1};
		int t[][]=new int[32][4];
		int x=16;
		//for(int i=16;i<t.length;i++){
			while(x<32){
				for(int fi=0;fi<f.length;fi++){
					if(f[fi]>0){
						t[fz[fi]][fi]=x;
						fz[fi]++;
						f[fi]--; x++;
					}
				}
			}
		//}
		for(int i=0;i<t.length;i++){
			System.out.print(i+" : ");
			for(int fi=0;fi<f.length;fi++){
				System.out.print(t[i][fi]+" ");
			}
			System.out.println();
		}
		
		int d[]={0,0,1,2,0,0,1,0,0,0,1,0,0,1,1,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0};
		int sx = 19;
		int count=0;
		for(int i=0;i<d.length;i++){
			System.out.print("("+sx+")");
			while(t[sx][d[i]]==0){
				System.out.print(sx&1);
				sx>>=1; count++;
			}System.out.println();
			sx=t[sx][d[i]];
		}
		System.out.println("("+sx+") count bits:"+(count+5) + " data:"+(d.length*2));
		
		writebits(0x1F, 5);writebits(0xA, 4);
		
	}
	
	public static byte[] dc(byte a[]){
		//delta ?
		int pv=a[0];
		for(int i=1;i<a.length;i++){
			int v = pv - a[i];
			pv = a[i];
			a[i]=(byte)v;
		}
		return a;
	}
	
	public static byte[] ddc(byte a[]){
		//delta ?
		for(int i=1;i<a.length;i++){
			int v = a[i-1] - a[i];
			a[i]=(byte)v;
		}
		return a;
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
	
	public static int wbData=0;
	public static int wbLen=0;
	public static void writebits(int data,int len){
		wbData=(wbData<<len)+data;
		wbLen+=len;
		while(wbLen >= 8){
			System.out.println("data : "+((wbData>>(wbLen-8))&0xFF));
			wbLen-=8;
		}
	}
}
