package fsdriver;

import java.util.ArrayList;
import java.util.HashMap;

public class FL {

	public static void main(String[] args) {
		FL fl = new FL();
		//fl.read("(add 2 3 (mul 4 5))");
		//fl.debug=true;
		fl.eval("(add 2 3 (mul 2 2))");
		fl.eval("(seq (set x 10) (mul 2 (get x)))");
		fl.eval("(prn \"Hello World = \" (get x))");
		fl.eval("(set adder ( fun (p q) (add (get p) (get q)) ))");
		fl.eval("(prn (adder (mul 2 6) 20))");
		fl.eval("(if (gt 5 2) (prn \"5>2\"))");
		fl.eval("(if (lt 5 2) (prn \"fail\") (prn \"5<2\"))");
		fl.eval("(seq (set v 1) (while (lt (get v) 21) (seq (prn \"5 x \" (get v) \" = \" (mul 5 (get v))) (set v (add (get v) 1)))) )");
		fl.eval("(seq (set qw (list 1 2 3)) (prn (gl qw 0)))");
		fl.eval("(seq (al qw 4 5 6) (prn \"list = \" (get qw)))");
		fl.eval("(set test (fun (tt) (mul 3 (get tt))))");
		fl.eval("(prn \"3 x list = \" (fe qw test))");
		fl.eval("(seq (set alist (list add 2 3)) (prn (eval (get alist))))");
		//String x="v=1;while(v<11){prn('5 x $v = '+(5*v));v++;}";
		//fl.eval("(gt 5 2)");
		fl.test();
	}
	
	private static int TNUM=1;
	private static int TSTR=2;
	private static int TLPS=3;
	private static int TRPS=4;
	private static int TALP=5;
	private static int TFUN=6; //list 0: args 1: code, use set to assign name
	private static int TLST=7; //list
	
	private String src;
	private int si;
	private int type;
	private int ival;
	private String sval;
	
	public boolean debug=false;
	
	private HashMap<String, FCell> env;
	
