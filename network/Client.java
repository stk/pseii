package pse2.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * TODO: document
 *
 * @date 22.08.2009
 * @author: Wiegand
 */

public class Client implements Connection
{
	public static final String DEFAULT_HOST = "localhost";

	private int _Port;
	private String _Addr;
	
	private Socket _Socket = null;
	
	private ObjectInputStream _In;
	private ObjectOutputStream _Out;
	
	public Client(int port, String addr)
	{
		_Port = port;
		_Addr = addr;
	}
	
	public void connect() throws IOException
	{
		InetAddress ia = InetAddress.getByName(_Addr);
		_Socket = new Socket(ia, _Port);
		
		_Out = new ObjectOutputStream(_Socket.getOutputStream());
		_In = new ObjectInputStream(_Socket.getInputStream());
	}
	
	public Object getData() throws IOException
	{
		if(_Socket == null)throw new IOException("not connected");
		
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
		if(_Socket == null)throw new IOException("not connected");
		
		_Out.writeObject(o);
		_Out.flush();
	}

	public void close() throws IOException
	{
		_Out.writeObject(new String("Close!"));
		_In.close();
		_Out.close();
		_Socket.close();
	}
}
