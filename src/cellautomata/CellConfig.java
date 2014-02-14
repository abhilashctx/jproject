package cellautomata;

public class CellConfig {

	CellHash ch = new CellHash();
	Cell living = null;
	CellHash nch = new CellHash();
	Cell newcell = null;
	
	public void clearConfig()
	{
		ch.clearCellHash();
		nch.clearCellHash();
		
		for(Cell cell=living; cell!=null; cell = cell.next)
		{
			CellManager.deleteCell(cell);
		}
		living = null;
		for(Cell cell=newcell; cell!=null; cell = cell.next)
		{
			CellManager.deleteCell(cell);
		}
		newcell = null;
	}
	
	public void createCell(int x,int y)
	{
		Cell cell = ch.retrieveCell(x,y);
		if(cell != null) return;
		cell = CellManager.createCell(x,y);
		ch.storeCell(cell);
		if(living == null) living = cell;
		else {
			cell.prev = null;
			cell.next = living;
			living.prev = cell;
			living = cell;
		}
	}
	
	public void killCell(Cell cell)
	{
		if(cell.prev == null)
		{
			living = cell.next;
		}
		else{
			cell.prev.next = cell.next;
			if(cell.next != null)
				cell.next.prev = cell.prev;
		}
		CellManager.deleteCell(cell);
		ch.deleteCell(cell.x,cell.y);
	}
	
	//highlife rules
	public boolean stayAlive_highlife(Cell cell)
	{
		int liveNeighbour = getAliveNeighbourForAliveCell(cell);
		if(liveNeighbour == 3 || liveNeighbour == 2) return true;
		return false;
	}
	
	public boolean becomeAlive_highlife(Cell cell)
	{
		int liveNeighbour = getAliveNeighbourForNewCell(cell);
		if(liveNeighbour == 3 || liveNeighbour == 6) return true;
		return false;
	}
	//highlife rules
	
	//conway rules
	public boolean stayAlive(Cell cell)
	{
		int liveNeighbour = getAliveNeighbourForAliveCell(cell);
		if(liveNeighbour == 3 || liveNeighbour == 2) return true;
		return false;
	}
	
	public boolean becomeAlive(Cell cell)
	{
		int liveNeighbour = getAliveNeighbourForNewCell(cell);
		if(liveNeighbour == 3 ) return true;
		return false;
	}
	//conway rules
	
	public int getAliveNeighbourForAliveCell(Cell cell)
	{
		int living=0;
		
		if(ch.retrieveCell(cell.x-1,cell.y-1) != null) living++;
		else 	addNewCell(cell.x-1,cell.y-1);
		if(ch.retrieveCell(cell.x,cell.y-1) != null) living++;
		else 	addNewCell(cell.x,cell.y-1);
		if(ch.retrieveCell(cell.x+1,cell.y-1) != null) living++;
		else 	addNewCell(cell.x+1,cell.y-1);

		if(ch.retrieveCell(cell.x-1,cell.y) != null) living++;
		else 	addNewCell(cell.x-1,cell.y);
		if(ch.retrieveCell(cell.x+1,cell.y) != null) living++;
		else 	addNewCell(cell.x+1,cell.y);

		if(ch.retrieveCell(cell.x-1,cell.y+1) != null) living++;
		else 	addNewCell(cell.x-1,cell.y+1);
		if(ch.retrieveCell(cell.x,cell.y+1) != null) living++;
		else 	addNewCell(cell.x,cell.y+1);
		if(ch.retrieveCell(cell.x+1,cell.y+1) != null) living++;
		else 	addNewCell(cell.x+1,cell.y+1);

		return living;
	}
	
	public int getAliveNeighbourForNewCell(Cell cell)
	{
		int living=0;
		
		if(ch.retrieveCell(cell.x-1,cell.y-1) != null) living++;
		if(ch.retrieveCell(cell.x,cell.y-1) != null) living++;
		if(ch.retrieveCell(cell.x+1,cell.y-1) != null) living++;

		if(ch.retrieveCell(cell.x-1,cell.y) != null) living++;
		if(ch.retrieveCell(cell.x+1,cell.y) != null) living++;

		if(ch.retrieveCell(cell.x-1,cell.y+1) != null) living++;
		if(ch.retrieveCell(cell.x,cell.y+1) != null) living++;
		if(ch.retrieveCell(cell.x+1,cell.y+1) != null) living++;

		return living;
	}
	
	//new cell that may live in the next gen
	//this is not added to cellhash yet
	public void addNewCell(int x,int y)
	{
		//if this is already added then dont add again
		if(nch.retrieveCell(x,y) != null) return;

		Cell cell = CellManager.createCell(x,y);
		nch.storeCell(cell);
		cell.prev = null;
		cell.next = newcell;
		if(newcell != null)
			newcell.prev = cell;
		newcell = cell;
	}
	
}
