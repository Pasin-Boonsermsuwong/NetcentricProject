import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConnectUI extends JPanel{
	
	public GameController gc;
	private JPanel contentPane;
	private JPanel[][] panelHolder = new JPanel[4][4]; 
	private JLabel ipLabel,portLabel;
	private JTextField ipTextField,portTextField;
	private JButton go,connect;
	
	//Server
	private ServerSocket serverSocket;
	public Server server;
	
	//Client
	public Client client;
	
	public static boolean connected = true;
	public static final String END_MESSAGE = "ENDCONNECTION.";
	public static final int PORT = 2000;
	
	public ConnectUI(){
		this.setLayout(new GridLayout(4,4));
		initiateGridPanels(4,4);
		
		ipTextField = new JTextField();
		panelHolder[1][2].add(ipTextField);
		ipTextField.setColumns(10);;
		
		portTextField = new JTextField();
		panelHolder[2][2].add(portTextField);
		portTextField.setColumns(10);;
		
		go = new JButton("Create Server");
		panelHolder[3][1].add(go);
		go.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				createServer();
			}
			
		});
		
		connect = new JButton("Connect");
		panelHolder[3][2].add(connect);
		connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
	
	private void createServer(){
		System.out.println("Creating server...");
		try {
			InetAddress addr= InetAddress.getLocalHost();
			String hostAddress = addr.getHostAddress();
			String hostName = addr.getHostName();
			//System.out.println(addr.toString());
			System.out.println(hostAddress);
			//System.out.println(hostName);
			serverSocket = new ServerSocket(PORT,50,addr);
			
			//serverSocket = new ServerSocket(PORT);
			gc.isServer=true;
			server = new Server(serverSocket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void connectToServer(String IP,int port){
		System.out.println("Connecting to server "+IP);
		gc.isServer=false;
		client = new Client(IP,port);
	}
	
	public static void main (String [] args){
		JFrame frame= new JFrame();
		frame.add(new ConnectUI());
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
