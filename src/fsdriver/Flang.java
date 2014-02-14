package fsdriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Flang {
	
	public static void main(String[] args) {
		Flang flang = new Flang();
		//System.out.println(flang.eval("(add 2 3 (mul (add 2 2) 5))"));
		System.out.println(flang.eval("(seq (prn (add 2 3 (mul (add 2 2) 5))) (fun sqr (mul 4 4)) (sqr))"));
	}
	
	public static void error(String msg){
		System.out.println(msg);
		System.exit(0);
	}
	private StringTokenizer stk;
	
	public String getToken(StringTokenizer stk){
		while(stk.hasMoreTokens()){
			String token = stk.nextToken();
			if(" \t\n\r\f".indexOf(token)==-1){
				//System.out.println(token);
				return token;
			}
		}
		return null;
	}
	public void next(){
		String token = getToken(stk);
		if(token == null) global.type=0;
		else if(token.equals("(")) global.type=1;
		else if(token.equals(")")) global.type=2;
		else if(token.charAt(0)>='0' && token.charAt(0)<='9'){
			global.ival = Integer.parseInt(token);
			global.type=3;
		}else if(token.charAt(0)>='a' && token.charAt(0)<='z'){
			global.sval = token;
			global.type=4;
		}else{
			System.out.println("unknown token "+token);
			global.type=0;
		}
	}
	public void build(ArrayList<SCell> slist){
		if(global.type==0) return;
		if(global.type==1) next(); else error("expected (");
		while(global.type!=2){
			SCell cell = new SCell();
			if(global.type==3){
				cell.type='i';
				cell.ival=global.ival;
				slist.add(cell);
				next();
			}else if(global.type==4){
				cell.type='s';
				cell.sval=global.sval;
				slist.add(cell);
				next();
			}else if(global.type==1){
				cell.type='l';
				cell.lval = new ArrayList<SCell>();
				build(cell.lval);
				slist.add(cell);
			}
		}
		if(global.type==2) next(); else error("expected )");
	}
	public ArrayList<SCell> read(String code){
		stk = new StringTokenizer(code," \t\n\r\f()",true);
		global = new SCell();
		ArrayList<SCell> slist = new ArrayList<SCell>();
		next();
		build(slist);
		System.out.println(slist);
		return slist;
	}
	public SCell eval(String code){
		ArrayList<SCell> slist = read(code);
		HashMap<String, SCell> env = new HashMap<String, SCell>();
		return eval(slist,env);
	}
	public SCell eval(ArrayList<SCell> slist,HashMap<String, SCell> env){
		SCell r = new SCell();
		SCell cmd = slist.get(0);
		if(cmd.type=='s'){
			if(cmd.sval.equals("add")){
				r.type='i'; r.ival=0;
				for(int i=1;i<slist.size();i++){
					SCell arg = slist.get(i);
					if(arg.type=='i') r.ival+=arg.ival;
					else if(arg.type=='l'){
						r.ival += eval(arg.lval,env).ival;
					}
					else{
						System.out.println("expected int, ignore:"+arg);
					}
				}
			}else if(cmd.sval.equals("mul")){
				r.type='i'; r.ival=1;
				for(int i=1;i<slist.size();i++){
					SCell arg = slist.get(i);
					if(arg.type=='i') r.ival*=arg.ival;
					else if(arg.type=='l'){
						r.ival *= eval(arg.lval,env).ival;
					}
					else{
						System.out.println("expected int, ignore:"+arg);
					}
				}
			}else if(cmd.sval.equals("seq")){
				for(int i=1;i<slist.size();i++){
					r = eval(slist.get(i).lval,env);
				}
			}else if(cmd.sval.equals("fun")){
				SCell fname = slist.get(1);
				SCell fcode = slist.get(2);
				env.put(fname.sval, fcode);
			}else if(cmd.sval.equals("prn")){
				r = eval(slist.get(1).lval,env);
				System.out.println(r);
			}else if(cmd.sval.equals("set")){
				SCell var = slist.get(1);
				if(var.type!='s') error("set error : expected string : "+var);
				r = eval(slist.get(2).lval,env);
				env.put(var.sval, r);
			}else if(cmd.sval.equals("get")){
				SCell var = slist.get(1);
				if(var.type!='s') error("set error : expected string : "+var);
				r = env.get(var.sval);
			}else if(env.get(cmd.sval)!=null){
				r = eval(env.get(cmd.sval).lval,env);
			}
		}else if(cmd.type=='i'){
			r = cmd;
		}
		return r;
	}
	
	public class SCell{
		public int type;
		public int ival;
		public String sval;
		public ArrayList<SCell> lval;
		public String toString() {
			String tos="";
			if(type=='i') tos="Int:"+ival;
			else if(type=='s') tos="Str:"+sval;
			else if(type=='l') tos="list:"+lval;
			else if(type==0) tos="NULL";
			return tos;
		}
	}

	public SCell global;
}
