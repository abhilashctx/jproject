
public class Knap {
	
	//public static int v[] = {0,10,10,9,5,7,3,8,12,13,14,11};
	//public static int w[] = {0,1,  5,3,4,2,2,4, 5, 7, 5, 9};
	
	public static int v[] = {0,30,60,40,20,90,130,100,120,70,10,80,50,5};
	public static int w[] = {0,20,50,18,15,200,100,200,150,35,25,60,20,7};
	public static String n[] = {"dummy","bread","icream","mirchi","samosa","pizza","home","kfc","fruits","juices","milk","milkshake","tiffins","choc"};

	public static void main(String[] args) {
		System.out.println(optim(0,8));
		optimIter(w, v, 200);
	}
	
	public static int optim(int i,int wx){
		int value=0;
		if(i>=v.length) return 0;
		if(w[i] > wx){
			value=optim(i+1,wx);
		}else{ //<=wmax
			int take=v[i]+optim(i+1,wx-w[i]);
			int notake=optim(i+1,wx);
			value = take>notake ? take : notake;
		}
		return value;
	}
	
	public static void optimIter(int w[],int v[],int kw){
		int items = w.length;
		int weits = kw;
		int O[][] = new int[items][weits+1];
		boolean K[][] = new boolean[items][weits+1];
		for(int i=1;i<items;i++){
			for(int k=1;k<=weits;k++){
				if(w[i]>k){
					O[i][k]=O[i-1][k];
				}else{ //w[i]<=kw
					int knoo= O[i-1][k];
					int kyes= v[i] + O[i-1][k-w[i]];
					if(kyes>knoo){
						O[i][k] = kyes;
						K[i][k] = true;
					}else{
						O[i][k] = knoo;
					}
				}
			}
		}
		
		for(int i=0;i<items;i++){
			for(int k=0;k<=weits;k++){
				System.out.print(O[i][k]+" ");
			}System.out.println();
		}System.out.println();
		
		for(int i=0;i<items;i++){
			for(int k=0;k<=weits;k++){
				if(K[i][k]) System.out.print(1+" ");
				else System.out.print(0+" ");
			}System.out.println();
		}
		
		int i=items-1;
		int j=kw;
		int we=0;
		while(i>0){
			if(K[i][j]){
				System.out.println("keep "+n[i]);
				j-=w[i];
				we+=w[i];
			}
			i--;
		}
		System.out.println("knap weight : "+we);
	}
}
