package cellautomata;



public class CellHash {

	private static final int HBIT = 16; 
	private static final int HASH_SIZE = 1<<HBIT;
	private Cell ct[];
	
	private int keyX;
	
	public CellHash() {
		this(HASH_SIZE);
	}
	public CellHash(int table_size) {
		ct = new Cell[table_size];
	}
	
	private void genKeys(int x, int y)
	{//generate keys for the table
		//keyX = (((x>>4)*(y>>5))+x*5+y*7) & (HASH_SIZE-1);
		keyX = (x*12345671+y*7854321+x*5+y*7) & (HASH_SIZE-1);
	}
	
	public void storeCell(Cell cell)
	{
		genKeys(cell.x,cell.y);
		cell.down = ct[keyX];
		ct[keyX] = cell;
	}
	
	public Cell retrieveCell(int x, int y)
	{
		genKeys(x,y);
		
		if(ct[keyX] == null) return null;
		
		for(Cell cell = ct[keyX]; cell != null; cell = cell.down){
			if(cell != null && (cell.x == x) && (cell.y == y))
				return cell;
		}
		return null;
	}
	
	public void deleteCell(int x,int y)
	{
		Cell prev=null;
		genKeys(x,y);
		
		for(Cell cell = ct[keyX]; cell != null; cell = cell.down){
			
			if((cell.x == x) && (cell.y == y)){
				if(prev != null){
					prev.down = cell.down;
				}
				else {
					ct[keyX] = cell.down;
				}
					
				cell.down = null;
			}
			prev = cell;
		}
	}
	
	public void clearCellHash()
	{
		//clear entire table
		for(int i=0;i<ct.length; i++)
		{
				ct[i] = null;
		}
	}
	
	public void print()
	{
		for(int i=700;i<900;i++)
		{
			System.out.print("\n"+i+"="+ct[i]);
		}
	}
	
	public static void main(String[] args) {
	
		CellHash cellHash = new CellHash();
		
		long st = System.currentTimeMillis();
		Cell cell = new Cell(120,345);
		cellHash.storeCell(cell);
		for(int i=0;i<100000;i++){
			cell = CellManager.createCell(i,i+1);//new Cell(i+2,i+5);
			cellHash.storeCell(cell);
		}
		System.out.println("Insert time = "+(System.currentTimeMillis()-st));
		for(int i=0;i<100000;i++){
			//cell = new Cell(i+2,i+5);
			cellHash.retrieveCell(i,i+1);
		}
		System.out.println("ret - >"+cellHash.retrieveCell(120,345));
		System.out.println("ret - >"+cellHash.retrieveCell(502,505));
		cellHash.deleteCell(502,505);
		System.out.println("after delete ret - >"+cellHash.retrieveCell(502,505));
		long tt = System.currentTimeMillis()-st;
		System.out.println("total time = "+tt);
		
//		cellHash.print();
//		//this has shown good results
//		HashMap ht = new HashMap();
//		st = System.currentTimeMillis();
//		for(int i=0;i<100000;i++){
//			cell = CellManager.createCell(i+2,i+5);//new Cell(i+2,i+5);
//			ht.put(cell,"");
//		}
//		//for(int i=0;i<100000;i++){
//			//cell = new Cell(i+2,i+5);
//			//ht.get(new Integer(i));
//		//}
//		tt = System.currentTimeMillis()-st;
//		System.out.println("HT total time = "+tt);
//		
//		//result CellHash took 115 msec
//		//if i increase the CellHash size i got upto 160msec
//		//Hashtable took 240 msec
//		
//		Vector v1 = new Vector();
//		Enumeration enumeration = v1.elements();
//		st = System.currentTimeMillis();
//		for(int i=0;i<100000;i++)
//		{
//			v1.add(CellManager.createCell(i+2,i+5));
//		}
//		System.out.println("Vector Final Time = "+(System.currentTimeMillis()-st));
//		
		
	}

}
