package compress.rep;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class REP {
	
	
	public static int enc(int blen,byte in[],byte out[]){
		int hcount=0;
		int olen=0,ilen=0;
		int f[]=new int[256];
		int fc=0;
		//int fcode[] = new int[256];
		for(int i=0;i<blen;i++) f[in[i]+128]++;

		int esc=0;
		for(int i=0;i<f.length;i++){if(f[i]<f[esc]) esc=i;}
		System.out.println("esc :"+esc);
		
		/*if(fc==0){
			out[olen]=0;olen=1;
			for(ilen=0;ilen<blen;ilen++) {out[olen]=in[ilen];olen++;}
			return olen;
		}*/
		final int HTSIZE=(1<<15);
		int fht[] = new int[HTSIZE];
		for(int i=0;i<blen-4;i++){
			int c = (in[ilen]+128<<24)+(in[ilen+1]+128<<16)+(in[ilen+2]+128<<8)+(in[ilen+3]+128);
			int h = ((in[ilen]+128)*17+(in[ilen+1]+128)*11+(in[ilen+2]+128)*5+(in[ilen+3]+128)) & (HTSIZE-1);
			fht[h]=c;
		}
		
		out[olen] = (byte)esc; olen++;
		
		while(ilen<blen){
			if(ilen+4<blen){
				int c = (in[ilen]+128<<24)+(in[ilen+1]+128<<16)+(in[ilen+2]+128<<8)+(in[ilen+3]+128);
				int h = ((in[ilen]+128)*17+(in[ilen+1]+128)*11+(in[ilen+2]+128)*5+(in[ilen+3]+128)) & (HTSIZE-1);
				if(fht[h]==c){ hcount++;
					out[olen]=(byte)esc; olen++;
					if(h>=128){
						out[olen] = (byte)((1<<7)+(h>>8)); olen++;
						out[olen] = (byte)(h&0xFF); olen++;
					}else{
						out[olen]=(byte)h; olen++;
					}
					ilen+=4;
				}else{
					//fht[h]=c;
					if(in[ilen]==esc){
						out[olen]=(byte)esc; olen++;
					}
					out[olen]=in[ilen];
					ilen++; olen++;
				}
			}else{
				if(in[ilen]==esc){
					out[olen]=(byte)esc; olen++;
				}
				out[olen]=in[ilen];
				ilen++; olen++;
			}
		}
		System.out.println("blen:"+blen+" olen:"+olen+" hcount:"+hcount);
		return olen;
	}
	
	public static void main(String[] args) {
		/*if(args.length<1){
			System.out.println("Usage: REP <file>");
			System.exit(0);
		}*/
		//replaces 4 byte repeats with a code which is not found in the block
		//post rep comp is not good without finding highest freq reps
		final int SIZE=(1<<18);
		byte in[]=new byte[SIZE];
		byte out[]=new byte[SIZE+2];
		int blen=0;
		//String fname = "E:/Abhilash/testarea/bbb.exe";//args[0];
		String fname = "E:/Abhilash/testarea/book2";//args[0];
		String fnamerep = fname+".rep";
		try{
			FileInputStream fis = new FileInputStream(fname);
			FileOutputStream fos = new FileOutputStream(fnamerep);
			while((blen=fis.read(in))!=-1){
				int rlen = REP.enc(blen, in, out);
				fos.write(out, 0, rlen);
			}
			fis.close();
			fos.close();
		}catch(Exception e){e.printStackTrace();}
	}

}
