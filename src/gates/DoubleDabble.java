package gates;

public class DoubleDabble {
	
	/**
	 * Only for 8-bit.
	 * conver binary byte x into bcd
	 * and print the values b1,b2,b3
	 * @param x
	 */
	public void bin2bcd(int x){
		int b1=0,b2=0,b3=0;
		for(int i=0;i<8;i++){
			//add 3 to bcd >4
			if(b3>4) b3=((b3+3)&0xF);
			if(b2>4) b2=((b2+3)&0xF);
			if(b1>4) b1=((b1+3)&0xF);
			//shift out
			b3=((b3<<1)&0xF);
			b3=((b3|((b2>>3)&1))&0xF);
			b2=((b2<<1)&0xF);
			b2=((b2|((b1>>3)&1))&0xF);
			b1=((b1<<1)&0xF);
			b1=((b1|((x>>7)&1))&0xF);
			x=((x<<1)&0xFF);
		}
		
		System.out.println("b3="+b3+" b2="+b2+" b1="+b1);
	}
	
	/**
	 * @param x - number
	 * @param xs - number of bits in x(size)
	 * @param bs - number of bcd digits
	 */
	public void bin2bcdx(int x,int xs,int bs){
		int b[]=new int[xs];
		for(int i=0;i<xs;i++){
			for(int j=bs-1;j>=0;j--){
				if(b[j]>4) b[j]=((b[j]+3)&0xF);
			}
			for(int j=bs-1;j>0;j--){
				b[j]=((b[j]<<1)&0xF);
				b[j]=((b[j]|((b[j-1]>>3)&1))&0xF);
			}
			b[0]=((b[0]<<1)&0xF);
			b[0]=((b[0]|((x>>(xs-1))&1))&0xF);
			x=((x<<1)&((1<<xs)-1));
		}
		
		for(int j=bs-1;j>=0;j--){
			System.out.print(" b["+j+"]:"+b[j]);
		}System.out.println();
	}
	
	public static void main(String[] args) {
		DoubleDabble dd = new DoubleDabble();
		dd.bin2bcd(195);
		dd.bin2bcdx(47569, 16,5);
	}

}
