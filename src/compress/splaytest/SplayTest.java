package compress.splaytest;
import java.util.Random;

public class SplayTest {

	/**
	 * @param args
	 */
	
	public static Random rand;
	
	public static void main(String[] args) {
		main_init(args);
		main_main(args);
		main_finit(args);
	}
	
	public static void main_init(String[] args){
		rand = new Random(System.currentTimeMillis());
	}
	
	public static void main_finit(String[] args){
		rand=null;
		System.out.println("*done*");
	}
	
	public static void main_main(String[] args){
		//ST st = new ST(16);
		//st.enc(0);st.enc(2);st.enc(4);st.enc(14);st.enc(15);
		int x[]={1,1,1,1,2,2,2,2,3,7,5,4,1,4,5,8,7,5,4,3,2,4,5,7,7,7,6,5,4,3,3,3,4,5,5,2,1,1,2,3,1,2,1};
		System.out.println("enc");
		bi=0;
		ST ste = new ST(16);
		for(int i=0;i<x.length;i++){
			ste.enc(x[i]);
		}
		System.out.println("dec "+bi);
		bi=0;
		ST std=new ST(16);
		for(int i=0;i<x.length;i++){
			std.dec();
		}
	}
	
	public static int buf[]=new int[1000];
	public static int bi=0;
	
	public static void outbit(int b){
		buf[bi++]=b;
	}
	public static int inbit(){
		return buf[bi++];
	}
	
	public static class ST{
		public int s;
		public int ts;
		public int R;
		public int f[];
		public int u[];
		public int l[];
		public int r[];
		
		public ST(int _s){
			s = _s;
			ts = 2*s;
			R=1;
			System.out.println("s="+s+" ts="+ts);
			f=new int[ts];
			u=new int[ts];
			l=new int[s];
			r=new int[s];
			for(int i=0;i<ts;i++){
				f[i]=1; u[i]=i/2;
				//System.out.println("u["+i+"]="+u[i]);
			}
			for(int i=0;i<s;i++){
				l[i]=2*i; r[i]=2*i+1;
				//System.out.println("l["+i+"]="+l[i]+"  r["+i+"]="+r[i]);
			}
		}
		public void uptf(int p){
			while(p!=R){
				f[p]=f[l[p]]+f[r[p]];
				p=u[p];
			}
			f[p]=f[l[p]]+f[r[p]];
			if(f[R]>10000){
				for(int i=0;i<ts;i++) f[i]=(f[i]>>1)+1;
			}
		}
		public void upt(int code){
			int a,ua,uua,b;
			a=code+s;
			f[a]++;
			uptf(u[a]);
			ua=u[a];
			while(ua!=R){
				uua=u[ua];
				if(l[uua]==ua) b=r[uua];
				else b=l[uua];
				if(f[a]>f[b]){
					if(l[uua]==b) l[uua]=a;
					else          r[uua]=a;
					if(l[ua]==a)  l[ua]=b;
					else 		  r[ua]=b;
					u[b]=ua; u[a]=uua;
					uptf(u[b]); a=b;
				}
				a=u[a]; ua=u[a];
			}
		}
		public void enc(int c){
			int st[],sti;
			sti=0; st = new int[ts+1];
			int a = c+s;
			do{
				if(r[u[a]]==a) st[sti++]=1;
				else st[sti++]=0;
				a=u[a];
			}while(a!=R);
			System.out.print(c+"=");
			do{
				System.out.print(st[--sti]);
				outbit(st[sti]);
			}while(sti>0);
			System.out.println();
			upt(c);
		}
		public int dec(){
			int a=R;
			while(a<s){
				if(inbit()==1) a=r[a];
				else a=l[a];
			}
			upt(a-s);
			System.out.print((a-s)+" ");
			return a;
		}
	}
	

	/*static class EQTest implements Runnable
	{
		int id;
		public EQTest(int _id) {
			id = _id;
		}
		public void run() {
			for(int i=0;i<10;i++)
			{
				System.out.println("Thread : "+id);
				try{Thread.sleep(500);}catch(Exception e ){}
			}
			System.out.println("Thread : "+id+" Done.");
		}
	}*/
}
