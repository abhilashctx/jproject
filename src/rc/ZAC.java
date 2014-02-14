package rc;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ZAC {

	private FileInputStream  is;
	private FileOutputStream os;
	
	private int codec;
	
	public static void main(String[] args) {
		ZAC zac = new ZAC();
		if(zac.processParams(args)!=0) return;
		/*zac.wb('A'>>4, 4);
		zac.wb('A'&0xF, 4);
		zac.wb('B'>>6, 2);
		zac.wb('B'&0x3F, 6);
		zac.wb('C'>>3, 5);
		zac.wb('C'&7, 3);
		zac.wbf();*/
		zac.processFiles();
		zac.closeFiles();
	}
	
	public void processFiles(){
		long st = System.currentTimeMillis();
		if(codec=='c') processC0();
		long et = System.currentTimeMillis()-st;
		System.out.println("time : "+((float)et/1000));
	}
	
	public void processC0(){
		int s[]=new int[256];
		int cs=0;
		int ctx=1;
		try{
			while(is.available()>0){
				int b = is.read();
				ctx=1;
				for(int i=7;i>=0;i--){
					int ss = s[ctx];
					cs += (ps[ss][2]);
					int bi = ((b>>i)&1);
					wb(as[cs][5-bi*3], as[cs][4-bi*3]);
					cs=as[cs][6-bi*3];
					s[ctx]=ps[ss][bi];
					ctx += (ctx+bi);
				}
			}
			wbf();
		}catch(Exception e){}
	}
	
	public void processC1(){
		short s[][]=new short[256][256];
		int cs=0;
		int ctx=1;
		int pb=0;
		try{
			while(is.available()>0){
				int b = is.read();
				ctx=1;
				for(int i=7;i>=0;i--){
					int ss = s[pb][ctx];
					cs += (ps[ss][2]);
					int bi = ((b>>i)&1);
					wb(as[cs][5-bi*3], as[cs][4-bi*3]);
					cs=as[cs][6-bi*3];
					s[pb][ctx]=ps[ss][bi];
					ctx += (ctx+bi);
				}
				pb=b;
			}
			wbf();
		}catch(Exception e){}
	}
	
	private int data;
	private int di;
	public void wrb_init(){
		di=0; data=0;
	}
	public void wb(int d,int c){
		data = (data<<c) + d;
		di+=c;
		while(di>=8){
			try{os.write( ((data>>(di-8))&0xFF) );}
			catch(Exception e){System.out.println(e.getMessage());}
			di-=8;
		}
	}
	public void wbf(){
		if(di>0){
			data = (data<<(8-di));
			try{os.write( ((data)&0xFF) );}catch(Exception e){}
		}
	}
	
	public int rb(int c){
		while(di<c){
			data = (data<<8);
			di+=8;
			try{
				if(is.available()>0){
					data += is.read();
				}
			}catch(Exception e){System.out.println(e.getMessage());}
		}
		int d = ((data>>(di-c))&((1<<c)-1));
		di-=c;
		return d;
	}
	
	public int processParams(String[] args){
		if(args.length != 3) {
			System.out.println("Invalid args");
			System.out.println("usage: zac c|d in out");
			return 1;
		}
		if(args[0].equalsIgnoreCase("c")) codec='c';
		else if(args[0].equalsIgnoreCase("d")) codec='d';
		else codec='x';
		if(codec=='x'){
			System.out.println("Invalid codec");
			return 2;
		}
		try{
			is = new FileInputStream(args[1]);
		}catch(Exception e){
			System.out.println("Invalid input file");
			return 3;
		}
		try{
			os = new FileOutputStream(args[2]);
		}catch(Exception e){
			System.out.println("Invalid output file");
			try{is.close();}catch(Exception ee){}
			return 4;
		}
		return 0;
	}
	
	public void closeFiles(){
		try{
			if(is!=null){is.close();is=null;}
		}catch(Exception e){}
		try{
			if(os!=null){os.close();os=null;}
		}catch(Exception e){}
	}
	
	public ZAC() {
		is=null; os=null;
		codec='x';
		wrb_init();
	}
	
	private short ps[][] = {
			{75 ,1 ,4}, {75 ,2 ,5}, {75 ,3 ,5}, {75 ,4 ,6}, {36 ,5 ,6},
			{36 ,6 ,6}, {36 ,7 ,6}, {36 ,8 ,6}, {52 ,9 ,7}, {52 ,10 ,7},
			{52 ,11 ,7}, {52 ,12 ,7}, {74 ,13 ,7}, {74 ,14 ,7}, {74 ,15 ,7},
			{74 ,16 ,7}, {73 ,17 ,7}, {73 ,18 ,7}, {73 ,19 ,7}, {73 ,20 ,7},
			{72 ,21 ,7}, {72 ,22 ,7}, {72 ,23 ,7}, {72 ,24 ,7}, {71 ,25 ,7},
			{71 ,26 ,7}, {71 ,27 ,7}, {71 ,28 ,7}, {32 ,29 ,7}, {32 ,30 ,7},
			{32 ,31 ,7}, {32 ,31 ,7}, {33 ,8 ,6}, {34 ,2 ,3}, {35 ,1 ,1},
			{38 ,36 ,1}, {37 ,2 ,4}, {34 ,1 ,2}, {39 ,36 ,1}, {40 ,36 ,1},
			{41 ,36 ,1}, {42 ,33 ,0}, {43 ,33 ,0}, {44 ,33 ,0}, {45 ,33 ,0},
			{47 ,46 ,0}, {35 ,2 ,2}, {48 ,46 ,0}, {49 ,46 ,0}, {50 ,46 ,0},
			{53 ,51 ,0}, {38 ,52 ,2}, {37 ,3 ,4}, {54 ,51 ,0}, {55 ,51 ,0},
			{56 ,51 ,0}, {58 ,57 ,0}, {39 ,52 ,2}, {59 ,57 ,0}, {60 ,57 ,0},
			{61 ,57 ,0}, {63 ,62 ,0}, {40 ,52 ,1}, {64 ,62 ,0}, {65 ,62 ,0},
			{66 ,62 ,0}, {68 ,67 ,0}, {41 ,52 ,1}, {69 ,67 ,0}, {70 ,67 ,0},
			{70 ,67 ,0}, {33 ,7 ,6}, {33 ,6 ,5}, {33 ,5 ,5}, {37 ,4 ,5},
			{37 ,1 ,2}
	};
	
	private short as[][] = {
			{0,4,0,0,0,0,8},{1,3,0,0,0,0,16},{3,2,0,0,0,0,32},{5,1,0,88,0,0,48},{7,1,0,0,1,1,0},
			{9,0,0,248,1,1,32},{11,0,0,88,2,3,0},{13,0,0,368,3,7,0},{1,4,1,0,0,0,16},{2,2,0,120,0,0,24},
			{4,1,0,264,0,0,40},{6,1,0,328,0,0,56},{8,0,0,360,1,1,16},{9,0,0,256,1,1,32},{11,0,0,96,2,3,0},
			{13,0,0,376,3,7,0},{2,4,2,0,0,0,24},{3,3,1,0,0,0,32},{5,1,0,120,0,0,48},{6,1,0,288,0,0,56},
			{8,0,0,320,1,1,16},{10,0,0,352,1,1,48},{11,0,0,104,2,3,0},{13,0,0,328,3,7,0},{3,4,3,0,0,0,32},
			{4,1,0,160,0,0,40},{6,1,0,224,0,0,56},{7,1,0,48,1,1,0},{9,0,0,272,1,1,32},{10,0,0,344,1,1,48},
			{12,0,0,416,2,3,32},{13,0,0,336,3,7,0},{4,4,4,0,0,0,40},{5,3,2,0,0,0,48},{6,2,1,88,0,0,56},
			{8,0,0,200,1,1,16},{9,0,0,208,1,1,32},{10,0,0,216,1,1,48},{12,0,0,408,2,3,32},{13,0,0,288,3,7,0},
			{5,4,5,0,0,0,48},{6,2,1,120,0,0,56},{7,2,1,32,1,1,0},{8,0,0,176,1,1,16},{10,0,0,192,1,1,48},
			{11,0,0,128,2,3,0},{12,0,0,304,2,3,32},{13,0,0,296,3,7,0},{6,4,6,0,0,0,56},{7,3,3,0,1,1,0},
			{8,0,0,152,1,1,16},{9,0,0,160,1,1,32},{10,0,0,168,1,1,48},{11,0,0,136,2,3,0},{12,0,0,312,2,3,32},
			{13,0,0,224,3,7,0},{7,4,7,0,1,1,0},{8,0,0,64,1,1,16},{9,0,0,72,1,1,32},{10,0,0,80,1,1,48},
			{11,0,0,144,2,3,0},{12,0,0,240,2,3,32},{13,0,0,232,3,7,0},{14,0,0,400,4,15,0},{7,4,7,0,4,8,0},
			{7,4,7,0,4,8,0},{7,4,7,0,4,8,0},{7,4,7,0,4,8,0},{7,4,7,0,4,8,0},{7,4,7,0,4,8,0},
			{7,4,7,0,4,8,0},{7,4,7,0,4,8,0},{7,4,7,0,3,4,0},{7,4,7,0,3,4,0},{7,4,7,0,3,4,0},
			{7,4,7,0,3,4,0},{8,0,0,64,4,9,0},{8,0,0,64,4,9,0},{8,0,0,64,4,9,0},{8,0,0,64,4,9,0},
			{7,4,7,0,2,2,88},{7,4,7,0,2,2,88},{7,4,7,0,2,2,88},{8,0,0,64,2,2,120},{8,0,0,64,2,2,120},
			{8,0,0,64,2,2,120},{9,0,0,72,4,10,0},{9,0,0,72,4,10,0},{0,4,0,0,0,0,96},{1,3,0,0,0,0,104},
			{2,2,0,88,0,0,112},{4,1,0,248,0,0,128},{5,1,0,88,0,0,136},{6,1,0,368,0,0,144},{8,0,0,392,2,2,32},
			{9,0,0,248,3,5,0},{1,4,1,0,0,0,104},{2,2,0,120,0,0,112},{3,2,0,32,0,0,120},{4,1,0,264,0,0,128},
			{6,1,0,328,0,0,144},{7,1,0,16,2,2,0},{8,0,0,360,2,2,32},{9,0,0,256,3,5,0},{2,4,2,0,0,0,112},
			{3,3,1,0,0,0,120},{4,1,0,208,0,0,128},{5,1,0,120,0,0,136},{6,1,0,288,0,0,144},{7,1,0,32,2,2,0},
			{8,0,0,320,2,2,32},{9,0,0,264,3,5,0},{3,4,3,0,0,0,120},{4,1,0,160,0,0,128},{5,1,0,136,0,0,136},
			{6,1,0,224,0,0,144},{7,1,0,48,2,2,0},{8,0,0,280,2,2,32},{9,0,0,272,3,5,0},{10,0,0,344,4,11,0},
			{4,4,4,0,0,0,128},{4,4,4,0,0,0,128},{5,3,2,0,0,0,136},{6,2,1,88,0,0,144},{7,2,1,0,2,2,0},
			{8,0,0,200,2,2,32},{9,0,0,208,3,5,0},{10,0,0,216,4,11,0},{5,4,5,0,0,0,136},{5,4,5,0,0,0,136},
			{6,2,1,120,0,0,144},{7,2,1,32,2,2,0},{8,0,0,176,2,2,32},{8,0,0,176,2,2,32},{9,0,0,184,3,5,0},
			{10,0,0,192,4,11,0},{6,4,6,0,0,0,144},{6,4,6,0,0,0,144},{7,3,3,0,2,2,0},{7,3,3,0,2,2,0},
			{8,0,0,152,2,2,32},{9,0,0,160,3,5,0},{9,0,0,160,3,5,0},{10,0,0,168,4,11,0},{7,4,7,0,2,2,0},
			{7,4,7,0,2,2,0},{8,0,0,64,2,2,32},{8,0,0,64,2,2,32},{9,0,0,72,3,5,0},{9,0,0,72,3,5,0},
			{10,0,0,80,4,11,0},{10,0,0,80,4,11,0},{6,4,6,0,0,0,64},{6,4,6,0,0,0,64},{6,4,6,0,0,0,64},
			{6,4,6,0,0,0,64},{7,3,3,0,4,8,0},{7,3,3,0,4,8,0},{7,3,3,0,4,8,0},{7,3,3,0,4,8,0},
			{6,4,6,0,0,0,72},{6,4,6,0,0,0,72},{6,4,6,0,0,0,72},{7,3,3,0,3,4,0},{7,3,3,0,3,4,0},
			{7,3,3,0,3,4,0},{8,0,0,152,4,9,0},{8,0,0,152,4,9,0},{6,4,6,0,0,0,80},{6,4,6,0,0,0,80},
			{7,3,3,0,2,2,88},{7,3,3,0,2,2,88},{8,0,0,152,2,2,120},{8,0,0,152,2,2,120},{9,0,0,160,4,10,0},
			{9,0,0,160,4,10,0},{5,4,5,0,0,0,152},{5,4,5,0,0,0,152},{5,4,5,0,0,0,152},{6,2,1,120,0,0,64},
			{6,2,1,120,0,0,64},{6,2,1,120,0,0,64},{7,2,1,32,4,8,0},{7,2,1,32,4,8,0},{5,4,5,0,0,0,160},
			{5,4,5,0,0,0,160},{6,2,1,120,0,0,72},{6,2,1,120,0,0,72},{7,2,1,32,3,4,0},{7,2,1,32,3,4,0},
			{8,0,0,176,4,9,0},{8,0,0,176,4,9,0},{5,4,5,0,0,0,168},{5,4,5,0,0,0,168},{6,2,1,120,0,0,80},
			{6,2,1,120,0,0,80},{7,2,1,32,2,2,88},{8,0,0,176,2,2,120},{8,0,0,176,2,2,120},{9,0,0,184,4,10,0},
			{4,4,4,0,0,0,176},{4,4,4,0,0,0,176},{5,3,2,0,0,0,152},{5,3,2,0,0,0,152},{6,2,1,88,0,0,64},
			{6,2,1,88,0,0,64},{7,2,1,0,4,8,0},{7,2,1,0,4,8,0},{4,4,4,0,0,0,184},{4,4,4,0,0,0,184},
			{5,3,2,0,0,0,160},{5,3,2,0,0,0,160},{6,2,1,88,0,0,72},{7,2,1,0,3,4,0},{7,2,1,0,3,4,0},
			{8,0,0,200,4,9,0},{4,4,4,0,0,0,192},{4,4,4,0,0,0,192},{5,3,2,0,0,0,168},{6,2,1,88,0,0,80},
			{7,2,1,0,2,2,88},{7,2,1,0,2,2,88},{8,0,0,200,2,2,120},{9,0,0,208,4,10,0},{6,4,6,0,0,0,232},
			{6,4,6,0,0,0,232},{7,3,3,0,1,1,88},{8,0,0,152,1,1,104},{9,0,0,160,1,1,120},{10,0,0,168,1,1,136},
			{11,0,0,136,3,6,0},{12,0,0,312,4,13,0},{7,4,7,0,1,1,88},{7,4,7,0,1,1,88},{8,0,0,64,1,1,104},
			{9,0,0,72,1,1,120},{10,0,0,80,1,1,136},{10,0,0,80,1,1,136},{11,0,0,144,3,6,0},{12,0,0,240,4,13,0},
			{7,4,7,0,1,1,248},{7,4,7,0,1,1,248},{8,0,0,64,1,1,264},{8,0,0,64,1,1,264},{9,0,0,72,1,1,208},
			{10,0,0,80,1,1,160},{10,0,0,80,1,1,160},{11,0,0,144,4,12,0},{0,4,0,0,0,0,256},{1,3,0,0,0,0,264},
			{2,2,0,88,0,0,272},{3,2,0,0,0,0,208},{4,1,0,248,0,0,184},{5,1,0,88,0,0,160},{6,1,0,368,0,0,72},
			{7,1,0,0,3,4,0},{1,4,1,0,0,0,264},{2,2,0,120,0,0,272},{3,2,0,32,0,0,208},{4,1,0,264,0,0,184},
			{5,1,0,104,0,0,160},{6,1,0,328,0,0,72},{7,1,0,16,3,4,0},{8,0,0,360,4,9,0},{2,4,2,0,0,0,272},
			{2,4,2,0,0,0,272},{3,3,1,0,0,0,208},{4,1,0,208,0,0,184},{5,1,0,120,0,0,160},{6,1,0,288,0,0,72},
			{7,1,0,32,3,4,0},{8,0,0,320,4,9,0},{3,4,3,0,0,0,208},{3,4,3,0,0,0,208},{4,1,0,160,0,0,184},
			{5,1,0,136,0,0,160},{6,1,0,224,0,0,72},{6,1,0,224,0,0,72},{7,1,0,48,3,4,0},{8,0,0,280,4,9,0},
			{3,4,3,0,0,0,200},{3,4,3,0,0,0,200},{4,1,0,160,0,0,176},{4,1,0,160,0,0,176},{5,1,0,136,0,0,152},
			{6,1,0,224,0,0,64},{6,1,0,224,0,0,64},{7,1,0,48,4,8,0},{4,4,4,0,0,0,296},{5,3,2,0,0,0,224},
			{6,2,1,88,0,0,232},{7,2,1,0,1,1,88},{8,0,0,200,1,1,104},{9,0,0,208,1,1,120},{10,0,0,216,1,1,136},
			{11,0,0,120,3,6,0},{5,4,5,0,0,0,224},{6,2,1,120,0,0,232},{7,2,1,32,1,1,88},{8,0,0,176,1,1,104},
			{9,0,0,184,1,1,120},{10,0,0,192,1,1,136},{11,0,0,128,3,6,0},{12,0,0,304,4,13,0},{5,4,5,0,0,0,312},
			{5,4,5,0,0,0,312},{6,2,1,120,0,0,240},{7,2,1,32,1,1,248},{8,0,0,176,1,1,264},{9,0,0,184,1,1,208},
			{10,0,0,192,1,1,160},{11,0,0,128,4,12,0},{6,4,6,0,0,0,240},{6,4,6,0,0,0,240},{7,3,3,0,1,1,248},
			{8,0,0,152,1,1,264},{9,0,0,160,1,1,208},{9,0,0,160,1,1,208},{10,0,0,168,1,1,160},{11,0,0,136,4,12,0},
			{2,4,2,0,0,0,280},{2,4,2,0,0,0,280},{3,3,1,0,0,0,200},{4,1,0,208,0,0,176},{5,1,0,120,0,0,152},
			{5,1,0,120,0,0,152},{6,1,0,288,0,0,64},{7,1,0,32,4,8,0},{2,4,2,0,0,0,336},{3,3,1,0,0,0,288},
			{4,1,0,208,0,0,296},{6,1,0,288,0,0,232},{7,1,0,32,1,1,88},{8,0,0,320,1,1,104},{10,0,0,352,1,1,136},
			{11,0,0,104,3,6,0},{3,4,3,0,0,0,288},{4,1,0,160,0,0,296},{5,1,0,136,0,0,224},{6,1,0,224,0,0,232},
			{8,0,0,280,1,1,104},{9,0,0,272,1,1,120},{10,0,0,344,1,1,136},{11,0,0,112,3,6,0},{3,4,3,0,0,0,216},
			{3,4,3,0,0,0,216},{4,1,0,160,0,0,192},{5,1,0,136,0,0,168},{6,1,0,224,0,0,80},{7,1,0,48,2,2,88},
			{8,0,0,280,2,2,120},{9,0,0,272,4,10,0},{2,4,2,0,0,0,344},{3,3,1,0,0,0,216},{4,1,0,208,0,0,192},
			{5,1,0,120,0,0,168},{6,1,0,288,0,0,80},{7,1,0,32,2,2,88},{8,0,0,320,2,2,120},{9,0,0,264,4,10,0},
			{1,4,1,0,0,0,320},{1,4,1,0,0,0,320},{2,2,0,120,0,0,280},{3,2,0,32,0,0,200},{4,1,0,264,0,0,176},
			{5,1,0,104,0,0,152},{6,1,0,328,0,0,64},{7,1,0,16,4,8,0},{0,4,0,0,0,0,376},{1,3,0,0,0,0,328},
			{3,2,0,0,0,0,288},{4,1,0,248,0,0,296},{6,1,0,368,0,0,232},{8,0,0,392,1,1,104},{9,0,0,248,1,1,120},
			{11,0,0,88,3,6,0},{1,4,1,0,0,0,328},{2,2,0,120,0,0,336},{4,1,0,264,0,0,296},{5,1,0,104,0,0,224},
			{7,1,0,16,1,1,88},{8,0,0,360,1,1,104},{10,0,0,384,1,1,136},{11,0,0,96,3,6,0},{1,4,1,0,0,0,352},
			{2,2,0,120,0,0,344},{3,2,0,32,0,0,216},{4,1,0,264,0,0,192},{5,1,0,104,0,0,168},{6,1,0,328,0,0,80},
			{7,1,0,16,2,2,88},{8,0,0,360,2,2,120},{0,4,0,0,0,0,360},{1,3,0,0,0,0,320},{2,2,0,88,0,0,280},
			{3,2,0,0,0,0,200},{4,1,0,248,0,0,176},{5,1,0,88,0,0,152},{6,1,0,368,0,0,64},{7,1,0,0,4,8,0},
			{7,4,7,0,1,1,368},{7,4,7,0,1,1,368},{8,0,0,64,1,1,328},{9,0,0,72,1,1,288},{10,0,0,80,1,1,224},
			{11,0,0,144,2,3,88},{12,0,0,240,2,3,120},{13,0,0,232,4,14,0},{4,4,4,0,0,0,304},{5,3,2,0,0,0,312},
			{6,2,1,88,0,0,240},{7,2,1,0,1,1,248},{8,0,0,200,1,1,264},{9,0,0,208,1,1,208},{10,0,0,216,1,1,160},
			{11,0,0,120,4,12,0},{3,4,3,0,0,0,408},{4,1,0,160,0,0,304},{5,1,0,136,0,0,312},{6,1,0,224,0,0,240},
			{7,1,0,48,1,1,248},{8,0,0,280,1,1,264},{9,0,0,272,1,1,208},{10,0,0,344,1,1,160}
	};
}
