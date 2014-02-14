package compress.rolz;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {

	int wbits,wsize,wmask;
	int lbits,lmin,lmax;
	int wi,wl;
	byte w[];
	int tbits,tsize;
	int tab[][];
	int mo,ml;
	
	FileInputStream fis; int c;
	FileOutputStream fos;
	
	int tag,taglst[],tagi;
	
	public Test(){
		wbits=16; wsize=(1<<wbits); wmask=wsize-1;
		lbits=4;  lmin=3; lmax=(1<<lbits)+lmin-2;
		w=new byte[wsize];
		tbits=4;tsize=(1<<tbits);
		tab=new int[(1<<16)][tsize];
		for(int i=0;i<(1<<16);i++){
			for(int j=0;j<tsize;j++){
				tab[i][j]=-1;
			}
		}
		wi=0;wl=0; c=0;
		mo=0;ml=0;
		
		tag=0;tagi=0;
		taglst=new int[8];
	}
	
	public void tf(){
		ml=0;
		int len=0;
		int pidx = ((wi+wmask-1)&wmask);
		int tidx = ((char)w[pidx]<<8)|((char)w[((pidx+1)&wmask)]);
		//System.out.println("wi="+wi+" pidx"+pidx+" tidx"+tidx);
		//if(w[pidx]<0) System.out.println("************************************"+w[pidx]);
		for(int i=0;i<tsize;i++){
			int p=tab[tidx][i];
			if(p==-1) break;
			int tmp = wi-p; if(tmp<0) tmp+=wsize;
			if(tmp > (wsize-lmax-10)) break;
			if( w[((wi+ml)&wmask)] != w[((p+ml)&wmask)] ) continue;
			len=0;
			while((len<wl) && (w[((wi+len)&wmask)] == w[((p+len)&wmask)])) len++;
			if(len>ml){
				ml=len; mo=i;
				if(len==lmax) return;
			}
		}
	}
	
	public void tad(){
		int pidx = ((wi+wmask-1)&wmask);
		int tidx = ((char)w[pidx]<<8)|((char)w[((pidx+1)&wmask)]);
		for(int i=tsize-1;i>0;i--) tab[tidx][i]=tab[tidx][i-1];
		tab[tidx][0]=wi;
		//System.out.print("tab["+tidx+"][0]=");
		//for(int i=0;i<tsize;i++) System.out.print(" "+tab[tidx][i]);
		//System.out.println();
	}
	
	public void t1()throws Exception{
		int c=0;
		while(wl<lmax){
			if((c=fread())==-1) break;
			w[wl++]=(byte)c;
		}
		while(wl>0){
			tf();
			if(ml<lmin){
				//System.out.print(w[wi]);
				fwriteLit(w[wi]);
				ml=1;
			}
			else{
				//System.out.println("mo="+mo+" ml="+ml);
				fwriteMatch(mo, ml);
			}
			while(ml>0){
				tad();
				if((c=fread())==-1) wl--;
				else w[((wi+lmax)&wmask)]=(byte)c;
				wi=((wi+1)&wmask);
				ml--;
			}
		}
		fwriteMatch(0, 18);//eof
		fwritetag();
	}
	int fread()throws Exception{
		if(c!=-1) c=fis.read();
		return c;
	}
	void fwriteLit(int b){
		tag=((tag<<1)|0);
		taglst[tagi++]=b;
		if(tagi==8) fwritetag();
	}
	void fwriteMatch(int bmo,int bml){
		bml-=lmin;
		tag=((tag<<1)|1);
		taglst[tagi++]=((bmo<<4)|bml);
		if(tagi==8) fwritetag();
	}
	void fwritetag(){
		if(tagi<8) tag=(tag<<(8-tagi));
		fwrite(tag);
		for(int i=0;i<tagi;i++) fwrite(taglst[i]);
		tag=0;tagi=0;
	}
	void fwrite(int b){
		try{
			fos.write(b);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void t2()throws Exception{
		while(true){
			tag = fread();
			for(int i=7;i>=0;i--){
				int choice=((tag>>i)&1);
				int data=fread();
				if(choice==0){
					w[wi]=(byte)data;
					fwrite(data);
					ml=1;
				}else{
					mo=(data>>4);
					ml=(data&15)+lmin;
					if(ml==18) return;
					int pidx = ((wi+wmask-1)&wmask);
					int tidx = ((char)w[pidx]<<8)|((char)w[((pidx+1)&wmask)]);
					int p=tab[tidx][mo];
					for(int j=0;j<ml;j++){
						data=w[((p+j)&wmask)];
						w[((wi+j)&wmask)]=(byte)data;
						fwrite(data);
					}
				}
				while(ml>0){
					tad();
					wi=((wi+1)&wmask);
					ml--;
				}
			}
		}
	}
	
	void set(FileInputStream _fis,FileOutputStream _fos){
		fis=_fis; fos=_fos;
	}
	public static void main(String[] args)throws Exception {
		if(args.length<3){
			System.out.println("test c|d in out");
			return;
		}
		String mode=args[0];
		System.out.println("mode="+mode);
		System.out.println("in="+args[1]);
		System.out.println("out="+args[2]);
		FileInputStream fis=new FileInputStream(args[1]);
		FileOutputStream fos=new FileOutputStream(args[2]);
		Test test = new Test();
		test.set(fis, fos);
		if(mode.equals("c")) test.t1(); else test.t2();
		fis.close(); fos.close();
	}
}
