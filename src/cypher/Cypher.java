package cypher;

public class Cypher {
	
	
	public static String cyph1(String msg,String alpha,String crypt,int cycles){
		String cmsg=msg;
		String nmsg="";
		for(int k=0;k<cycles;k++)
		{
			for(int i=0;i<cmsg.length();i++){
				
				for(int j=0;j<alpha.length();j++){
					if(cmsg.charAt(i)==alpha.charAt(j)){
						nmsg += crypt.charAt(j);
						break;
					}
				}
				
			}
			cmsg = nmsg;
			nmsg = "";
		}
		return cmsg;
	}
	
	public static String cyph2(String msg,String alpha,String crypt,int cycles){
		String cmsg=msg;
		String nmsg="";
		for(int k=0;k<cycles;k++)
		{
			for(int m=0;m<cmsg.length();m++){
				for(int i=0;i<m;i++){
					nmsg += cmsg.charAt(i);
				}
				for(int i=m;i<cmsg.length();i++){
					
					for(int j=0;j<alpha.length();j++){
						if(cmsg.charAt(i)==alpha.charAt(j)){
							nmsg += crypt.charAt(j);
							break;
						}
					}
					
				}
				cmsg = nmsg;
				nmsg = "";
			}
		}
		return cmsg;
	}

	public static void main(String[] args) {
		
		String txt1="abcdefghijklmnopqrstuvwxyz";
		String txt2="wydtflhkjigonmrspqeuvbxcza";
		
		String prv  = "lunchtime";
		String msg  = prv;
		String nmsg = "";
		
		int cycles=3;
		
		
		
		String result = cyph1("stillmilldrillbillaaaaaaaaaaaaaaaaaaa", txt1, txt2, cycles);
		System.out.println(result);
		System.out.println(cyph1(result, txt2, txt1, cycles));
		
		result = cyph2("stillmilldrillbillaaaaaaaaaaaaaaaaaaazbaaab", txt1, txt2, cycles);
		System.out.println(result);
		System.out.println(cyph2(result, txt2, txt1, cycles));
		
	}
}
