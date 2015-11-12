package a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;


public class Server{

	private Thread threadReceive;
	private static Thread serverThread;
	//private Thread threadSend;
	
	private ServerSocket serverSocket;
	private Socket socket;
	public ArrayList<MiniServer> socketList = new ArrayList<MiniServer>();
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	//private String clientName;
	//private boolean receivedMessageBoolean = false;
	private GameController gc;
	
	private int id =1;
	
	private MiniServer miniServer;
	
	public Server(ServerSocket serverSocket){
		System.out.println("IP = "+serverSocket.getInetAddress());
		System.out.println("Port = "+serverSocket.getLocalPort());
		System.out.println("HostAddress = "+serverSocket.getInetAddress().getHostAddress());
		System.out.println("HostName = "+serverSocket.getInetAddress().getHostName());
		System.out.println("LocalSocketAddress = "+serverSocket.getLocalSocketAddress().toString());
		this.serverSocket = serverSocket;
		gc = MainFrame.gc;
		gc.isFirstPlayer = true;
		serverThread = new Thread(){
			public void run(){
				while(this.isInterrupted()== false){
					try {
						System.out.println("Server is listening...");
						socket = serverSocket.accept();
						System.out.println("Server is connected to a socket");
						miniServer = new MiniServer(socket);
						miniServer.id = id++;
						socketList.add(miniServer);
						miniServer.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		serverThread.start();
	}
	
	public void closeServerThread(){
		serverThread.interrupt();
	}
	/*
	
	//return number of clients
	 public int getClients(){
		 return socketList.size();
	 }
	 
	 //reuturn Socket of index i
	 public Socket getClient(int index){
		 return socketList.get(index);
	 }
	 
	 */
}