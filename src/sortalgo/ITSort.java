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
				int y=tb[i]-1;
				qsort(a, ai, x, y);
			}
		}
		
		//type-a sort
		for(int i=0;i<a.length-1;i++){
			
		}
		
		System.out.println("DONE");
		
		if(debugTimer){
			dt = System.currentTimeMillis()-dt;
			System.out.println(dt+" ms");
		}
	}
	
	public static void prnArray(String title,int arr[]){
		System.out.println(title + ":");
		for(int i=0;i<arr.length;i++) //System.out.println(arr[i]+" ");
			System.out.format("%5d ", arr[i]);
		System.out.println();
	}
	
	public static void qsort(byte a[],int ai[],int x,int y){
		
		if((y-x)<65){
			//insertsort(a,ai, x, y);
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
		
		int p = (x+y)/2;
		int i=x;
		int j=y;
		while(i<=j){
			while(cmp(a,ai[i],ai[p])<0) i++;
			while(cmp(a,ai[j],ai[p])>0) j--;
			if(i<=j){
				swap(ai,i,j); i++;j--;
			}
		}
		if(x<j) qsort(a,ai, x, j);
		if(i<y) qsort(a,ai, i, y);
	}
	
	public static int cmp(byte a[],int x,int y){
		if(x==y) return 0;
		int diff=0;
		int len = Math.min(a.length-x, a.length-y);
		boolean xbig=x<y;
		int xi=x;
		int yi=y;
		for(int i=0;i<len;i++){
			diff = a[xi]-a[yi];
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

}
