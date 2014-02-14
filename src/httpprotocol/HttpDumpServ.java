package httpprotocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpDumpServ {
	
	public static void main(String[] args) {
		try{
			byte b[] = new byte[1024];
			ServerSocket ss = new ServerSocket(8989);
			Socket s = null;
			System.out.println("Server listen on 8989");
			while(true){
				s = ss.accept();
				System.out.println("Got connection from "+s.getRemoteSocketAddress());
				BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
				int len = 0;
				System.out.println("Reading data");
				while((len=bis.read(b))>0){
					String str = new String(b,0,len);
					System.out.println(str);
					if(len<1024) break;
				}
				BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
				// below string display 'hello world' in browser
				//String str = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\nhello world";
				// below string sends a text file omg.txt with content 'hello world' to browser
				String str = "HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\ncontent-disposition: attachment; filename=omg.txt\r\n\r\nhello world";
				//application/octet-stream
				bos.write(str.getBytes());
				bos.flush();
				s.close();
				s=null;
				System.out.println("--- DONE ---");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
