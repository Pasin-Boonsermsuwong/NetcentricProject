package b;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import a.GameController;
import a.MainFrame;
import a.MiniServer;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;


public class MainServer_b extends JFrame {

	private JPanel contentPane;
	private ServerSocket serverSocket;
	private Server_b server;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel countLabel;
	private JPanel panel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		MainServer_b frame = new MainServer_b();
		frame.setVisible(true);
		frame.createServer();
		
	}

	public MainServer_b() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblNewLabel = new JLabel("Server is running");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		lblNewLabel_1 = new JLabel("Player count: ");
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		countLabel = new JLabel("");
		countLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(countLabel);
	}
	
	private void createServer(){
		System.out.println("Creating server...");
		try {
			InetAddress addr= InetAddress.getLocalHost();
			String hostAddress = addr.getHostAddress();
			String hostName = addr.getHostName();
			System.out.println(hostAddress);
			serverSocket = new ServerSocket(2000,50,addr);
			server = new Server_b(serverSocket);
			server.mainServer = this;
		}catch (BindException e){
			JOptionPane.showMessageDialog(null, "Address already in use", "", JOptionPane.ERROR_MESSAGE);
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void updatePlayerCount(int count){
		countLabel.setText(""+count);
	}
}
