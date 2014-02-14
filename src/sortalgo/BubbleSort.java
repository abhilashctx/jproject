package sortalgo;

public class BubbleSort implements Sort {

	private int swpCount=0;
	
	public void sort(int[] a) {
		for(int i=0;i<a.length;i++){
			boolean swap=false;
			for(int j=1;j<(a.length-i);j++){
				if(a[j-1] > a[j]){
					swpCount++;
					int tmp = a[j-1];
					a[j-1] = a[j];
					a[j] = tmp;
					swap=true;
				}
			}
			if(!swap) break;
		}
	}
	
	@Override
	public String toString() {
		return "BS";
	}
	
	public int getSwapCount() {
		return swpCount;
	}

}
