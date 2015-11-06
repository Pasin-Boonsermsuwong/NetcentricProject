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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.SwingConstants;


public class UIwindow extends JFrame {

	final int numbers = 5;
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
	private JButton button;
	private JButton button_1;
	private JButton btnX;
	private JButton button_3;
	private JTextField leftField;
	private JLabel l3;
	private JTextField rightField;
	private JLabel p1;
	private JLabel l00;
	private Component rigidArea_1;
	private JLabel p2;
	private JLabel l01;
	private JLabel p1score;
	private JLabel p2score;
	JButton[] buttons = new JButton[numbers];
	private JLabel lblTheResultIs;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private JLabel resultLabel;
	private Component rigidArea_2;
	private JLabel lblTimeLeft;
	private JLabel timeLabel;
	private JLabel lblSec;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		//String s = " 2 x 4 x 5 x 6 x 3";
		//	System.out.println(Arrays.toString(s.trim().split(" ")));

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIwindow frame = new UIwindow();
					frame.setVisible(true);
					NumberClass nc = NumberGenerator.generate((long)(Math.random()*599));
					NumberGenerator.shuffleArray(nc.list);
					frame.setButtons(nc.list);
					frame.resultLabel.setText(""+nc.answer);
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
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));

		p1 = new JLabel("[player1]");
		p1.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(p1);

		l00 = new JLabel(":     ");
		l00.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(l00);

		p1score = new JLabel("[score1]");
		p1score.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(p1score);

		rigidArea_1 = Box.createRigidArea(new Dimension(50, 50));
		namePanel.add(rigidArea_1);

		p2 = new JLabel("[player2]");
		p2.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(p2);

		l01 = new JLabel(":     ");
		l01.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(l01);

		p2score = new JLabel("[score2]");
		p2score.setFont(new Font("Tahoma", Font.BOLD, 18));
		namePanel.add(p2score);

		dataPanel = new JPanel();
		gamePanel.add(dataPanel);
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));

		rigidArea = Box.createRigidArea(new Dimension(50, 50));
		dataPanel.add(rigidArea);

		l1 = new JLabel("This is ");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(l1);

		currentPlayerLabel = new JLabel("[Name]");
		currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currentPlayerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(currentPlayerLabel);

		l2 = new JLabel("'s turn");
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(l2);

		horizontalGlue = Box.createHorizontalGlue();
		dataPanel.add(horizontalGlue);

		lblTheResultIs = new JLabel("The result is ");
		lblTheResultIs.setHorizontalAlignment(SwingConstants.CENTER);
		lblTheResultIs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(lblTheResultIs);

		resultLabel = new JLabel("[Result]");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(resultLabel);

		horizontalGlue_1 = Box.createHorizontalGlue();
		dataPanel.add(horizontalGlue_1);

		lblTimeLeft = new JLabel("Time left: ");
		lblTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(lblTimeLeft);

		timeLabel = new JLabel("[Time]");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(timeLabel);

		lblSec = new JLabel(" sec");
		lblSec.setHorizontalAlignment(SwingConstants.CENTER);
		lblSec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(lblSec);

		rigidArea_2 = Box.createRigidArea(new Dimension(50, 50));
		dataPanel.add(rigidArea_2);

		numberPanel = new JPanel();
		gamePanel.add(numberPanel);
		FlowLayout fl_numberPanel = new FlowLayout(FlowLayout.CENTER, 15, 10);
		numberPanel.setLayout(fl_numberPanel);

		for(int i = 0;i<buttons.length;i++){
			buttons[i] = new JButton("-");
			buttons[i].setFont(new Font("Tahoma", Font.BOLD, 20));
			numberPanel.add(buttons[i]);
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addToField(((JButton)arg0.getSource()).getText());
				}
			});
		}

		outputPanel = new JPanel();
		gamePanel.add(outputPanel);
		outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		leftField = new JTextField();
		leftField.setEditable(false);
		leftField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		outputPanel.add(leftField);
		leftField.setColumns(20);

		l3 = new JLabel("=");
		l3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputPanel.add(l3);

		rightField = new JTextField();
		rightField.setEditable(false);
		rightField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rightField.setColumns(3);
		outputPanel.add(rightField);

		opPanel = new JPanel();
		gamePanel.add(opPanel);
		opPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button);

		button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button_1);

		btnX = new JButton("x");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		btnX.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(btnX);

		button_3 = new JButton("/");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(button_3);

	}

	public void setButtons(int[] num){
		if(num.length != buttons.length){
			System.err.println("#Buttons and #NumLength mismatch");
			return;
		}
		for(int i = 0;i<num.length;i++){
			buttons[i].setText(num[i]+"");
		}
	}
	public void setButtons(double[] num){
		if(num.length != buttons.length){
			System.err.println("#Buttons and #NumLength mismatch");
			return;
		}
		for(int i = 0;i<num.length;i++){
			buttons[i].setText(num[i]+"");
		}
	}
	public void setButtons(NumberClass n){
		double[] num = n.list;
		if(num.length != buttons.length){
			System.err.println("#Buttons and #NumLength mismatch");
			return;
		}
		for(int i = 0;i<num.length;i++){
			buttons[i].setText(num[i]+"");
		}
	}
	public void addToField(String s){
		String[] field = leftField.getText().trim().split(" ");
		if(field.length>=numbers*2-1)return;
		//TODO: backspace button also clear answer

		if(isNumber(s)!=isNumber(field[field.length-1])){	//next input must be operator if previous is number - vice versa
			leftField.setText(leftField.getText() +" "+s);
		}else return;

		if(leftField.getText().trim().split(" ").length%2==1){		// if length % 2 ==1 , calculate answer
			field = leftField.getText().trim().split(" ");	//update array
			StringBuilder sb = new StringBuilder();
			for(int i= 0;i<field.length;i++){
				if(isNumber(field[i])){
					//	sb.append(field[i]+".0");
					sb.append(field[i]);
				}else
					sb.append(field[i]);
			}
			System.out.println("FINAL: "+sb.toString().replace('x','*'));
			rightField.setText(""+NumberGenerator.calculateAnswerDouble(sb.toString().replace('x','*')));
			rightField.setCaretPosition(0);
		}
	}

	public boolean isNumber(String s){
		try{
			Double.parseDouble(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

}
