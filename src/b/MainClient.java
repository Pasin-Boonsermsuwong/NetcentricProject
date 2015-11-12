package b;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import a.ConnectUI;
import a.GameController;
import a.MainFrame;
import a.NameUI;
import a.WelcomeUI;

import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;


public class MainClient extends JFrame {

	private JPanel contentPane;
	public CardLayout cl = new CardLayout(0,0);
	public GameUI_b gameUI = new GameUI_b();
	public NameUI nameUI = new NameUI();
	public ConnectUI connectUI = new ConnectUI();
	public WelcomeUI welcomeUI = new WelcomeUI();
	
	private Socket socket;
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintWriter pw;
	
	private Thread thread;
	
	private String serverName;
	
	private int id=0;
	
	public static void main(String[] args) {

		GameController_b gc = new GameController_b();
		MainClient frame;
		frame = new MainClient();
		frame.setVisible(true);	
		
		setGc(new GameController());
		gc.gameUI = frame.gameUI;
		gc.nameUI = frame.nameUI;
		gc.connectUI = frame.connectUI;
		gc.welcomeUI = frame.welcomeUI;

		frame.gameUI.gc = getGc();
		frame.nameUI.gc = getGc();
		frame.connectUI.gc = getGc();
		frame.welcomeUI.gc = getGc();

	//	frame.changeCard("gameUI");
	}

	public MainClient(){
		String IP = null;
		String port = "2000";
		initGUI();
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
				sendData("1#"+getGc().playerName);
				while(ConnectUI.connected){
					String receivedMSG = "";
				//	System.out.println("Client Ready to receive message");
					try {
						receivedMSG = br.readLine();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(getGc().gameUI, e.getMessage(),e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
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
				getGc().setOpponentName(d[1]);//flow c - set gc.p2 - OPPONENT NAME
				getGc().generateSeed();//flow d - generate seed
				sendData("2#"+NameUI.name+"#"+getGc().seed+"#"+!getGc().isFirstPlayer+id);// flow e - send type 2
				if(getGc().isFirstPlayer){
					getGc().GameStateUpdate(getGc().gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE
				}else{
					getGc().GameStateUpdate(getGc().gamestate.GAME_WAITING);
				}
				break;
			case "2":	//SERVER REMOTELY INITIALIZE CLIENT'S GAME
				getGc().setOpponentName(d[1]);//flow g
				getGc().seed=Long.parseLong(d[2]);
				if (this.id == 0){
					this.id =Integer.parseInt(d[4]);
					getGc().clientID = this.id;
				}
				if(Boolean.parseBoolean(d[3])){
					getGc().GameStateUpdate(getGc().gamestate.GAME_PLAYING);
				}else{
					getGc().GameStateUpdate(getGc().gamestate.GAME_WAITING);
				}
				break;
			case "3":	//SERVER/CLIENT TELL THEY FINISHED TURN
				//flow k
				getGc().elapsedTime_opponent = Long.parseLong(d[1]);
				//flow l
				if(getGc().bothPlayerFinished()){
					getGc().compareScore();
				}else{
					getGc().GameStateUpdate(getGc().gamestate.GAME_PLAYING);
				}
				break;
			case "4":	//CLIENT TELL TO START NEXT GAME
				getGc().startNextGame_opponent = true;
				getGc().startNextGame();
				break;
			default:
				System.err.println("Unknown data type");
				break;
		}
	}
	

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);
		contentPane.add(welcomeUI,"welcomeUI");
		contentPane.add(nameUI, "nameUI");
		contentPane.add(connectUI,"connectUI");
		contentPane.add(gameUI,"gameUI");		
	}

	void changeCard(String cardName){
		System.out.println("Showing "+cardName);
		cl.show(contentPane, cardName);
	}

	public GameController_b getGc() {
		return gc;
	}

	public void setGc(GameController_b gc) {
		this.gc = gc;
	}
	
}
