package cellautomata;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class CellLife {

	private boolean cell_lut[];
	private int TL = 1;	//Top Left
	private int T  = 2; //Top
	private int TR = 4; //Top Right
	private int L  = 8; //Left
	private int C  = 16; //Center
	private int R  = 32; //Right
	private int DL = 64; //Down Left
	private int D  = 128; //Down
	private int DR = 256; //Down Right
	
	private boolean pause = false; //this stops the next generation from calculation
	
//	private Cell living;	//list of living + may live cells
//							//this is used to create next generation
//	private CellHash lHash; //living configuration
//	
//	private Cell sleeping;  //these cell seem to be stable
//	
//	private Cell nextliving; //this created when nextGeneration is called
//							 //all cells in the current generation need to be
//							 //recycled
//	private CellHash nHash; //living configuration
	
	CellConfig cur_config = new CellConfig();
	CellConfig nxt_config = new CellConfig();
	
	public CellLife() {
		
		// cell LUT initialize
		//cell_lut = new boolean[512];
		//build_conwayRule();
		
		//init with a line for start pattern
		for(int i=0;i<100;i++)
			cur_config.createCell(200,100+i);
//		
//		cur_config.createCell(100,100);
//		cur_config.createCell(100,101);
//		cur_config.createCell(101,100);
//		cur_config.createCell(101,101);
		
		//new CellLifeThread(this).start();
	}
	
	private void build_conwayRule()
	{
		//creates the lut
		int liveCount=0;
		for(int i=0;i<cell_lut.length; i++){
			liveCount = getAliveCount(i);
			System.out.println(i+" = "+liveCount);
			if(liveCount == 3) cell_lut[i]=true;
			else if(isCenterAlive(i) && (liveCount != 2 && liveCount != 3)) cell_lut[i]=false;
			System.out.println("cell_lut = "+cell_lut[i]);
		}
	}
	
	private boolean isCenterAlive(int c3)
	{
		//In c3(cell 3x3) if center cell is alive then return true
		if((c3 & C)>0) return true;
		return false;
	}
	
	private int getAliveCount(int c3)
	{
		//c3 = cell 3x3
		//returns the number of cell alive in 3x3
		//without counting the center
		
		int liveCount = 0;
		if((c3 & TL)>0) liveCount++;
		if((c3 & T)>0) liveCount++;
		if((c3 & TR)>0) liveCount++;
		if((c3 & L)>0) liveCount++;
		if((c3 & R)>0) liveCount++;
		if((c3 & DL)>0) liveCount++;
		if((c3 & D)>0) liveCount++;
		if((c3 & DR)>0) liveCount++;
		
		return liveCount;
	}
	
	public void nextGeneration()
	{
		//calculates the next generation if not paused
		if(pause) return;
		//live cell that may stay live
		for(Cell cell = cur_config.living; cell!=null; cell=cell.next){
			//System.out.println("living cell = "+cell);
			
			if(cur_config.stayAlive(cell)){
				//System.out.println("stay alive");
				nxt_config.createCell(cell.x,cell.y);
			}
//			else{
//				//System.out.println("die");
//			}
		}
		//new cells that may become alive
		for(Cell cell = cur_config.newcell; cell!=null; cell=cell.next){
			//System.out.println("new cell = "+cell);
			
			if(cur_config.becomeAlive(cell)){
				//System.out.println("become alive");
				nxt_config.createCell(cell.x,cell.y);
			}
//			else{
//				//System.out.println("ignore");
//			}
		}
		//clear the current configuration
		cur_config.clearConfig();
		
		//swap the cur and nxt config
		CellConfig tmp = cur_config;
		cur_config = nxt_config;
		nxt_config = tmp;
	}
	
	public void nextGeneration(BufferedImage bi)
	{
		//calculates the next generation if not paused
		if(pause) return;
		//live cell that may stay live
		for(Cell cell = cur_config.living; cell!=null; cell=cell.next){
			//System.out.println("living cell = "+cell);
			
			if(cell.x>=0 && cell.x<600 && cell.y>=0 && cell.y<600)
				bi.setRGB(cell.x,cell.y,0xFFFFFFFF);
			
			if(cur_config.stayAlive(cell)){
				//System.out.println("stay alive");
				nxt_config.createCell(cell.x,cell.y);
			}
//			else{
//				//System.out.println("die");
//			}
		}
		//new cells that may become alive
		for(Cell cell = cur_config.newcell; cell!=null; cell=cell.next){
			//System.out.println("new cell = "+cell);
			
			if(cur_config.becomeAlive(cell)){
				//System.out.println("become alive");
				nxt_config.createCell(cell.x,cell.y);
			}
//			else{
//				//System.out.println("ignore");
//			}
		}
		//clear the current configuration
		cur_config.clearConfig();
		
		//swap the cur and nxt config
		CellConfig tmp = cur_config;
		cur_config = nxt_config;
		nxt_config = tmp;
	}
	
	private void getCellStatus(Cell cell){
		//Cell ctl = 
	}
	
	public void drawCells(BufferedImage g,int width, int height)
	{
		//draw onto the graphics context
		//width and height r used to draw only those in visible view
		for(int i=0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				if(cur_config.ch.retrieveCell(i,j) != null)
					g.setRGB(i,j,0xFFFFFFFF);
			}
		}
	}
	
	public void drawCellWithMouse(BufferedImage g,int x1,int y1,int x2, int y2)
	{
		//called when mouse is used to draw cells
		int dx = x2-x1;
		int dy = y2-y1;
		if(dx > 0) dx = 1; else if(dx == 0) dx = 0; else dx = -1;
		if(dy > 0) dy = 1; else if(dy == 0) dy = 0; else dy = -1;
//		for(int i=0;x1<x2 && y1<y2;i++)
//		{
			//g.setRGB(x1,y1,0xFFFFFFFF);
			cur_config.createCell(x1-1,y1-1);
			cur_config.createCell(x1,y1-1);
			cur_config.createCell(x1+1,y1-1);
			
			cur_config.createCell(x1-1,y1);
			cur_config.createCell(x1,y1);
			cur_config.createCell(x1+1,y1);
			
			cur_config.createCell(x1-1,y1+1);
			cur_config.createCell(x1,y1+1);
			cur_config.createCell(x1+1,y1+1);
			if(x1<x2) x1+=dx;
			if(y1<y2) y1+=dy;
		//}
//		Graphics gg = g.getGraphics();
//		gg.drawLine(x1,y1,x2,y2);
//		gg.dispose();
		
		
	}
	
	public void setPause(boolean _pause)
	{pause = _pause;}
	public boolean getPause()
	{return pause;}
	
	private class CellLifeThread extends Thread
	{
		CellLife parent;
		public CellLifeThread(CellLife cellLife)
		{
			parent = cellLife;
		}
		
		public void run() {
			long genCount=0;
			while(true){
				parent.nextGeneration();
				System.out.println(genCount++);
				try{Thread.sleep(1);}catch(Exception e){}
			}
		}
	}
	
	public static void main(String[] args) {
		
		CellLife cellLife = new CellLife();
		System.out.println("1========================================================");
		cellLife.nextGeneration();
		System.out.println("2========================================================");
		cellLife.nextGeneration();
		System.out.println("3========================================================");
		cellLife.nextGeneration();
	}

}
