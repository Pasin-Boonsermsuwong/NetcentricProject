package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import a.NameUI;



public class MiniServer_b extends Thread{
	
	Socket socket;
	InputStreamReader isr;
	BufferedReader br;
	PrintWriter pw;
	GameController_b gc;

	//STATUS OF EACH CLIENT
	
	public boolean update = false;
	public String name;
	public ClientState state = ClientState.NONE;
	
	public enum ClientState {
		NONE,
		WAITING,
		PLAYING
	}
	
	
	
	
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
			case "1":	//CLIENT TELL SERVER THEIR NAME, ALSO MEANS WAITING TO PLAY
				name = d[1];
		//		sendData("2#"+"TEMPNAME"+"#"+5000+"#"+true+"#"+id);// flow e - send type 2
				update = true;
				state = ClientState.WAITING;
				break;

			default:
				System.err.println("Unknown data type");
				break;
		}
	}
}