	private int lex(){
		type = 0;
		while(si<src.length()){
			if(" \t\n\r\f".indexOf(src.charAt(si))>-1){
				while(" \t\n\r\f".indexOf(src.charAt(si))>-1) si++;
			}else if(src.charAt(si)=='('){
				type = TLPS; si++; return type;
			}else if(src.charAt(si)==')'){
				type = TRPS; si++; return type;
			}else if(src.charAt(si)>='0' && src.charAt(si)<='9'){
				ival=0;
				while(src.charAt(si)>='0' && src.charAt(si)<='9'){
					ival=(ival*10)+src.charAt(si)-'0'; si++;
				}
				type = TNUM; return type;
			}else if(src.charAt(si)=='"'){
				sval=""; si++;
				while(src.charAt(si)!='"'){
					sval += src.charAt(si); si++;
				}
				type=TSTR; si++; return type;
			}else if(src.charAt(si)>='a' && src.charAt(si)<='z'){
				sval="";
				while((src.charAt(si)>='a' && src.charAt(si)<='z')||src.charAt(si)=='-'){
					sval += src.charAt(si); si++;
				}
				type=TALP; return type;
			}
		}
		return 0;
	}
	public void parse(FCell cell){
		cell.type=0;
		if(type==0) return;
		if(type==TNUM){
			cell.type=TNUM;
			cell.ival=ival;
			lex();
		}else if(type==TSTR || type==TALP){
			cell.type=type;
			cell.sval=sval;
			lex();
		}else if(type==TLPS){
			cell.type=TLPS;
			cell.fval = new ArrayList<FL.FCell>();
			lex();
			while(type!=TRPS){
				FCell tcell = new FCell();
				parse(tcell);
				cell.fval.add(tcell);
			}
			lex();
		}
	}
	public FCell read(String code){
		src = code;
		si = 0;
		FCell cell = new FCell();
		lex(); parse(cell);
		if(debug) System.out.println(cell);
		return cell;
	}
	public FCell eval(String code){
		FCell cell = read(code);
		if(env==null) env = new HashMap<String, FL.FCell>();
		FCell result = eval(cell,env);
		if(debug) System.out.println("Result:"+result);
		return result;
	}
	public FCell eval(String code, HashMap<String, FCell> env){
		FCell cell = read(code);
		FCell result = eval(cell,env);
		return result;
	}
	public FCell eval(FCell cell, HashMap<String, FCell> env){
		FCell res = null;
		if(cell.type==TNUM) return cell;
		else if(cell.type==TSTR) return cell;
		else if(cell.type==TALP) return cell;
		else if(cell.type==TLPS || cell.type==TLST){
			FCell cmd = cell.fval.get(0);
			if(cmd.type!=TALP) error("*list: expected TALP");
			
			FCell fcell = env.get(cmd.sval);
			if(fcell!=null && fcell.type==TFUN){
				res = handleFunCall(cell, env);
			}else{
				if(cmd.sval.equals("add") || cmd.sval.equals("sub") || cmd.sval.equals("mul") || cmd.sval.equals("div")){
					res = handleAddSubMulDiv(cell, env);
				}else if(cmd.sval.equals("set")){
					res = handleSet(cell, env);
				}else if(cmd.sval.equals("get")){
					res = handleGet(cell, env);
				}else if(cmd.sval.equals("seq")){
					res = handleSeq(cell, env);
				}else if(cmd.sval.equals("prn")){
					res = handlePrn(cell, env);
				}else if(cmd.sval.equals("fun")){
					res = handleFunDef(cell, env);
				}else if(cmd.sval.equals("eq") || cmd.sval.equals("gt") || cmd.sval.equals("lt")){
					res = handleEqGtLt(cell, env);
				}else if(cmd.sval.equals("and") || cmd.sval.equals("or")){
					res = handleAndOr(cell, env);
				}else if(cmd.sval.equals("not")){
					res = handleNot(cell, env);
				}else if(cmd.sval.equals("if")){
					res = handleIf(cell, env);
				}else if(cmd.sval.equals("while")){
					res = handleWhile(cell, env);
				}else if(cmd.sval.equals("list")){
					res = handleList(cell, env);
				}else if(cmd.sval.equals("al")){
					res = handleAddList(cell, env);
				}else if(cmd.sval.equals("sl")){
					res = handleSetList(cell, env);
				}else if(cmd.sval.equals("gl")){
					res = handleGetList(cell, env);
				}else if(cmd.sval.equals("fe")){
					res = handleForEach(cell, env);
				}else if(cmd.sval.equals("eval")){
					res = eval(cell.fval.get(1), env);
				}
			}
		}
		return res;
	}
	
	private FCell handleList(FCell cell,HashMap<String, FCell> env){
		FCell lcell = new FCell();
		lcell.type = TLST;
		lcell.fval = new ArrayList<FL.FCell>();
		for(int i=1;i<cell.fval.size();i++) lcell.fval.add(cell.fval.get(i));
		return lcell;
	}
	
	private FCell handleAddList(FCell cell,HashMap<String, FCell> env){
		FCell lcell = cell.fval.get(1); //if(scell.type!=TALP) error("*addlist error 1");
		if(lcell.type==TALP) lcell = env.get(lcell.sval);
		if(lcell==null || lcell.type!=TLST) error("*addlist error 1");
		for(int i=2;i<cell.fval.size();i++) lcell.fval.add(cell.fval.get(i));
		return lcell;
	}
	private FCell handleSetList(FCell cell,HashMap<String, FCell> env){
		FCell lcell = cell.fval.get(1);
		if(lcell.type==TALP) lcell = env.get(lcell.sval);
		if(lcell==null || lcell.type!=TLST) error("*setlist error 1");
		FCell icell = eval(cell.fval.get(2),env);
		if(icell.type!=TNUM) error("*setlist error 2");
		FCell vcell = eval(cell.fval.get(3),env);
		lcell.fval.set(icell.ival, vcell);
		return lcell;
	}
	private FCell handleGetList(FCell cell,HashMap<String, FCell> env){
		FCell lcell = cell.fval.get(1);
		if(lcell.type==TALP) lcell = env.get(lcell.sval);
		if(lcell==null || lcell.type!=TLST) error("*getlist error 1");
		FCell icell = eval(cell.fval.get(2),env);
		if(icell.type!=TNUM) error("*setlist error 2");
		FCell vcell = lcell.fval.get(icell.ival);
		return vcell;
	}
	private FCell handleForEach(FCell cell,HashMap<String, FCell> env){
		FCell lcell = eval(cell.fval.get(1),env);
		if(lcell.type==TALP) lcell = env.get(lcell.sval);
		if(lcell==null || lcell.type!=TLST) error("*foreach error 1");
		FCell fcell = eval(cell.fval.get(2),env);
		FCell fcall = new FCell();
		fcall.fval = new ArrayList<FL.FCell>();
		fcall.fval.add(fcell); fcall.fval.add(null);
		for(int i=0;i<lcell.fval.size();i++){
			fcall.fval.set(1,lcell.fval.get(i));
			FCell result = handleFunCall(fcall, env);
			lcell.fval.set(i, result);
		}
		return lcell;
	}
	
