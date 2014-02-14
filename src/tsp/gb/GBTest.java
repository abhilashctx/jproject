package tsp.gb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.regex.Matcher;

public class GBTest{
	
	
	
	public static void main(String[] args) {
		System.out.println("v1.18");
		//System.out.println((int)Float.parseFloat("3.1"));
		//fractions base 1000
		//int param[]={20,30,1,255,25,1500,10,5,200,30,20, 20,50,400,50,1000,9,200,40,15,15,15,10,30};
		GBTest g = new GBTest();
		//g.genb(param);
		//g.testit();
		g.sa();
	}
	
	//static String path = "E:\\Abhilash\\experiments\\Gbt\\";
	static String path = "";
	static String gexe = "gh.exe";
	static String test = "test\\";
	static Random rnd = new Random(System.currentTimeMillis());
	
	static int best[]={20,30,1000,255,25,1500,10,5,200,30,20, 20,50,400,50,1000,9,200,40,15,15,15,10,30};
	
	public void sa(){
		double t=1000;
		double c=10;
		int newr[]={20,30,1000,255,25,1500,10,5,200,30,20, 20,50,400,50,1000,9,200,40,15,15,15,10,30};
		double newscore=0;
		int curr[]={20,30,1000,255,25,1500,10,5,200,30,20, 20,50,400,50,1000,9,200,40,15,15,15,10,30};
		double currscore=0;
		//int best[]={20,30,1000,255,25,1500,10,5,200,30,20, 20,50,400,50,1000,9,200,40,15,15,15,10,30};
		double bestscore=0;
		
		genb(curr,"bt.gb");
		currscore=testit();
		
		while(t>1){ System.out.println("Temp : "+t);
			
			genNewParam(newr, curr);
			genb(newr,"bt.gb"); sleepx(10);
			newscore = testit(); sleepx(10);
			
			if(newscore > currscore){
				copy(curr, newr); currscore=newscore;
			}else{
				if(Math.exp((((100-currscore)-(100-newscore))*300)/t) > rnd.nextDouble() ){
					copy(curr, newr); currscore=newscore;
				}
			}
			
			if(currscore > bestscore){
				bestscore = currscore; copy(best,curr);
				genb(best,"best.gb");
			}
			
			t-=c;
			System.out.print("newr : ");
			for(int i=0;i<newr.length;i++) System.out.print(newr[i]+",");
			System.out.println();
			System.out.print("curr : ");
			for(int i=0;i<curr.length;i++) System.out.print(curr[i]+",");
			System.out.println();
			System.out.print("best : ");
			for(int i=0;i<best.length;i++) System.out.print(best[i]+",");
			System.out.println();
			System.out.println("curr:"+currscore+" best:"+bestscore);
		}
		System.out.println("Final best : ");
		for(int i=0;i<best.length;i++) System.out.print(best[i]+",");
		System.out.println();
	}
	
	public void genNewParam(int newr[],int curr[]){
		genNew(newr, curr);
		if(rnd.nextInt(1000)<500){
			genNew(newr, newr);
			if(rnd.nextInt(1000)<500){
				genNew(newr, newr);
				if(rnd.nextInt(1000)<500){
					genNew(newr, newr);	
				}
			}
		}
	}
	
	public void genNew(int newr[],int curr[]){//1:1-100,2:1-1000,3:1-2000,4:1-30
		int types[]={1,2,3,2,1,3,4,4,2,1,1, 1,2,2,1,3,4,2,2,4,1,4,4,2};
		int idx = rnd.nextInt(24);
		copy(newr, curr);
		int incdec=rnd.nextInt(5)+1;
		if(rnd.nextInt(1000)<500){
			newr[idx]+=incdec;
		}else{
			newr[idx]-=incdec;
		}
		if(newr[idx]<1) newr[idx]=1;
		if(types[idx]==1){ if(newr[idx]>100) newr[idx]=100; } //newr[idx] = rnd.nextInt(100)+1; }
		else if(types[idx]==2){ if(newr[idx]>1000) newr[idx]=1000; }//newr[idx] = rnd.nextInt(1000)+1; }
		else if(types[idx]==3){if(newr[idx]>2000) newr[idx]=2000;}//newr[idx] = rnd.nextInt(2000)+1; }
		else if(types[idx]==4){if(newr[idx]>30) newr[idx]=30;}//newr[idx] = rnd.nextInt(30)+1; }
	}
	
	//11 ,13
	
	public double testit(){
		String pathtest = path+test;
		String cmd = "cmd /C "+path+gexe+" -t5 -b10000 -l10000 "+pathtest+"bt.gb "+pathtest+"1.gb "+pathtest+"2.gb "+pathtest+"3.gb ";
		boolean found=false;
		double score=0;
		try{
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while((line=br.readLine())!=null){
				//System.out.println(line);
				if(found){ found=false;
					int perc = line.indexOf('%'); System.out.println("  >>"+line);
					String tscore = line.substring(0, perc);
					score = Double.parseDouble(tscore);
					System.out.println("score = "+tscore);
				}
				if(line.startsWith("#results")) {found=true;}//System.out.println("found");}
			}
			br.close();
			//InputStream is = p.getInputStream();
			//int x;
			//while((x=is.read())!=-1){ System.out.print((char)x); }
		}catch(Exception e){e.printStackTrace();}
		return score;
	}
	
	//default fname = bt.gb
	public void genb(int param[],String fname){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path+test+"b.gb")));
			PrintWriter pw = new PrintWriter(path+test+fname);
			String line = br.readLine();
			while(line!=null){
				//modify line
				int $ = line.indexOf('$');
				while($!=-1){
					String tag = line.substring($,$+4); //System.out.println("tag = "+tag);
					char type = line.charAt($+1);
					int idx  = (line.charAt($+2)-'0')*10 + (line.charAt($+3)-'0');
					//System.out.println("type="+type+" idx="+idx);
					if(type=='i')
						line = line.replaceFirst(Matcher.quoteReplacement(tag), ""+param[idx]);
					else{
						float f = (float)param[idx]/1000;
						line = line.replaceFirst(Matcher.quoteReplacement(tag), ""+f);
					}
					$ = line.indexOf('$');
				}
				pw.println(line);
				line=br.readLine();
			}
			br.close();
			pw.close();
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void copy(int dst[],int src[]){
		for(int i=0;i<src.length;i++) dst[i]=src[i];
	}
	
	public void sleepx(long delay){
		try{Thread.sleep(delay);}catch(Exception e){}
	}
}
