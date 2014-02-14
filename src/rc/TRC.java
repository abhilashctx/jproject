package rc;

import java.util.Random;

/*
 * table base rc
 */
public class TRC {

	int q[];
	int c,r;
	int outcount;
	public void buildtable(){
		q = new int[256];
		for(int i=0;i<256;i++){
			q[i] = (i * 80)/100;
			System.out.println("q["+i+"]="+q[i]);
		}
		c=0;r=255;
		outcount=0;
	}
	public void enc(int b){ // bit
		int m = q[r];
		if(b==1) r=m;
		else {c+=m; r-=m;}
		while(r<128){
			System.out.print(((c & 0x80)>>7));
			outcount++;
			c=((c<<1)&0xff);
			r=((r<<1)&0xff) +1;
			if(r>(0xFF-c)) r=(0xFF-c);
		}
		System.out.println();
	}
	public static void main(String[] args) {
		TRC trc = new TRC();
		trc.buildtable();
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<1000;i++){
			int b=1;
			if(rand.nextInt(100)<10) b=0;
			//int b = rand.nextInt(2);
			trc.enc(b);
		}
		System.out.println("out:"+trc.outcount);
	}
}
