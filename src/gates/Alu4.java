package gates;

public class Alu4 {
	
	public int alu1(int x,int y,int zx,int nx,int zy,int ny,int ao,int no){
		int r=0;
		if(zx==1) x=0;
		if(nx==1) x=(~x);
		if(zy==1) y=0;
		if(ny==1) y=(~y);
		if(ao==1) r=(x|y);
		else r=(x&y);
		if(no==1) r=(~r);
		r&=1;
		return r;
	}
	
	public int alu4(int x,int y,int zx,int nx,int zy,int ny,int ao,int no){
		int r=0;
		r=alu1(x>>3,y>>3,zx,nx,zy,ny,ao,no);
		r=(r<<1)|alu1(x>>2,y>>2,zx,nx,zy,ny,ao,no);
		r=(r<<1)|alu1(x>>1,y>>1,zx,nx,zy,ny,ao,no);
		r=(r<<1)|alu1(x,y,zx,nx,zy,ny,ao,no);
		return r;
	}
	
	public static void main(String[] args) {
		Alu4 alu4 = new Alu4();
		System.out.println(alu4.alu4(5, 2, 0, 0, 1, 1, 0, 0));
		//xor
		System.out.println("5 ^ 2 : "+
		alu4.alu4(alu4.alu4(5, 2, 0, 1, 0, 0, 0, 0),alu4.alu4(5, 2, 0, 0, 0, 1, 0, 0),0,0,0,0,1,0));
	}

}
