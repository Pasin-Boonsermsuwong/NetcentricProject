import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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
	
	private GameController gc;
	
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
				sendData(1,NameUI.name);
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
					decipherData(receivedMSG);
					//serverName = receivedMSG;
				}
			}
		};
		gc = MainFrame.gc;
		gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);
		thread.start();
	}
	
	public void sendData(int type,String data){
		String message = type+"#"+data;
		pw.println(message);
		pw.flush();
	}
	
	//probably not needed here. lmao
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
