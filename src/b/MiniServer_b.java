package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import a.NameUI;



public class MiniServer_b extends Thread{
	
	Socket socket;
	InputStreamReader isr;
	BufferedReader br;
	PrintWriter pw;
	GameController_b gc;
	Server_b server;
	
	boolean running = true;
	boolean firstPlayer;
	int id = -1;
	int id_pair = -1;
	public ClientState state = ClientState.NON;
	public String name;
	public enum ClientState{
		NON,
		WAITING,
		PLAYING
	}
	public boolean startNextGame = false;
	
	
	
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
		while(running){
			String receivedMSG = "";
			try {
				receivedMSG = br.readLine();
			} catch (IOException e) {
				if(server!=null)server.destroy(id);
				running = false;
				
			//	JOptionPane.showMessageDialog(null, e.getMessage(),e.getClass().toString(), JOptionPane.ERROR_MESSAGE);	
			//	System.exit(0);
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
		//PASS THE DATA UP TO MAIN SERVER
		if(id==-1){
			System.err.print("ID error in miniserver");return;
		}
		System.out.println("miniserver receive: "+data);
		server.decipherData(id+"#"+data,this);
	}
}