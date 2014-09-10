package compress.aritest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class RCTest {
	
	private int l,r,c;
	private boolean d;
	
	public void init(boolean dec,InputStream is){
		l=0; r=0xFFFF; d=dec; c=0;
		if(d) {try{c= (is.read()<<8)+is.read();}catch(Exception e){}}
	}
	
	public int process(int b,int p,OutputStream os,InputStream is){
		int nr = ((r*p)>>5);
		if(d){b= ((c-l)>=nr)? 0:1;}
		if(b==1) r=nr; else {r-=nr;l+=nr;}
		while(r<0x100){
			if(!d) {try{os.write((byte)(l>>8));}catch(Exception e){}}
			if(d)  {try{c=((c<<8)+is.read())&0xFFFF;}catch(Exception e){}}
			l=((l<<8)&0xFFFF); r=((r<<8)&0xFFFF)+0xFF;
			if((0xFFFF-l)<r) r=0xFFFF-l;
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
			System.out.print(" "+rc.process(0, (((i>>1)&1)==1 ? 31 : 1), null, bis));
		}System.out.println();
	}
	// (i)&1 should give    0 1 0 1 0 1 ...
	// (i>>1)&1 should give 0 0 1 1 0 0 1 1 ...
}
