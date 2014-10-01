
public class QuickSortTest {

	public static void qs(int a[],int x, int y){
		//System.out.println(x+","+y);
		if(x>=y) return;
		int i=x,p=x;
		for(int j=i+1;j<=y;j++){
			if(a[p]>a[j]){
				i++; int t=a[i]; a[i]=a[j]; a[j]=t;
			}
		}
		int t=a[i]; a[i]=a[p]; a[p]=t;
		d(a);System.out.println("i:"+i+" x,y:"+x+","+y);
		qs(a,x,i-1);
		qs(a,i+1,y);
	}
	
	public static void qs1(int a[],int x, int y){
		
		if(x>=y) return;
		int p=(x+y)/2;
		int i=x,j=y;
		while(i<j){
			while(a[i]<=a[p] && i<y) i++;
			while(a[j]>a[p] && x<j)  j--;
			if(i<j){
				int t=a[i]; a[i]=a[j]; a[j]=t;
			}
		}
		int t=a[i]; a[i]=a[p]; a[p]=t;
		
		System.out.println("p:"+p+" i:"+i+" j:"+j+" x,y:"+x+","+y);
		d(a);
		
		qs1(a,x,i-1);
		qs1(a,i+1,y);
	}
	
	public static void main(String[] args) {
		int a[] = {6,10,11,5,12,15,3,1,8,2,14,4};
		qs(a,0,a.length-1);
		d(a);
	}
	
	public static void d(int a[]){
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]+" ");
		System.out.println();
	}
	
}
