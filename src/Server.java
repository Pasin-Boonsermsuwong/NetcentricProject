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
	//private ArrayList<Socket> socketList = new ArrayList<Socket>();
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	//private String clientName;
	//private boolean receivedMessageBoolean = false;
	private GameController gc;
	
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
						//socketList.add(socket);
						isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						pw = new PrintWriter(socket.getOutputStream());
						threadReceive = new Thread(){
							public void run(){
								while(ConnectUI.connected){
									String receivedMSG = "";
								//	System.out.println("Server Ready to receive message");
									try {
										receivedMSG = br.readLine();
									} catch (IOException e) {
										JOptionPane.showMessageDialog(gc.gameUI, e.getMessage(),e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
										System.exit(0);
									}
									System.out.println("Server received msg : " + receivedMSG);
									if(receivedMSG == ConnectUI.END_MESSAGE){
										System.out.println("Received END_MESSAGE");
										pw.close();
									}
								//	gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
									decipherData(receivedMSG);
									//clientName = receivedMSG;
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
	
	public void closeServerThread(){
		serverThread.interrupt();
	}
	public void sendData(String data){
		pw.println(data);
		pw.flush();
	}
	public void decipherData(String data){
		
		String[] d = data.split("#");
		/*
			TYPE 1 = name
			TYPE 2 = seed,name,isFirstPlayer			// game initializer
			TYPE 3 = elaspedTime
		*/
		switch(d[0]){
			case "1":	//CLIENT TELL SERVER THEIR NAME
				gc.setOpponentName(d[1]);//flow c - set gc.p2 - OPPONENT NAME
				gc.generateSeed();//flow d - generate seed
				sendData("2#"+NameUI.name+"#"+gc.seed+"#"+!gc.isFirstPlayer);// flow e - send type 2
				if(gc.isFirstPlayer){
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_WAITING);
				}
				break;
			case "2":	//SERVER REMOTELY INITIALIZE CLIENT'S GAME
				gc.setOpponentName(d[1]);//flow g
				gc.seed=Long.parseLong(d[2]);
				if(Boolean.parseBoolean(d[3])){
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_WAITING);
				}
				break;
			case "3":	//SERVER/CLIENT TELL THEY FINISHED TURN
				//flow k
				gc.elapsedTime_opponent = Long.parseLong(d[1]);
				//flow l
				if(gc.bothPlayerFinished()){
					gc.compareScore();
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
				}
				break;
			case "4":	//CLIENT TELL TO START NEXT GAME
				gc.startNextGame_opponent = true;
				gc.startNextGame();
				break;
			default:
				System.err.println("Unknown data type");
				break;
		}
	}
}
