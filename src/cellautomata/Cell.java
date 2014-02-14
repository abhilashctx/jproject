package cellautomata;

public class Cell {

	//private boolean alive = true;
	
	public int x,y; //co-ordinates of this cell
	
	public Cell prev,next; //Doubly Linked List
	
	public Cell down; //for hash chain
	
	public int data_p,data_q; //data
	
	public Cell TL,T,TR,L,R,BL,B,BR; // relatives
	
	public boolean isLive = true; //indicates if live or not
	
	public Cell()
	{}
	public Cell(int _x, int _y)
	{x=_x; y=_y;}
//	public Cell(int _x, int _y,boolean _alive)
//	{x=_x; y=_y; alive=_alive;}
	
	public String toString() {
		
		return "Cell["+x+","+y+"]";
	}
}
