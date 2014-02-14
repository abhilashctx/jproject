package sortalgo;

public class SortFactory {
	
	public static final int INSERT_SORT = 1;
	
	public static final int BUBBLE_SORT = 2;
	
	public static final int HEAP_SORT = 3;
	
	public static final int QUICK_SORT = 4;
	
	public static final int QUICK_INSERT_SORT = 5;
	
	public static Sort getSorter(int ID){
		if(ID == INSERT_SORT){
			return new InsertionSort();
		}else if (ID == BUBBLE_SORT){
			return new BubbleSort();
		}else if (ID == HEAP_SORT){
			return new HeapSort();
		}else if (ID == QUICK_SORT){
			return new QuickSort();
		}else if (ID == QUICK_INSERT_SORT){
			return new QuickInsertSort();
		}
		return null;
	}

}
