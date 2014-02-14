package sortalgo;

public class TimedOpt {
	
	public static long exec(Sort s, int a[]){
		long st = System.currentTimeMillis();
		s.sort(a);
		long dt = System.currentTimeMillis() - st;
		return dt;
	}

}
