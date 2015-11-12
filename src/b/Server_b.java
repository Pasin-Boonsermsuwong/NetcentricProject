package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public 	class Server_b{

	private Thread threadReceive;
	private static Thread serverThread;
	
	private ServerSocket serverSocket;
	private Socket socket;
	public ArrayList<MiniServer_b> socketList = new ArrayList<MiniServer_b>();
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;

	
	private int id =1;
	
	private MiniServer_b miniServer;
	
	public Server_b(ServerSocket serverSocket){
		System.out.println("IP = "+serverSocket.getInetAddress());
		System.out.println("Port = "+serverSocket.getLocalPort());
		System.out.println("HostAddress = "+serverSocket.getInetAddress().getHostAddress());
		System.out.println("HostName = "+serverSocket.getInetAddress().getHostName());
		System.out.println("LocalSocketAddress = "+serverSocket.getLocalSocketAddress().toString());
		this.serverSocket = serverSocket;
		serverThread = new Thread(){
			public void run(){
				while(this.isInterrupted()== false){
					try {
						System.out.println("Server is listening...");
						socket = serverSocket.accept();
						System.out.println("Server is connected to a socket");
						miniServer = new MiniServer_b(socket);
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
