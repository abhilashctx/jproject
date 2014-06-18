package cbasic;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;


public class Console extends Frame implements Runnable{

	private static final long serialVersionUID = 1L;
	private static final String cname = "Console";
	private boolean isRun;
	private char keychar;
	private Thread me;
	
	char data[][];
	int cc, lc;
	
	public Console() {
		super(cname);
		setSize(423, 370);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				isRun=false;System.exit(0);
			}
		});
		
		data=new char[25][50];
		lc=0;cc=0;
		
		enableEvents(AWTEvent.KEY_EVENT_MASK);
		isRun=true;
		me=new Thread(this); me.start();
	}
	public void update(Graphics g) {}
	public void stop(){isRun=false;}
	public void run(){
		createBufferStrategy(2);
		BufferStrategy bs = getBufferStrategy();
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);
		boolean cur=true;
		try{Thread.sleep(500);}catch(Exception e){}
		while(isRun){
			Graphics2D g = (Graphics2D)bs.getDrawGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(font);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.GRAY);
			for(int i=0;i<data.length;i++) for(int j=0;j<data[0].length;j++)
					g.drawChars(data[i], j, 1, 10+j*8, 40+i*13);
			cur=!cur;
			g.dispose();
			bs.show();
			try{Thread.sleep(100);}catch(Exception e){}
		}
	}
	protected void processKeyEvent(KeyEvent e) {
		if(e.getID()==400){keychar=e.getKeyChar();}
	}
	public String read(){
		StringBuffer sbin = new StringBuffer();
		keychar=0;
		int incount=0;
		while(true){
			while(keychar==0){try{Thread.sleep(10);}catch(Exception e){}}
			if(keychar==10) break;
			if(keychar==8){
				if(incount>0){back();incount--;sbin.deleteCharAt(sbin.length()-1);}
			}
			else{
				sbin.append(keychar);
				write(keychar);
				incount++;
			}
			keychar=0;
		}
		nextLine();
		return sbin.toString();
	}
	public void write(char ouChar){
		data[lc][cc]=ouChar;
		cc++;
		if(ouChar=='\n' || cc>=data[0].length) nextLine();
	}
	private void nextLine(){
		lc++;cc=0;
		if(lc>=data.length){
			for(int i=1;i<data.length;i++) for(int j=0;j<data[0].length;j++)
					data[i-1][j]=data[i][j];
			for(int j=0;j<data[0].length;j++) data[data.length-1][j]=' ';
			lc--;
		}
	}
	private void back(){
		cc--;
		if(cc<0) {lc--;cc=data[0].length-1;}
		data[lc][cc]=' ';
	}
	public void write(String ouText){
		for(int i=0;i<ouText.length();i++) write(ouText.charAt(i));
	}
	public void cls(){
		for(int i=0;i<data.length;i++)for(int j=0;j<data[0].length;j++)
				data[i][j]=' ';
		lc=0;cc=0;
	}
	public void gotoxy(){}
	public static void main(String[] args) {
		Console console = new Console();
		console.cls();
		console.write('c');
		console.write("\nhello console\nNext LINE?");
		for(int i=0;i<50;i++) {
			console.write("\ntest string data "+i);
			try{Thread.sleep(10);}catch(Exception e){}
		}
		console.write("\nenter :");
		System.out.println("hmm..."+console.read());
		console.write("done reading\n");
		console.write("1234567890123456789012345678901234567890");
		//console.stop();
	}
}
