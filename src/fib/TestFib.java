package fib;

public class TestFib {

	public static void main(String[] args) {
		Fib f1 = new Fib1();
		runFib(f1, 45);
		Fib f2 = new Fib2();
		runFib(f2, 45);
		Fib f3 = new Fib3();
		runFib(f3, 45);
		runFib(f3, 35);
		runFib(f3, 46);
	}
	
	public static void runFib(Fib f,int x){
		long st = System.currentTimeMillis();
		int y = f.fib(x);
		long et = System.currentTimeMillis() - st;
		System.out.println(f.name()+"("+x+")="+y);
		System.out.println("Time : "+et);
	}
	
	//fib interface
	public static interface Fib{
		public int fib(int x);
		public String name();
	}
	
	//the usual fib
	public static class Fib1 implements Fib {
		public int fib(int x){
			if(x<=1) return x;
			return (fib(x-1) + fib(x-2));
		}
		public String name() {
			return "Fib1";
		}
	}
	
	//fib memorized first 8 values
	public static class Fib2 implements Fib {
		int y[];
		public Fib2() {
			y = new int[8];
			for(int i=0;i<y.length;i++) y[i]=-1;
		}
		public int fib(int x){
			if(x<=1) return x;
			if(x<y.length){
				if(y[x]!=-1) return y[x];
				else{
					int z=fib(x-1) + fib(x-2);
					y[x]=z;
					return z;
				}
			}
			return (fib(x-1) + fib(x-2));
		}
		public String name() {
			return "Fib2";
		}
	}
	
	//fib with a cache of size 4(tag:2bits)
	public static class Fib3 implements Fib {
		int ctag[],cval[];
		public Fib3() {
			ctag = new int[4];
			cval = new int[4];
			for(int i=0;i<ctag.length;i++){
				ctag[i] = -1; cval[i] = -1;
			}
		}
		public int fib(int x){
			if(x<=1) return x;
			int tag = (x&3);
			if(ctag[tag]==x) return cval[tag];
			else{
				int z = fib(x-1) + fib(x-2);
				ctag[tag]=x; cval[tag]=z;
				return z;
			}
			//return (fib(x-1) + fib(x-2));
		}
		public String name() {
			return "Fib3";
		}
	}
}
