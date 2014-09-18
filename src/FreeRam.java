import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FreeRam {

	public static void main(String[] args) throws Exception {
		
		
		//q();
		//r(0,0);
		
		byte b[] = new byte[10000000];
		try{
			FileInputStream fis = new FileInputStream("E:/Abhilash/testarea/enwik8");
			fis.read(b);
			fis.close();
			fis=null;
			
			FileOutputStream fos = new FileOutputStream("E:/Abhilash/testarea/enwik7");
			fos.write(b);
			fos.close();
			fos=null;
		}catch(Exception e){}
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
