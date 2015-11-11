import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


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
	public void sendData(String data){
		pw.println(data);
		pw.flush();
	}
	public void decipherData(String data){
		
		String[] d = data.split("\n");
		System.out.println("Server received: "+Arrays.toString(d));
		/*
			TYPE 1 = name
			TYPE 2 = seed,name
			TYPE 3 = elaspedTime
		*/
		switch(d[0]){
			case "1":
				gc.opponentName = d[1];//flow c - set gc.p2 - OPPONENT NAME
				gc.generateSeed();//flow d - generate seed
				sendData("2+\n"+NameUI.name+"\n"+gc.seed);// flow e - send type 2
				gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE

				break;
			case "2":
				gc.opponentName = d[1];//flow g
				gc.seed=Long.parseLong(d[2]);
				gc.GameStateUpdate(gc.gamestate.GAME_WAITING);//flow h?

				break;
			case "3":
				//flow k
				gc.elapsedTime_opponent = Long.parseLong(d[1]);
				//flow l
				if(gc.bothPlayerFinished()){
					gc.compareScore();
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
				}
				break;
			default:
				System.err.println("Unknown data type");
				break;
		}
	}
}
