package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import a.GameController;
import a.MainFrame;
import a.NameUI;


public class MiniServer_b extends Thread{
	
	Socket socket;
	InputStreamReader isr;
	BufferedReader br;
	PrintWriter pw;
	
	int id = 0;
	
	public MiniServer_b(Socket socket){
		this.socket = socket;
		gc = MainFrame.gc;
		try {
			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr);
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		
		String[] d = data.split("#");	
		/*
			TYPE 1 = name
			TYPE 2 = seed,name,isFirstPlayer			// game initializer
			TYPE 3 = elaspedTime
		*/
		switch(d[0]){
			case "1":	//CLIENT TELL SERVER THEIR NAME

				break;
			default:
				System.err.println("Unknown data type");
				break;
		}
	}
}