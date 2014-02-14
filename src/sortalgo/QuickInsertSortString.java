package sortalgo;

public class QuickInsertSortString {
	
	public void qsort(byte a[][],int x,int y){
		if((y-x)<65){
			insertsort(a, x, y);
			return;
		}
		byte p[] = a[(x+y)/2];
		int i=x;
		int j=y;
		while(i<=j){
			while(cmp1(a[i],p)<0) i++;
			while(cmp1(a[j],p)>0) j--;
			if(i<=j){
				swap(a,i,j); i++;j--;
			}
		}
		if(x<j) qsort(a, x, j);
		if(i<y) qsort(a, i, y);
	}
	
	public void insertsort(byte a[][],int x,int y){
		for(int i=x+1;i<=y;i++){
			int f=-1;
			byte tmp[]=a[i];
			for(int j=i-1;(j>=x) && (cmp1(tmp,a[j])<0);j--){
				f=j;
			}
			if(f==-1) continue;
			for(int j=i;j>f;j--){ swpCount++;
				a[j]=a[j-1];
			}
			a[f]=tmp;
		}
	}
	
	public int cmp(byte a[],byte b[]){//1000
		int diff=0;
		for(int i=0;i<a.length;i++){
			diff = a[i]-b[i];
			if(diff!=0) return diff;
		}
		return 0;
	}
	
	public int cmp1(byte a[],byte b[]){//689
		cmpCount++;
		int diff=0;
		int alen=a.length;
		for(int i=0;i<alen;){
			diff = a[i]-b[i];
			if(diff!=0) return diff;
			i+=1;
			if(i < alen){
				diff = a[i]-b[i];
				if(diff!=0) return diff;
				i+=1;
				if(i < alen){
					diff = a[i]-b[i];
					if(diff!=0) return diff;
					i+=1;
					if(i < alen){
						diff = a[i]-b[i];
						if(diff!=0) return diff;
						i+=1;
						if(i < alen){
							diff = a[i]-b[i];
							if(diff!=0) return diff;
							i+=1;
							if(i < alen){
								diff = a[i]-b[i];
								if(diff!=0) return diff;
								i+=1;
								if(i < alen){
									diff = a[i]-b[i];
									if(diff!=0) return diff;
									i+=1;
									if(i < alen){
										diff = a[i]-b[i];
										if(diff!=0) return diff;
										i+=1;
										if(i < alen){
											diff = a[i]-b[i];
											if(diff!=0) return diff;
											i+=1;
										} else return 0;
									} else return 0;
								} else return 0;
							} else return 0;
						} else return 0;
					} else return 0;
				} else return 0;
			} else return 0;
		}
		return 0;
	}
	
	public int cmp2(byte a[],byte b[]){//910
		int diff=0;
		for(int i=0;i<a.length;){
			int dl = a.length-i;
			if(dl > 2){
				int x = ((a[i]<<8)|a[i+1]);
				int y = ((b[i]<<8)|b[i+1]);
				diff = x-y;
				if(diff!=0) return diff;
				i+=2;
			}else{
				diff = a[i]-b[i];
				if(diff!=0) return diff;
				i+=1;
			}
		}
		return 0;
	}
	
	public void swap(byte a[][],int i,int j){
		swpCount++;
		byte t[] = a[i];
		a[i] = a[j];
		a[j] = t;
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
	
	public static int cmpCount=0;
	public static int swpCount=0;
	public static void qsorttest(){
		// 17000 with cmp is ~1 sec , with cmp1 is ~750ms
		//20000 with QS cmp1 ~950ms
		//20000 with QIS cmp1 ~40ms
		//int x=17000;
		int x=15000;
		byte a[][] = RandomArray.genStrings(x, x);
		System.out.println("start");
		QuickInsertSortString q = new QuickInsertSortString();
		long st = System.currentTimeMillis();
		q.qsort(a, 0, x-1);
		long dt = System.currentTimeMillis() - st;
		dump(a);
		System.out.println("time "+dt);
		System.out.println("cmp calls "+cmpCount);
		System.out.println("swp calls "+swpCount);
	}
	
	public static void dump(byte a[][]){
		for(int i=0;i<10;i++){
			System.out.print(i+" -> ");
			for(int j=0;j<10;j++){
				System.out.print((char)a[i][j]);
			}System.out.println("");
		}
	}
	
}
