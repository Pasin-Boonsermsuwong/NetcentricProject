import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import javax.swing.JOptionPane;


public class Client{

	private Socket socket;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	private Thread thread;
	
	private String serverName;
	
	private GameController gc;
	
	private int id=0;
	
	public Client(String IP, int port){
		try {
			socket = new Socket();
			InetAddress addr = InetAddress.getByName(IP);
			System.out.println("HostAddress = "+addr.getHostAddress());
			String hostName = addr.getHostName();
			socket.connect(new InetSocketAddress(hostName,port), 30);
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
			return;
			//e.printStackTrace();
		}
		thread = new Thread(){
			public void run(){
				sendData("1#"+gc.playerName);
				while(ConnectUI.connected){
					String receivedMSG = "";
				//	System.out.println("Client Ready to receive message");
					try {
						receivedMSG = br.readLine();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(gc.gameUI, e.getMessage(),e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					System.out.println("Client received msg : " + receivedMSG);
					if(receivedMSG == ConnectUI.END_MESSAGE){
						System.out.println("Received END_MESSAGE");
						pw.close();
					}
					decipherData(receivedMSG);
					//serverName = receivedMSG;
				}
			}
		};
		gc = MainFrame.gc;
		//gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
		thread.start();
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
				sendData("2#"+NameUI.name+"#"+gc.seed+"#"+!gc.isFirstPlayer+id);// flow e - send type 2
				if(gc.isFirstPlayer){
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_WAITING);
				}
				break;
			case "2":	//SERVER REMOTELY INITIALIZE CLIENT'S GAME
				gc.setOpponentName(d[1]);//flow g
				gc.seed=Long.parseLong(d[2]);
				if (this.id == 0){
					this.id =Integer.parseInt(d[4]);
					gc.clientID = this.id;
				}
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
