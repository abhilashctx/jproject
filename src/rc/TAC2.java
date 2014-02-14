package rc;

import java.util.Arrays;

public class TAC2 {
	
	static class T{
		int l,h,p;
		int m;
		int b1,b0;
		int c1,c0;
		int n1,n0;
		public T() {
			h=l=-1;
		}
	};
	
	//static boolean mark[];
	static T t[];
	static int ti;
	static int ti_jump=8;
	
	public static void main(String[] args) {
		t = new T[1<<11];
		for(int i=0;i<t.length;i++) t[i]=new T();
		//mark = new boolean[2048];
		//Arrays.fill(mark, false);
		
		System.out.println("start");
		
		state(0,0xF);
		
		dump();
		
		dumpJava();
		
		System.out.println("stop");
	}
	
	public static int state(int l,int h){
		int r = find(l, h);
		if(r!=-1) return r;
		int s=ti;
		ti+=ti_jump;
		for(int i=0;i<ti_jump;i++){ // 1 to 7 probab
			int lhp = s+i; //s + i - 1;
			//System.out.println("l:"+l+" h:"+h+" p:"+i);
			//if(mark[lhp]) return;
			//mark[lhp]=true;
			t[lhp] = new T();
			t[lhp].l=l;t[lhp].h=h;t[lhp].p=i;
			
			int m = l + (((h-l) * i)>>3);
			t[lhp].m=m;
			// 1
			int l1 = l; int h1 = m;
			int c1=0; int b1=0;
			while (((l1^h1)&0x8)==0) {
				c1++; b1=(b1<<1)+((l1>>3)&1);
				l1 = ((l1<<1)&0xF);
				h1 = ((h1<<1)&0xF)+1;
			}
			t[lhp].c1=c1; t[lhp].b1=b1; t[lhp].n1=state(l1,h1);
			// 0
			int l0 = m+1; int h0 = h;
			int c0=0; int b0=0;
			while (((l0^h0)&0x8)==0) {
				c0++; b0=(b0<<1)+((l0>>3)&1);
				l0 = ((l0<<1)&0xF);
				h0 = ((h0<<1)&0xF)+1;
			}
			t[lhp].c0=c0; t[lhp].b0=b0; t[lhp].n0=state(l0,h0);
		}
		return s;
	}
	
	public static int find(int l,int h){
		for(int i=0;i<ti;i+=ti_jump){
			if(t[i].l==l && t[i].h==h) return i;
		}
		return -1;
	}
	
	public static void dump(){
		
		for(int i=0;i<ti;i++){
			System.out.println(i+"> l:"+t[i].l+" h:"+t[i].h+" p:"+t[i].p+" m:"+t[i].m+" c1:"+t[i].c1+" n1:"+t[i].n1+" c0:"+t[i].c0+" n0:"+t[i].n0);
		}
		System.out.println("Total states : "+ti);
	}
	
	public static void dumpJava(){
		
		int linebreak=5;
		for(int i=0;i<ti;i++){
			System.out.print("{"+t[i].m+","+t[i].c1+","+t[i].b1+","+t[i].n1+","+t[i].c0+","+t[i].b0+","+t[i].n0+"},"); // /*"+i+"*/,");
			linebreak--;
			if(linebreak==0){linebreak=5;System.out.println();}
		}
		System.out.println("Total states : "+ti);
	}

}
