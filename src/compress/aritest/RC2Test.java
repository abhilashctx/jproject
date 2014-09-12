package compress.aritest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class RC2Test {

	private int x,y,c;
	private boolean d;
	
	public void init(boolean dec,InputStream is){
		x=0; y=0xFFFF; d=dec; c=0;
		if(d) {try{c= (is.read()<<8)+is.read();}catch(Exception e){}}
	}
	
	public int process(int b,int p,OutputStream os,InputStream is){
		int xy = x+(((y-x)*p)>>5);
		//int xy = x+xyt[((y-x)>>12)][(p>>1)]; //table test
		if(d){b=(c<=xy)? 1:0;}
		//if(d){System.out.println("x,y:"+x+","+y+" c:"+c+" xy:"+xy+" b:"+b);}
		if(b==1) y=xy; else x=xy+1;
		//while(((x^y)&0xFF00)==0){
		while((x^y)<0x100){
			if(d)  {try{c=((c<<8)+is.read())&0xFFFF;}catch(Exception e){}}
			else {try{os.write((byte)(x>>8));}catch(Exception e){}}
			x=((x<<8)&0xFFFF); y=((y<<8)&0xFFFF)+0xFF;
		}
		return b;
	}
	
	public void flush(OutputStream os){
		try{
			os.write((byte)(x>>8));x=((x<<8)&0xFFFF);
			os.write((byte)(x>>8));x=((x<<8)&0xFFFF);
		}catch(Exception e){}
	}
	
	public static void main(String[] args) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RC2Test rc = new RC2Test();
		rc.xyTableGen(); //table test
		rc.init(false,null);
		for(int i=0;i<32;i++) rc.process((i>>1)&1, (((i>>1)&1)==1 ? 31 : 1), bos, null); // (((i>>1)&1)==1 ? 30 : 2)
		rc.flush(bos);
		System.out.println("len : "+bos.size());
		byte a[] = bos.toByteArray();
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}System.out.println();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(a);
		rc.init(true,bis);
		for(int i=0;i<32;i++){
			int z=rc.process(0, (((i>>1)&1)==1 ? 31 : 1), null, bis);
			System.out.print(" "+z);
		}System.out.println();
		
		speedCheck();
	}
	
	
	public static void speedCheck(){
		final int BITCOUNT = 1048576*10;
		RC2Test rc = new RC2Test();
		//rc.xyTableGen();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		long st = System.currentTimeMillis();
		long dt = 0;
		
		rc.init(false,null);
		for(int i=0;i<BITCOUNT;i++) rc.process((i>>1)&1, (((i>>1)&1)==1 ? 31 : 1), bos, null);
		rc.flush(bos);
		
		dt = System.currentTimeMillis() - st;
		System.out.println("Enc Time :"+dt+" ms");
		
		byte a[] = bos.toByteArray();
		System.out.println("len :"+a.length);
		ByteArrayInputStream bis = new ByteArrayInputStream(a);
		
		st = System.currentTimeMillis();
		
		rc.init(true,bis);
		for(int i=0;i<BITCOUNT;i++) rc.process(0, (((i>>1)&1)==1 ? 31 : 1), null, bis);
		
		dt += (System.currentTimeMillis()-st);
		
		System.out.println("Time : "+dt+" ms");
	}
	
	private int xyt[][];
	public void xyTableGen(){
		xyt = new int[16][16];
		for(int i=0;i<xyt.length;i++) for(int j=0;j<xyt[0].length;j++){
			xyt[i][j]= ((i*j)<<8);
		}
	}
}
