package sortalgo;

public class QuickInsertSort implements Sort {

	private int swpCount=0;
	
	public void sort(int[] a) {
		qsort(a, 0, a.length-1);
	}
	
	public void qsort(int[] a,int x,int y){
		
		if((y-x)<16){
			//insertsort(a, x, y);
			for(int i=x+1;i<=y;i++){
				int j=i;
				int tmp=a[j];
				while(j>0 && a[j-1]>tmp){
					a[j]=a[j-1]; j--; swpCount++;
				}a[j]=tmp;
			}
			return;
		}
		
		if(x>=y) return;
		int p=(x+y)/2;
		int ts=a[p]; a[p]=a[x]; a[x]=ts;
		int i=x; p=x;
		for(int j=i+1;j<=y;j++){
			if(a[j]<=a[p]){
				i++;swap(a, i, j);
			}
		}
		swap(a, i, p);
		qsort(a,x,i-1);
		qsort(a,i+1,y);
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
