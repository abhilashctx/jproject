package fsdriver;

import java.io.RandomAccessFile;

public class FSD {
	
	
	public static void main(String[] args) {
		FSD fsd = new FSD();
		fsd.openDisk("testraf.txt");
		fsd.format();
		fsd.loadDir();

		int hello = fsd.openFileWrite("hello");
		fsd.write('A', hello);fsd.write('B', hello);fsd.write('C', hello);fsd.write('D', hello);
		String s=" this is a test string";
		for(int i=0;i<s.length();i++) fsd.write(s.charAt(i), hello);
		fsd.closeFile(hello);
		
		hello = fsd.openFileRead("hello");
		int x=fsd.read(hello);
		System.out.print("hello : ");
		while (x!=-1){
			System.out.print((char)x);
			x=fsd.read(hello);
		}System.out.println();
		fsd.closeFile(hello);
		
		fsd.dispDir();
		fsd.closeDisk();
		System.out.println("[done]");
	}
	
	private static final int BSIZE=256;
	private static final int BOOTBLOCK=0;
	private static final int FATBLOCK=1;
	private static final int DIRSTART=2;
	private static final int DATASTART=7;
	private static final int DIRFREE=0;
	private static final int DIRUSED=1;
	private int block[];
	
	private String fname;
	private RandomAccessFile raf;
	
	private class DirEntry{
		public int used;
		public String fname;
		public int startbid;
		public int size;
		
		public boolean isOpen;
		public boolean isRead;
		public boolean isWrite;
		public int buf[];
		public int cbid,offset;
		public int rp;
	}
	private DirEntry dir[];
	private int fat[];

	public FSD() {
		block = new int[BSIZE];
		for(int i=0;i<BSIZE;i++) block[i]=0;
		dir = new DirEntry[105];
		for(int i=0;i<dir.length;i++)
			dir[i]=new DirEntry();
		fat = new int[BSIZE];
	}
	
