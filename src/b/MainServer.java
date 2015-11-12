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


public class MainServer extends JFrame {

	private JPanel contentPane;
	private ServerSocket serverSocket;
	private Server_b server;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		MainServer frame = new MainServer();
		frame.setVisible(true);
		frame.createServer();
		
	}

	public MainServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
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
			serverSocket = new ServerSocket(2000,50,addr);
			
			server = new Server_b(serverSocket);
		}catch (BindException e){
			JOptionPane.showMessageDialog(null, "Address already in use", "", JOptionPane.ERROR_MESSAGE);
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
