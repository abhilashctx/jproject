package shutabort;

public class AbortShutdown {

	
	public static void sleepfor(int s){
		try{Thread.sleep(s);}catch(Exception e){}
	}
	public static void main(String[] args) {
		String cmd = "cmd /C shutdown -a";
		int repeat    = 0; //infinite
		int delay     = 1000 * 5;
		if(args.length > 0){
			for(int i=0;i<args.length;i++){
				if(args[i].equals("-c")){
					cmd = args[i+1];
					i++;
				}
				if(args[i].equals("-r")){
					repeat = Integer.parseInt(args[i+1]);
					i++;
				}
				if(args[i].equals("-d")){
					delay = Integer.parseInt(args[i+1]);
					i++;
				}
			}
		}
		System.out.println("cmd="+cmd+";rep="+repeat+";del="+delay);

		Runtime r = Runtime.getRuntime();
		if(repeat==0) repeat = 0xffffff; // many times
		for(int i=0;i<repeat;i++){
			try{
				Process p = r.exec(cmd);
				p.waitFor();
				System.out.println("->"+p.exitValue());
			}catch(Exception e){System.out.println(e);}
			sleepfor(delay);
		}
	}
}