	private FCell handleSet(FCell cell,HashMap<String, FCell> env){
		FCell var = cell.fval.get(1);
		if(var.type!=TALP) error("*set error , expected var");
		FCell val = eval(cell.fval.get(2), env);
		env.put(var.sval, val);
		return val;
	}
	
	private FCell handleGet(FCell cell,HashMap<String, FCell> env){
		FCell var = cell.fval.get(1);
		if(var.type!=TALP) error("*get error , expected var");
		FCell val = env.get(var.sval);
		if(val==null) error("*get error , var not set "+var);
		return val;
	}
	
	private FCell handleFunDef(FCell cell,HashMap<String, FCell> env){
		FCell args = cell.fval.get(1);
		FCell code = cell.fval.get(2);
		if(args.type != TLPS) error("*fun error, expected arg list");
		if(code.type != TLPS) error("*fun error, expected code list");
		FCell fcell = new FCell();
		fcell.type = TFUN;
		fcell.fval = new ArrayList<FL.FCell>();
		fcell.fval.add(args);
		fcell.fval.add(code);
		return fcell;
	}
	
	private FCell handleFunCall(FCell cell,HashMap<String, FCell> env){
		FCell fcell = env.get(cell.fval.get(0).sval);
		FCell farg  = fcell.fval.get(0);
		FCell fcode = fcell.fval.get(1);
		if( (farg.fval.size()+1) != cell.fval.size()) error("*call error, invalid number of args");
		HashMap<String, FCell> fenv = new HashMap<String, FL.FCell>();
		for(int i=0;i<farg.fval.size();i++){
			FCell a = eval(cell.fval.get(i+1),env);
			fenv.put(farg.fval.get(i).sval, a);
		}
		FCell result = eval(fcode, fenv);
		return result;
	}
	
	private FCell handleIf(FCell cell,HashMap<String, FCell> env){
		FCell cond = eval(cell.fval.get(1),env);
		if(cond.ival==1) cond=eval(cell.fval.get(2),env);
		else if(cell.fval.size()==4) cond=eval(cell.fval.get(3),env);
		return cond;
	}
	
	private FCell handleWhile(FCell cell,HashMap<String, FCell> env){
		FCell cond = cell.fval.get(1);
		FCell code = cell.fval.get(2);
		FCell res=eval(cond,env);
		while(res.ival==1){
			eval(code, env);
			res=eval(cond,env);
		}
		return res;
	}
	
	private FCell handleEqGtLt(FCell cell,HashMap<String, FCell> env){
		FCell cmd = cell.fval.get(0);
		FCell x = eval(cell.fval.get(1),env);
		FCell y = eval(cell.fval.get(2),env);
		FCell z = new FCell();
		z.type = TNUM;
		z.ival=0;
		if(x.type == TNUM && y.type == TNUM){
			if(cmd.sval.equals("eq") && (x.ival == y.ival)) z.ival=1;
			else if(cmd.sval.equals("gt") && (x.ival > y.ival)) z.ival=1;
			else if(cmd.sval.equals("lt") && (x.ival < y.ival)) z.ival=1;
		}else if(x.type == TSTR && y.type == TSTR){
			if(cmd.sval.equals("eq") && (x.sval.compareTo(y.sval)==0)) z.ival=1;
			else if(cmd.sval.equals("gt") && (x.sval.compareTo(y.sval)>0)) z.ival=1;
			else if(cmd.sval.equals("lt") && (x.sval.compareTo(y.sval)<0)) z.ival=1;
		}else error("*eq error , incompatible types");
		return z;
	}
	
