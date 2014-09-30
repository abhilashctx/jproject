

import java.io.FileOutputStream;

import sortalgo.RandomArray;

public class ITSort {

	public static void main(String[] args) {
		main_main(args);
	}
	
	public static void main_main(String[] args) {
		
		//debug flag
		final boolean debugLength=true;
		final boolean debugBuckets=false;//true;
		final boolean debugTimer=true;
		
		long dt=0;
		if(debugTimer){
			dt = System.currentTimeMillis();
		}
		
		//load data from file
		byte a[] = RandomArray.genText();
		a=rle(a);
		if(debugLength)	System.out.println("data len :"+a.length);
		
		//create index array and initialize
		int  ai[] = new int[a.length];
		for(int i=0;i<ai.length;i++) ai[i]=-1;
		
		//create type a,b
		int ta[]=new int[256];
		int tb[]=new int[256];
		
		//count suffixes
		int tac=0,tbc=0;
		for(int i=0;i<a.length-1;i++){
			if((a[i]+128) > (a[i+1]+128)){
				ta[a[i]+128]++; tac++;
			}else{
				tb[a[i]+128]++; tbc++;
			}
		}
		ta[a[a.length-1]+128]++; tac++;
		
		System.out.println("Type A : "+tac+" Type B : "+tbc);
		
		//create buckets
		int ba[]=new int[256];
		int bb[]=new int[256];
		
		//allocate buckets
		int cb=0;
		for(int i=0;i<256;i++){
			ba[i]=cb;
			cb+=ta[i]; cb+=tb[i];
			bb[i]=cb;
		}
		
		//debug
		if(debugBuckets){
			prnArray("ba", ba);
			prnArray("bb", bb);
		}
		
		//set type-b index on array
		tbc=0;
		int tbb[]=new int[256];
		for(int i=0;i<a.length-2;i++){
			if((a[i]+128) <= (a[i+1]+128) && (a[i+1]+128) > (a[i+2]+128)){
				bb[a[i]+128]--;
				ai[bb[a[i]+128]]=i;
				tbb[a[i]+128]++;
				tbc++;
			}
		}
		System.out.println("add TypeB "+tbc);
		
		//sort them
		for(int i=0;i<256;i++){
			if(tb[i]>1){
				int x=bb[i];
				int y=x+tbb[i]-1;
				//int y=(i+1)<256 ? ba[i+1]-1 : ai.length-1;
				if(x<0 || y<0) {System.out.println(x+","+y+" error");}
				qsort(a, ai, x, y);
			}
		}
		
		//set remaining type-b
		for(int i=ai.length-1;i>0;i--){
			if(ai[i]!=-1){
				int pidx = ai[i]-1;
				if(pidx<0) continue;
				if(pidx==a.length-1) continue;
				if((a[pidx]+128) <= (a[pidx+1]+128)){
					bb[a[pidx]+128]--;
					ai[bb[a[pidx]+128]]=pidx;
					tbc++;
				}
			}
		}
		System.out.println("add TypeB "+tbc);
		
		//type-a sort
		tac=0;
		
		//add first symbol which is before $(eof) charecter and update boundry
		ai[ba[a[a.length-1]+128]]=a.length-1;ba[a[a.length-1]+128]++; tac++;
		for(int i=0;i<ai.length;i++){
			int anow = ai[i];
			if(anow==0) continue;
			if(anow>0){
				if(a[anow-1]+128>a[anow]+128){
					int aprev=a[anow-1]+128;
					ai[ba[aprev]]=anow-1;
					ba[aprev]++;
					tac++;
				}
			}else{
				System.out.println(i+":-1");
			}
		}
		System.out.println("add TypeA "+tac);
		
		if(debugTimer){
			dt = System.currentTimeMillis()-dt;
			System.out.println(dt+" ms");
		}
		
		System.out.println("DONE");
		
		verifyResult(a, ai);
		
		dump2File(getL(a, ai));
		//dump2File(dc(getL(a, ai)));

	}
	
	public static byte[] getL(byte a[],int ai[]){
		byte b[]=new byte[a.length];
		for(int i=0;i<a.length;i++){
			int idx = ai[i]-1;
			if(idx==-1) idx=a.length-1;
			b[i]=a[idx];
		}
		return b;
	}
	public static void dump2File(byte a[]){
		try{
			FileOutputStream fos = new FileOutputStream("E:/Abhilash/testarea/bwtout");
			fos.write(a);
			fos.close();
			System.out.println("created bwtout");
		}catch(Exception e){}
	}
	
	public static void verifyResult(byte a[],int ai[]){
		for(int i=1;i<ai.length;i++){
			if(ai[i-1]<0){System.out.println("*-1 at "+(i-1));continue;}
			if(ai[i]<0)  {System.out.println("*-1 at "+(i));continue;}
			if(a[ai[i-1]] > a[ai[i]]){
				System.out.println("*Verify failed! ["+i+"] "+ai[i-1]+":"+a[ai[i-1]]+" > "+ai[i]+":"+a[ai[i]]);
				//return;
			}
		}
		System.out.println("verify complete");
		//verifyICount(ai);
	}
	
	public static void verifyICount(int ai[]){
		int count[]=new int[ai.length];
		for(int i=0;i<ai.length;i++) count[ai[i]]++;
		for(int i=0;i<count.length;i++){
			if(count[i]>1) System.out.println(i+"="+count[i]);
		}
	}
	
	public static void prnArray(String title,int arr[]){
		System.out.println(title + ":");
		for(int i=0;i<arr.length;i++) //System.out.println(arr[i]+" ");
			System.out.format("%5d ", arr[i]);
		System.out.println();
	}
	
public static void qsort(byte a[],int ai[],int x,int y){
		
		if(x>=y) return;
		
		if((y-x)<17){
			for(int i=x+1;i<=y;i++){
				int j=i;
				int t=ai[i];
				while(j>x && cmp(a,ai[j-1],t)>0){
					ai[j]=ai[j-1]; j--;
				}
				ai[j]=t;
			}
			return;
		}
		
		int p=x,i=x;
		for(int j=i+1;j<=y;j++){if(j<0 || p<0) System.out.println("xi yi check fail");
			if(cmp(a,ai[j],ai[p])<=0){
				i++; swap(ai, i, j);
			}
		}
		swap(ai, p, i);
		qsort(a,ai, x, i-1);
		qsort(a,ai, i+1, y);
	}

	public static int cmp(byte a[],int x,int y){
		if(x==y) return 0;
		int len = Math.min(a.length-x, a.length-y);
		boolean xbig=x<y;
		int xi=x;
		int yi=y;
		for(int i=0;i<len;i++){
			int diff = a[xi]-a[yi];
			if(diff!=0) return diff;
			xi++;yi++;
		}
		if(xbig) return 1; else return -1;
	}
	
	public static void swap(int ai[],int i,int j){
		int t = ai[i];
		ai[i] = ai[j];
		ai[j] = t;
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
	
}
