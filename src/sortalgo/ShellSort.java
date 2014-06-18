package sortalgo;

public class ShellSort implements Sort{

	public void sort(int[] a) {
		int h=1;
		int len=a.length;
		int lenby3 = a.length/3;
		while(h<=lenby3) h=h*3+1;
		//System.out.println("h="+h);
		while(h>0){
			for(int out=h;out<len;out++){
				int tmp=a[out];
				int in=out;
				while(in>=h && a[in-h]>=tmp){
					a[in]=a[in-h];
					in-=h;
				}
				a[in]=tmp;
			}
			h=(h-1)/3;
		}
//		for(int i=0;i<a.length;i++)
//			System.out.print(a[i]+" ");
//		System.out.println();
	}
	public int getSwapCount() {
		return 0;
	}
	public String toString() {
		return "SS";
	}
}
