package b;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.util.Arrays;


public class MainClient extends JFrame {

	private JPanel contentPane;
	public CardLayout cl = new CardLayout(0,0);
	public GameUI_b gameUI = new GameUI_b();
	public NameUI_b nameUI = new NameUI_b();
	public ConnectUI_b connectUI = new ConnectUI_b();
	public WelcomeUI_b welcomeUI = new WelcomeUI_b();
	public static GameController_b gc;

	public static void main(String[] args) {

		MainClient frame;
		frame = new MainClient();
		frame.setVisible(true);	
		
		gc = new GameController_b();
		gc.mainClient = frame;
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

	public MainClient() {
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
	}

	void changeCard(String cardName){
		System.out.println("Showing "+cardName);
		cl.show(contentPane, cardName);
	}
	
}
