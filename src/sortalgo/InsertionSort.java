package sortalgo;

public class InsertionSort implements Sort{
	
	private int swpCount=0;
	
	public void sort(int a[]){
		for(int i=1;i<a.length;i++){
			/*for(int j=i;(j>0) && (a[j]<a[j-1]);j--){
				int tmp = a[j];
				a[j] = a[j-1];
				a[j-1] = tmp;
			}*/
			int f=-1;
			int tmp=a[i];
			for(int j=i-1;(j>=0) && (tmp<a[j]);j--){
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
		return "IS";
	}
	
	public static void main(String[] args) {
		int a[] = RandomArray.getArray(10, 10);
		ArrayDump.dump(a);
		Sort s = SortFactory.getSorter(SortFactory.INSERT_SORT);
		long dt = TimedOpt.exec(s,a);
		ArrayDump.dump(a);
		System.out.println("time="+dt);
	}
	
	public int getSwapCount() {
		return swpCount;
	}

}
