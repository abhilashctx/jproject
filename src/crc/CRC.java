package crc;

public class CRC {

	public static void main(String[] args) {
		
		int crctable[] = new int[256];
		
		int r=0;
		
		int poly = 0x876543;
		
		for(int d=0; d<256; d++)
		{
			r = (d << 16);
			
			//for(int b=8;b>0;b--)
			int b=8;
			do
			{
				if( (r & 0x100000) > 0)
				{
					r = ((r<<1) ^ poly) & 0xffffff;
				}
				else
				{
					r = (r<<1) & 0xffffff;
				}
			}while(--b>0);
			crctable[d] = r;
			System.out.println(d+" = "+r+" = "+Integer.toHexString(r));
		}
		
//		now compute crc for a giving msg
		
		int msg[] = {'j','e','l','l','o',' ','w','o','r','l','d'};
		
		r = 0;
		
		for(int b=0;b<msg.length;b++)
		{
			int data = msg[b] ^ (r>>16);
			r = (crctable[data] ^ (r << 8)) & 0xffffff;
		}
		
		System.out.println("crc = "+Integer.toHexString(r));
		
		/*int p1 = 100;
		int p2 = 150;
		int p3 = 1;
		int p4 = 3596;
		int p = (p1+p2+p3+p4)/(4);
		System.out.println("p="+p);
		p = (p1+p2*4+p3*9+p4*16)/(1+4+9+16);
		System.out.println("p="+p);
		p1-=2048;p2-=2048;p3-=2048;p4-=2048;
		p = (p1+p2*4+p3*9+p4*16)/(1+4+9+16);
		p+=2048;
		System.out.println("p="+p);*/
	}
}
