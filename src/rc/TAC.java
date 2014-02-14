package rc;

import java.util.Arrays;

public class TAC {
	
	static class T{
		int l,h,p;
		int m;
		int b1,b0;
		int c1,c0;
		int n1,n0;
	};
	
	static boolean mark[];
	static T t[];
	static int ti; 
	
	public static void main(String[] args) {
		t = new T[2048];
		for(int i=0;i<t.length;i++) t[i]=new T();
		mark = new boolean[2048];
		Arrays.fill(mark, false);
		
		System.out.println("start");
		
		state(0,0xF);
		
		dump();
		
		System.out.println("stop");
	}
	
	public static void state(int l,int h){
		int lh = (((l<<4)+h)<<3);
		for(int i=1;i<8;i++){ // 1 to 7 probab
			int lhp = lh + i;
			//System.out.println("l:"+l+" h:"+h+" p:"+i);
			if(mark[lhp]) return;
			mark[lhp]=true;
			
			t[lhp].l=l;t[lhp].h=h;t[lhp].p=i;
			
			int m = l + (((h-l) * i)>>3);
			// 1
			int l1 = l; int h1 = m;
			int c1=0;
			while (((l1^h1)&0x8)==0) {
				c1++;
				l1 = ((l1<<1)&0xF);
				h1 = ((h1<<1)&0xF)+1;
			}
			t[lhp].c1=c1; t[lhp].n1=(l1<<4)+h1;
			state(l1,h1);
			// 0
			int l0 = m+1; int h0 = h;
			int c0=0;
			while (((l0^h0)&0x8)==0) {
				c0++;
				l0 = ((l0<<1)&0xF);
				h0 = ((h0<<1)&0xF)+1;
			}
			t[lhp].c0=c0; t[lhp].n0=(l0<<4)+h0;
			state(l0,h0);
		}
	}
	
	public static void dump(){
		
		int c=0;
		for(int i=0;i<mark.length;i++){
			if(mark[i]){
				c++;
				System.out.println(i+"> l:"+t[i].l+" h:"+t[i].h+" p:"+t[i].p+" c1:"+t[i].c1+" n1:"+t[i].n1+" c0:"+t[i].c0+" n0:"+t[i].n0);
			}
		}
		System.out.println("Total states : "+c);
	}

}
