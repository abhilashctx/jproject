package sortalgo;

import java.util.Arrays;

public class MainTest {
	
	public static void main(String[] args) {
		int x=(1<<21);
		//int x=2000000;
		//int x=(1<<17);
		//int x=65536;
		int a[] = RandomArray.getArray(x, x);
		
		//ArrayDump.dump(a);
		
		test(SortFactory.SHELL_SORT,a);
		test(SortFactory.QUICK_SORT,a);
		test(SortFactory.QUICK_INSERT_SORT,a);
		test(SortFactory.HEAP_SORT,a);
		//test(SortFactory.INSERT_SORT,a);
		//test(SortFactory.BUBBLE_SORT,a);
		
		testjavasort(a);
		
		System.out.println("sorted");
		a = RandomArray.getSortedArray(x);
		test(SortFactory.SHELL_SORT,a);
		test(SortFactory.QUICK_SORT,a);
		test(SortFactory.INSERT_SORT,a);
		test(SortFactory.QUICK_INSERT_SORT,a);
		test(SortFactory.HEAP_SORT,a);
	}
	
	public static void test(int ID, int a[]) {
		int b[] = RandomArray.copy(a);
		Sort s = SortFactory.getSorter(ID);
		long dt = TimedOpt.exec(s,b);
		//ArrayDump.dump(b);
		System.out.println(s+" time="+dt+" swp="+s.getSwapCount());
		//delay(500);
	}
	
	public static void testjavasort(int a[]) {
		int b[] = RandomArray.copy(a);
		
		long st = System.currentTimeMillis();
		Arrays.sort(b);
		long dt = System.currentTimeMillis() -st;
		
		System.out.println("javasort time="+dt);
	}
	
	public static void delay(int s){
		try{Thread.sleep(s);}catch(Exception e){}
	}//9848342678 sriLaxmi ramaneni, olx add, near janachaytanya layout, torrur road, rs.6350/sqy ,240sqy

}
