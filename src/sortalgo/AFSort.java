package sortalgo;

public class AFSort{
	
	public void sort(int[] a) {
		int c[] = new int[10];
		
		for(int i=0;i<c.length;i++) c[i]=0;
		
		for(int i=0;i<a.length;i++) c[a[i]]+=1;
		ArrayDump.dump("freq ",c);
		
		int cc[] = new int[c.length+1];
		for(int i=1;i<cc.length;i++) cc[i]+=(cc[i-1]+c[i-1]);
		ArrayDump.dump("cfreq",cc);
		
		/*for(int i=0;i<cc.length-1;i++){
			for(int j=cc[i];j<cc[i+1];j++){
				
			}
		}*/
		
		/*for(int i=0;i<a.length;){
			if(i >= cc[a[i]] && i<cc[a[i]+1]) i++;
			else {
				
			}
		}*/
		
		int p=0, pv=0;
		while(p<a.length){
			pv=a[p];
				while( (c[pv] > 0) && (cc[pv] != p)){
					ArrayDump.dump("tmp",a);
					int tpv = a[cc[pv]];
					a[cc[pv]]=pv;
					cc[pv]++; c[pv]--;
					pv=tpv;
				}
				if(c[pv] > 0){
					a[cc[pv]]=pv;
					cc[pv]++; c[pv]--;
				}
			p++;
		}
		
	}
	
	public static void main(String[] args) {
		int a[] = RandomArray.getArray(10, 10);
		ArrayDump.dump("rand ",a);
		AFSort s = new AFSort();
		s.sort(a);
	}

}
