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

public class ConnectUI_b extends JPanel{
	public GameController_b gc;
	private JPanel contentPane;
	private JPanel[][] panelHolder = new JPanel[4][4]; 
	private JLabel ipLabel,portLabel;
	private JTextField ipTextField,portTextField;
	private JButton connect;
	
	//Client
	public Client_b client;
	
	public static boolean connected = true;
	public static final String END_MESSAGE = "ENDCONNECTION.";
	public static final int PORT = 2000;
	
	public ConnectUI_b(){
		this.setLayout(new GridLayout(4,4));
		initiateGridPanels(4,4);
		
		ipLabel = new JLabel();
		ipLabel.setText("IP  :");
		panelHolder[1][1].add(ipLabel);
		
		portLabel = new JLabel();
		portLabel.setText("Port:");
		panelHolder[2][1].add(portLabel);
		
	//	ipTextField = new JTextField("169.254.80.80");		//TODO TEMP
		panelHolder[1][2].add(ipTextField);
		ipTextField.setColumns(10);;
		
		portTextField = new JTextField("2000");				//TODO TEMP
		panelHolder[2][2].add(portTextField);
		portTextField.setColumns(10);;
		
		
		connect = new JButton("Connect");
		panelHolder[3][2].add(connect);
		connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				connectToServer(ipTextField.getText(),Integer.parseInt(portTextField.getText()));
			}
			
		});
	}
	
	private void initiateGridPanels(int row,int col){
		for(int m = 0; m < row; m++) {
			   for(int n = 0; n < col; n++) {
			      panelHolder[m][n] = new JPanel();
			      this.add(panelHolder[m][n]);
			   }
			}
	}
	
	private void connectToServer(String IP,int port){
		System.out.println("Connecting to server "+IP);
		client = new Client_b(IP,port);
	}

}
