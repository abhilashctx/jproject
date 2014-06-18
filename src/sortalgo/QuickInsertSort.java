package sortalgo;

public class QuickInsertSort implements Sort {

	private int swpCount=0;
	
	public void sort(int[] a) {
		qsort(a, 0, a.length-1);
	}
	
	public void qsort(int[] a,int x,int y){
		
		if((y-x)<16){
			//insertsort(a, x, y);
			for(int i=x+1;i<y;i++){
				int j=i;
				int tmp=a[j];
				while(j>0 && a[j-1]>tmp){
					a[j]=a[j-1]; j--;
				}a[j]=tmp;
			}
			return;
		}
		
		int p = (a[x] + a[(x+y)/2] + a[y])/3;
		int i = x;
		int j = y;
		while (i<=j) {
			while(a[i]<p) i++;
			while(a[j]>p) j--;
			if(i<=j){
				swap(a,i,j); i++; j--;
			}
		}
		//swap(a,p,j);
		if(x<j) qsort(a,x,j);
		if(i<y) qsort(a,i,y);
	}

	public void swap(int[] a,int x,int y){
		swpCount++;
		int tmp = a[x]; a[x] = a[y]; a[y] = tmp;
	}
	
	public void insertsort(int a[],int x,int y){
		for(int i=x+1;i<=y;i++){
			int f=-1;
			int tmp=a[i];
			for(int j=i-1;(j>=x) && (tmp<a[j]);j--){
				f=j;
			}
			if(f==-1) continue;
			for(int j=i;j>f;j--){ swpCount++;
				a[j]=a[j-1];
			}
			a[f]=tmp;
		}
	}
	
	public String toString() {
		return "QIS";
	}
	
	public int getSwapCount() {
		return swpCount;
	}
}
