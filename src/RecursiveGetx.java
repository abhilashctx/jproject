import java.util.Random;


public class RecursiveGetx {

	public static String rgetx(String x){
		int index = x.indexOf('$');
		while(index>-1){
			int lp = index+5, rp = lp+1, pc = 0;
			while(pc!=0 || x.charAt(rp)!=')'){
				if(x.charAt(rp)=='(') pc++;
				if(x.charAt(rp)==')') pc--;
				rp++;
			}rp++;
			String data = x.substring(lp+1, rp-1);
			//System.out.println(spacer(indent)+"calling $getx("+data+")");
			data = rgetx(data);
			String called = "calling $getx("+data+")";
			String result = getx(data); // database call lol ?
			x = x.substring(0, index) + result + x.substring(rp);
			//System.out.println(spacer(indent)+"return:"+result+"    after modification:"+x);
			System.out.println(called +" = "+ result);
			index = x.indexOf('$');
		}
		return x;
	}
	
	//simulate database call with this function getx(string)
	public static Random r = new Random();
	public static String getx(String x){
		int strlen = r.nextInt(x.length())+2;
		String result="";
		for(int i=0;i<strlen;i++)
			result+= (char)(r.nextInt(26)+'A');
		return result;
	}
	public static void main(String[] args) {
		//example 1
		String test1 = "xy$getx(aa$getx(zzz))zz$getx(bbb)yy";
		System.out.println("input : "+test1);
		String result1 = rgetx(test1);
		System.out.println(test1+" = "+result1);
		System.out.println();
		//example 2
		String test2 = "xx$getx(yy$getx(aaa))zz$getx(bbb)pp";
		System.out.println("input : "+test2);
		String result2 = rgetx(test2);
		System.out.println(test2+" = "+result2);
		System.out.println();
		//example 3
		String test3 = "xx$getx(yy$getx(a$getx(www)aa))zz$getx(bbb$getx(ccc))pp";
		System.out.println("input : "+test3);
		String result3 = rgetx(test3);
		System.out.println(test3+" = "+result3);
	}
}
