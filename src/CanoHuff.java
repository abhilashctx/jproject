
public class CanoHuff {
	
	public static void main(String[] args) {
		int cl[] = new int[16];
		cl[5]=2;
		cl[4]=3;
		cl[3]=2;
		cl[2]=2;
		int cc[] = new int[16];
		
		int last=0;
		int c=0;
		
		for(int i=cl.length-2;i>0;i--){
			if(cl[i]>0){
				c+=last;
				c>>=1;
				cc[i]=c;
				last=cl[i];
				System.out.println(i+":"+z(i,Integer.toBinaryString(cc[i])) );
			}
		}
	}
	
	public static String z(int b,String sb){
		int db=b-sb.length();
		String result="";
		while(db>0) {result+="0";db--;}
		return result+sb;
	}

}
