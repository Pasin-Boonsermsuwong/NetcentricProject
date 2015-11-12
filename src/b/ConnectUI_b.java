package b;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Font;

public class ConnectUI_b extends JPanel{
	

	public GameController_b gc;
	private JPanel contentPane;
	private JButton connect;
	
	//Client
	public Client_b client;
	
	public static boolean connected = true;
	public static final String END_MESSAGE = "ENDCONNECTION.";
	public static final int PORT = 2000;
	private JLabel noteLabel;
	private Component rigidArea;
	
	public ConnectUI_b(){
		initGUI();
	}
	private void initGUI() {
		;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		connect = new JButton("Connect to game Server");
		connect.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(connect);
		connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				connectToServer();
			}
			
		});
		
		rigidArea = Box.createRigidArea(new Dimension(20, 100));
		add(rigidArea);
		
		noteLabel = new JLabel("");
		noteLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(noteLabel);
	}

	private void connectToServer(){
	//	System.out.println("Connecting to server ");
		client = new Client_b();
		if(client.isConnected){
			connect.setEnabled(false);
			noteLabel.setText("Waiting for other player");
		}
	}

	public void resetState(){
		connect.setEnabled(true);
		noteLabel.setText("");
	}
}
