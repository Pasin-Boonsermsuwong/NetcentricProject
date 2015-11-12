import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class WelcomeUI extends JPanel{
	
	BufferedImage welcome,logo;
	GameController gc;
	
	public WelcomeUI() {
		setLayout(new GridLayout(3, 1, 0, 0));
		
		
		try {
			welcome = ImageIO.read(getClass().getResource("welcome_update.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setIcon(new ImageIcon(welcome));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setVisible(true);
		panel.setBackground(Color.white);
		panel.add(welcomeLabel,BorderLayout.CENTER);
		add(panel);
		
		//JPanel panel_2 = new JPanel();
		//add(panel_2);
		
		try {
			logo = ImageIO.read(getClass().getResource("resized_logo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		JLabel logoLabel = new JLabel();
		logoLabel.setIcon(new ImageIcon(logo));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.add(logoLabel);
		add(panel_1);
		
		ImagePanel imgPanel = new ImagePanel();
		add(imgPanel);
		imgPanel.setLayout(new GridLayout(1,3,0,0));
		JPanel dummy1 = new JPanel();
		dummy1.setBackground(Color.WHITE);
		imgPanel.add(dummy1);
		JPanel textPanel = new JPanel();
		imgPanel.add(textPanel);
		JPanel bulbPanel = new JPanel();
		bulbPanel.setLayout(new BorderLayout());
		imgPanel.add(bulbPanel);
		
		textPanel.setLayout(new GridLayout(2,1,0,0));
		JPanel textNorth = new JPanel();
		textNorth.setLayout(new BorderLayout());
		textNorth.setBackground(Color.WHITE);
		JPanel textSouth = new JPanel();
		textSouth.setLayout(new BorderLayout());
		textSouth.setBackground(Color.WHITE);
		textPanel.add(textNorth);
		textPanel.add(textSouth);
		JLabel label = new JLabel();
		textNorth.add(label, BorderLayout.SOUTH);
		Font labelFont = label.getFont();
		label.setFont(new Font(labelFont.getName(), Font.PLAIN,22 ));
		JLabel label2 = new JLabel();
		label2.setFont(new Font(label2.getName(),Font.PLAIN,22));
		textSouth.add(label2,BorderLayout.NORTH);
		label.setText("Press LightBulb");
		label2.setText("to Continue");
		
		LightBulb button = new LightBulb();
		button.setSize(bulbPanel.getWidth(), bulbPanel.getHeight());
		bulbPanel.add(button,BorderLayout.CENTER);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("Clicked");
				//System.out.println("Height = "+button.getHeight());
				//System.out.println("Width = "+button.getWidth());
				gc.GameStateUpdate(gc.gamestate.ENTER_NAME);
			}
			
		});
		
		//mg = new ImageIcon(this.getClass().getResource("/logo1size.jpg")).getImage();
		
		//JPanel panel_3 = new JPanel();
		//panel_1.add(panel_3);
		//JLabel picLabel = new JLabel();
		//panel_3.add(picLabel);
		//picLabel.setIcon(new ImageIcon(img));
		//this.setVisible(true);
		
		//JButton btn = new JButton("GO");
		//panel_3.add(btn);
	}

	public static void main(String [] args){
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WelcomeUI welcome = new WelcomeUI();
		frame.getContentPane().add(welcome);
		frame.setVisible(true);
	}
	
	class LightBulb extends JButton{
		BufferedImage light,dark;
		boolean isLight= false;
		public LightBulb(){
			super();
			try {
				light = ImageIO.read(getClass().getResource("button_bulb1l.jpg"));
				dark = ImageIO.read(getClass().getResource("button_bulb2.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("LIGHT DARK ERROR");
				e.printStackTrace();
				return;
			}
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(isLight){
				g.drawImage(dark,0,0,this.getWidth(),this.getHeight(),this);
				isLight = false;
			}else{
				g.drawImage(light, 0, 0,this.getWidth(),this.getHeight(), null);
				isLight = true;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	class ImagePanel extends JPanel{
		BufferedImage b;
		public ImagePanel(){
			super();
			try {
				b = ImageIO.read(getClass().getResource("logo1size.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if(b!=null){
				g.drawImage(b, 0, 0, this);
			}
			repaint();
		}
		
		
	}
	
}
