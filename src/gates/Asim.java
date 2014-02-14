package gates;

import java.util.ArrayList;
import java.util.Arrays;

public class Asim {
	
	public boolean pins[];
	
	public ArrayList<Gate> gates;
	
	public ArrayList<Event> list;
	
	
	public static void main(String[] args) {
		Asim asim = new Asim();
		asim.test();
	}
	
	public void test(){
		set(1,false);set(2,false);
		set(3,false);set(4,true);
		
		addGate(createNot(2, 2, 5));
		addGate(createAnd(1, 5, 6));
		addGate(createAnd(1, 2, 7));
		addGate(createOr(6, 4, 8));
		addGate(createOr(7, 3, 9));
		addGate(createNot(8, 8, 3));
		addGate(createNot(9, 9, 4));
		
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
		
		set(1,true);set(2,true);
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
		
		set(1,false);set(2,false);
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
		
		set(1,true);set(2,false);
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
	}
	
	public void sim(){
		boolean again=true;
		int count=0;
		while(again){
			again=false;
			for(Gate g : gates){
				boolean r = pins[g.o1];
				switch(g.t){
					case 1: r=(pins[g.i1] & pins[g.i2]); break;
					case 2: r=(pins[g.i1] | pins[g.i2]); break;
					case 3: r=(!pins[g.i1]); break;
				}
				if(r!=pins[g.o1]){
					pins[g.o1]=r;
					again=true;
				}
			}
			count+=1;
		}
		System.out.println("sim count : "+count);
	}
	
	public Asim() {
		pins = new boolean[32];
		Arrays.fill(pins, false);
		gates = new ArrayList<Gate>();
		list = new ArrayList<Event>();
	}
	
	public void addGate(Gate g){
		gates.add(g);
	}
	
	public void set(int pi,boolean v){
		pins[pi]=v;
	}
	public boolean get(int pi){
		return pins[pi];
	}
	
	public Gate createAnd(int i1,int i2,int o1){
		Gate g = new Gate();
		g.t=1; g.i1=i1; g.i2=i2; g.o1=o1;
		return g;
	}
	public Gate createOr(int i1,int i2,int o1){
		Gate g = new Gate();
		g.t=2; g.i1=i1; g.i2=i2; g.o1=o1;
		return g;
	}
	public Gate createNot(int i1,int i2,int o1){
		Gate g = new Gate();
		g.t=3; g.i1=i1; g.i2=i2; g.o1=o1;
		return g;
	}
	
	public class Gate{
		public int t,i1,i2,o1;
	}
	
	public class Event{
		Gate g;
	}

}

/*
xor
		addGate(createNot(1, 1, 4));
		addGate(createNot(2, 2, 5));
		addGate(createAnd(4, 2, 6));
		addGate(createAnd(1, 5, 7));
		addGate(createOr(6, 7, 3)); 
s,c
		addGate(createNot(1, 1, 5));
		addGate(createNot(2, 2, 6));
		addGate(createAnd(5, 2, 7));
		addGate(createAnd(1, 6, 8));
		addGate(createOr(7, 8, 3));
		addGate(createAnd(1, 2, 4));
2bit adder
		set(1,true);set(2,true);
		set(3,true);set(4,false);
		addGate(createNot(2, 2, 9));
		addGate(createNot(4, 4, 10));
		addGate(createAnd(9, 4, 11));
		addGate(createAnd(2, 10, 12));
		addGate(createOr(11, 12, 8));
		addGate(createAnd(2, 4, 13));
		
		addGate(createNot(1, 1, 14));
		addGate(createNot(3, 3, 15));
		addGate(createAnd(14, 3, 16));
		addGate(createAnd(1, 15, 17));
		addGate(createOr(16, 17, 18));
		addGate(createAnd(1, 3, 19));
		
		addGate(createNot(13, 13, 20));
		addGate(createNot(18, 18, 21));
		addGate(createAnd(20, 18, 22));
		addGate(createAnd(13, 21, 23));
		addGate(createOr(22, 23, 7));
		addGate(createAnd(13, 18, 24));
		
		addGate(createNot(19, 19, 25));
		addGate(createNot(24, 24, 26));
		addGate(createAnd(25, 24, 27));
		addGate(createAnd(19, 26, 28));
		addGate(createOr(27, 28, 6));
		addGate(createAnd(19, 24, 5));
		
		sim();
		System.out.println("r5:"+pins[5]);
		System.out.println("r6:"+pins[6]);
		System.out.println("r7:"+pins[7]);
		System.out.println("r8:"+pins[8]);
		
dflipflop - d gated sr nor latch
		set(1,false);set(2,false);
		set(3,false);set(4,true);
		
		addGate(createNot(2, 2, 5));
		addGate(createAnd(1, 5, 6));
		addGate(createAnd(1, 2, 7));
		addGate(createOr(6, 4, 8));
		addGate(createOr(7, 3, 9));
		addGate(createNot(8, 8, 3));
		addGate(createNot(9, 9, 4));
		
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
		
		set(1,true);set(2,true);
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
		
		set(1,false);set(2,false);
		sim();
		System.out.println("r3:"+pins[3]);
		System.out.println("r4:"+pins[4]);
*/
