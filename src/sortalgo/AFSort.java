package sortalgo;

public class AFSort{
	
	public void sort(int[] a) {
		dsort(a, 0, a.length-1);
	}
	
	public void dsort(int a[],int left,int right){
		if((right-left)<2) return;
		int p = a[left];
		int q = a[right];
		int l=left+1;
		int g=right-1;
		int k=l;
		while(k<=g){
			if(a[k]<p){
				swap(a,k,l);
				l++;
			}else{
				if(a[k]>q){
					while(a[g]>q && k<g) g--;
					swap(a,k,g);
					g--;
					if(a[k]<p){
						swap(a, k, l);
						l++;
					}
				}
			}
			k++;
		}
		l--;g++;
		swap(a,left,l);
		swap(a,right,g);
		dsort(a, left, l-1);
		dsort(a, l+1, g-1);
		dsort(a, g+1, right);
	}
	
	private void swap(int a[],int x,int y){
		int t=a[x]; a[x]=a[y]; a[y]=t;
	}
	
	public static void main(String[] args) {
		int a[] = RandomArray.getArray(1000000, 1000000);
		//ArrayDump.dump("rand ",a);
		AFSort s = new AFSort();
		long st=System.currentTimeMillis();
		//s.sort(a);
		s.qsort(a, 0, a.length-1);
		long dt = System.currentTimeMillis()-st;
		//ArrayDump.dump("res ",a);
		System.out.println("time "+dt);
	}
	
	public void qsort(int a[],int x,int y){
		if((y-x)<1) return;
		if((y-x)<32){
			for(int i=x+1;i<y;i++){
				int j=i;
				int t=a[j];
				while(j>=1 && a[j-1]>t){
					a[j]=a[j-1];j--;
				}
				a[j]=t;
			}
			return;
		}
		int i=x;
		int j=y;
		int p=(a[x]+a[y])/2;
		while(i<=j){
			while(a[i]<p && i<y) i++;
			while(a[j]>p && j>x) j--;
			if(i<=j){
				int t=a[i];a[i]=a[j];a[j]=t;
				i++;j--;
			}
		}
		if(x<j) qsort(a, x, j);
		if(i<y) qsort(a, i, y);
	}

}
