package strmul;

public class Decimal8 {

	public static void main(String[] args) {
		/*int l = 10;
		int h = 0;
		
		long base = 65536*65536;
		//System.out.println(base);
		
		int hl = 259;
		String txt = "";
		while(hl>0){
			int digit = hl % 10;// hl - ((hl/10)*10);
			System.out.println(digit);
			txt = digit + txt;
			hl /= 10;
		}
		System.out.println(txt);*/
		
		//System.out.println((int)'0');
		//System.out.println((int)'-');
		
		/*int a[] = new int[32];
		int b[] = new int[32];
		int c[] = new int[32];
		fillup(a,0);
		fillup(b,0);
		fillup(c,0);
		filldata(a,239);
		filldata(b,69);
		printdata(a);
		printdata(b);
		multiply(a, b, c);
		printdata(c);*/
		
		//System.out.println(strmul("23283064365386962890625","23283064365386962890625"));
		System.out.println(strmul("256","256"));
	}
	
	public static String strmul(String sa,String sb){
		int a[] = new int[128];
		int b[] = new int[128];
		int c[] = new int[128];
		fillup(a,0);
		fillup(b,0);
		fillup(c,0);
		int idx = a.length-1;
		for(int i=sa.length()-1;i>=0;i--){
			a[idx--] = sa.charAt(i)-'0';
		}
		idx = b.length-1;
		for(int i=sb.length()-1;i>=0;i--){
			b[idx--] = sb.charAt(i)-'0';
		}
		multiply(a, b, c);
		String r = "";
		for(idx=0;c[idx]==0;idx++); //skip 0s
		for(;idx<c.length;idx++){
			r += c[idx];
		}
		return r;
	}
	
	public static void multiply(int a[],int b[],int c[]){
		int addidx = c.length-1;
		int mid    = a.length/2;
		int idx2   = b.length-1;
		int cidx   = c.length-1;
		for(int i=0;i<mid;i++){
			int idx1=a.length-1;
			cidx = addidx;
			addidx--;
			while(idx1 >= mid){
				int t = a[idx1] * b[idx2];
				//System.out.println(" "+a[idx1] +" * "+ b[idx2]);
				idx1--;
				c[cidx]+=t;
				//System.out.println(" = "+c[cidx]);
				if(c[cidx]>=10){
					int carry = c[cidx]/10;
					c[cidx] %= 10;
					int ccidx=cidx-1;
					//System.out.println("carry!!! "+carry);
					while(carry>0){
						c[ccidx]+=carry;
						carry=c[ccidx]/10;
						c[ccidx] %= 10;
						ccidx--;
					}
				}
				cidx--;
			}
			idx2--;
		}
	}
	
	public static void printdata(int q[]){
		for(int i=0;i<q.length;i++){
			System.out.print(q[i]);
		}
		System.out.println();
	}
	
	public static void filldata(int q[],int value){
		int i = q.length-1;
		while(value>0){
			int d = value % 10;
			q[i--]=d;
			value /= 10;
		}
	}
	
	public static void fillup(int q[],int value){
		for(int i=0;i<q.length;i++) q[i]=value;
	}
}
