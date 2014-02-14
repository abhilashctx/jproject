package cellautomata;

public class Cell4LUT {

	
	//Cell4 Index
	public static final int c4I[] = {0x0001,0x0002,0x0004,0x0008,
									 0x0010,0x0020,0x0040,0x0080,
									 0x0100,0x0200,0x0400,0x0800,
									 0x1000,0x2000,0x4000,0x8000};
	
	public static final int C00 = c4I[0];
	public static final int C01 = c4I[1];
	public static final int C02 = c4I[2];
	public static final int C03 = c4I[3];
	public static final int C04 = c4I[4];
	public static final int C05 = c4I[5];
	public static final int C06 = c4I[6];
	public static final int C07 = c4I[7];
	public static final int C08 = c4I[8];
	public static final int C09 = c4I[9];
	public static final int C10 = c4I[10];
	public static final int C11 = c4I[11];
	public static final int C12 = c4I[12];
	public static final int C13 = c4I[13];
	public static final int C14 = c4I[14];
	public static final int C15 = c4I[15];
	
	public static final int lut_size = 1<<16;
	public static final int lut[] = new int[lut_size];
	
	static{
		int count=0,value=0;
		boolean centerAlive = false;
		for(int i=0; i<lut_size; i++)
		{
			value=0;
			count=0;
			centerAlive = false;
			if((i & C00) > 0) count++;
			if((i & C01) > 0) count++;
			if((i & C02) > 0) count++;
			if((i & C04) > 0) count++;
			if((i & C06) > 0) count++;
			if((i & C08) > 0) count++;
			if((i & C09) > 0) count++;
			if((i & C10) > 0) count++;
			if((i & C05) > 0) centerAlive=true;
			if(getNewValue(centerAlive,count)) value^=C05;
			
			count=0;
			centerAlive = false;
			if((i & C01) > 0) count++;
			if((i & C02) > 0) count++;
			if((i & C03) > 0) count++;
			if((i & C05) > 0) count++;
			if((i & C07) > 0) count++;
			if((i & C09) > 0) count++;
			if((i & C10) > 0) count++;
			if((i & C11) > 0) count++;
			if((i & C06) > 0) centerAlive=true;
			if(getNewValue(centerAlive,count)) value^=C06;
			
			count=0;
			centerAlive = false;
			if((i & C04) > 0) count++;
			if((i & C05) > 0) count++;
			if((i & C06) > 0) count++;
			if((i & C08) > 0) count++;
			if((i & C10) > 0) count++;
			if((i & C12) > 0) count++;
			if((i & C13) > 0) count++;
			if((i & C14) > 0) count++;
			if((i & C09) > 0) centerAlive=true;
			if(getNewValue(centerAlive,count)) value^=C09;
			
			count=0;
			centerAlive = false;
			if((i & C05) > 0) count++;
			if((i & C06) > 0) count++;
			if((i & C07) > 0) count++;
			if((i & C09) > 0) count++;
			if((i & C11) > 0) count++;
			if((i & C13) > 0) count++;
			if((i & C14) > 0) count++;
			if((i & C15) > 0) count++;
			if((i & C10) > 0) centerAlive=true;
			if(getNewValue(centerAlive,count)) value^=C10;
			
			lut[i] = value;
		}
	}
	
	public static boolean getNewValue(boolean centerAlive,int count)
	{
		if(count == 3) return true;
		if(centerAlive && count == 2) return true;
		return false;
	}
	
	public static void print()
	{
		for(int i=61000;i<61456;i++){
			System.out.println();
			for(int j=0;j<16;j++)
				if((i & c4I[j])>0) System.out.print(j+",");
			System.out.print(" = ");
			for(int j=0;j<16;j++)
				if((lut[i] & c4I[j])>0) System.out.print(j+",");
			//System.out.println(i+"="+lut[i]);
		}
	}
	
	public static void main(String[] args) {
	
		//for(int i=0;i<1000;i++)
		//	System.out.println(i+"="+lut[i]);
		print();

	}

}
