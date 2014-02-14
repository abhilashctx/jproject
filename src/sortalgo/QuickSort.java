package sortalgo;

public class QuickSort implements Sort {

	private int swpCount=0;
	
	public void sort(int[] a) {
		qsort(a, 0, a.length-1);
	}
	
	public void qsort(int[] a,int x,int y){
		//int p = a[(x+y)/2];
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
	
	public String toString() {
		return "QS";
	}
	
	public int getSwapCount() {
		return swpCount;
	}
}
