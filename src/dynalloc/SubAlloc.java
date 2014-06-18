package dynalloc;
import java.util.Random;


public class SubAlloc {
	
	private int size;
	private char data[];
	private int start;
	
	public SubAlloc() {
		size=65536;
		data = new char[size];
		start = (size/9);
	}
	public int alloc(int xsize){
		int bsize = ((xsize-1)/8)+1;
		int loc=0,bcount=0,loci=0;
		boolean found=false;
		while(loc<start){
			while(data[loc]!=0){
				loc+=data[loc];
			}
			bcount=0;
			for(loci=loc;loci<start;loci++){
				if(data[loci]==0) bcount++; else break;
				if(bcount>=bsize) {found=true;break;}
			}
			if(found){
				data[loc]=(char)bsize;//System.out.println("loc:"+loc+" data[loc]:"+(int)data[loc]);
				return (start+loc*8);
			}else loc=loci;
		}
		return 0;
	}
	public void free(int xloc){
		if(xloc==0) return;
		int bloc = (xloc-start)/8;//System.out.println("bloc:"+bloc);
		if(data[bloc]!=0) data[bloc]=0;
		else System.out.println("*error already free!");
	}
	public void list(){
		int loc=0;
		while(loc<start){
			if(data[loc]!=0){
				System.out.println("alloc:"+(data[loc]*8)+"units at "+loc);
				loc+=data[loc];
			}else loc++;
		}
	}
	
	public static void main(String[] args) {
		SubAlloc s=new SubAlloc();
		int z=s.alloc(100);
		int x=s.alloc(57);
		int y=s.alloc(1021);
		int w=s.alloc(200);
		System.out.println(z+" "+x+" "+y+" "+w);
		System.out.println("---");s.list();
		s.free(y);
		y=s.alloc(500);
		int v=s.alloc(300);
		
		System.out.println(z+" "+x+" "+y+" "+w+" "+v);
		System.out.println("---");s.list();
		s.free(z);
		System.out.println("---");s.list();
		z=s.alloc(10000);
		System.out.println("---");s.list();
		s.free(y);
		y=s.alloc(20000);
		System.out.println("---");s.list();
		s.free(x);
		x=s.alloc(26000);
		System.out.println("---");s.list();
		s.free(z);s.free(x);s.free(y);s.free(v);
		System.out.println("---");s.list();
		
		long st = System.currentTimeMillis();
		Random r=new Random(System.currentTimeMillis());
		int a[]=new int[2000];
		for(int i=0;i<a.length;i++){
			a[i]=s.alloc(10+r.nextInt(10));//System.out.println("a["+i+"]:"+a[i]);
		}
		//System.out.println("---");s.list();
		for(int i=0;i<a.length;i++){
			s.free(a[i]);
		}
		long et=System.currentTimeMillis();
		System.out.println("---");s.list();
		System.out.println("Time:"+(et-st));
	}

}
