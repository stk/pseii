package pse2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pse2.network.Client;
import pse2.network.Connection;
import pse2.network.Server;

/**
 * TODO: document
 *
 * @date 22.08.2009
 * @author: Wiegand
 */

public class Launcher extends JFrame
{
	/**
	 * 0.1 beta 
	 */
	private static final long serialVersionUID = 1L;

	public Launcher()
	{
		//Gui Elemente bauen
		
		//client server?
		final JCheckBox isServer = new JCheckBox("ist Server?");
		
		//port
		JLabel portLabel = new JLabel("Port:");
		final JTextField port = new JTextField("" + Server.DEFAULT_PORT); 
		
		//adresse
		JLabel addrLabel = new JLabel("Adresse:");
		final JTextField addr = new JTextField(Client.DEFAULT_HOST);
		
		//Zünd
		JButton start = new JButton("Zünd");
		
		//Layout setzten
		getContentPane().setLayout(new GridLayout(0,2));
		
		//Elemente auf die GUI werfen
		getContentPane().add(isServer);
		getContentPane().add(new JLabel(""));
		getContentPane().add(portLabel);
		getContentPane().add(port);
		getContentPane().add(addrLabel);
		getContentPane().add(addr);
		getContentPane().add(start);
				
		//actions
		isServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				addr.setEnabled(!isServer.isSelected());
			}
		});
		
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				Connection con = null;
				boolean errorBit = false;
				if(isServer.isSelected())
				{
					try
					{
						con = new Server(Integer.parseInt(port.getText()));
						
						setTitle("waiting for client...");
						
						((Server)con).waitForClient();
						
						//verbindungstest
						con.sendData(new String("RoflLol"));
						Object o = con.getData();
						if(! (o instanceof String))
						{
							throw new IOException("bad reply");
						}
						else
						{
							String reply = (String)o;
							if(!reply.equals("LolRofl"))
							{
								throw new IOException("bad reply");
							}
						}						
					} 
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(getContentPane(), "unexpected Exception: " + e);
						errorBit = true;
					}
				}
				else
				{
					try
					{
						con = new Client(Integer.parseInt(port.getText()),addr.getText());
						((Client)con).connect();
						
						//verbindungstest
						Object o = con.getData();
						if(! (o instanceof String))
						{
							throw new IOException("bad reply");
						}
						else
						{
							String reply = (String)o;
							if(!reply.equals("RoflLol"))
							{
								throw new IOException("bad reply");
							}
						}
						con.sendData(new String("LolRofl"));
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(getContentPane(), "unexpected Exception: " + e);
						errorBit = true;
					}
				}
				
				if(!errorBit && con != null)
				{
					JOptionPane.showMessageDialog(getContentPane(), "Verbindung erfolgreich aufgebaut");
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(), "verbindungsfehler");
				}
			}
		});
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pack();
		
		//TODO: Titel anpassen
		setTitle("Astoroids Launcher");
		
		setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Launcher();
	}

}
