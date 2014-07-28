import java.util.Timer;
import java.util.TimerTask;


public class TimerDemo {

	public static void main(String[] args) {
		TimerTask t1 = new TimerTask() {
			int count=0;
			public void run() {
				count++;
				System.out.println("t1:"+count);
			}
		};
		TimerTask t2 = new TimerTask() {
			int count=0;
			public void run() {
				count++;
				System.out.println("t2:"+count);
			}
		};
		Timer timer = new Timer();
		timer.schedule(t1, 10,100);
		timer.schedule(t2, 10,50);
		try{Thread.sleep(1000);}catch(Exception e){}
		timer.cancel();
		System.out.println("done");
	}
}
