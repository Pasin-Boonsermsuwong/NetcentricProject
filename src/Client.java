import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;


public class Client{

	private Socket socket;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	private Thread thread;
	
	private String serverName;
	
	public Client(String IP){
		try {
			socket = new Socket();
			socket.connect(new InetSocketAddress(IP,ConnectUI.PORT), 10);
			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			JOptionPane.showMessageDialog(null,
					"Could not connect to Socket with IP : "+IP,
					"Connect Failed",
					JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
		thread = new Thread(){
			public void run(){
				sendName();
				while(ConnectUI.connected){
					String receivedMSG = "";
					System.out.println("Client Ready to receive message");
					try {
						receivedMSG = br.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Client received msg : " + receivedMSG);
					if(receivedMSG == ConnectUI.END_MESSAGE){
						System.out.println("Received END_MESSAGE");
						pw.close();
					}
					serverName = receivedMSG;
				}
			}
		};
		thread.start();
	}
	
	public void sendName(){
		pw.println(NameUI.name);
		pw.flush();
	}
	
	public void closeClient(){
		pw.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			isr = new InputStreamReader(socket.getInputStream());
			brFromServer = new BufferedReader(isr);
			pr = new PrintWriter(socket.getOutputStream(),true);
			//brFromClient = new BufferedReader(new InputStreamReader(System.in));
			//String message = brFromClient.readLine();
			String message = "CONNECTED BITCHES";
			pr.println(message);
			
			String received = brFromServer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to connect");
			e.printStackTrace();
		}
	}*/

}
