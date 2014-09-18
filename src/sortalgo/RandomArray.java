package sortalgo;

import java.io.FileInputStream;
import java.util.Random;

public class RandomArray {
	
	public static int[] getArray(int size, int range){
		int a[] = new int[size];
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<size;i++){
			a[i] = rand.nextInt(range);
		}
		return a;
	}
	
	public static int[] getSortedArray(int size){
		int a[] = new int[size];
		for(int i=0;i<size;i++){
			a[i] = i;
		}
		return a;
	}
	
	public static int[] copy (int a[]){
		int b[] = new int[a.length];
		for(int i=0;i<a.length;i++) b[i]=a[i];
		return b;
	}
	
	public static byte[] copy (byte a[]){
		byte b[] = new byte[a.length];
		for(int i=0;i<a.length;i++) b[i]=a[i];
		return b;
	}
	
	public static byte[] genText(){
		//String fname = "E:\\Abhilash\\testarea\\book1";
		String fname = "E:\\Abhilash\\testarea\\enwik8";
		try{
			FileInputStream fis = new FileInputStream(fname);
			int size = fis.available();
			byte buffer[] = new byte[size];
			fis.read(buffer);
			fis.close();
			return buffer;
		}catch(Exception e){
			System.out.println("genText error"+e.getMessage());
		}
		return null;
	}
	
	public static byte[][] genStrings(int size,int len){
		byte list[][] = new byte[size][len];
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<list.length;i++)
			for(int j=0;j<list.length;j++){
				list[i][j] = (byte)('A' + rand.nextInt(26));
			}
		return list;
	}
	
	public static byte[][] genSimilarStrings(int size,int len){
		byte list[][] = new byte[size][len];
		Random rand = new Random(System.currentTimeMillis());
		
		for(int j=0;j<list.length;j++){
			list[0][j] = (byte)('A' + rand.nextInt(26));
		}
		
		for(int i=1;i<list.length;i++){
			for(int j=0;j<list.length;j++){
				list[i][j] = list[0][j];
				if(rand.nextInt(100)<10){
					list[i][j] = (byte)('A' + rand.nextInt(26));
				}
			}
		}
		return list;
	}

}
