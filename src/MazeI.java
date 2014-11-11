import java.util.Random;


public class MazeI {
	
	public static int block = '#';
	public static void main(String[] args) {
		
		int dim=31; //odd numbers
		int dimx=dim;
		int dimy=dim;
		int d[][] = new int[dimx][dimy];
		for(int i=0;i<dimx;i++)
			for(int j=0;j<dimy;j++)
				d[i][j]=block;
		recbt(d,dimx/2+1*(1-((dimx/2)&1)),dimy/2+1*(1-((dimy/2)&1)));
		//System.out.println(rndDir()+" "+rndDir()+" "+rndDir());
		prn(d);
	}

	public static void prn(int d[][]){
		System.out.println();
		for(int i=0;i<d.length;i++){
			for(int j=0;j<d[0].length;j++){
				System.out.print((char)d[i][j]);
			}System.out.println();
		}
	}
	
	public static void recbt(int d[][],int x,int y){
		d[x][y]=' ';
		int dir = rndDir();
		int n=4;
		int nx=0,ny=0;
		while(n>0){
			int idir = dir&3;
			if(idir==0)		{nx=x;ny=y-2;if(isValid(nx, ny, d)){d[x][y-1]=' ';recbt(d,nx,ny);}}
			else if(idir==1){nx=x+2;ny=y;if(isValid(nx, ny, d)){d[x+1][y]=' ';recbt(d,nx,ny);}}
			else if(idir==2){nx=x;ny=y+2;if(isValid(nx, ny, d)){d[x][y+1]=' ';recbt(d,nx,ny);}}
			else			{nx=x-2;ny=y;if(isValid(nx, ny, d)){d[x-1][y]=' ';recbt(d,nx,ny);}}
			dir>>=2;
			n--;
		}
	}
	public static boolean isValid(int x,int y,int d[][]){
		if((x>0 && x<d.length-1) && (y>0 && y<d[0].length-1)){
			if(d[x][y]==block) return true;
		}
		return false;
	}
	
	public static Random rnd = new Random(System.currentTimeMillis());
	public static int rndDir(){
		int dir[] = {0,1,2,3};
		int shuffle = rnd.nextInt(10)+1;
		while(shuffle>0){
			int i = rnd.nextInt(dir.length);
			int j = rnd.nextInt(dir.length);
			int t = dir[i]; dir[i]=dir[j]; dir[j]=t;
			shuffle--;
		}
		shuffle=0;
		for(int i=0;i<dir.length;i++){
			shuffle=(shuffle<<2)+(dir[i]);
		}
		return shuffle;
	}
}
