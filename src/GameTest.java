import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.EventQueue;


public class GameTest extends JFrame{
	private NameUI nameUI;
	private ConnectUI connectUI;
	
	public static JPanel contentPane;
	public static CardLayout cardLayout;
	
	public GameTest() {
		contentPane = new JPanel();
		cardLayout = new CardLayout();
		
		nameUI = new NameUI();
		cardLayout.addLayoutComponent(nameUI, "name");
		connectUI = new ConnectUI();
		cardLayout.addLayoutComponent(connectUI, "connect");
		
		contentPane.add(nameUI);
		contentPane.add(connectUI);
		
		contentPane.setLayout(cardLayout);
		cardLayout.show(contentPane, "name");
		this.add(contentPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	public static void main (String [] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameTest frame = new GameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}