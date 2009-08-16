package pse2;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class network {
	static int DEFAULT_PORT = 1236;
	static String host = "localhost"; //Localhost
	public static void main(String args[]) throws ClassNotFoundException, IOException{
		new Server(DEFAULT_PORT);
		//new Client(host, DEFAULT_PORT);		
	}	
}

class Server {
	public Server(int port) throws ClassNotFoundException, IOException {
		System.out.println("waiting for connections on port " + port);
        ServerSocket servSock = new ServerSocket(port);
        this.GetData(servSock);
	}

	public void SendData (){
	}

	@SuppressWarnings("unchecked")
	public void GetData (ServerSocket servSock) throws IOException, ClassNotFoundException{
		while (true) {	
			Socket socket = servSock.accept();
			InputStream is = socket.getInputStream();
			InputStream bis = new BufferedInputStream( is );
			ObjectInputStream ois = new ObjectInputStream( bis );
         		       
			HashMap<String, Integer> hm = (HashMap<String, Integer>) ois.readObject();
        
			ois.close();
        
			System.out.println( hm );
		}
	}
}

class Client {
	public Client(String host, int port) throws IOException {
		InetAddress ia = InetAddress.getByName(host);
		Socket socket = new Socket(ia, port);
		this.SendData(socket);
	}

	public void SendData (Socket socket) throws IOException{
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put( "One",new Integer(1) );
		hm.put( "Two",new Integer(2) );
		hm.put( "Three",new Integer(3) );
	  
		BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream() );
		ObjectOutputStream oos = new ObjectOutputStream( bos );
		  	 
		oos.writeObject(hm);
		oos.close();
	}

	public void GetData (){
	}
}