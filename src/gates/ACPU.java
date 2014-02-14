package gates;

import java.util.ArrayList;
import java.util.Arrays;

public class ACPU {
	
	public ArrayList<Module> list;
	
	public int a[];
	
	public static final int TCU=1;
	public static final int TPC=2;
	public static final int TIR=3;
	public static final int TAC=4;
	public static final int TAU=5;
	public static final int TRM=6;
	public static final int TT1=7; //t1, t2 alu in
	public static final int TT2=8;
	
	public static void main(String[] args) {
		ACPU acpu = new ACPU();
		acpu.init();
	}
	
	public void init(){
		for(Module m : list){
			if(m.t==TRM){
				m.data[0]=1010;
				m.data[1]=2011;
				m.data[2]=1112;
				m.data[3]=0;
				m.data[10]=8;
				m.data[11]=4;
				break;
			}
		}
	}
	
	public void run(){
		boolean again=true;
		while(again){
			for(Module m:list){
				switch(m.t){
				case TCU:
					break;
				case TPC:
					if(m.bus.c==1) m.bus.a=m.data[0];
					if(m.bus.c==2) m.data[0]=m.bus.a;
					if(m.bus.c==3) m.data[0]+=1;
					break;
				case TIR:
					if(m.bus.c==4) m.bus.a=m.data[0];
					if(m.bus.c==5) m.data[0]=m.bus.d;
					break;
				case TAC:
					if(m.bus.c==6) m.bus.d=m.data[0];
					if(m.bus.c==7) m.data[0]=m.bus.d;
					break;
				case TAU:
					if(m.bus.c==8) m.data[0]=m.bus.d;
					if(m.bus.c==9) m.data[1]=m.bus.d;
					if(m.bus.c==10) m.data[0]+=m.data[1];
					if(m.bus.c==11) m.data[0]-=m.data[1];
					if(m.bus.c==12) m.data[0]*=m.data[1];
					if(m.bus.c==13) m.data[0]/=m.data[1];
					if(m.bus.c==14) m.bus.d=m.data[0];
					break;
				case TRM:
					if(m.bus.c==15) m.bus.d=m.data[m.bus.a];
					if(m.bus.c==16) m.data[m.bus.a]=m.bus.d;
					break;
				default: System.out.println("*error:"+m.t); again=false; break;
				}
			}
		}
	}
	
	public ACPU() {
		list = new ArrayList<Module>();
		a = new int[100];
		Arrays.fill(a, 0);
		
		BUS bus = new BUS();
		Module m = new Module(TCU,1,0,bus);list.add(m);
		m = new Module(TPC, 1, 0, bus);  list.add(m);
		m = new Module(TIR, 1, 0, bus);  list.add(m);
		m = new Module(TAC, 1, 0, bus);  list.add(m);
		m = new Module(TAU, 2, 0, bus);  list.add(m);
		m = new Module(TRM, 100, 0, bus);list.add(m);
		m = new Module(TT1, 1, 0, bus);  list.add(m);
		m = new Module(TT2, 1, 0, bus);  list.add(m);
	}

	public class Module{
		public int t;
		public BUS bus;
		public int data[];
		public Module(int _t,int _d,int _v,BUS _bus) {
			t=_t; bus=_bus;
			data=new int[_d];
			Arrays.fill(data, _v);
		}
	}
	
	public class BUS{
		public int c,a,d; //control,address,data bus
	}
}
