package pse2.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TODO: document
 *
 * @date 22.08.2009
 * @author: Wiegand
 */

public class Server implements Connection
{
	public static final int DEFAULT_PORT = 1236;

	private int _Port;
	
	private Socket _Client = null;
	
	private ObjectInputStream _In;
	private ObjectOutputStream _Out;
	
	public Server(int port)
	{
		_Port = port;
	}
		
	public void waitForClient() throws IOException
	{
		ServerSocket servSock = new ServerSocket(_Port);
		
		_Client = servSock.accept();
		
		InputStream in = _Client.getInputStream();
		_Out = new ObjectOutputStream(_Client.getOutputStream());
		_In = new ObjectInputStream(in);
	}
	
	public Object getData() throws IOException
	{
		if(_Client == null)throw new IOException("not connected");
		
		Object o;
		try
		{
			o = _In.readObject();
		} catch (ClassNotFoundException e)
		{
			throw new IOException("unknown object class", e);
		}
		
		return o;
	}

	public void sendData(Object o) throws IOException
	{
		if(_Client == null)throw new IOException("not connected");
		
		_Out.writeObject(o);
		_Out.flush();
	}

	public void close() throws IOException
	{
		_Out.writeObject(new String("Close!"));
		_In.close();
		_Out.close();
		_Client.close();
	}
}
