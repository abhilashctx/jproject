package sortalgo;

public class ArrayDump {

	public static void dump(int a[]){
		System.out.print("[");
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]);
			if(i+1<a.length) System.out.print(",");
		}
		System.out.println("]");
	}
	
	public static void dump(String label, int a[]){
		System.out.print(label+":");
		dump(a);
	}
}
