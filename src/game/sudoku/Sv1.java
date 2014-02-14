package game.sudoku;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Sv1 {
	
	int b[];
	boolean bl[][];
	Random rnd;
	int undo[];
	int undoi;
	public void shell(){
		ln("Sudoku 1.2");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		p("init");
		p("help");
		initInsult();
		while(true){
			l("$>");
			try {
				String s = br.readLine();
				s=p(s);
				if(s.equals("bye")) break;
				else ln(s);
				String won=p("check");
				if(won.equals("Won")) ln("Congrats!!! You Completed The Puzzle, now get back to work ;)");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public String p(String s){
		if(s.equals("exit")){return "bye";}//exit
		else if(s.equals("init")){init();}//new
		else if(s.startsWith("new")){return newg(s);}//new L
		else if(s.equals("show")){return showg(b);}//show
		else if(s.startsWith("set")){return set(s);}//set x y v
		else if(s.equals("undo")){return undoit();}//undo
		else if(s.startsWith("cls")){return clear(s);}//cls x y
		else if(s.equals("check")){return check();}//check
		else if(s.startsWith("solve")){return solve(s);}//solve x y
		else if(s.equals("help")){help();}
		else return "*What???";
		return "";
	}
	public void help(){
		ln("List of commands\n" +
		   " anything inside [] is optional\n"+
		   " new [level]   -- creates new level , default level=1 (ex: new 5)\n"+
		   " show          -- display current board\n"+
		   " set x y value -- set value at position x,y(ex: set 0 2 5)\n"+
		   " solve x y     -- Munnabhai will try to find value at x,y\n"+
		   " undo          -- Undo the last set value\n"+
		   " cls x y       -- clear [x,y] position (ex: cls 1 0)\n"+
		   " exit          -- exit ... You want to know what it does ?\n"+
		   " help          -- show this help"
		);
	}
	public void init(){
		b=new int[81];
		bl=new boolean[81][9];
		undo=new int[256];
		undoi=0;
		rnd=new Random(System.currentTimeMillis());
		newg("new");
	}
	public String undoit(){
		if(undoi==0) return "*Nothing to Undo";
		int xy=undo[--undoi];int v=undo[--undoi];
		b[xy]=v;int x=xy/9;int y=xy-x*9;
		return "Undo: ["+x+","+y+"] replace value "+
				(v==99?"(empty)":""+(v+1));
	}
	private void save(int xy,int v){
		if(undoi>=256){
			for(int i=50;i<256;i++) undo[i-50]=undo[i];
			undoi-=50;
		}
		undo[undoi++]=v;undo[undoi++]=xy;
	}
	public String clear(String s){
		String ss[]=s.split(" ");
		if(ss.length==3){
			int x=ss[1].charAt(0)-'0';
			int y=ss[2].charAt(0)-'0';
			if(x<0 || x>8) return "*Value should be in range [0..8]";
			if(y<0 || y>8) return "*Value should be in range [0..8]";
			int xy=x*9+y;
			if(b[xy]!=99){
				save(xy,b[xy]);
				b[xy]=99;
			}
			return "Cleared ["+x+","+y+"]";
		}else return "*Invalid args cls row col";
	}
	public String solve(String s){
		String ss[]=s.split(" ");
		if(ss.length==3){
			int x=ss[1].charAt(0)-'0';
			int y=ss[2].charAt(0)-'0';
			if(x<0 || x>8) return "*Value should be in range [0..8]";
			if(y<0 || y>8) return "*Value should be in range [0..8]";
			int v=solve(x*9+y);
			if(v==99) return "*Cannot solve, you made a mistake somewhere";
			return rndInsult()+"\nAt ["+x+","+y+"] value is "+(v+1);
		}else return "*Invalid args solve x y";
	}
	public int solve(int xy){
		int cb[] = new int[81];
		for(int i=0;i<81;i++) cb[i]=b[i];
		int sr=99;int e=-1;
		for(int i=0;i<81;i++){
			if(cb[i]==99){e=i;break;}
		}
		if(e>-1){sr=slv(cb,e);}
		if(sr==99) return 99;
		return cb[xy];
	}
	public int slv(int cb[],int xy){
		for(int i=0;i<9;i++){
			cb[xy]=i;
			int r=failed(cb,xy);
			if(r>0) continue;
			int e=-1;
			for(int j=xy;j<81;j++){
				if(cb[j]==99){e=j;break;}
			}
			if(e==-1){//solved
				return i;
			}else{
				int sr=slv(cb,e);
				if(sr!=99) return i;
			}
		}
		cb[xy]=99;return 99;//fail
	}
	public String check(){
		for(int i=0;i<9;i++)for(int j=0;j<9;j++)
			if(b[i*9+j]==99) return "...";
		return "Won";
	}
	public String set(String s){
		String msg="";
		String ss[]=s.split(" ");
		if(ss.length==4){
			int x = ss[1].charAt(0)-'0';
			int y = ss[2].charAt(0)-'0';
			int v = ss[3].charAt(0)-'0';
			if(x<0 || x>8) return "*Value should be in range [0..8]";
			if(y<0 || y>8) return "*Value should be in range [0..8]";
			if(v<1 || v>9) return "*Value should be in range [1..9]";
			int xy = x*9+y;
			int t = b[xy]; //save prev
			b[xy]=v-1;
			int r = failed(b,xy);
			if(r>0) b[xy]=t;
			if(r==0){
				if(b[xy]!=t)save(xy,t);
				return "Value:"+v+" set at position["+x+","+y+"]";
			}
			else if(r==1) return "*Value exists in coloumn";
			else if(r==2) return "*Value exists in row";
			else if(r==3) return "*Value exists in 3x3";
		}else return "*Invalid number of args set x y value";
		return msg;
	}
	public String newg(String s){
		int level = 1;
		String ss[] = s.split(" ");
		if(ss.length==2){
			int t = ss[1].charAt(0)-'0';
			if(t>=1 && t<=5) level=t;
		}
		for(int i=0;i<9;i++)for(int j=0;j<9;j++)b[i*9+j]=99;
		for(int i=0;i<81;i++)for(int j=0;j<9;j++)bl[i][j]=true;
		undoi=0;
		g(0);gd(level);
		return "new game difficulty:"+level;
	}
	public String showg(int b[]){
		String bs="board\n   ";
		for(int i=0;i<9;i++) bs+=i+" "; bs+="\n";
		for(int i=0;i<9;i++){
			bs+=i+" |";
			for(int j=0;j<9;j++){
				if(b[i*9+j]!=99) bs+=(b[i*9+j]+1);
				else bs+=" ";
				bs += (j==2 || j==5?""+(char)186:"|");
			}
			bs+="\n";
			if(i==2 || i==5){
				bs+="  ";
				for(int k=0;k<19;k++) bs+="=";
				bs+="\n";
			}
		}
		return bs;
	}
	public boolean g(int xy){
		int r;
		if(xy>=81) return true;
		while(true){
			r=pik(xy);
			if(r==-1){b[xy]=99;klear(xy);return false;}
			bl[xy][r]=false;
			b[xy]=r;
			if(failed(b,xy)>0) continue;
			if(g(xy+1)) break;
		}
		return true;
	}
	public int failed(int b[],int xy){
		int x = xy/9;int y = xy-x*9;
		for(int i=0;i<9;i++){
			int iy = i*9+y;
			if(iy==xy) continue;
			if(b[iy]==b[xy]) return 1;
		}
		for(int i=0;i<9;i++){
			int xi = x*9+i;
			if(xi==xy) continue;
			if(b[xi]==b[xy]) return 2;
		}
		int xr = (x/3)*3;
		int yr = (y/3)*3;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int ij=(xr+i)*9+(yr+j);
				if(ij==xy) continue;
				if(b[ij]==b[xy]) return 3;
			}
		}
		return 0;
	}
	public int pik(int xy){
		int r=rnd.nextInt(9);
		if(bl[xy][r]) return r;
		while(r>=0){if(bl[xy][r]) return r; else r--;}r=0;
		while(r<9){if(bl[xy][r]) return r; else r++;}
		return -1;
	}
	public void klear(int xy){
		for(int i=0;i<9;i++) bl[xy][i]=true;
	}
	public void gd(int level){
		if(level==0) level=1;
		level=(level+5)*10;
		int c=(81*level)/100;
		for(int i=0;i<c;i++){int xy=rnd.nextInt(81);b[xy]=99;}
	}
	public static void main(String[] args) {
		Sv1 t=new Sv1();
		t.shell();
	}
	public static void ln(String s){
		System.out.println(s);
	}
	public static void l(String s){
		System.out.print(s);
	}
	
	ArrayList<String> keys;
	HashMap<String, ArrayList<String>> values;
	public String rndInsult(){
		int ikey = rnd.nextInt(keys.size());
		ArrayList<String> data=new ArrayList<String>();
		String t[] = keys.get(ikey).split(" ");
		data.add(t[0]);data.add(t[1]);
		int rdata = rnd.nextInt(20)+20;
		for(int i=0;i<rdata;i++){
			String skey = data.get(data.size()-2)+" "+data.get(data.size()-1);
			ArrayList<String> jvalue = values.get(skey);
			if(jvalue == null) break;
			String sval = jvalue.get(rnd.nextInt(jvalue.size()));
			data.add(sval);
		}
		String sdata="";
		for(int i=0;i<data.size();i++) sdata+= data.get(i)+" ";
		return sdata;
	}
	public void initInsult(){

		String s[]={
				"Solve it yourself dude !!!",
				"Cheater ... hahaha",
				"Such simple thing you can't solve?",
				"That is shamefull",
				"You need help with this ???",
				"Perhaps this is too challenging for you?",
				"wow..you really need my help",
				"wow..you really need some help",
				"Arey Circuit! Itna bhi nahi malum",
				"Arey Circuit! kya baat hai",
				"kya baat hai beja khali hai",
				"Itna mushkil hai kya",
				"This is too easy...",
				"This is too hard?",
				"This is hard isit",
				"This game is beyond you",
				"This is to easy for me",
				"This is very very easy",
				"If you can't solve this you need some sleep",
				"If you can't solve this then shame on you",
				"You sure you have brains?",
				"That is so shamefull",
				"Solve this you cannot",
				"It looks like your face caught on fire and someone tried to put it out with a hammer.",
				"If I wanted to kill myself I'd climb your ego and jump to your IQ.",
				"I don't exactly hate you, but if you were on fire and I had water, I'd drink it.",
				"thought crossed your mind? Must have been a long and lonely journey.",
				"If assholes could fly, this place would be an airport!",
				"You are proof that God has a sense of humor.",
				"Well I could agree with you, but then we'd both be wrong.",
				"Shut up you'll never be the man your mother is.",
				"You are proof that God has a sense of humor.",
				"It's better to let someone think you are an Idiot than to open your mouth and prove it.",
				"You look like a before picture.",
				"Shock me, say something intelligent.",
				"Hey you have somthing on your chin.",
				"Do you still love nature, despite what it did to you?",
				"Am I getting smart with you?",
				"Oh my God, look at you.",
				"I'll never forget the first time we met, although I'll keep trying.",
				"Everyone who ever loved you was wrong.",
				"You couldn't pour water out of a boot if the instructions were on the heel.",
				"You must have a very low opinion of people if you think they are your equals.",
				"Why don't you slip into something more comfortable like a coma.",
				"Are you always an idiot, or just when I'm around?",
				"You are proof that evolution CAN go in reverse.",
				"I've seen people like you, but I had to pay admission!",
				"Did your parents keep the placenta and throw away the baby?",
				"You stare at frozen juice cans because they say, concentrate.",
				"I love what you've done with your hair.",
				"Ordinarily people live and learn.",
				"Looks like you traded in your neck for an extra chin!",
				"If your brain exploded it wouldn't even mess up your hair.",
				"If your brain exploded it wouldn't affect you in anyway.",
				"Maybe if you ate some of that makeup you could be pretty on the inside.",
				"You are so stupid, you'd trip over a cordless phone.",
				"Nice tan, orange is my favorite color.",
				"Don't you need a license to be that ugly?",
				"You do realize makeup isn't going to fix your stupidity?",
				"You may not be the best looking girl here, but beauty is only a light switch away!",
				"It’s better to keep your mouth shut and give the 'impression' that you’re stupid than to open it and remove all doubt.",
				"You can run java?",
				"You so ugly when who were born the doctor threw you out the window and the window threw you back!",
				"I may be fat, but you're ugly, and I can lose weight.",
				"If I were to slap you, it would be considered animal abuse!",
				"What are you doing here?",
				"I heard you took an IQ test and they said you're results were negative.",
				"Aww, it's so cute when you try to talk about things you don't understand.",
				"You're so stupid, it takes you an hour to cook minute rice.",
				"You are so old, your birth certificate expired.",
				"Your genius amuzes me do you use a toaster as your bath toy",
				"Come again when you can't stay quite so long.",
				"You act like your arrogance is a virtue.",
				"You are so old, you fart dust.",
				"Don't feel sad, don't feel blue, Frankenstein was ugly too.",
				"I don't know what makes you so stupid, but it really works!",
				"If a crackhead saw you, he'd think he needs to go on a diet.",
				"If what you don't know can't hurt you, you're invulnerable.",
				"I'd like to help you out.",
				"If you had another brain, it would be lonely.",
				"Your ambition outweighs your relevant skills.",
				"It is too bad stupidity is not painful.",
				"If you are going to be two faced, at least make one of them pretty.",
				"We all sprang from apes, but you didn't spring far enough.",
				"Jesus loves you, everyone else thinks you're an asshole!",
				"Being around you is like having a cancer of the soul.",
				"If you spoke your mind, you'd be speechless.",
				"If you spoke your mind, you'd be hallow sounds.",
				"If you think ... wait you can't there is no brains.",
				"If you think about it you realize you can't.",
				"You occasionally stumble over the truth, but you quickly pick yourself up and carry on as if nothing happened.",
				"You're as useless as a screen door on a submarine.",
				"You're as useful as an ashtray on a motorcycle.",
				"You're as useful as an icecream on a cold day.",
				"You're as useful as a door without a room.",
				"You're as useful as a key without a lock.",
				"You're as useful as a monkey with a computer.",
				"The best part of you is still running down your old mans leg.",
				"When was the last time you could see your whole body in the mirror?",
				"So you've changed your mind, does this one work any better?",
				"Brains are not everything.",
				"Brains are what you dont have.",
				"You must think you're strong, but you only smell strong.",
				"If brains were dynamite you wouldn't have enough to blow your nose.",
				"Ever since I saw you in your family tree, I've wanted to cut it down.",
				"If I want your opinion, I'll give it to you.",
				"If I want your opinion, I'll never ask you.",
				"You'll make a great first wife some day.",
				"I look into your eyes and get the feeling someone else is driving.",
				"Yeah you're pretty, pretty stupid",
				"Beauty is skin deep, but ugly is to the bone.",
				"You are the reason why we are still developing",
				"You are the reason why aliens don't visit us",
				"You are depriving some poor village of its idiot.",
				"For those who never forget a face, you are an exception.",
				"Is your name Maple Syrup?",
				"You are so old, even your memory is in black and white.",
				"I hear the only place you're ever invited is outside.",
				"Just reminding u there is a very fine line between hobby and mental illness.",
				"Even if you were twice as smart, you'd still be stupid!",
				"You couldn't hit water if you fell out of a boat.",
				"People like you are the reason I work out.",
				"I wish you no harm, but it would have been much better if you had never lived.",
				"When anorexics see you, they think they need to go on a diet.",
				"I thought of you all day today.",
				"Please tell me you don't home school your kids.",
				"You're stupid because you're blonde.",
				"Roses are red violets are blue, God made me pretty, what the hell happen to you?",
				"Right now I'm sitting here looking at you trying to see things from your point of view but I cant",
				"A pretty girl can kiss a guy a bird can kiss a butterfly the rising sun can kiss the grass but you my friend will do nothing",
				"If you didn't have feet you wouldn't wear shoes then why do you wear a bra??!",
				"mirrors don't talk but lucky for you they don't laugh",
				"Poof be gone, your breath is too strong, I don't wanna be mean, but you need listerine, not a sip, not a swallow, but the whole friggin bottle",
				"People like you are the reason I'm on medication.",
				"Don't piss me off today, I'm running out of places to hide these bodies",
				"I have always woundered why people bang their heads against brick walls then I met you.",
				"Don't bother leaving a message.",
				"Don't let your mind wander. It's way to small to be outside by itself!",
				"I had a nightmare. I dreamt I was you.",
				"Hey Remember that time I told you I thought you were cool? I LIED.",
				"I need you I want you To get out of my face",
				"Damn not you again",
				"How many times do I solve this for you",
				"Everyone is entitled to be stupid, but you abuse the privilege.",
				"If I wanted to talk to you, I would have called you first.",
				"I am not anti-social I just don't like you",
				"Hmm I dont know what your probelm is but I'm going to bet it's really hard to pronounce...",
				"There are some stupid people in this world. You just helped me realize it.",
				"Until you called me I couldn't remember the last time I wanted somebody's fingers to break so badly.",
				"If you ran 1,000,000 miles to see the boy/girl of your dreams, what would you say when you got there?",
				"Wow you looked a lot hotter from a distance!",
				"Cancel my subscriptions ... I'm tired of your issues.",
				"I may be fat but you're ugly and I can diet!!!",
				"Earth is full. Go home.",
				"If I could be one person for a day, it sure as hell wouldn't be you.",
				"Hey, heres a hint. If i don't answer you the first 25 times, what makes you think the next 25 will work?",
				"how do you keep an idiot in suspense? Leave a message and I'll get back to you...",
				"Oh dear! Looks like you fell out of the ugly tree and hit every branch on the way down!",
				"What's that ugly thing growing out of your neck Oh It's your head...",
				"I'm sorry, Talking to you seems as appealing as playing leapfrog with unicorns.",
				"Oh Im sorry how many times did you drop on your head when you were a baby?",
				"Don't hate me because I'm beautiful hate me because your boyfriend thinks so.",
				"god made mountains, god made trees, god made you but we all make mistakes.",
				"Remember JESUS loves you but everyone else thinks you're an idiot.",
				"I'm not mean you are just a sissy.",
				"Sorry I can't think of an insult stupid enough for you.",
				"Why don't you go outside and play with zombies",
				"Beauty is skin deep, but ugly is to the bone",
				"How about a little less questions and a little more shut the hell up? I'm away live with it.",
				"morning! Now leave me alone!",
				"Let's see, I've walked the dog, cleaned my room, gone shopping and gossiped with my friends Nope, this list doesn't say that I'm required to talk to you.",
				"My Mom said never talk to strangers and well, since you're really strange I guess that means I can't talk to you!",
				"Forget the ugly stick! you must have been born in the ugly forrest!",
				"I really don't like you but if you really must leave a message, I'll be nice and at least pretend to care.",
				"You know the drill! You leave a message and I ignore it!",
				"The Village just called. They said they were missing their town idiot, I couldn't really understand them, but I think they were saying the name was yours...",
				"I'm not here right now so cry me a river, build yourself a bridge, and GET OVER IT!!!",
				"Why are you bothering me? I have my away message on cause I don't want to listen to you and your stupid nonsense.",
				"You dont know me, you just wish you did.",
				"Hey I am away from my computer but in the meantime, why don't you go play in traffic?!",
				"You have your whole life to be a jerk so why dont you take a day off so leave me a message for when I get back!!!!"
		};
		keys = new ArrayList<String>();
		values = new HashMap<String, ArrayList<String>>();
		for(int i=0;i<s.length;i++){
			String token[] = s[i].split(" ");
			if(token.length>2){
				String key = token[0] + " " + token[1];
				if(!keys.contains(key)){
					keys.add(key);
				}
				for(int j=2;j<token.length;j++){
					String jkey = token[j-2] + " " + token[j-1];
					ArrayList<String> jvalue = values.get(jkey);
					if(jvalue == null){
						jvalue=new ArrayList<String>();
						values.put(jkey, jvalue);
					}
					if(!jvalue.contains(token[j]))
						jvalue.add(token[j]);
				}
			}
		}
	}
	
}
