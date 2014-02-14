package cellautomata;

public class Cell4Life {

	
	Cell living = CellManager.createCell(100,100);
	Cell sleeping;
	CellHash ch = new CellHash();
	
	boolean pCycle = true;
	
	public void nextGen()
	{//living.data_p = 61455;
		if(pCycle) gen_p();
		else gen_q();
		//draw screen depending on cycle
		pCycle = !pCycle;
	}
	
	public void gen_p()
	{
		int data=0; //new data is put here
		Cell next = living;
		Cell cell = living;
		while(cell != null)
		{
			next = cell.next;
			
			data=0;
			boolean ctl15 = false;
			boolean ct12=false,ct13=false,ct14=false,ct15=false;
			boolean ctr12=false;
			boolean cl3=false,cl7=false,cl11=false,cl15=false;
			boolean cr0=false,cr4=false,cr8=false,cr12=false;
			boolean cbl3=false;
			boolean cb0=false,cb1=false,cb2=false,cb3=false;
			boolean cbr0=false;
			//if cell exists then check value and set else leave it 0
			//Top Left
			if((cell.TL != null) && (cell.TL.data_p & Cell4LUT.C15)>0) ctl15=true;
			//Top
			if(cell.T != null){
				if( (cell.T.data_p & Cell4LUT.C12)>0) ct12=true;
				if( (cell.T.data_p & Cell4LUT.C13)>0) ct13=true;
				if( (cell.T.data_p & Cell4LUT.C14)>0) ct14=true;
				if( (cell.T.data_p & Cell4LUT.C15)>0) ct15=true;
			}
			//Top Right
			if((cell.TR != null) && (cell.TR.data_p & Cell4LUT.C12)>0) ctr12=true;
			//left
			if(cell.L != null){
				if( (cell.L.data_p & Cell4LUT.C03)>0) cl3=true;
				if( (cell.L.data_p & Cell4LUT.C07)>0) cl7=true;
				if( (cell.L.data_p & Cell4LUT.C11)>0) cl11=true;
				if( (cell.L.data_p & Cell4LUT.C15)>0) cl15=true;
			}
			//right
			if(cell.R != null){
				if( (cell.R.data_p & Cell4LUT.C00)>0) cr0=true;
				if( (cell.R.data_p & Cell4LUT.C04)>0) cr4=true;
				if( (cell.R.data_p & Cell4LUT.C08)>0) cr8=true;
				if( (cell.R.data_p & Cell4LUT.C12)>0) cr12=true;
			}
			//bottom Left
			if((cell.BL != null) && (cell.BL.data_p & Cell4LUT.C03)>0) cbl3=true;
			//bottom
			if(cell.B != null){
				if( (cell.B.data_p & Cell4LUT.C00)>0) cb0=true;
				if( (cell.B.data_p & Cell4LUT.C01)>0) cb1=true;
				if( (cell.B.data_p & Cell4LUT.C02)>0) cb2=true;
				if( (cell.B.data_p & Cell4LUT.C03)>0) cb3=true;
			}
			//bottom Right
			if((cell.BR != null) && (cell.BR.data_p & Cell4LUT.C00)>0) cbr0=true;
			
			//create data sets and find result using look up!!! ofcourse
//			cell 1 = first 2x2 block in cell
			data=0;
			if(ctl15) data|=Cell4LUT.C00;
			if(ct12)  data|=Cell4LUT.C01;
			if(ct13)  data|=Cell4LUT.C02;
			if(ct14)  data|=Cell4LUT.C03;
			if(cl3)	  data|=Cell4LUT.C04;
			if((cell.data_p & Cell4LUT.C00) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_p & Cell4LUT.C01) > 0)	  data|=Cell4LUT.C06;
			if((cell.data_p & Cell4LUT.C02) > 0)	  data|=Cell4LUT.C07;
			if(cl7)	  data|=Cell4LUT.C08;
			if((cell.data_p & Cell4LUT.C04) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_p & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C10;
			if((cell.data_p & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C11;
			if(cl11)	  data|=Cell4LUT.C12;
			if((cell.data_p & Cell4LUT.C08) > 0)	  data|=Cell4LUT.C13;
			if((cell.data_p & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C14;
			if((cell.data_p & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C15;
			int cell1 = Cell4LUT.lut[data];

//			cell 2 = 2x2 block in cell
			data=0;
			if(ct13) data|=Cell4LUT.C00;
			if(ct14)  data|=Cell4LUT.C01;
			if(ct15)  data|=Cell4LUT.C02;
			if(ctr12)  data|=Cell4LUT.C03;
			if((cell.data_p & Cell4LUT.C01) > 0)	  data|=Cell4LUT.C04;
			if((cell.data_p & Cell4LUT.C02) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_p & Cell4LUT.C03) > 0)	  data|=Cell4LUT.C06;
			if(cr0)	  data|=Cell4LUT.C07;
			if((cell.data_p & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C08;
			if((cell.data_p & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_p & Cell4LUT.C07) > 0)	  data|=Cell4LUT.C10;
			if(cr4)	  data|=Cell4LUT.C11;
			if((cell.data_p & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C12;
			if((cell.data_p & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C13;
			if((cell.data_p & Cell4LUT.C11) > 0)	  data|=Cell4LUT.C14;
			if(cr8)	  data|=Cell4LUT.C15;
			int cell2 = Cell4LUT.lut[data];
			
//			cell 3 = 2x2 block in cell
			data=0;
			if(cl7) data|=Cell4LUT.C00;
			if((cell.data_p & Cell4LUT.C04) > 0)	  data|=Cell4LUT.C01;
			if((cell.data_p & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C02;
			if((cell.data_p & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C03;
			if(cl11)  data|=Cell4LUT.C04;
			if((cell.data_p & Cell4LUT.C08) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_p & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C06;
			if((cell.data_p & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C07;
			if(cl15)  data|=Cell4LUT.C08;
			if((cell.data_p & Cell4LUT.C12) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_p & Cell4LUT.C13) > 0)	  data|=Cell4LUT.C10;
			if((cell.data_p & Cell4LUT.C14) > 0)	  data|=Cell4LUT.C11;
			if(cbl3)  data|=Cell4LUT.C12;
			if(cb0)	  data|=Cell4LUT.C13;
			if(cb1)	  data|=Cell4LUT.C14;
			if(cb2)	  data|=Cell4LUT.C15;
			int cell3 = Cell4LUT.lut[data];
			
//			cell 4 = 2x2 block in cell
			data=0;
			if((cell.data_p & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C00;
			if((cell.data_p & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C01;
			if((cell.data_p & Cell4LUT.C07) > 0)	  data|=Cell4LUT.C02;
			if(cr4) data|=Cell4LUT.C03;
			if((cell.data_p & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C04;
			if((cell.data_p & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_p & Cell4LUT.C11) > 0)	  data|=Cell4LUT.C06;
			if(cr8)  data|=Cell4LUT.C07;
			if((cell.data_p & Cell4LUT.C13) > 0)	  data|=Cell4LUT.C08;
			if((cell.data_p & Cell4LUT.C14) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_p & Cell4LUT.C15) > 0)	  data|=Cell4LUT.C10;
			if(cr12) data|=Cell4LUT.C11;
			if(cb1)  data|=Cell4LUT.C12;
			if(cb2)	 data|=Cell4LUT.C13;
			if(cb3)	 data|=Cell4LUT.C14;
			if(cbr0) data|=Cell4LUT.C15;
			int cell4 = Cell4LUT.lut[data];
			
			cell.data_q = (cell1>>5) | (cell2>>3) | (cell3<<3) | (cell4<<5);
			System.out.println("q->"+cell.data_q);
			
			//next cell to compute
			cell = next;
		}
	}
	
	public void gen_q()
	{

		int data=0; //new data is put here
		Cell next = living;
		Cell cell = living;
		while(cell != null)
		{
			next = cell.next;
			
			data=0;
			boolean ctl15 = false;
			boolean ct12=false,ct13=false,ct14=false,ct15=false;
			boolean ctr12=false;
			boolean cl3=false,cl7=false,cl11=false,cl15=false;
			boolean cr0=false,cr4=false,cr8=false,cr12=false;
			boolean cbl3=false;
			boolean cb0=false,cb1=false,cb2=false,cb3=false;
			boolean cbr0=false;
			//if cell exists then check value and set else leave it 0
			//Top Left
			if((cell.TL != null) && (cell.TL.data_q & Cell4LUT.C15)>0) ctl15=true;
			//Top
			if(cell.T != null){
				if( (cell.T.data_q & Cell4LUT.C12)>0) ct12=true;
				if( (cell.T.data_q & Cell4LUT.C13)>0) ct13=true;
				if( (cell.T.data_q & Cell4LUT.C14)>0) ct14=true;
				if( (cell.T.data_q & Cell4LUT.C15)>0) ct15=true;
			}
			//Top Right
			if((cell.TR != null) && (cell.TR.data_q & Cell4LUT.C12)>0) ctr12=true;
			//left
			if(cell.L != null){
				if( (cell.L.data_q & Cell4LUT.C03)>0) cl3=true;
				if( (cell.L.data_q & Cell4LUT.C07)>0) cl7=true;
				if( (cell.L.data_q & Cell4LUT.C11)>0) cl11=true;
				if( (cell.L.data_q & Cell4LUT.C15)>0) cl15=true;
			}
			//right
			if(cell.R != null){
				if( (cell.R.data_q & Cell4LUT.C00)>0) cr0=true;
				if( (cell.R.data_q & Cell4LUT.C04)>0) cr4=true;
				if( (cell.R.data_q & Cell4LUT.C08)>0) cr8=true;
				if( (cell.R.data_q & Cell4LUT.C12)>0) cr12=true;
			}
			//bottom Left
			if((cell.BL != null) && (cell.BL.data_q & Cell4LUT.C03)>0) cbl3=true;
			//bottom
			if(cell.B != null){
				if( (cell.B.data_q & Cell4LUT.C00)>0) cb0=true;
				if( (cell.B.data_q & Cell4LUT.C01)>0) cb1=true;
				if( (cell.B.data_q & Cell4LUT.C02)>0) cb2=true;
				if( (cell.B.data_q & Cell4LUT.C03)>0) cb3=true;
			}
			//bottom Right
			if((cell.BR != null) && (cell.BR.data_q & Cell4LUT.C00)>0) cbr0=true;
			
			//create data sets and find result using look up!!! ofcourse
//			cell 1 = first 2x2 block in cell
			data=0;
			if(ctl15) data|=Cell4LUT.C00;
			if(ct12)  data|=Cell4LUT.C01;
			if(ct13)  data|=Cell4LUT.C02;
			if(ct14)  data|=Cell4LUT.C03;
			if(cl3)	  data|=Cell4LUT.C04;
			if((cell.data_q & Cell4LUT.C00) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_q & Cell4LUT.C01) > 0)	  data|=Cell4LUT.C06;
			if((cell.data_q & Cell4LUT.C02) > 0)	  data|=Cell4LUT.C07;
			if(cl7)	  data|=Cell4LUT.C08;
			if((cell.data_q & Cell4LUT.C04) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_q & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C10;
			if((cell.data_q & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C11;
			if(cl11)	  data|=Cell4LUT.C12;
			if((cell.data_q & Cell4LUT.C08) > 0)	  data|=Cell4LUT.C13;
			if((cell.data_q & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C14;
			if((cell.data_q & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C15;
			int cell1 = Cell4LUT.lut[data];

//			cell 2 = 2x2 block in cell
			data=0;
			if(ct13) data|=Cell4LUT.C00;
			if(ct14)  data|=Cell4LUT.C01;
			if(ct15)  data|=Cell4LUT.C02;
			if(ctr12)  data|=Cell4LUT.C03;
			if((cell.data_q & Cell4LUT.C01) > 0)	  data|=Cell4LUT.C04;
			if((cell.data_q & Cell4LUT.C02) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_q & Cell4LUT.C03) > 0)	  data|=Cell4LUT.C06;
			if(cr0)	  data|=Cell4LUT.C07;
			if((cell.data_q & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C08;
			if((cell.data_q & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_q & Cell4LUT.C07) > 0)	  data|=Cell4LUT.C10;
			if(cr4)	  data|=Cell4LUT.C11;
			if((cell.data_q & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C12;
			if((cell.data_q & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C13;
			if((cell.data_q & Cell4LUT.C11) > 0)	  data|=Cell4LUT.C14;
			if(cr8)	  data|=Cell4LUT.C15;
			int cell2 = Cell4LUT.lut[data];
			
//			cell 3 = 2x2 block in cell
			data=0;
			if(cl7) data|=Cell4LUT.C00;
			if((cell.data_q & Cell4LUT.C04) > 0)	  data|=Cell4LUT.C01;
			if((cell.data_q & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C02;
			if((cell.data_q & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C03;
			if(cl11)  data|=Cell4LUT.C04;
			if((cell.data_q & Cell4LUT.C08) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_q & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C06;
			if((cell.data_q & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C07;
			if(cl15)  data|=Cell4LUT.C08;
			if((cell.data_q & Cell4LUT.C12) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_q & Cell4LUT.C13) > 0)	  data|=Cell4LUT.C10;
			if((cell.data_q & Cell4LUT.C14) > 0)	  data|=Cell4LUT.C11;
			if(cbl3)  data|=Cell4LUT.C12;
			if(cb0)	  data|=Cell4LUT.C13;
			if(cb1)	  data|=Cell4LUT.C14;
			if(cb2)	  data|=Cell4LUT.C15;
			int cell3 = Cell4LUT.lut[data];
			
//			cell 4 = 2x2 block in cell
			data=0;
			if((cell.data_q & Cell4LUT.C05) > 0)	  data|=Cell4LUT.C00;
			if((cell.data_q & Cell4LUT.C06) > 0)	  data|=Cell4LUT.C01;
			if((cell.data_q & Cell4LUT.C07) > 0)	  data|=Cell4LUT.C02;
			if(cr4) data|=Cell4LUT.C03;
			if((cell.data_q & Cell4LUT.C09) > 0)	  data|=Cell4LUT.C04;
			if((cell.data_q & Cell4LUT.C10) > 0)	  data|=Cell4LUT.C05;
			if((cell.data_q & Cell4LUT.C11) > 0)	  data|=Cell4LUT.C06;
			if(cr8)  data|=Cell4LUT.C07;
			if((cell.data_q & Cell4LUT.C13) > 0)	  data|=Cell4LUT.C08;
			if((cell.data_q & Cell4LUT.C14) > 0)	  data|=Cell4LUT.C09;
			if((cell.data_q & Cell4LUT.C15) > 0)	  data|=Cell4LUT.C10;
			if(cr12) data|=Cell4LUT.C11;
			if(cb1)  data|=Cell4LUT.C12;
			if(cb2)	 data|=Cell4LUT.C13;
			if(cb3)	 data|=Cell4LUT.C14;
			if(cbr0) data|=Cell4LUT.C15;
			int cell4 = Cell4LUT.lut[data];
			
			cell.data_p = (cell1>>5) | (cell2>>3) | (cell3<<3) | (cell4<<5);
			
			System.out.println("p->"+cell.data_q);
			
			//next cell to compute
			cell = next;
		}
	}
	
	public void checkCellState(Cell cell,boolean pCycle)
	{
		if(cell.data_p == 0 && cell.data_q == 0)
		{//no life so put to sleep
			deleteCell4(cell);
			return;
		}
		if(cell.data_p == cell.data_q)
		{//stable so sleep
			moveCell2sleep(cell);
			return;
		}
	}
	
	//move cell from living to sleeping list
	public void moveCell2sleep(Cell cell)
	{
		cell.isLive = false;
		if(cell.prev != null) cell.prev.next = cell.next;
		if(cell.next != null) cell.next.prev = cell.prev;
		cell.prev = null;
		cell.next = sleeping;
		sleeping = cell;
	}
	
	public void moveCell2live(Cell cell)
	{
		cell.isLive = true;
		if(cell.prev != null) cell.prev.next = cell.next;
		if(cell.next != null) cell.next.prev = cell.prev;
		cell.prev = null;
		cell.next = living;
		living = cell;
	}
	
	public Cell createCell4(int x,int y)
	{
		Cell cell = ch.retrieveCell(x,y);
		if(cell != null) return cell;
		cell = CellManager.createCell(x,y);
		//find relatives and assign them
		cell.data_p = cell.data_q = 0;
		cell.TL 	= ch.retrieveCell(x-1,y-1);
		if(cell.TL != null) cell.TL.BR 	= cell;
		cell.T  	= ch.retrieveCell(x,y-1);
		if(cell.T != null) cell.T.B 	= cell;
		cell.TR  	= ch.retrieveCell(x+1,y-1);
		if(cell.TR != null) cell.TR.BL 	= cell;
		cell.L  	= ch.retrieveCell(x-1,y);
		if(cell.L != null) cell.L.R 	= cell;
		cell.R  	= ch.retrieveCell(x+1,y);
		if(cell.R != null) cell.R.L 	= cell;
		cell.BL  	= ch.retrieveCell(x-1,y+1);
		if(cell.BL != null) cell.BL.TR 	= cell;
		cell.B  	= ch.retrieveCell(x,y+1);
		if(cell.B != null) cell.B.T 	= cell;
		cell.BR  	= ch.retrieveCell(x+1,y+1);
		if(cell.BR != null) cell.BR.TL 	= cell;
		//register
		ch.storeCell(cell);
		//add to living list
		cell.isLive = true; //set the live flag
		cell.prev = null;
		cell.next = living;
		living = cell;
		return cell;
	}
	
	public void deleteCell4(Cell cell)
	{
		//inform relatives
		if(cell.TL != null) cell.TL.BR 	= null;
		if(cell.T != null) cell.T.B 	= null;
		if(cell.TR != null) cell.TR.BL 	= null;
		if(cell.L != null) cell.L.R 	= null;
		if(cell.R != null) cell.R.L 	= null;
		if(cell.BL != null) cell.BL.TR 	= null;
		if(cell.B != null) cell.B.T 	= null;
		if(cell.BR != null) cell.BR.TL 	= null;
		//remove from living list
		if(living == cell) living = cell.next;
		if(cell.prev != null) cell.prev.next = cell.next;
		if(cell.next != null) cell.next.prev = cell.prev;
		//un-register
		ch.deleteCell(cell.x,cell.y);
		//recycle
		CellManager.deleteCell(cell);
	}
	
	public static void main(String[] args) {
		Cell4Life c4l = new Cell4Life();
		
		c4l.nextGen();
	}

}
