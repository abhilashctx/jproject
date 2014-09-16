package sortalgo;

public class QuickInsertSortText {
	
	public void qsort(byte a[],int ai[],int x,int y){
		
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
	
	public void insertsort(byte a[],int ai[],int x,int y){
		for(int i=x+1;i<=y;i++){
			int f=-1;
			int tmp=ai[i];
			for(int j=i-1;(j>=x) && (cmp(a,tmp,ai[j])<0);j--){
				f=j;
			}
			if(f==-1) continue;
			for(int j=i;j>f;j--){
				ai[j]=ai[j-1];
			}
			ai[f]=tmp;
		}
	}
	
	public int cmp(byte a[],int x,int y){//1000
		if(x==y) return 0;
		int diff=0;
		//int len = a.length ;//- ((x > y) ? x : y);
		int len=Math.min(a.length-x, a.length-y);
		boolean xbig = x<y;
		int xi=x;
		int yi=y;
		for(int i=0;i<len;i++){
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
	
	public int cmp1(byte a[],int x,int y){//1000
		int diff=0;
		int len = a.length ;//- ((x > y) ? x : y);
		for(int i=0;i<len;){
			int xi = x+i; int yi = y+i;
			if(xi >= len) xi-=len; if(yi >= len) yi-=len;
			diff = a[xi]-a[yi];
			if(diff!=0) return diff;
			i++;
			if(i<len){
				xi = x+i; yi = y+i;
				if(xi >= len) xi-=len; if(yi >= len) yi-=len;
				diff = a[xi]-a[yi];
				if(diff!=0) return diff;
				i++;
				if(i<len){
					xi = x+i; yi = y+i;
					if(xi >= len) xi-=len; if(yi >= len) yi-=len;
					diff = a[xi]-a[yi];
					if(diff!=0) return diff;
					i++;
					if(i<len){
						xi = x+i; yi = y+i;
						if(xi >= len) xi-=len; if(yi >= len) yi-=len;
						diff = a[xi]-a[yi];
						if(diff!=0) return diff;
						i++;
						if(i<len){
							xi = x+i; yi = y+i;
							if(xi >= len) xi-=len; if(yi >= len) yi-=len;
							diff = a[xi]-a[yi];
							if(diff!=0) return diff;
							i++;
							if(i<len){
								xi = x+i; yi = y+i;
								if(xi >= len) xi-=len; if(yi >= len) yi-=len;
								diff = a[xi]-a[yi];
								if(diff!=0) return diff;
								i++;
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	
	
	public void swap(int ai[],int i,int j){
		int t = ai[i];
		ai[i] = ai[j];
		ai[j] = t;
	}
	
	public static void main(String[] args) {
		qsorttest();
		/*int x=10;
		byte a[][] = RandomArray.genStrings(x, x);
		System.out.println("start");
		long st = System.currentTimeMillis();
		rad(a,0,0,a.length);		
		long dt = System.currentTimeMillis() - st;
		dump(a);
		System.out.println("rad time "+dt);*/
	}
	
	public static void qsorttest(){
		byte a[] = RandomArray.genText();
		int  ai[] = new int[a.length];
		for(int i=0;i<ai.length;i++) ai[i]=i;
		System.out.println("start text length :"+a.length);
		QuickInsertSortText q = new QuickInsertSortText();
		long st = System.currentTimeMillis();
		q.qsort(a,ai, 0, a.length-1);
		long dt = System.currentTimeMillis() - st;
		//dump(a,ai);
		System.out.println("time "+dt);
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
	
}
