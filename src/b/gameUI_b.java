package b;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import a.NumberClass;
import a.NumberGenerator;


public class GameUI_b extends JPanel {

	final int numbers = 5;
//	private JPanel contentPane;
	private JPanel gamePanel;
	private JPanel namePanel;
	private JPanel dataPanel;
	private JPanel numberPanel;
	private JPanel outputPanel;
	private JPanel opPanel;
	private JLabel l1;
	private JLabel l2;
	public JLabel currentPlayerLabel;
	private Component rigidArea;
	private JButton buttonAdd;
	private JButton buttonMinus;
	private JButton buttonMulti;
	private JButton buttonDiv;
	private JTextField leftField;
	private JLabel l3;
	private JTextField rightField;
	private JLabel l00;
	private Component rigidArea_1;
	private JLabel l01;

	JButton[] buttons = new JButton[numbers];
	private JLabel lblTheResultIs;
	private Component horizontalGlue;
	private Component horizontalGlue_1;
	private Component rigidArea_2;
	private JLabel lblTimeLeft;
	private JLabel lblSec;
	private JButton buttonBack;
	
	public JLabel p1;
	public JLabel p2;
	public JLabel p1score;
	public JLabel p2score;
	public JLabel timeLabel;
	public JLabel resultLabel;
	
	GameController_b gc;
	private JPanel miscPanel;
	public JButton buttonNextGame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
	}

	/**
	 * Create the frame.
	 */
	public GameUI_b() {
		initGUI();
	}
	private void initGUI() {

	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 407);
	//	contentPane = new JPanel();
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
	//	setContentPane(contentPane);
		this.setLayout(new CardLayout(0, 0));

		gamePanel = new JPanel();
		this.add(gamePanel, "name_258739885609642");
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

		currentPlayerLabel = new JLabel("-");
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

		resultLabel = new JLabel("-");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(resultLabel);

		horizontalGlue_1 = Box.createHorizontalGlue();
		dataPanel.add(horizontalGlue_1);

		lblTimeLeft = new JLabel("Time left: ");
		lblTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeLeft.setFont(new Font("Tahoma", Font.PLAIN, 14));
		dataPanel.add(lblTimeLeft);

		timeLabel = new JLabel("-");
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
					JButton src = (JButton)arg0.getSource();
					boolean addSuccess = addToField(src.getText());
					if(addSuccess)src.setEnabled(false);
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
		rightField.setColumns(7);
		outputPanel.add(rightField);

		opPanel = new JPanel();
		gamePanel.add(opPanel);
		opPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		buttonAdd.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(buttonAdd);

		buttonMinus = new JButton("-");
		buttonMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		buttonMinus.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(buttonMinus);

		buttonMulti = new JButton("x");
		buttonMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		buttonMulti.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(buttonMulti);

		buttonDiv = new JButton("/");
		buttonDiv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToField(((JButton)arg0.getSource()).getText());
			}
		});
		buttonDiv.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(buttonDiv);
		
		buttonBack = new JButton("BACK");
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFromField();
			}
		});
		buttonBack.setFont(new Font("Tahoma", Font.BOLD, 20));
		opPanel.add(buttonBack);
		
		miscPanel = new JPanel();
		gamePanel.add(miscPanel);
		
		buttonNextGame = new JButton("START NEXT GAME");
		buttonNextGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gc.startNextGame_player = true;
				gc.startNextGame();
				((JButton)arg0.getSource()).setEnabled(false);
			}
		});
		buttonNextGame.setVisible(false);
		buttonNextGame.setEnabled(false);
		buttonNextGame.setFont(new Font("Tahoma", Font.BOLD, 20));
		miscPanel.add(buttonNextGame);
		
		p1score.setText("0");
		p2score.setText("0");

	}
	/*
	public void setName(String playerName,String opponentName){
		p1.setText(playerName);
		p2.setText(opponentName);
		p1score.setText("0");
		p2score.setText("0");
	}
	*/
	public void resetAnswerField(){
		rightField.setText("");
		leftField.setText("");
	}
	public void setQuestion(NumberClass nc){
		setButtons(nc);
		resultLabel.setText(""+nc.answer);
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
	public void setButtons(String s){
		for(int i = 0;i<buttons.length;i++){
			buttons[i].setText(s);
		}
	}
	public void setEnableNumberButtons(boolean enabled){
		for(int i = 0;i<buttons.length;i++){
			buttons[i].setEnabled(enabled);
		}
	}
	public void setEnableOperatorButtons(boolean enabled){
		buttonAdd.setEnabled(enabled);
		buttonMinus.setEnabled(enabled);
		buttonMulti.setEnabled(enabled);
		buttonDiv.setEnabled(enabled);
		buttonBack.setEnabled(enabled);
	}
	/**
	 * @param s
	 * @return false if addition in invalid for whatever reason
	 */
	boolean addToField(String s){
		String[] field = leftField.getText().trim().split(" ");
		if(field.length>=numbers*2-1)return false;
		//TODO: backspace button also clear answer

		if(isNumber(s)!=isNumber(field[field.length-1])){	//next input must be operator if previous is number - vice versa
			leftField.setText(leftField.getText() +" "+s);
		}else return false;

		if(leftField.getText().trim().split(" ").length%2==1){		// if length % 2 ==1 , calculate answer
			field = leftField.getText().trim().split(" ");	//update array
			StringBuilder sb = new StringBuilder();
			for(int i= 0;i<field.length;i++){
				if(isNumber(field[i])){
					sb.append(field[i]);
				}else
					sb.append(field[i]);
			}
			rightField.setText(""+NumberGenerator.calculateAnswerDouble(sb.toString().replace('x','*')));
			rightField.setCaretPosition(0);
			
			// if all numbers are used, 
			if(field.length==numbers*2-1){
				//check if result is correct
			//	System.out.println("Final answer = "+rightField.getText()+" : "+resultLabel.getText());
				if(rightField.getText().equals(resultLabel.getText())){//then popup win message
					gc.endTurn(true);
				}			
			}	
		}
		return true;
	}
	void deleteFromField(){
		String s = leftField.getText();
		if(s.length()==0)return;
		boolean calculate = false;
		String deletedNumber = (s.substring(s.lastIndexOf(' '), s.length()).trim());
		System.out.println("DELETED:"+deletedNumber);
		if(isNumber(deletedNumber)){
			calculate = true;
			//undo disabling of specific button
			boolean found = false;
			for(int i = 0;i<buttons.length;i++){
				if(buttons[i].getText().equals(deletedNumber)&&buttons[i].isEnabled()==false){
					buttons[i].setEnabled(true);
					found = true;
					break;
				}
			}
			if(!found)System.err.println("UNDO BUTTON ENABLE NOT FOUND");
		}
		//recalculate answer
		if(!calculate){
			rightField.setText(""+NumberGenerator.calculateAnswerDouble(s.substring(0,s.lastIndexOf(' ')).replace('x','*')));
			rightField.setCaretPosition(0);
		}else{
			rightField.setText("");
		}
		leftField.setText(s.substring(0,s.lastIndexOf(' ')));
	}

	public boolean isNumber(String s){
		s = s.trim();
		try{
			Double.parseDouble(s);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

}
