import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WelcomeUI extends JPanel{
	
	Image img;
	
	public WelcomeUI() {
		setLayout(new GridLayout(2, 1, 0, 0));
		
		try {
			img = ImageIO.read(new File(this.getClass().getResource("/logo1.jpg").toURI()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.red);
		panel.setVisible(true);
		add(panel);
		
		img = new ImageIcon(this.getClass().getResource("/logo1size.jpg")).getImage();
		JLabel picLabel = new JLabel();
		picLabel.setIcon(new ImageIcon(img));
		panel_1.add(picLabel);
		this.setVisible(true);
	}

	public static void main(String [] args){
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WelcomeUI welcome = new WelcomeUI();
		frame.add(welcome);
		frame.setVisible(true);
	}
	
}
