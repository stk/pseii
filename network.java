package pse2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class network{
	static OutputStream bos = null;
	static ObjectOutputStream oos = null;
	static InputStream is = null;
	static ObjectInputStream ois = null;
	static HashMap<String, Integer> hm = null;
	static Socket socket = null;
	static InetAddress host = null;
	static ServerSocket server = null;
	static int port = 7777;
	static boolean isServer = false; //got to be set to true if runnung as client
	
	static void OpenSocket() throws IOException{		
		if (isServer){
		server = new ServerSocket(port);
		socket = server.accept(); 
		}
		else{
		host = InetAddress.getLocalHost();
		socket = new Socket(host.getHostName(), port);
		}
	}
	
	static void SendData() throws IOException{
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put( "One",new Integer(1) );
		hm.put( "Two",new Integer(2) );
		hm.put( "Three",new Integer(3) );
		
		bos = new BufferedOutputStream( socket.getOutputStream() );
		oos = new ObjectOutputStream( bos );
		oos.writeObject(hm);
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	static void GetData() throws IOException, ClassNotFoundException{
		is = socket.getInputStream();
		InputStream bis = new BufferedInputStream( is );
		ois = new ObjectInputStream( bis );
        
		hm = (HashMap<String, Integer>) ois.readObject();
		ois.close();
		is.close();
		socket.close();
		System.out.println("Message Received: " + hm);
	}
	
	public static void main(String[] args) throws Exception {
		OpenSocket();
		SendData();
		GetData();
	}
}