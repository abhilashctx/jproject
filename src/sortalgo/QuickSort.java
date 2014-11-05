package sortalgo;

public class QuickSort implements Sort {

	private int swpCount=0;
	
	public void sort(int[] a) {
		qsort(a, 0, a.length-1);
	}
	
	public void qsort(int[] a,int x,int y){
		
		// commented bad implementation of qsort
		
		//int p = a[(x+y)/2];
		/*int p = (a[x] + a[(x+y)/2] + a[y])/3;
		int i = x;
		int j = y;
		while (i<=j) {
			while(i<y && a[i]<p) i++;
			while(j>x && a[j]>p) j--;
			if(i<=j){
				swap(a,i,j); i++; j--;
			}
		}
		//System.out.println(i+","+j);
		if(x<j) qsort(a,x,j);
		if(i<y) qsort(a,i,y);*/
		
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
	
	public String toString() {
		return "QS";
	}
	
	public int getSwapCount() {
		return swpCount;
	}
}
