package compiler.nfa;

public class NFATool3 {

	private String regex;
	private int regexInd;
	
	private int t_from[],t_to[],t_input[];
	private int t_idx;
	
	private int nfastack[];
	private int nfastackidx;
	
	private int statecounter=0;
	
	private static final int EPSILON=-1;
	
	public NFATool3() {
		t_idx=0;
		t_from = new int[100];
		t_to = new int[100];
		t_input = new int[100];
		
		nfastack = new int[100]; //hold state
		nfastackidx=0;
		
		statecounter=0;
	}
	
	private int getNextState()
	{
		return statecounter++;
	}
	
	private void addTransition(int _input,int _from, int _to)
	{
		t_input[t_idx] = _input;
		t_from[t_idx]  = _from;
		t_to[t_idx]  = _to;
		t_idx++;
	}
	
	private void pushNFA(int _nfa)
	{
		nfastack[nfastackidx++]=_nfa;
	}
	private int popNFA()
	{
		return nfastack[--nfastackidx];
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
					(regex.charAt(regexInd)>='0' && regex.charAt(regexInd)<='9') ||
					regex.charAt(regexInd)==' ' || regex.charAt(regexInd)=='@')
				return true;
			else return false;
		}
		return (regex.charAt(regexInd)==tok.charAt(0));
	}
	
	private boolean isAlphaNumeric(int tok)
	{
		if((tok>='a' && tok<='z') ||
				(tok>='A' && tok<='Z') ||
				(tok>='0' && tok<='9') ||
				tok==' ')
			return true;
		return false;
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
	{//System.out.println("parse_regex");
		//while(regexInd<regex.length())
		//{
			p2();
			while(match("@") || match("(")){
				p2();
				int t2nfa1 = popNFA();
				int t2nfa2 = popNFA();
				int t1nfa1 = popNFA();
				int t1nfa2 = popNFA();
				addTransition(EPSILON,t1nfa2,t2nfa1);
				pushNFA(t2nfa2);
				pushNFA(t1nfa1);
			}
		//}
	}
	
	public void p2()
	{//System.out.println("p2");
		p3();
		while(match("|")){
			advance();
			p3();
			//int nfa1 = getNextState();
			//int nfa2 = getNextState();
			int t2nfa1 = popNFA();
			int t2nfa2 = popNFA();
			int t1nfa1 = popNFA();
			int t1nfa2 = popNFA();
			//addTransition(EPSILON,nfa1,t1nfa1);
			//addTransition(EPSILON,nfa1,t2nfa1);
			//addTransition(EPSILON,t1nfa2,nfa2);
			//addTransition(EPSILON,t2nfa2,nfa2);
			
			addTransition(EPSILON,t1nfa1,t2nfa1);
			addTransition(EPSILON,t2nfa2,t1nfa2);
			pushNFA(t1nfa2);
			pushNFA(t1nfa1);
		}
	}
	
	public void p3()
	{//System.out.println("p3");
		parse_atom();
		if(match("?") || match("*") || match("+")){
			//o2();
			int nfa1 = 0;
			int nfa2 = 0;
			if(match("?")){
				int tnfa1=popNFA();
				int tnfa2=popNFA();
				addTransition(EPSILON,tnfa1,tnfa2);
				nfa1 = tnfa1;
				nfa2 = tnfa2;
			}
			else if(match("+")){
				int tnfa1=popNFA();
				int tnfa2=popNFA();
				addTransition(EPSILON,tnfa2,tnfa1);
				nfa1 = tnfa1;
				nfa2 = tnfa2;
			}
			else if(match("*")){
				int tnfa1=popNFA();
				int tnfa2=popNFA();
				addTransition(EPSILON,tnfa1,tnfa2);
				addTransition(EPSILON,tnfa2,tnfa1);
				nfa1 = tnfa1;
				nfa2 = tnfa2;
			}
			pushNFA(nfa2);
			pushNFA(nfa1);
			advance();
		}
		
	}
	
	public void o2()
	{//System.out.println("o2");
		if(match("?") || match("*") || match("+")){
			System.out.println("got *");
			advance();
		}
	}
	
	public void parse_atom()
	{//System.out.println("p_atom");
		if(match("@")){
			System.out.println("@peek="+(char)peek());
			
			int nfa1 = getNextState();
			int nfa2 = getNextState();
			addTransition(peek(),nfa1,nfa2);
			pushNFA(nfa2);
			pushNFA(nfa1);
			
			advance();
		}
		else if(match("(")){
			advance();
			//p2();
			parse_regex();
			if(!match(")")) System.out.println(regexInd+":missing )");
			else advance();
		}
	}
	
	public void print_Transitions()
	{
		//this will make it un usable
		//System.out.println("Start State = "+popNFA());
		//System.out.println("End   State = "+popNFA());
		System.out.println("Transition table");
		System.out.println("INPUT\tFROM\tTO");
		System.out.println("----------------------------------------------");
		for(int i=0;i<t_idx;i++){
			System.out.println(""+t_input[i]+"\t"+t_from[i]+"\t"+t_to[i]);
		}
	}
	
	//return number of elements added to array
	private int find_Transition(int _input,int _from,int _list[])
	{
		int count=0;
		for(int i=0;i<t_idx;i++){
			if((t_input[i]==_input || t_input[i]==EPSILON) && t_from[i]==_from){
				_list[count++]=t_to[i];
			}
			else if(t_input[i]=='@' && isAlphaNumeric(_input) && t_from[i]==_from){
				_list[count++]=t_to[i];
			}
		}
		//System.out.println("count="+count);
		//for(int i=0;i<count;i++)
		//	System.out.println("f_t>"+_list[i]);
		return count;
	}
	
	private boolean contains(int _list[],int _len,int value)
	{
		for(int i=0;i<_len;i++){
			if(_list[i]==value) return true;
		}
		return false;
	}
	
	private int closure(int _input,int _stk[],int _len)
	{
		int tmp[] = new int[10];
		boolean changed=true;
			for(int i=0;i<_len;i++){
				int count = find_Transition(_input,_stk[i],tmp);
				//System.out.println("count="+count);
				for(int k=0;k<count;k++){
					if(!contains(_stk,_len,tmp[k])){
						_stk[_len++]=tmp[k];
						//System.out.println("closure add="+tmp[k]);
					}
					//System.out.println(_len);
				}
			}
		return _len;
	}
	
	public boolean parse(String _string)
	{
		System.out.println("parse string");
		int stk[] = new int[50];
		int tmpstk[] = new int[50];
		int tmp[] = new int[50];
		int stkidx=0,tmpstkidx=0;
		int nfa1 = popNFA();
		int nfa2 = popNFA();
		pushNFA(nfa2);
		pushNFA(nfa1);
		
		stk[stkidx++]=nfa1;
		stkidx=closure(EPSILON,stk,stkidx);
//		print stacks for checking
		System.out.print("stk =");
		for(int s=0;s<stkidx;s++){
			System.out.print(" "+stk[s]);
		}
		System.out.println();
		
		System.out.println("Start state = "+nfa1);
		System.out.println("End   state = "+nfa2);
		for(int i=0;i<_string.length();i++){//get chars
			
			System.out.println("char : "+(char)_string.charAt(i));
			//closure on the states
			tmpstkidx=0;
			for(int j=0;j<stkidx;j++){//fill stack
				
				int count = find_Transition(_string.charAt(i),stk[j],tmp);
				for(int k=0;k<count;k++){
					if(!contains(tmpstk,tmpstkidx,tmp[k]))
						tmpstk[tmpstkidx++]=tmp[k];
				}
			}
			//closure
			tmpstkidx=closure(_string.charAt(i),tmpstk,tmpstkidx);
			//print stacks for checking
			System.out.print("tmpstk =");
			for(int s=0;s<tmpstkidx;s++){
				System.out.print(" "+tmpstk[s]);
			}
			System.out.println();
			//after closure
			//exchange stacks
			{
				int _tmp[] = stk;
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
		for(int i=0;i<stkidx;i++){
			if(stk[i]==nfa2){
				System.out.println("parse success : found match");
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		NFATool3 tool = new NFATool3();
//		tool.compile("(a|b)*abb(c|d)");
//		tool.print_Transitions();
//		tool.parse("abababbaaaaabbd");
		tool.compile("((H|h)el+o+ (W|w|V|v)orld+)|((G|g)ood+ (B|b)ye ((W|w)orld)?)");
		tool.print_Transitions();
		tool.parse("hello world");
		tool.parse("Hellllo Worlddd");
		tool.parse("good bye world");
	}

}