	public void openDisk(String _fname){
		try{
			fname = _fname;
			raf = new RandomAccessFile(fname, "rw");
		}catch(Exception e){e.printStackTrace();}
	}
	public void closeDisk(){
		if(raf!=null){
			try{
				raf.close();
			}catch(Exception e){e.printStackTrace();}
			raf=null;
			fname=null;
		}
	}
	public void seekBlock(int bid){
		int bidloc = bid * BSIZE;
		try{
			raf.seek(bidloc);
		}catch(Exception e){e.printStackTrace();}
	}
	public void readBlock(){
		try{
			for(int i=0;i<BSIZE;i++)
				block[i]=raf.read();
		}catch(Exception e){e.printStackTrace();}
	}
	public void writeBlock(){
		try{
			for(int i=0;i<BSIZE;i++)
				raf.write(block[i]);
		}catch(Exception e){e.printStackTrace();}
	}
	public void resetBufferBlock(){
			for(int i=0;i<BSIZE;i++)
				block[i]=0;
	}
	public void format(){
		resetBufferBlock();
		seekBlock(0);
		for(int i=0;i<BSIZE;i++)
			writeBlock();
	}
	public void readFAT(){
		seekBlock(FATBLOCK);
		readBlock();
		for(int i=0;i<BSIZE;i++)
			fat[i]=block[i];
	}
	public void writeFAT(){
		for(int i=0;i<BSIZE;i++)
			block[i]=fat[i];
		seekBlock(FATBLOCK);
		writeBlock();
	}
	public void loadDir(){
		readFAT();
		seekBlock(DIRSTART);
		int diridx=0;
		for(int i=0;i<5;i++){
			readBlock();
			for(int j=0;j<21;j++){
				int k=j*12;
				dir[diridx].used = block[k];
				String tname = "";
				for(int m=k+1;block[m]!=0 && m<(k+8);m++)
					tname += (char)block[m];
				dir[diridx].fname = tname;
				dir[diridx].startbid = block[k+9];
				dir[diridx].size = ((block[k+10]<<8) | block[k+11]);
				diridx++;
			}
		}
	}
	public void writeDir(){
		seekBlock(DIRSTART);
		int diridx=0;
		for(int i=0;i<5;i++){
			resetBufferBlock();
			for(int j=0;j<21;j++){
				int k=j*12;
				block[k] = dir[diridx].used;
				for(int m=k+1;m<(k+1+dir[diridx].fname.length());m++)
					block[m] = dir[diridx].fname.charAt(m-k-1);
				block[k+9] = dir[diridx].startbid;
				block[k+10] = ((dir[diridx].size>>8) & 0xFF);
				block[k+11] = (dir[diridx].size & 0xFF);
				diridx++;
			}
			writeBlock();
		}
	}
	public void dispDir(){
		for(int i=0;i<dir.length;i++){
			System.out.println("Used:"+dir[i].used+" Fname:"+dir[i].fname+" BID:"+dir[i].startbid+" Size:"+dir[i].size+" isOpen:"+dir[i].isOpen+
					" isRead:"+dir[i].isRead+" isWrite:"+dir[i].isWrite);
		}
	}
	public int findFile(String sname){
		for(int i=0;i<dir.length;i++){
			if(dir[i].used==DIRUSED && dir[i].fname.equals(sname)){
				return i;
			}
		}
		return -1;
	}
	public void delFile(String sname){
		int filehandle = findFile(sname);
		if(filehandle==-1){
			return;
		}
		if(dir[filehandle].isOpen){
			System.out.println("*Del failed, file:"+sname+" is open");
			return;
		}
		int tbid = dir[filehandle].startbid;
		while(fat[tbid]>0){
			int tbidnext = fat[tbid];
			fat[tbid]=0;
			tbid=tbidnext;
		}
		dir[filehandle].used = DIRFREE;
		dir[filehandle].fname="";
		dir[filehandle].startbid=0;
		dir[filehandle].size=0;
		writeFAT();
		writeDir();
	}
	public int findFreeFat(){
		for(int i=DATASTART;i<BSIZE;i++){
			if(fat[i]==0) return i;
		}
		return 0;
	}
	public int findFreeDir(){
		for(int i=0;i<dir.length;i++){
			if(dir[i].used == DIRFREE) return i;
		}
		return -1;
	}
	public int openFileWrite(String sname){
		delFile(sname);
		int freefat = findFreeFat();
		if(freefat==0){
			System.out.println("*Disk full");
			return 0;
		}
		int freedir = findFreeDir();
		if(freedir==-1){
			System.out.println("*Dir full");
			return 0;
		}
		dir[freedir].fname=sname;
		dir[freedir].size=0;
		dir[freedir].startbid=freefat;
		dir[freedir].used=DIRUSED;
		
		dir[freedir].isOpen=true;
		dir[freedir].isRead=false;
		dir[freedir].isWrite=true;
		dir[freedir].buf = new int[BSIZE];
		dir[freedir].cbid=freefat;
		dir[freedir].offset=0;
		
		fat[freefat]=1;
		writeDir();
		writeFAT();
		return freedir;
	}
	public int openFileRead(String sname){
		int filedir = findFile(sname);
		if(filedir==-1){
			System.out.println("*File Not Found");
			return -1;
		}
		dir[filedir].isOpen=true;
		dir[filedir].isRead=true;
		dir[filedir].isWrite=false;
		dir[filedir].buf = new int[BSIZE];
		dir[filedir].cbid=dir[filedir].startbid;
		dir[filedir].offset=0;
		dir[filedir].rp=0;
		seekBlock(dir[filedir].cbid);
		readBlock();
		for(int i=0;i<BSIZE;i++) dir[filedir].buf[i]=block[i];
		return filedir;
	}
	public void closeFile(int handle){
		if(dir[handle].isOpen && dir[handle].isWrite){
			if(dir[handle].offset>0){
				seekBlock(dir[handle].cbid);
				for(int i=0;i<BSIZE;i++) block[i]=dir[handle].buf[i];
				writeBlock();
			}
		}
		dir[handle].buf = null;
		dir[handle].cbid=0;
		dir[handle].offset=0;
		dir[handle].isRead=false;
		dir[handle].isWrite=false;
		dir[handle].isOpen=false;
		writeDir();
	}
	public void write(int data,int handle){
		if(dir[handle].isOpen && dir[handle].isWrite){
			if(dir[handle].offset==BSIZE){
				seekBlock(dir[handle].cbid);
				for(int i=0;i<BSIZE;i++) block[i]=dir[handle].buf[i];
				writeBlock();
				int freefat = findFreeFat();
				if(freefat == 0){
					System.out.println("*write error: disk full");
					return;
				}
				fat[freefat]=1;
				fat[dir[handle].cbid]=freefat;
				dir[handle].cbid=freefat;
				dir[handle].offset=0;
				for(int i=0;i<BSIZE;i++) dir[handle].buf[i]=0;
				writeFAT();
			}
			dir[handle].buf[dir[handle].offset]=data;
			dir[handle].offset++;
			dir[handle].size++;
		}else{
			System.out.println("*write error: isOpen:"+dir[handle].isOpen+" isWrite:"+dir[handle].isWrite);
		}
	}
	public int read(int handle){
		if(dir[handle].isOpen && dir[handle].isRead){
			if(dir[handle].rp==dir[handle].size) return -1;
			if(dir[handle].offset==BSIZE){
				dir[handle].cbid = fat[dir[handle].cbid];
				dir[handle].offset=0;
				seekBlock(dir[handle].cbid);
				readBlock();
				for(int i=0;i<BSIZE;i++) dir[handle].buf[i]=block[i];
			}
			int r = dir[handle].buf[dir[handle].offset];
			dir[handle].offset++;
			dir[handle].rp++;
			return r;
		}else{
			System.out.println("*read error: isOpen:"+dir[handle].isOpen+" isWrite:"+dir[handle].isWrite);
		}
		return -1;
	}
	public int getSize(int handle){
		return dir[handle].size;
	}
	public int getFreeSpace(){
		int c=0;
		for(int i=DATASTART;i<BSIZE;i++){
			if(fat[i]==0) c++;
		}
		return (c*BSIZE);
	}
}
