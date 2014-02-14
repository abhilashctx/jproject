package compiler.nfa;

public class NFATool {

	private String regex;
	
	private int regexInd;
	
	private NFASTATE nfastack[];
	
	private int nfastackInd;
	
	public NFATool() {
		nfastack = new NFASTATE[20];
		nfastackInd=0;
	}
	
	private void pushNFA(NFASTATE _nfa)
	{
		nfastack[nfastackInd++]=_nfa;
	}
	private NFASTATE popNFA()
	{
		return nfastack[--nfastackInd];
	}
	
	//init the regex tokenizer
	private void init_lex(String _regex)
	{
		regex = _regex;
		regexInd=0;
	}
	private boolean match(String tok)
	{
		if(regexInd>=regex.length()) return false;
		if(tok.equals("@")){
			if((regex.charAt(regexInd)>='a' && regex.charAt(regexInd)<='z') ||
					(regex.charAt(regexInd)>='A' && regex.charAt(regexInd)<='Z') ||
					(regex.charAt(regexInd)>='0' && regex.charAt(regexInd)<='9'))
				return true;
			else return false;
		}
		return (regex.charAt(regexInd)==tok.charAt(0));
	}
	private void advance()
	{
		regexInd++;
	}
	private int peek()
	{
		return regex.charAt(regexInd);
	}
	
	public void compile(String _regex)
	{
		init_lex(_regex);
		
		parse_regex();
		
		System.out.println("regexInd="+regexInd);
	}
	
	public void parse_regex()
	{System.out.println("parse_regex");
		while(regexInd<regex.length())
		{
			p2();
			while(match("@")){
				p2();
				NFASTATE t2nfa1 = popNFA();
				NFASTATE t2nfa2 = popNFA();
				NFASTATE t1nfa1 = popNFA();
				NFASTATE t1nfa2 = popNFA();
				t1nfa2.c1 = NFASTATE.EPSILON;
				t1nfa2.o1 = t2nfa1;
				pushNFA(t2nfa2);
				pushNFA(t1nfa1);
			}
		}
	}
	
	public void p2()
	{System.out.println("p2");
		p3();
		while(match("|")){
			advance();
			p3();
			NFASTATE nfa1 = new NFASTATE();
			NFASTATE nfa2 = new NFASTATE();
			NFASTATE t2nfa1 = popNFA();
			NFASTATE t2nfa2 = popNFA();
			NFASTATE t1nfa1 = popNFA();
			NFASTATE t1nfa2 = popNFA();
			nfa1.c1 = NFASTATE.EPSILON;
			nfa1.o1 = t1nfa1;
			nfa1.c2 = NFASTATE.EPSILON;
			nfa1.o2 = t2nfa1;
			t1nfa2.c1 = NFASTATE.EPSILON;
			t1nfa2.o1 = nfa2;
			t2nfa2.c1 = NFASTATE.EPSILON;
			t2nfa2.o1 = nfa2;
			pushNFA(nfa2);
			pushNFA(nfa1);
		}
	}
	
	public void p3()
	{System.out.println("p3");
		parse_atom();
		if(match("?") || match("*") || match("+")){
			//o2();
			NFASTATE nfa1 = new NFASTATE();
			NFASTATE nfa2 = new NFASTATE();
			if(match("?")){
				NFASTATE tnfa1=popNFA();
				NFASTATE tnfa2=popNFA();
				nfa1.c1 = NFASTATE.EPSILON;
				nfa1.o1 = tnfa1;
				tnfa2.c1 = NFASTATE.EPSILON;
				tnfa2.o1 = nfa2;
				nfa1.c2 = NFASTATE.EPSILON;
				nfa1.o2 = nfa2;
			}
			else if(match("+")){
				NFASTATE tnfa1=popNFA();
				NFASTATE tnfa2=popNFA();
				nfa1.c1 = NFASTATE.EPSILON;
				nfa1.o1 = tnfa1;
				tnfa2.c1 = NFASTATE.EPSILON;
				tnfa2.o1 = nfa2;
				tnfa2.c2 = NFASTATE.EPSILON;
				tnfa2.o2 = tnfa1;
			}
			else if(match("*")){
				NFASTATE tnfa1=popNFA();
				NFASTATE tnfa2=popNFA();
				nfa1.c1 = NFASTATE.EPSILON;
				nfa1.o1 = tnfa1;
				tnfa2.c1 = NFASTATE.EPSILON;
				tnfa2.o1 = nfa2;
				tnfa2.c2 = NFASTATE.EPSILON;
				tnfa2.o2 = tnfa1;
				nfa1.c2 = NFASTATE.EPSILON;
				nfa1.o2 = nfa2;
			}
			pushNFA(nfa2);
			pushNFA(nfa1);
			advance();
		}
		
	}
	
	public void o2()
	{System.out.println("o2");
		if(match("?") || match("*") || match("+")){
			System.out.println("got *");
			advance();
		}
	}
	
	public void parse_atom()
	{System.out.println("p_atom");
		if(match("@")){
			System.out.println("peek="+(char)peek());
			
			NFASTATE nfa1 = new NFASTATE();
			NFASTATE nfa2 = new NFASTATE();
			nfa1.c1 = peek();
			nfa1.o1 = nfa2;
			pushNFA(nfa2);
			pushNFA(nfa1);
			
			advance();
		}
		else if(match("(")){
			advance();
			p2();
			if(!match(")")) System.out.println(regexInd+":missing )");
			else advance();
		}
	}
	
	
	public void parse(String _string)
	{
		System.out.println("parse string");
		NFASTATE stk[] = new NFASTATE[50];
		NFASTATE tmpstk[] = new NFASTATE[50];
		int stkidx=0,tmpstkidx=0;
		NFASTATE nfa1 = popNFA();
		NFASTATE nfa2 = popNFA();
		
		stk[stkidx++]=nfa1;
		for(int i=0;i<_string.length();i++){//get chars
			
			System.out.println("char : "+(char)_string.charAt(i));
			//closure on the states
			tmpstkidx=0;
			for(int j=0;j<stkidx;j++){//fill stack
				
				if(_string.charAt(i)==stk[j].c1 || stk[j].c1==NFASTATE.EPSILON){
					if(stk[j].o1!=null)
						tmpstk[tmpstkidx++]=stk[j].o1;
				}
				else if(_string.charAt(i)==stk[j].c2 || stk[j].c2==NFASTATE.EPSILON){
					if(stk[j].o2!=null)
						tmpstk[tmpstkidx++]=stk[j].o2;
				}
			}
			//after closure
			//exchange stacks
			{
				NFASTATE _tmp[] = stk;
				stk = tmpstk;
				tmpstk = _tmp;
				int _tmpidx = stkidx;
				stkidx = tmpstkidx;
				tmpstkidx = _tmpidx;
			}
			if(stkidx==0){
				System.out.println("No transitions on char : "+(char)_string.charAt(i));
				break;
			}
		}
		System.out.println("stkidx="+stkidx+" tmpsktidx="+tmpstkidx);
		for(int i=0;i<tmpstkidx;i++){
			if(tmpstk[i]==nfa2 || tmpstk[i]==nfa1){
				System.out.println("parse success : found match");
				break;
			}
		}
		System.out.println("done");
	}

	public static void main(String[] args) {
		NFATool tool = new NFATool();
		tool.compile("(a|b)*cd");
		tool.parse("abcde");
	}

	
	private class NFASTATE{
		public static final int EPSILON=-1;
		//public static final int NOLINK=-2;
		public int c1,c2;
		public NFASTATE o1,o2;
		//public boolean isFinal;
	}
}
