package cellautomata;

public class CellManager {

	//this manages all the cells that are created and deleted
	//has a pool of reusable Cells
	
	private static Cell recycle = null;
	
	//public CellManager() {
	static{
		// create an initial pool of cells
		Cell cell=null;
		for(int i=0;i<100000;i++)
		{
			cell = new Cell();
			cell.down = recycle;
			recycle = cell;
		}
	}
	
	public static synchronized Cell createCell(int x,int y)
	{
		//return a new cell from pool or create new
		Cell cell = null;
		if(recycle != null){
			cell = recycle;
			recycle = cell.down;
		}
		else{
			cell = new Cell(x,y);
		}
		cell.x=x;
		cell.y=y;
		cell.down = null;
		cell.prev = null;
		cell.next = null;

		return cell;
	}
	
	public static synchronized void deleteCell(Cell cell)
	{
		//delete a cell by adding it to pool
			cell.down = recycle;
			recycle = cell;
	}
	
	public static void main(String[] args) {
		//test CellManager for speed of alloc and deletion
		
		long st = System.currentTimeMillis();
		
		for(int i=0;i<100000;i++) CellManager.createCell(i,i);
		
		System.out.println("Time difference = "+(System.currentTimeMillis()-st));
		//promising results
	}
}
