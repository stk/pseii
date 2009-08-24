package pse2.network;

import java.io.IOException;

/**
 * TODO: document
 *
 * @date 22.08.2009
 * @author: Wiegand
 */

public interface Connection
{
	public void sendData(Object o)throws IOException;
	public Object getData()throws IOException;
	public void close()throws IOException;
}
