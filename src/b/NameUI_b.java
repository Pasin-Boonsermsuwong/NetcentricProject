package b;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class NameUI_b extends JPanel{
	
	public GameController_b gc;
	private JTextField textField;

	
	public static String name;
	
	public NameUI_b(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("Enter Your Name");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_4.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("GO!");
		btnNewButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				name = textField.getText();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null,
							"Please enter a name",
							"No name",
							JOptionPane.ERROR_MESSAGE);
				}else{
					System.out.println("Name : "+name);
					setName(name);
					gc.GameStateUpdate(gc.gamestate.ENTER_IP);
				}
			}
			
		});
		panel_1.add(btnNewButton);
		
	}
	
	public void setName(String name){
//		gc = MainFrame.gc;
		gc.playerName = name;
		gc.gameUI.p1.setText(name);
//		gc.isFirstPlayer = true;
		System.out.println("Set name :" + gc.playerName);
	}
	
}
