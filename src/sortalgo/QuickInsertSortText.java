package sortalgo;

import java.io.FileOutputStream;

public class QuickInsertSortText {
	
	public void qsort(byte a[],int ai[],int x,int y){
		
		if(x>=y) return;
		
		if((y-x)<10){
			for(int i=x+1;i<=y;i++){
				int j=i,t=ai[i];
				while(j>x && cmp(a,ai[j-1],t)>0){
					ai[j]=ai[j-1]; j--;
				}
				ai[j]=t;
			}
			return;
		}
		
		int p=x,i=x;
		for(int j=i+1;j<=y;j++){
			if(cmp(a,ai[j],ai[p])<=0){
				i++; swap(ai, i, j);
			}
		}
		swap(ai, p, i);
		qsort(a,ai, x, i-1);
		qsort(a,ai, i+1, y);
	}
	
	public int cmp(byte a[],int x,int y){//1000
		if(x==y) return 0;
		int diff=0;
		//int len = a.length ;//- ((x > y) ? x : y);
		int len=Math.min(a.length-x, a.length-y);
		boolean xbig = x<y;
		int xi=x,yi=y;
		for(int i=0;i<len;i++){
			//diff = (a[xi]+128)-(a[yi]+128);
			diff = a[xi]-a[yi];
			if(diff!=0) return diff;
			xi++;yi++;
			//if(xi>=len) xi=0;
			//if(yi>=len) yi=0;
		}
		if(xbig) return 1;
		else return -1;
		//return 0;
	}
	
	
	public void swap(int ai[],int i,int j){
		int t = ai[i]; ai[i] = ai[j]; ai[j] = t;
	}
	
	public void radix(byte a[],int ai[]){
		int r[]=new int[256];
		for(int i=0;i<a.length;i++) r[a[i]+128]++;
		int rstart[]=new int[256];
		for(int i=1;i<r.length;i++) rstart[i]=rstart[i-1]+r[i-1];
		for(int i=0;i<a.length;i++){
			ai[rstart[a[i]+128]]=i;rstart[a[i]+128]++;
		}
		rstart[0]=0;for(int i=1;i<r.length;i++) rstart[i]=rstart[i-1]+r[i-1];
		for(int i=1;i<r.length;i++){
			qsort(a, ai, rstart[i], rstart[i]+r[i]-1);
		}
	}
	
	public static void main(String[] args) {
		qsorttest();
	}
	
	public static void qsorttest(){
		byte a[] = RandomArray.genText();
		a=rle(a);
		int  ai[] = new int[a.length];
		for(int i=0;i<ai.length;i++) ai[i]=i;
		System.out.println("start text length :"+a.length);
		QuickInsertSortText q = new QuickInsertSortText();
		long st = System.currentTimeMillis();
		
		//q.qsort(a,ai, 0, a.length-1);
		q.radix(a, ai);
		
		long dt = System.currentTimeMillis() - st;
		System.out.println("time "+dt);
		dt=System.currentTimeMillis();
		//dump(a,ai);
		
		dump2File(getL(a, ai));
		//dump2File(mtf(getL(a, ai)));
		//dump2File(rle(mtf(getL(a, ai))));
		//dump2Con(getL(a, ai));
		//dump2Con(mtf(getL(a, ai)));
		//dump2Con(rle(mtf(getL(a, ai))));
		
		dt = System.currentTimeMillis()-dt;
		System.out.println("post processing took "+dt+" ms");
		
		verifyResult(a, ai);
	}
	
	public static void verifyResult(byte a[],int ai[]){
		for(int i=1;i<ai.length;i++){
			if(a[ai[i-1]] > a[ai[i]]){
				System.out.println("*Verify failed! ["+i+"] "+a[ai[i-1]]+" > "+a[ai[i]]);
				return;
			}
		}
		System.out.println("verify complete");
	}
	
	public static void dump(byte a[],int ai[]){
		System.out.println("----");
		for(int i=0;i<a.length;i++){
			int idx = ai[i]+a.length-1;
			if(idx >= a.length) idx-=a.length;
			if(a[idx]!='\n' && a[idx]!=' ' && a[idx]!='\t' && a[idx]!='\r')
			  System.out.print((char)a[idx]);
		}System.out.println();
	}
	
	public static void dump2File(byte a[]){
		try{
			FileOutputStream fos = new FileOutputStream("E:/Abhilash/testarea/bwtout");
			fos.write(a);
			fos.close();
			System.out.println("created bwtout");
		}catch(Exception e){}
	}
	
	public static void dump2Con(byte a[]){
		int min = Math.min(100, a.length);
		for(int i=0;i<min;i++) System.out.print(a[i]+" ");
		System.out.println();
	}
	
	public static byte[] getL(byte a[],int ai[]){
		byte b[]=new byte[a.length];
		for(int i=0;i<a.length;i++){
			int idx = ai[i]-1;
			if(idx<0) idx=a.length-1;
			b[i]=a[idx];
		}
		return b;
	}
	
	public static byte[] mtf(byte a[]){
		int c[]=new int[256];
		for(int i=0;i<256;i++) c[i]=i;
		for(int i=0;i<a.length;i++){
			int ch = a[i]+128;
			int j=0;
			while(c[j]!=ch) j++;
			a[i]=(byte)j;
			while(j>0){
				c[j]=c[j-1]; j--;
			}
			c[j]=ch;
		}
		return a;
	}
	
	public static byte[] rle(byte a[]){
		byte b[] = new byte[a.length];
		int bi=0;
		int count=1;
		boolean dorle=true;
		for(int i=1;i<a.length;i++){
			if(count<250 && a[i]==a[i-1]){
				count++;
			}else{
				if(count==1){
					b[bi++]=a[i-1];
				}
				else{
					b[bi++]=a[i-1]; b[bi++]=a[i-1];
					b[bi++]= (byte)(count-2);
					count=1;
				}
			}
			if(bi+3 >= a.length && i+3 < a.length){
				dorle=false; break;
			}
		}
		byte c[];
		if(dorle){
			c=new byte[bi];
			for(int i=0;i<bi;i++) c[i]=b[i];
			b=null;
		}else c=a;
		System.out.println("rle:"+dorle);
		return c;
	}
	
}
