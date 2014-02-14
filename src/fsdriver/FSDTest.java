package fsdriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FSDTest {

	public static FSD fsd;
	
	public static void main(String[] args) {
		fsd = new FSD();
		fsd.openDisk("fsdtest.txt");
		
		fsd.format();
		fsd.loadDir();
		
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		
		fsd.closeDisk();
	}
	
	public static void test1(){
		int hfile1 = fsd.openFileWrite("hfile1");
		fsd.closeFile(hfile1);
		int hfile1find = fsd.findFile("hfile1");
		int hfile2find = fsd.findFile("hfile2");
		if(hfile1find==-1){
			System.out.println("fail: hfile1 not found");
		}
		if(hfile2find>-1){
			System.out.println("fail: hfile2 should not exist !!!");
		}
	}
	
	public static void test2(){
		String s="This is a test string that is not long enough to test the block size overflow , then a new block should not be allocated to continue with this test";
		int hfile3 = fsd.openFileWrite("hfile3");
		for(int i=0;i<s.length();i++){
			fsd.write(s.charAt(i), hfile3);
		}
		fsd.closeFile(hfile3);
		System.out.println("file size:"+fsd.getSize(hfile3));
		System.out.println("string size :"+s.length());
		if(s.length() != fsd.getSize(hfile3)){
			System.out.println("fail: file size is not same as writen text");
		}
	}
	
	public static void test3(){
		int hfile4=fsd.openFileWrite("hfile4");
		for(int i=0;i<260;i++){
			fsd.write((i%26)+'A', hfile4);
		}
		fsd.closeFile(hfile4);
		System.out.println("500 :"+fsd.getSize(hfile4));
	}
	
	public static void test4(){
		int hfile3=fsd.openFileRead("hfile3");
		int x = fsd.read(hfile3);
		System.out.print("hfile3: ");
		while(x!=-1){
			System.out.print((char)x);
			x=fsd.read(hfile3);
		}System.out.println();
		fsd.closeFile(hfile3);
	}
	
	public static void test5() {
		int hfile4=fsd.openFileRead("hfile4");
		int size = fsd.getSize(hfile4);
		int count=0;
		int x = fsd.read(hfile4);
		while(x!=-1){
			count++; x=fsd.read(hfile4);
		}
		fsd.closeFile(hfile4);
		System.out.println("size:"+size+" count:"+count);
		if(size != count){
			System.out.println("fail: size not same as count of bytes");
		}
	}
	
	public static void test6(){
		try{
			FileInputStream fis = new FileInputStream("darwin.pdf");
			int darwin = fsd.openFileWrite("darwin");
			int x=fis.read();
			while(x!=-1){
				fsd.write(x, darwin);
				x=fis.read();
			}
			fsd.closeFile(darwin);
			fis.close();
		}catch(Exception e){e.printStackTrace();}
		
		try{
			int darwin = fsd.openFileRead("darwin");
			FileOutputStream fos = new FileOutputStream("darwintest.pdf");
			int x = fsd.read(darwin);
			while(x!=-1){
				fos.write(x); x=fsd.read(darwin);
			}
			fos.close();
			fsd.closeFile(darwin);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void test7(){
		System.out.println("free space (before) : "+fsd.getFreeSpace());
		fsd.delFile("darwin");
		System.out.println("free space (after ) : "+fsd.getFreeSpace());
	}
}
