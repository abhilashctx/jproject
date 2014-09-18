package sortalgo;

public class ITSort {

	public static void main(String[] args) {
		
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
		//for(int i=0;i<ai.length;i++) ai[i]=i;
		
		//create type a,b
		int ta[]=new int[256];
		int tb[]=new int[256];
		
		//count suffixes
		for(int i=0;i<a.length-1;i++){
			if((a[i]+128) > (a[i+1]+128)){
				ta[a[i]+128]++;
			}else{
				tb[a[i]+128]++;
			}
		}
		ta[a[a.length-1]+128]++;
		
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
		for(int i=0;i<a.length-1;i++){
			if((a[i]+128) <= (a[i+1]+128)){
				bb[a[i]+128]--;
				ai[bb[a[i]+128]]=i;
			}
		}
		
		//sort them
		for(int i=0;i<256;i++){
			if(tb[i]>1){
				int x=bb[i];
				int y=x+tb[i]-1;
				qsort(a, ai, x, y);
			}
		}
		
		//type-a sort
		for(int i=0;i<a.length;i++){
			int anow = ai[i];
			if(anow>0){
				if(a[anow-1]>a[anow]){
					int aprev=a[anow-1]+128;
					ai[ba[aprev]]=anow-1;
					ba[aprev]++;
				}
			}
		}
		ai[ba[a[a.length-1]+128]]=a.length-1;
		
		if(debugTimer){
			dt = System.currentTimeMillis()-dt;
			System.out.println(dt+" ms");
		}
		
		System.out.println("DONE");
		
		verifyResult(a, ai);
		
	}
	
	public static void verifyResult(byte a[],int ai[]){
		for(int i=1;i<ai.length;i++){
			if(a[ai[i-1]] > a[ai[i]]){
				System.out.println("*Verify failed! ["+i+"] "+a[ai[i-1]]+" > "+a[ai[i]]);
				//return;
			}
		}
		System.out.println("verify complete");
		int count[]=new int[ai.length];
		for(int i=0;i<ai.length;i++) count[i]++;
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
		for(int j=i+1;j<=y;j++){
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

}
