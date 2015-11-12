package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import a.GameController;
import a.NameUI;


public class MiniServer_b extends Thread{
	
	Socket socket;
	InputStreamReader isr;
	BufferedReader br;
	PrintWriter pw;
	GameController_b gc;

	int id = 0;
	
	public MiniServer_b(Socket socket){
		this.socket = socket;

		try {
			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			String receivedMSG = "";
			try {
				receivedMSG = br.readLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(gc.gameUI, e.getMessage(),e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			System.out.println("Server revceived msg : "+ receivedMSG);
			decipherData(receivedMSG);
		}
	}
	
	public void sendData(String data){
		pw.println(data);
		pw.flush();
	}
	public void decipherData(String data){
		System.out.println("miniserver receive: "+data);
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
				System.out.println("Send ID :"+id);
				gc.serverID = id;
				sendData("2#"+NameUI.name+"#"+gc.seed+"#"+!gc.isFirstPlayer+"#"+id);// flow e - send type 2
				String sent = "2#"+NameUI.name+"#"+gc.seed+"#"+!gc.isFirstPlayer+"#"+id;
				String [] split = sent.split("#");
				System.out.println("2#"+NameUI.name+"#"+gc.seed+"#"+!gc.isFirstPlayer+"#"+id);
				System.out.println("Split = "+split[0]+" "+split[1]+" "+split[2]+" "+split[3]+" "+split[4]);
				if(gc.isFirstPlayer){
					gc.GameStateUpdate(gc.gamestate.GAME_PLAYING);//flow f -setState active turn NOT SURE
				}else{
					gc.GameStateUpdate(gc.gamestate.GAME_WAITING);
				}
				break;
//TODO: copmlete server shit
			default:
				System.err.println("Unknown data type");
				break;
		}
	}
}