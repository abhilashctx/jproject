package sortalgo;

public class HeapSort implements Sort{

	private int swpCount=0;
	
	public void sort(int a[]){
		int end = a.length-1;
		int start = ((end-1)>>1);
		while(start>=0){
			siftDw(a, start, end-1);
			start-=1;
		}
		
		while (end > 0){
			{int t=a[end];a[end]=a[0];a[0]=t;}
			end-=1;
			siftDw(a, 0, end);
		}
	}
	
	/*public void sort(int[] a) {
		heapify(a, a.length);
		int end = a.length-1;
		while (end > 0){
			swap(a,end,0);
			end-=1;
			siftDw(a, 0, end);
		}
	}*/
	
	/*public void heapify(int a[], int count){
		int start = ((count-1)>>1);//(count-1)/2;
		while(start>=0){
			siftDw(a, start, count-1);
			start-=1;
		}
	}*/
	
	public void siftDw(int a[], int start, int end){
		int root = start;
		while ((root*2+1) <= end){
			int c = root*2+1;
			int s = root;
			if(a[s]<a[c]) s=c;
			if((c+1)<=end && (a[s]<a[c+1])) s=c+1;
			if(s!=root) {swap(a,s,root);root=s;}
			else return;
		}
	}
	
	public void swap(int a[],int i,int j){
		swpCount++;
		int tmp = a[i];
		a[i]=a[j];
		a[j]=tmp;
	}
	
	public String toString() {
		return "HS";
	}
	
	public int getSwapCount() {
		return swpCount;
	}
}
