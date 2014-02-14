package gates;

public class ALU {
	
	public int and(int x,int y) {
		return ((x&y)&1);
	}
	public int or(int x,int y) {
		return ((x|y)&1);
	}
	public int not(int x) {
		return ((~x)&1);
	}
	public int xor(int x,int y) {
		return ((x^y)&1);
	}
	
	public int mux2(int s,int i0,int i1) {
		int r = or(and(not(s),i0), and(s,i1));
		return r;
	}
	public int dmux2(int s,int i) {
		int r = (and(not(s),i)<<1)|and(s,i);
		return r;
	}
	
	public static void main(String[] args) {
		ALU alu = new ALU();
		System.out.println(alu.mux2(1,0,1));
		System.out.println(alu.dmux2(0,1));
	}

}
