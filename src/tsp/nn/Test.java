package tsp.nn;

public class Test {

	/*public int eval(int w[],int in[]){
		int r=0;
		int r1 = in[0]*w[0]+in[1]*w[1];
		int r2 = in[0]*w[2]+in[1]*w[3];
		
		if(r1<-100) r1=-100;
		if(r1>100)  r1=100;
		if(r2<-100) r2=-100;
		if(r2>100)  r2=100;
		
		r = r1*w[4] + r2*w[5];
		if(r<-100) r=-100;
		if(r>100)  r=100;
		
		return r;
	}*/
	
	public int[] randw(){
		int w[]=new int[9];
		for(int i=0;i<w.length;i++)
			w[i] = ((int)(Math.random()*11))-5;
		return w;
	}
	
	public int[] cpy(int w[]){
		int c[]=new int[w.length];
		for(int i=0;i<w.length;i++) c[i]=w[i];
		return c;
	}
	
	public int[] randi(int w[]){
		int i = (int)(Math.random()*w.length);
		w[i] = ((int)(Math.random()*11))-5;
		return w;
	}
	
	public int evallimit(int x){
		if(x<-100) return 0;//-100;
		if(x>100)  return 100;
		return x;
	}
	
	public int eval(int i0,int i1,int w[]){
		int er=0;
		int r1 = evallimit(i0*w[0]+i1*w[1]);
		int r2 = evallimit(i0*w[2]+i1*w[3]);
		int r3 = evallimit(i0*w[4]+i1*w[5]);
		er = evallimit(r1*w[6] + r2*w[7] + r3*w[8]);
		return er;
	}
	
	public int rmseval(int i0[],int i1[],int r[],int w[]){
		int s=0;
		for(int i=0;i<i0.length;i++){
			int ar = eval(i0[i],i1[i],w);
			ar=(r[i]-ar);
			s+=(ar*ar);
		}
		s=(int)Math.sqrt(s);
		return s;
	}
	
	public double accept(int curr,int news,double tmp){
		if(news<curr) return 1.0;
		return Math.exp((curr-news)/tmp);
	}
	
	public static void main(String[] args) {
		
		int i0[]={0,0,100,100};
		int i1[]={0,100,0,100};
		//int r[] ={0,100,100,0};
		int r[] ={0,100,100,0};
		
		Test t = new Test();
		//System.out.println(t.rmseval(i0, i1, r, t.randw()));
		
		double tmp=1000;
		double cr=0.001;
		int w[] = t.randw();
		int b[] = t.cpy(w);
		while(tmp>1){
			int n[] = t.randi(t.cpy(w));
			int curr = t.rmseval(i0, i1, r, w);
			int news = t.rmseval(i0, i1, r, n);
			if(t.accept(curr, news, tmp) > Math.random()){
				w = n;
			}
			if(news < t.rmseval(i0, i1, r, b)){
				b = n;
				System.out.println("b:"+t.rmseval(i0, i1, r, b));
			}
			tmp*=1-cr;
		}
		System.out.print("best:");
		for(int i=0;i<b.length;i++)
			System.out.print(b[i]+",");
		System.out.println();
		System.out.println("rms : "+t.rmseval(i0, i1, r, b));
	}
}