	private FCell handleAndOr(FCell cell,HashMap<String, FCell> env){
		FCell cmd = cell.fval.get(0);
		FCell x = eval(cell.fval.get(1),env);
		FCell y = eval(cell.fval.get(2),env);
		FCell z = new FCell();
		z.type = TNUM; z.ival=0;
		if(x.type == TNUM && y.type == TNUM){
			if(cmd.sval.equals("and") && (x.ival==1) && (y.ival==1)) z.ival=1;
			else if(cmd.sval.equals("or") && ((x.ival==1) || (y.ival==1))) z.ival=1;
		}else if(x.type == TSTR && y.type == TSTR){
			if(cmd.sval.equals("and") && (x.sval.equals("true")) && (y.sval.equals(true))) z.ival=1;
			else if(cmd.sval.equals("or") && (x.sval.equals("true") || y.sval.equals(true))) z.ival=1;
		}else error("*andor error, incompatible types (only num|str allowed)");
		return z;
	}
	
	private FCell handleNot(FCell cell,HashMap<String, FCell> env){
		FCell x = eval(cell.fval.get(1),env);
		FCell z = new FCell();
		z.type=TNUM; z.ival=0;
		if(x.type==TNUM && x.ival!=1) z.ival=1;
		else if(x.type==TSTR && !x.sval.equals("true")) z.ival=1;
		return z;
	}
	
	private FCell handleSeq(FCell cell,HashMap<String, FCell> env){
		FCell res=null;
		for(int i=1;i<cell.fval.size();i++){
			res = eval(cell.fval.get(i),env);
		}
		return res;
	}
	
	private FCell handleAddSubMulDiv(FCell cell,HashMap<String, FCell> env){
		FCell cmd = cell.fval.get(0);
		FCell tcell = eval(cell.fval.get(1),env);
		if(tcell.type!=TNUM) error("*math error "+tcell);
		FCell res = new FCell();
		res.type=TNUM;
		res.ival = tcell.ival;
		for(int i=2;i<cell.fval.size();i++){
			FCell icell = eval(cell.fval.get(i),env);
			if(icell.type != TNUM) error("*expected num, got "+icell);
			if(cmd.sval.equals("add"))	res.ival += icell.ival;
			else if(cmd.sval.equals("sub"))	res.ival -= icell.ival;
			else if(cmd.sval.equals("mul"))	res.ival *= icell.ival;
			else if(cmd.sval.equals("div"))	res.ival /= icell.ival;
		}
		return res;
	}
	
	private FCell handlePrn(FCell cell,HashMap<String, FCell> env){
		FCell tcell=new FCell();
		tcell.type=TSTR;
		tcell.sval="";
		for(int i=1;i<cell.fval.size();i++){
			FCell icell = eval(cell.fval.get(i),env);
			if(icell.type==TSTR) tcell.sval += icell.sval;
			else if(icell.type==TNUM) tcell.sval += icell.ival;
			else if(icell.type==TLST) tcell.sval += icell.fval;
			else error("*prn error expected str or num");
		}
		System.out.println(tcell.sval);
		return tcell;
	}
	
	public class FCell{
		public int type;
		public int ival;
		public String sval;
		public ArrayList<FCell> fval;
		public String toString() {
			if(type==TNUM) return "Num:"+ival;
			if(type==TSTR) return "Str:"+sval;
			if(type==TALP) return "Alp:"+sval;
			if(type==TLPS) return "Lst:"+fval;
			if(type==TFUN) return "Function: {"+fval+"}";
			if(type==TLST) return "List:"+fval;
			return "NULL";
		}
	}
	
	public static void error(String msg){
		System.out.println(msg); System.exit(0);
	}
	
	public class LObj{
		
	}
	public class LCell extends LObj{
		public LObj a,b;
		public String toString() {
			return "["+a+","+b+"]";
		}
	}
	public class LAtom extends LObj{
		public int s;
		public String toString() {
			return "("+s+")";
		}
	}
	public void test(){
		LAtom a = new LAtom();
		a.s=1;
		LAtom b = new LAtom();
		b.s=5;
		LAtom c = new LAtom();
		c.s=10;
		LCell e = new LCell();
		e.a = c;
		e.b = null;
		LCell f = new LCell();
		f.a = b;
		f.b = e;
		LCell g = new LCell();
		g.a = a;
		g.b = f;
		System.out.println(g);
	}
}
