package compress.aritest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class RCTest {
	
	private int l,r,c;
	private boolean d;
	private int buff,buff_c;
	public void init(boolean dec,InputStream is){
		l=0; r=0xFFFF; d=dec; c=0; buff=0; buff_c=0;
		if(d) {try{c= (is.read()<<8)+is.read();}catch(Exception e){}}
	}
	
	public int process(int b,int p,OutputStream os,InputStream is){
		int nr = ((r*p)>>5)+1; //add 1 so r is never 0, coz r=nr-1 below might be negative
		if(d){b=((c-l)<nr)? 1:0;}
		//if(d){System.out.println("c:"+c+" l:"+l+" (c-l):"+(c-l)+" nr:"+nr+" geq:"+((c-l)>=nr)+" b:"+b);}
		if(b==1) r=nr; else {r-=nr;l+=nr;}
		while( (l^(l+r))<0x100 || r<0x10){
			if((l>>16)>0) System.out.println("carry "+(l>>16)); //check carry should never happen
			if((l^(l+r))>=0x100 && r<0x10){
				r=(-l)&0xF;
			}
			if(d)  {try{c=((c<<8)+is.read())&0xFFFF;}catch(Exception e){}}
			else {try{os.write((byte)(l>>8));}catch(Exception e){}}
			l=((l<<8)&0xFFFF); r=((r<<8)&0xFFFF)+0xFF;
			//if((0xFFFF-l)<r) r=0xFFFF-l; //does not happen if while (l^(l+r))<0x100 is checked
		}
		return b;
	}
	
	public void flush(OutputStream os){
		try{
			os.write((byte)(l>>8));l=((l<<8)&0xFFFF);r=((r<<8)&0xFFFF);
			os.write((byte)(l>>8));l=((l<<8)&0xFFFF);r=((r<<8)&0xFFFF);
		}catch(Exception e){}
	}
	
	public static void main(String[] args) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		RCTest rc = new RCTest();
		
		rc.init(false,null);
		for(int i=0;i<64;i++) rc.process((i>>1)&1, (((i>>1)&1)==1 ? 31 : 1), bos, null); // (((i>>1)&1)==1 ? 30 : 2)
		rc.flush(bos);
		System.out.println("len : "+bos.size());
		byte a[] = bos.toByteArray();
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}System.out.println();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(a);
		rc.init(true,bis);
		for(int i=0;i<64;i++){
			int z=rc.process(0, (((i>>1)&1)==1 ? 31 : 1), null, bis);
			System.out.print(" "+z);
		}System.out.println();
		
		speedCheck();
	}
	// (i)&1 should give    0 1 0 1 0 1 ...
	// (i>>1)&1 should give 0 0 1 1 0 0 1 1 ...
	
	public static void speedCheck(){
		final int BITCOUNT = 1048576*10;
		RCTest rc = new RCTest();
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
}
