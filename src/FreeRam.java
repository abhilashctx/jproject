
public class FreeRam {

	public static void main(String[] args) {
		//fail
		int max=425;
		int siz=1048576;
		int a[][] = new int[max][];
		for(int i=0;i<max;i++){
			a[i] = new int[siz];
		}
		try{Thread.sleep(2000);}catch(Exception e){}
		for(int i=0;i<max;i++){
			a[i] = null;
		}
		a=null;
		try{Thread.sleep(2000);}catch(Exception e){}
	}
}
