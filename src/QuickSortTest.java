
public class QuickSortTest {

	public static void qs(int a[],int x, int y){
		if(x>=y) return;
		int i=x,p=x;
		for(int j=i+1;j<=y;j++){
			if(a[p]>a[j]){
				i++; int t=a[i]; a[i]=a[j]; a[j]=t;
			}
		}
		int t=a[i]; a[i]=a[p]; a[p]=t;
		d(a);System.out.println("i:"+i+" x,y:"+x+","+y);
		qs(a,x,i-1); qs(a,i+1,y);
	}
	
	public static void main(String[] args) {
		int a[] = {6,10,11,9,5,12,15,3,1,8,2,14,4,7,13};
		tqs(a,0,a.length-1);
		d(a);
	}
	
	public static void d(int a[]){
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]+" ");
		System.out.println();
	}
	
	public static void tqs(int a[],int x,int y){
		if(x>=y) return;
		int i=x,p=i;
		for(int j=i+1;j<=y;j++){
			if(a[j]<=a[p]){
				i++; int tmp=a[i]; a[i]=a[j]; a[j]=tmp;
			}
		}
		int tmp=a[i]; a[i]=a[p]; a[p]=tmp;
		tqs(a,x,i-1); tqs(a,i+1,y);
	}
	
}
