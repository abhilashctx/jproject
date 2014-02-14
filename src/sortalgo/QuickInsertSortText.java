package sortalgo;

public class QuickInsertSortText {
	
	public void qsort(byte a[],int ai[],int x,int y){
		
		if((y-x)<65){
			insertsort(a,ai, x, y);
			return;
		}
		
		int p = (x+y)/2;
		int i=x;
		int j=y;
		while(i<=j){
			while(cmp1(a,ai[i],ai[p])<0) i++;
			while(cmp1(a,ai[j],ai[p])>0) j--;
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
			for(int j=i-1;(j>=x) && (cmp1(a,tmp,ai[j])<0);j--){
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
		int diff=0;
		int len = a.length ;//- ((x > y) ? x : y);
		for(int i=0;i<len;i++){
			int xi = x+i;
			int yi = y+i;
			if(xi >= len) xi-=len;
			if(yi >= len) yi-=len;
			diff = a[xi]-a[yi];
			if(diff!=0) return diff;
		}
		return 0;
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
		// 17000 with cmp is ~1 sec , with cmp1 is ~750ms
		//int x=17000;
		//20000 with cmp1 ~950ms
		byte a[] = RandomArray.genText();
		int  ai[] = new int[a.length];
		for(int i=0;i<ai.length;i++) ai[i]=i;
		System.out.println("start");
		QuickInsertSortText q = new QuickInsertSortText();
		long st = System.currentTimeMillis();
		q.qsort(a,ai, 0, a.length-1);
		long dt = System.currentTimeMillis() - st;
		dump(a,ai);
		System.out.println("time "+dt);
	}
	
	public static void dump(byte a[],int ai[]){
		/*for(int i=0;i<10;i++){
			System.out.print(i+" -> ");
			for(int j=0;j<10;j++){
				System.out.print((char)a[ai[i]+j]);
			}System.out.println("");
		}*/
		System.out.println("----");
		for(int i=0;i<a.length;i++){
			int idx = ai[i]+a.length-1;
			if(idx >= a.length) idx-=a.length;
			if(a[idx]!='\n' && a[idx]!=' ' && a[idx]!='\t' && a[idx]!='\r')
			  System.out.print((char)a[idx]);
		}System.out.println();
	}
	
}
