import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	public CardLayout cl = new CardLayout(0,0);
	public UIwindow gameUI = new UIwindow();
	public NameUI nameUI = new NameUI();
	public ConnectUI connectUI = new ConnectUI();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		MainFrame frame;
		frame = new MainFrame();
		frame.setVisible(true);	
		
		GameController gc = new GameController();
		gc.mainFrame = frame;
		gc.gameUI = frame.gameUI;
		gc.nameUI = frame.nameUI;
		gc.connectUI = frame.connectUI;

		frame.gameUI.gc = gc;
		frame.nameUI.gc = gc;
		frame.connectUI.gc = gc;

		frame.changeCard("gameUI");
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
		contentPane.add(nameUI, "nameUI");
		contentPane.add(connectUI,"connectUI");
		contentPane.add(gameUI,"gameUI");		
	}

	void changeCard(String cardName){
		System.out.println("Showing "+cardName);
		cl.show(contentPane, cardName);
	}
	
}
