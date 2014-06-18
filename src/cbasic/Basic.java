package cbasic;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class Basic {
	
	ArrayList<String> list;
	//BufferedReader br;

	public Basic() {
		list = new ArrayList<String>();
		goMap = new HashMap<String, Integer>();
		env = new HashMap<String, String>();
		callStack = new Stack<Integer>();
	}
	
	private Console con;
	public void initCon(){con=new Console();}
	public String read(){
		if(con==null) initCon();
		return con.read();
	}
	public void print(String msg){
		if(con==null) initCon();
		con.write(msg);
	}
	public void println(String msg){
		if(con==null) initCon();
		con.write(msg+"\n");
	}
	public void stopio(){con.stop();}
	public void cls(){con.cls();}
	
	public void run(){
		try{
			//br = new BufferedReader(new InputStreamReader(System.in));
			String input=null;
			print(">");
			while(!(input=read()).equals("exit")){
				if(input.equals("cls")){
					list.clear(); goMap.clear(); env.clear();cls();
				}else if(input.equals("lst")){
					for(int i=0;i<list.size();i++)
						println((i+1)+": "+list.get(i));
				}else if(input.equals("len")){
					println("length: "+list.size());
				}else if(input.startsWith("ed")){
					int i=2; while(input.charAt(i)==' ')i++;
					int lno=0;
					while(i<input.length() && (input.charAt(i)>='0' && input.charAt(i)<='9'))
						{lno=(lno*10)+(input.charAt(i)-'0');i++;}
					lno--;
					if(lno<list.size()){
						println("select "+(lno+1)+">"+list.get(lno));
						print("modify "+(lno+1)+">"); input=read();
						list.set(lno, input);
						println("done. (use lst to check)");
					}else println("invalid edit line");
				}else if(input.equals("run")){
					exeRUN();
				}else if(input.startsWith("run ")){
					int i=3; while(input.charAt(i)==' ')i++;
					String fname="";
					while(i<input.length() && input.charAt(i)!=' '){
						fname+=input.charAt(i);i++;
					}
					try{
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
						String line = "";
						while((line=br.readLine())!=null){list.add(line);}
						br.close();
					}catch(Exception e){
						println("* File load failed");
					}
					exeRUN();
				}else{
					input = input.trim();
					if(input.length()>0) list.add(input);
				}
				print(">");
			}
			stopio();
			System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static final int EOI=0;
	public static final int NUM=1;
	public static final int LIT=2;
	public static final int STR=3;
	public static final int LPA=4;
	public static final int RPA=5;
	public static final int EQU=6;
	public static final int LT=7;
	public static final int GT=8;
	public static final int ADD=9;
	public static final int SUB=10;
	public static final int MUL=11;
	public static final int DIV=12;
	
	public static final int END=20;
	public static final int PRN=21;
	public static final int VAR=22;
	public static final int GOT=23;
	public static final int INP=24;
	public static final int CAL=25;
	public static final int RET=26;
	public static final int  IF=27;
	public static final int LET=28;
	public static final int INC=29;
	public static final int DEC=30;
	public static final int NOP=50;
	
	private HashMap<String, Integer> goMap;
	private HashMap<String, String> env;
	private Stack<Integer> callStack;
	
	private int li,ci;
	private int type;
	private String tk;
	private String line;
	private void exeRUN(){
		long st=System.currentTimeMillis();
		exe();
		println("ok ["+(System.currentTimeMillis()-st)+" ms]");
	}
	private void exe(){
		goMap.clear();
		for(li=0;li<list.size();li++){
			ci=0;type=0;
			line = list.get(li);
			getTK();
			if(type==NUM){
				Integer go = goMap.get(tk);
				if(go!=null){
					println(li+": GoTo multiple definitions "+tk);
					System.exit(0);
				}
				go = Integer.valueOf(li);
				goMap.put(tk, go);
			}
		}
		for(li=0;li<list.size();li++){
			line = list.get(li);
			ci=0;type=0;tk=null;
			
			getTK(); if(type==NUM) getTK();
			switch(type){
			case END: exeEND(); break;
			case PRN: exePRN(); break;
			case VAR: exeVAR(); break;
			case GOT: exeGOT(); break;
			case INP: exeINP(); break;
			case CAL: exeCAL(); break;
			case RET: exeRET(); break;
			case IF:  exeIF(); break;
			case LET: exeLET(); break;
			case INC: case DEC: exeINCDEC(); break;
			case NOP: break;
			default: println(li+" invalid cmd "+type);
			}
		}
	}
	
	public void getTK(){
		while(ci<line.length() && (line.charAt(ci)==' '||line.charAt(ci)=='\t')) ci++;
		if(ci>=line.length()){
			type=EOI; tk=null; return;
		}
		if(line.charAt(ci)>='a' && line.charAt(ci)<='z'){
			tk="";
			while(ci<line.length() && (line.charAt(ci)>='a' && line.charAt(ci)<='z')){
				tk+=line.charAt(ci); ci++;
			}
			type=LIT;
			if(tk.equals("end"))       type=END;
			else if(tk.equals("prn"))  type=PRN;
			else if(tk.equals("var"))  type=VAR;
			else if(tk.equals("goto")) type=GOT;
			else if(tk.equals("inp"))  type=INP;
			else if(tk.equals("call")) type=CAL;
			else if(tk.equals("ret"))  type=RET;
			else if(tk.equals("if"))   type=IF;
			else if(tk.equals("let"))  type=LET;
			else if(tk.equals("inc"))  type=INC;
			else if(tk.equals("dec"))  type=DEC;
			else if(tk.equals("nop"))  type=NOP;
			return;
		}
		if(line.charAt(ci)=='"'){
			tk=""; ci++;
			while(ci<line.length() && line.charAt(ci)!='"'){
				tk+=line.charAt(ci); ci++;
			}ci++;
			type=STR;
			return;
		}
		if(line.charAt(ci)>='0' && line.charAt(ci)<='9'){
			tk="";
			while(ci<line.length() && (line.charAt(ci)>='0' && line.charAt(ci)<='9')){
				tk+=line.charAt(ci); ci++;
			}
			type=NUM;
			return;
		}
		char ops[]={'(',')','=','<','>','+','-','*','/'};
		for(int i=0;i<ops.length;i++){
			if(line.charAt(ci)==ops[i]){
				ci++; type=i+4; return;
			}
		}
	}

	public void exeEND(){
		li=list.size();
	}
	public void exePRN(){
		getTK();
		while(type!=EOI){
			if(type==STR) print(tk);
			else if(type==LIT) print(env.get(tk));
			else if(type==NUM) print(tk);
			else{
				println("*Invalid type "+type);
			}
			getTK();
		}
		println("");
	}
	public void exeVAR(){
		getTK();
		if(type==LIT){
			String vname = tk;
			String sval = "0";
			getTK();
			if(type==EQU){
				getTK();
				if(type==STR || type==NUM) sval=tk;
			}
			env.put(vname, sval);
		}else println(li+" expected literal "+type);
	}
	public void exeGOT(){
		getTK();
		if(type==NUM){
			Integer go=goMap.get(tk);
			if(go!=null) li=go.intValue()-1;
		}else println(li+" expected number");
	}
	public void exeINP(){
		getTK();
		if(type==STR){
			print(tk);
			getTK();
			if(type==LIT){
				String val = "";
				try{val=read();}catch(Exception e){}
				env.put(tk, val);
			}else println(li+" var for input");
		}else println(li+" prompt string");
	}
	public void exeCAL(){
		getTK();
		if(type==NUM){
			String sub=tk;
			Integer go = goMap.get(sub);
			if(go!=null){
				callStack.push(Integer.valueOf(li));
				li=go.intValue()-1;
			} else println(li+" invalid call");
		}
	}
	public void exeRET(){
		Integer go=callStack.pop();
		li=go.intValue();
	}
	public void exeIF(){
		String v1="",v2="";
		int iv1=0,iv2=0,res=0;
		getTK();
		if(type==LIT) v1=env.get(tk);
		else if(type==NUM || type==STR) v1=tk;
		getTK();
		int op=type;
		getTK();
		if(type==LIT) v2=env.get(tk);
		else if(type==NUM || type==STR) v2=tk;
		try{iv1=Integer.parseInt(v1);iv2=Integer.parseInt(v2);res=iv1-iv2;}
		catch(Exception e){res = v1.compareTo(v2);}
		getTK();
		if((op==EQU && res==0)||(op==LT && res<0)||(op==GT && res>0)){
			if(type==GOT) exeGOT();
			else if(type==PRN) exePRN();
			else if(type==LET) exeLET();
		}
	}
	public void exeLET(){
		getTK();
		if(type==LIT){
			String dest=tk;
			String v1="0",v2="0";
			int op=0;
			getTK();
			if(type==EQU){
				getTK();
				if(type==LIT) v1=env.get(tk);
				else if(type==NUM) v1=tk;
				getTK();
				if(type!=EOI){
					op=type; getTK();
					if(type==LIT) v2=env.get(tk);
					else if(type==NUM) v2=tk;
					int iv1=Integer.parseInt(v1);
					int iv2=Integer.parseInt(v2);
					switch(op){
						case ADD: iv1+=iv2; break;
						case SUB: iv1-=iv2; break;
						case MUL: iv1*=iv2; break;
						case DIV: iv1/=iv2; break;
					}
					v1=""+iv1;
				}
				env.put(dest, ""+v1);
			}else println(li+" expect =");
		}else println(li+" expect var");
	}
	public void exeINCDEC(){
		int sop=type;
		getTK();
		if(type==LIT){
			String v=env.get(tk);
			try{
				int iv=Integer.parseInt(v);
				if(sop==INC) iv++; else iv--;
				env.put(tk, ""+iv);
			}catch(Exception e){println(li+" inc/dec expects number");}
		}else println(li+" inc/dec expects var");
	}
	
	public static void main(String[] args) {
		Basic b = new Basic();
		b.println("v1.5");
		b.run();
	}
}
