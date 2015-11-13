package a;
import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class MainFrame extends JFrame {

	private static Clip clip = null;
	
	private JPanel contentPane;
	public CardLayout cl = new CardLayout(0,0);
	public GameUI gameUI = new GameUI();
	public NameUI nameUI = new NameUI();
	public ConnectUI connectUI = new ConnectUI();
	public WelcomeUI welcomeUI = new WelcomeUI();
	
	
	public static GameController gc;

	public static void main(String[] args) {

		MainFrame frame;
		frame = new MainFrame();
		frame.setVisible(true);	
		
		gc = new GameController();
		gc.mainFrame = frame;
		gc.gameUI = frame.gameUI;
		gc.nameUI = frame.nameUI;
		gc.connectUI = frame.connectUI;
		gc.welcomeUI = frame.welcomeUI;

		frame.gameUI.gc = gc;
		frame.nameUI.gc = gc;
		frame.connectUI.gc = gc;
		frame.welcomeUI.gc = gc;

	//	frame.changeCard("gameUI");
	}

	public MainFrame() {
		initGUI();
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
		musicStart();
	}

	void changeCard(String cardName){
		System.out.println("Showing "+cardName);
		cl.show(contentPane, cardName);
	}
	
	public void musicStart(){
		File audioFile = new File("netcentricMusic.wav");
		AudioInputStream audIn = null;
		try {
			audIn = AudioSystem.getAudioInputStream(audioFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audIn);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
		clip.loop(2);
	}
	
	public static void musicStop(){
		clip.stop();
	}
	
}
