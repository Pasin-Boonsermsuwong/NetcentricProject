import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;


public class UIwindow extends JFrame {

	private JPanel contentPane;
	private JPanel gamePanel;
	private JPanel namePanel;
	private JPanel dataPanel;
	private JPanel numberPanel;
	private JPanel outputPanel;
	private JPanel opPanel;
	private JLabel l1;
	private JLabel l2;
	private JLabel currentPlayerLabel;
	private Component rigidArea;
	private JButton nButton1;
	private JButton nButton3;
	private JButton nButton4;
	private JButton nButton5;
	private JButton nButton2;
	private JButton button;
	private JButton button_1;
	private JButton btnX;
	private JButton button_3;
	private JTextField leftField;
	private JLabel l3;
	private JTextField rightField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIwindow frame = new UIwindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UIwindow() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		gamePanel = new JPanel();
		contentPane.add(gamePanel, "name_258739885609642");
		gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		
		namePanel = new JPanel();
		gamePanel.add(namePanel);
		
		dataPanel = new JPanel();
		gamePanel.add(dataPanel);
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
		
		l1 = new JLabel("This is ");
		l1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(l1);
		
		currentPlayerLabel = new JLabel("[Name]");
		currentPlayerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(currentPlayerLabel);
		
		l2 = new JLabel("'s turn");
		l2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(l2);
		
		rigidArea = Box.createRigidArea(new Dimension(30, 50));
		dataPanel.add(rigidArea);
		
		numberPanel = new JPanel();
		gamePanel.add(numberPanel);
		FlowLayout fl_numberPanel = new FlowLayout(FlowLayout.CENTER, 15, 10);
		numberPanel.setLayout(fl_numberPanel);
		
		nButton1 = new JButton("-");
		nButton1.setFont(new Font("Tahoma", Font.BOLD, 20));
		numberPanel.add(nButton1);
		
		nButton2 = new JButton("-");
		nButton2.setFont(new Font("Tahoma", Font.BOLD, 20));
		numberPanel.add(nButton2);
		
		nButton3 = new JButton("-");
		nButton3.setFont(new Font("Tahoma", Font.BOLD, 20));
		numberPanel.add(nButton3);
		
		nButton4 = new JButton("-");
		nButton4.setFont(new Font("Tahoma", Font.BOLD, 20));
		numberPanel.add(nButton4);
		
		nButton5 = new JButton("-");
		nButton5.setFont(new Font("Tahoma", Font.BOLD, 20));
		numberPanel.add(nButton5);
		
		outputPanel = new JPanel();
		gamePanel.add(outputPanel);
		outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		leftField = new JTextField();
		leftField.setEditable(false);
		leftField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputPanel.add(leftField);
		leftField.setColumns(20);
		
		l3 = new JLabel("=");
		l3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputPanel.add(l3);
		
		rightField = new JTextField();
		rightField.setEditable(false);
		rightField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rightField.setColumns(3);
		outputPanel.add(rightField);
		
		opPanel = new JPanel();
		gamePanel.add(opPanel);
		opPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		button = new JButton("+");
		button.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button);
		
		button_1 = new JButton("-");
		button_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button_1);
		
		btnX = new JButton("x");
		btnX.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(btnX);
		
		button_3 = new JButton("/");
		button_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button_3);
	}

}
