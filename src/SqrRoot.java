
public class SqrRoot {
	/*
	 * Newton-Raphson
	 * x(n+1) = x(n)-f(x)/f'(x)
	 * */
	
	public static double NRsqrt(double v){
		double x = v/2;
		for(int i=0;i<16;i++){
			x=x-(((x*x)-v)/(2*x));
		}
		return x;
	}
	public static double NRcbrt(double v){
		double x = v/3;
		for(int i=0;i<32;i++){
			x=x-(((x*x*x)-v)/(3*x*x));
		}
		return x;
	}
	
	public static double BSsqrt(double v){
		double low=0;
		double high=v+1;
		//int loops=0;
		while((high-low)>0.1){//loops++;
			double mid = (high+low)/2.0;
			if(mid*mid <= v) low=mid;
			else high=mid;
		}
		//System.out.println("loops:"+loops);
		return low;
	}
	public static double BScbrt(double v){
		double low=0;
		double high=v+1;
		//int loops=0;
		while((high-low)>0.01){//loops++;
			double mid = (high+low)/2.0;
			if(mid*mid*mid <= v) low=mid;
			else high=mid;
		}
		//System.out.println("loops:"+loops);
		return low;
	}
	public static void main(String[] args) {
		System.out.println(NRsqrt(105124009));
		System.out.println(BSsqrt(105124009));
		System.out.println(NRcbrt(105124009));
		System.out.println(BScbrt(105124009));
		
		long st = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			//NRsqrt(105124009);
			NRcbrt(105124009);
		}
		long dt=System.currentTimeMillis()-st;
		System.out.println(dt+" ms");
		
		st=System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			//BSsqrt(105124009);
			BScbrt(105124009);
		}
		dt=System.currentTimeMillis()-st;
		System.out.println(dt+" ms");
	}
}
