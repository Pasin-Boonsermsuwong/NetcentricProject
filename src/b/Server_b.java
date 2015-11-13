package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import a.NumberGenerator;
import b.MiniServer_b.ClientState;

public 	class Server_b{

	private static Thread serverThread;
	
//	private ServerSocket serverSocket;
	private Socket socket;
	
	
	public ArrayList<MiniServer_b> socketList = new ArrayList<MiniServer_b>();
	public int[] gamePairs;
	
	int ID = 1;
	
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	private MiniServer_b miniServer;
	public MainServer_b mainServer;
	
	public Server_b(ServerSocket serverSocket){
		System.out.println("IP = "+serverSocket.getInetAddress());
		System.out.println("Port = "+serverSocket.getLocalPort());
		System.out.println("HostAddress = "+serverSocket.getInetAddress().getHostAddress());
		System.out.println("HostName = "+serverSocket.getInetAddress().getHostName());
		System.out.println("LocalSocketAddress = "+serverSocket.getLocalSocketAddress().toString());
	//	this.serverSocket = serverSocket;
		serverThread = new Thread(){
			public void run(){
				while(this.isInterrupted()== false){
					try {
						System.out.println("Server is listening...");
						socket = serverSocket.accept();
						System.out.println("Server is connected to a socket");
						miniServer = new MiniServer_b(socket);
						socketList.add(miniServer);
						miniServer.id = ID++;
						miniServer.server = Server_b.this;
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
	
	public void decipherData(String s,MiniServer_b mini){
		
		String[] d = s.split("#");
		System.out.println(Arrays.toString(d));
		if(d.length<=1){
			destroy(mini.id);
			return;
		}
		/*
			TYPE 1 = ID,type,name
			TYPE 2 = ID,type,seed,name,isFirstPlayer			INITIALIZE GAME
			TYPE 3 = ID,type,elaspedTime
			TYPE 4 = ID,type		- want to start next game
			TYPE 5 = reset score
			TYPE 6 = pair disconnected - BACK TO MAIN MENU
		*/
		switch(d[1]){
			case "1":	//CLIENT TELL SERVER THEIR NAME, ALSO MEANS WAITING TO PLAY
		//		sendData("2#"+"TEMPNAME"+"#"+5000+"#"+true+"#"+id);// flow e - send type 2		
				mini.name = d[2];
				mini.state = ClientState.WAITING;

				
				//FIND PAIR
				for(int i = 0;i<getClients();i++){
					MiniServer_b mini2 = socketList.get(i);
					if(mini2.id!=mini.id && mini2.state==ClientState.WAITING ){		// another player is waiting and it NOT the same player
						
						//PAIR FOUND, INITIALIZE GAME FOR BOTH PLAYERS
						System.out.println("PAIR FOUND");
						
						mini2.id_pair = mini.id;
						mini.id_pair = mini2.id;
						mini.state = ClientState.PLAYING;
						mini2.state = ClientState.PLAYING;
						
						Random r = new Random();
						long seed = r.nextLong();
						boolean firstplayer = r.nextBoolean();
					
						mini.firstPlayer = firstplayer;
						mini.sendData("2#"+seed+"#"+mini2.name+"#"+firstplayer);
						mini2.firstPlayer = !firstplayer;
						mini2.sendData("2#"+seed+"#"+mini.name+"#"+!firstplayer);
						mini.startNextGame = false;
						mini2.startNextGame = false;
					}
				}
				
				break;

			case "2":
				System.err.println("Server shoudldn't receive type 2 from clients");
				break;
			case "3":
				getClient(mini.id_pair).sendData("3#"+d[2]);   		//Simply resend elaspedTime to the pair
				break;
			case "4":
				mini.startNextGame = true;
				MiniServer_b mini2 = getClient(mini.id_pair);
				if(mini2.startNextGame){		// IF PAIR ALSO WANTS TO START NEXT GAME
					
					//THEN START NEXT GAME
					mini.state = ClientState.PLAYING;
					mini2.state = ClientState.PLAYING;
					
					Random r = new Random();
					long seed = r.nextLong();
				
					mini.sendData("2#"+seed+"#"+mini2.name+"#"+!mini.firstPlayer);
					mini2.sendData("2#"+seed+"#"+mini.name+"#"+!mini2.firstPlayer);
					mini.startNextGame = false;
					mini2.startNextGame = false;
				}
				break;
			default:
				System.err.println("Unknown data type");
				break;
		}
		mainServer.updatePlayerCount(getClients());
	}
	void FindPair(){
		
	}
	
	public void closeServerThread(){
		serverThread.interrupt();
	}
	
	
	//return number of clients
	 public int getClients(){
		 return socketList.size();
	 }
	 /*
	 //reuturn Socket of index i
	 public MiniServer_b getClient(int index){
		 return socketList.get(index);
	 }
	 */
	 
	 //GET BY ID
	 public MiniServer_b getClient(int ID){
		 for(int i = 0;i<getClients();i++){
			 if(socketList.get(i).id==ID)return socketList.get(i);
		 }
		 return null;
	 }
	 
	 /** remove a client from socketList (list of clients)
	 * @param id
	 */
	public void destroy(int id){
		 System.out.println("Destroy client ID: "+id);
		 MiniServer_b mini = getClient(id);
		 if(mini!=null&&mini.id_pair!=-1){		// TELL PAIR THAT PAIR HAS DISCONNECTED
			 MiniServer_b mini2 = getClient(mini.id_pair);
			 if(mini2 !=null){
				 mini2.state = ClientState.NON;
				 mini2.sendData("6");
			 }
		 }
		 socketList.remove(getClient(id));
		 mainServer.updatePlayerCount(getClients());
	 }
	 public void resetGames(){
		 ArrayList<Integer> sentPlayers = new ArrayList<Integer>();	//player IDs that has received reset message
		 for(int i = 0;i<getClients();i++){
			 MiniServer_b mini = socketList.get(i);
			 if(!sentPlayers.contains(mini.id)&&(mini.state == ClientState.PLAYING)){
				 //reset score of player and his/her pair
				 MiniServer_b mini2 = getClient(mini.id_pair);
				 mini.sendData("5");
				 mini2.sendData("5");
				 sentPlayers.add(mini.id);
				 sentPlayers.add(mini2.id);
				 // and initialize new game for both
				mini.state = ClientState.PLAYING;
				mini2.state = ClientState.PLAYING;

				Random r = new Random();
				long seed = r.nextLong();
				
				mini.sendData("2#"+seed+"#"+mini2.name+"#"+mini.firstPlayer);
				mini2.sendData("2#"+seed+"#"+mini.name+"#"+mini2.firstPlayer);
				mini.startNextGame = false;
				mini2.startNextGame = false;
			 }
				
		 }
	 }
}
