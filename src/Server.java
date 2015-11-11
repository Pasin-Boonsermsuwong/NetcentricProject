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
									gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
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
	public void sendData(int type,String data){
		String message = type+"#"+data;
		pw.println(message);
		pw.flush();
	}
	//if part probably redundant
	public void sendData(int type,String data,long seed){
		if(type == 1){
			pw.println(type+"#"+"data");
		}else if(type == 2){
			pw.println(type+"#"+data+"#"+seed);
		}
		pw.flush();
	}
	
	public void decipherData(String data){
		String typeS = data.substring(0, data.indexOf("#"));
		int type = Integer.parseInt(typeS);
		String message="";
		String seedS="";
		long seed = 0;
		if(type==1){
			message = data.substring(data.indexOf("#")+1);
		}else if(type==2){
			message = data.substring(data.indexOf("#")+1,data.lastIndexOf("#"));
			seedS = data.substring(data.lastIndexOf("#")+1);
			seed = Long.parseLong(seedS);
		}else if(type==3){
			
		}
		System.out.println("Seed = "+seedS);
		System.out.println(typeS +" "+message);
		switch(type){
			case 1:
				gc.opponentName = message;//flow c - set gc.p2
				gc.generateSeed();//flow d - generate seed
				sendData(2,NameUI.name,gc.seed);// flow e - send type 2
				gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE
				gc.activeTurn=true;
				break;
			case 2:
				gc.opponentName = message;//flow g
				gc.seed=seed;
				gc.GameStateUpdate(gc.gamestate.GAME_WAITING);//flow h?
				gc.activeTurn=false;
				break;
			case 3:
				//flow k
				gc.elapsedTime_opponent = Long.parseLong(message);
				//flow l
				gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
				gc.activeTurn=true;
				break;
			default:
				break;
		}
	}
	
}
