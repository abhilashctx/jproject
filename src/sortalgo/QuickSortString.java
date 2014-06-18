package sortalgo;

public class QuickSortString {
	
	public void qsort(byte a[][],int x,int y){
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
		//int x=17000;
		//20000 with cmp1 ~950ms
		int x=20000;
		byte a[][] = RandomArray.genStrings(x, x);
		System.out.println("start");
		QuickSortString q = new QuickSortString();
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
	
	public static void rad(byte a[][],int ridx,int r1,int r2){
		if(ridx>3) return;
		int c[]=new int[27];
		for(int i=0;i<c.length;i++) c[i]=0;
		for(int i=r1;i<r2;i++) c[a[i][ridx]-'A'+1]+=1;
		for(int i=0;i<c.length;i++) System.out.println("c["+i+"]="+c[i]);
		for(int i=1;i<c.length;i++) c[i]+=c[i-1];
		for(int i=0;i<c.length;i++) System.out.println("* c["+i+"]="+c[i]);
		int d[]=RandomArray.copy(c);
		for(int i=0;i<c.length-1;i++){
			if((d[i+1]-d[i])<=0) continue;
			
		}
		/*for(int i=r1;i<r2;i++){
			int didx = a[i][ridx]-'A';
			int aidx = d[didx];
			if(c[didx]<=i && i<=c[didx+1]){
				if(aidx==i) d[didx]+=1;
				i++;
			} else {
				byte t[] = a[i];
				a[i] = a[aidx];
				a[aidx]=t;
				d[didx]+=1;
			}
		}*/
		d=null;
		/*for(int i=0;i<c.length-1;i++){
			if((c[i+1]-c[i])>1)	rad(a,ridx+1,c[i],c[i+1]);
		}*/
	}

}
