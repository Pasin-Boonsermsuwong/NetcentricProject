import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server{

	private Thread threadReceive;
	private static Thread serverThread;
	//private Thread threadSend;
	
	private ServerSocket serverSocket;
	private Socket socket;
	//private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	private String clientName;
	//private boolean receivedMessageBoolean = false;
	
	public Server(ServerSocket serverSocket){
		System.out.println("IP = "+serverSocket.getInetAddress());
		System.out.println("Port = "+serverSocket.getLocalPort());
		this.serverSocket = serverSocket;
		serverThread = new Thread(){
			public void run(){
				while(this.isInterrupted()== false){
					try {
						System.out.println("Server is listening...");
						socket = serverSocket.accept();
						System.out.println("Server is connected to a socket");
						//socketList.add(socket);
						isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						pw = new PrintWriter(socket.getOutputStream());
						threadReceive = new Thread(){
							public void run(){
								while(ConnectUI.connected){
									String receivedMSG = "";
									System.out.println("Server Ready to receive message");
									try {
										receivedMSG = br.readLine();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									System.out.println("Server received msg : " + receivedMSG);
									if(receivedMSG == ConnectUI.END_MESSAGE){
										System.out.println("Received END_MESSAGE");
										pw.close();
									}
									clientName = receivedMSG;
									sendName();
									//receivedMessageBoolean = true;
								}
							}
						};
						threadReceive.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		serverThread.start();
	}
	
	public void sendName(){
		pw.println(NameUI.name);
		pw.flush();
	}
	
	public void closeServerThread(){
		serverThread.interrupt();
	}
	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(serverSocket.getInetAddress());
			//System.out.println(serverSocket.getInetAddress().getHostAddress());
			//System.out.println(serverSocket.getInetAddress().getHostName());
			while(true){
				System.out.println("server listening");
				socket = serverSocket.accept();
				isr = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(isr);
				pw = new PrintWriter(socket.getOutputStream());
				System.out.println("Ready to receive message.");
				String receivedMsg = br.readLine();
				while(receivedMsg!="END CONNECTION"){
					System.out.println("message recieved: "+ receivedMsg);
					receivedMsg = br.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
}
