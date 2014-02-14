package compress.adaphuff;
public class AdapHuff {

	
	public int LEAFS;
	public int TOTAL_NODES;
	public int NYT;
	public int EOF;
	
	public int ROOT;
	private int left[],right[],up[],freq[];
	
	//dummy stats
	int outbitcount;
	
	public AdapHuff()
	{
		LEAFS = 258;
		TOTAL_NODES = 2*LEAFS -1;
		NYT=LEAFS-2;
		EOF=LEAFS-1;
		ROOT=0;
		
		left  = new int[LEAFS];
		right = new int[LEAFS];
		up 	  = new int[TOTAL_NODES];
		freq  = new int[TOTAL_NODES];
		
		for(int i=0;i<TOTAL_NODES;i++)
		{
			freq[i]=1;
			up[i]=i/2;
		}
		for(int i=0;i<LEAFS;i++)
		{
			left[i]=i*2;
			right[i]=i*2+1;
		}
		
		//remove all this
		outbitcount=0;
	}
	
	private void update_freq(int a,int b)
	{
		do{
			freq[up[a]+LEAFS]=freq[a]+freq[b];
			a=up[a];
			if(a!=ROOT){
				if(left[up[a]] == a) b=right[up[a]];
				else b=left[up[a]];
			}
		}while(a!=ROOT);
		if(freq[ROOT]>10000){
			for(a=0;a<TOTAL_NODES;a++) freq[a] >>=1;
			System.out.println("*Scaled freq");
		}
	}
	
	private void update_model(int code) //always call this with (code-leafs)
	{
		int a,ua,uua,b,c;
		a = code;
		freq[a]++;
		if(up[a]!=ROOT){
			ua = up[a];
			if(left[ua] == a) {update_freq(a,right[ua]);}
			else {update_freq(a,left[ua]);}
			do{
				uua = up[ua];
				if(left[uua]==ua) b = right[uua];
				else b=left[uua];
				
				if(freq[a]>freq[b]){
					if(left[uua]==ua) right[uua]=a;
					else left[uua]=a;
					if(left[ua]==a){
						left[ua]=b;c=right[ua];
					}
					else{
						right[ua]=b;c=left[ua];
					}
					up[b]=ua;up[a]=uua;
					update_freq(b,c);
					a=b;
				}
				a=up[a];ua=up[a];
			}while(ua != ROOT);
		}
	}
	
	public void encode(int code)
	{
		int stack[],sp=0;
		stack = new int[300];
		int a=code;
		do{
			if(right[up[a]]==a) stack[sp++]=1;
			else stack[sp++]=0;
			a=up[a];
		}while(a!=ROOT);
		System.out.print("\nbits : ");
		while(sp>0)
		{
			sp--;
			System.out.print(""+stack[sp]);
			outbitcount++;
		}
		update_model(code);
	}
	
	public void debug_dump()
	{
		for(int i=0;i<TOTAL_NODES;i++)
		{
			System.out.println("up["+i+"]="+up[i]);
		}
		for(int i=0;i<LEAFS;i++)
		{
			System.out.println("left["+i+"]="+left[i]);
		}
		System.out.println("ROOT="+ROOT);
	}
	
	public static void main(String[] args) {
		
		AdapHuff adapHuff = new AdapHuff();
		//adapHuff.debug_dump();
		/*adapHuff.encode('a');
		adapHuff.encode('a');
		adapHuff.encode('a');
		adapHuff.encode('a');
		adapHuff.encode('b');
		adapHuff.encode('b');
		adapHuff.encode('z');
		adapHuff.encode('z');
		adapHuff.encode('a');
		adapHuff.encode('z');
		adapHuff.encode('a');*/
		String s = "qwqwqwqwqasdfqwqwqwqwerqwerjlkguqwawe wed wee wed weed";
		//String s = "Features have a manifest that provides basic information about the feature and its content. Content may include plug-ins, fragments and any other files that are important for the feature. A feature can also include other features. The delivery format for a feature is a JAR, but each included plug-in will be provided as a separate JAR A Feature is used to package a group of plug-ins together into a single installable and updatable unit";
		for(int i=0;i<s.length();i++){
			adapHuff.encode(s.charAt(i));
		}
		System.out.println("\ninbytes="+(s.length()));
		System.out.println("outbitcount="+(adapHuff.outbitcount/8));
	}

}
