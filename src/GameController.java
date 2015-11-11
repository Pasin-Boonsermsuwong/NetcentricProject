import java.awt.EventQueue;
import java.time.Instant;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GameController {

	final int TURNTIME = 60;
	boolean isFirstPlayer;
	long seed;
	String playerName;
	String opponentName;
	int scorePlayer = 0;
	int scoreOpponent = 0;
	boolean activeTurn = false;

	public MainFrame mainFrame;
	public UIwindow gameUI;
	public NameUI nameUI;
	public ConnectUI connectUI;

	Timer timer;
	public long elapsedTime_player;
	public long elapsedTime_opponent;

	public enum GameState {
		ENTER_NAME,
		ENTER_IP,
		GAME_WAITING,
		GAME_PLAYING,
		GAME_FINISHED
	}

	public static void main(String[] args) {
		/*
		String s = "1/n2389tj9028t/n23333";
		System.out.println(Arrays.toString(s.split("/n")));
		 */

		UIwindow gameUI = new UIwindow();
		JFrame f= new JFrame();
		f.add(gameUI);
		f.setVisible(true);


		GameController gc = new GameController();
		gc.gameUI = gameUI;
		gameUI.gc = gc;
		gc.playerName = "LENON";
		gc.opponentName = "LEPAN";
		gc.seed = (long) (Math.random()*900);
		
		gameUI.setName(gc.playerName, gc.opponentName);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameUI.setQuestion(NumberGenerator.generate(gc.seed,true));	
		gc.startTurn();

	}

	public void GameStateUpdate(GameState gs) {
		// handle update
		switch(gs) {
		case ENTER_NAME:
			mainFrame.changeCard("nameUI");
			break;
		case ENTER_IP:
			mainFrame.changeCard("connectUI");
			break;
		case GAME_WAITING:
			mainFrame.changeCard("gameUI");
			waitTurn();
			break;
		case GAME_PLAYING:
			mainFrame.changeCard("gameUI");
			startTurn();
			break;
		case GAME_FINISHED:
			mainFrame.changeCard("gameUI");
			
			break;
		}
		//TODO: send data TYPE 1/2
	}

	public void setUIwindow(UIwindow window){
		this.gameUI = window;
		window.gc = this;
	}
	/**
	 * @param isFirstPlayer
	 */
	public void init(long seed,boolean isFirstPlayer){
		this.isFirstPlayer = isFirstPlayer;
		this.seed = seed;

		if(isFirstPlayer){
			GameStateUpdate(GameState.GAME_PLAYING);
		}else GameStateUpdate(GameState.GAME_WAITING);
	}
	private void finishedTurn(){
		activeTurn = false;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.setEnableOperatorButtons(false);
		gameUI.setEnableNumberButtons(false);
		gameUI.setButtons("-");
		gameUI.currentPlayerLabel.setText("-");
		
	}
	private void waitTurn(){	//START WAITING TURN (OTHER PLAYER IS PLAYING)
		activeTurn = false;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.setEnableOperatorButtons(false);
		gameUI.setEnableNumberButtons(false);
		gameUI.setButtons("-");
		gameUI.currentPlayerLabel.setText(opponentName);
	}

	private void startTurn(){	//STARTING PLAYER'S TURN
		activeTurn = true;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.setEnableOperatorButtons(true);
		gameUI.setEnableNumberButtons(true);
		gameUI.setQuestion(NumberGenerator.generate(seed,true));
		gameUI.currentPlayerLabel.setText(playerName);
		elapsedTime_player = Instant.now().toEpochMilli();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = TURNTIME;
			public void run() {
				System.out.println("Time left: "+i--);
				gameUI.timeLabel.setText(""+i);
				if (i<= 0||!activeTurn){
					if(activeTurn)GameController.this.endTurn(false);

					System.out.println("Timer Stopped");
					timer.cancel();
				}                
			}
		}, 0, 1000);

	}
	public void endTurn(boolean FinishOnTime){
		if(!activeTurn){
			System.err.println("endTurn called when turn is not active");
			return;
		}
		activeTurn = false;
		gameUI.setEnableOperatorButtons(false);
		gameUI.setEnableNumberButtons(false);
		if(FinishOnTime){
			elapsedTime_player =  Instant.now().toEpochMilli() - elapsedTime_player;
			System.out.println("Elasped "+elapsedTime_player+" ms");    		
			JOptionPane.showMessageDialog(null, "Correct Answer!", "", JOptionPane.INFORMATION_MESSAGE);
		}else{
			elapsedTime_player = Long.MAX_VALUE;
			JOptionPane.showMessageDialog(null, "Time is up", "", JOptionPane.INFORMATION_MESSAGE);	
		}
		System.out.println("Elasped Time: "+elapsedTime_player);
		compareScore();
		//TODO: SEND DATA (type 3 elasped time)
	}

	//TODO: COMPARE SCORE WHEN RECEIVED TYPE 3
	public void compareScore(){		//update score if both players finished
		if(elapsedTime_player>0&&elapsedTime_opponent>0){	//both elapsed time are set
			if(elapsedTime_player==Long.MAX_VALUE&&elapsedTime_opponent==Long.MAX_VALUE){
				JOptionPane.showMessageDialog(null, "Both players cannot solve","Draw!",  JOptionPane.INFORMATION_MESSAGE);
			}
			else if(elapsedTime_player<elapsedTime_opponent){
				JOptionPane.showMessageDialog(null,  "Your time: "+elapsedTime_player+"/nOpponent time: "+elapsedTime_opponent,"You win!", JOptionPane.INFORMATION_MESSAGE);
				gameUI.p1score.setText(""+Integer.parseInt(gameUI.p1score.getText()) + 1);
			}else if(elapsedTime_player>elapsedTime_opponent){
				JOptionPane.showMessageDialog(null, "Opponent time: "+elapsedTime_opponent+"/nYour time: "+elapsedTime_player,"Opponent wins!",  JOptionPane.INFORMATION_MESSAGE);
				gameUI.p2score.setText(""+Integer.parseInt(gameUI.p2score.getText()) + 1);
			}else if(elapsedTime_player==elapsedTime_opponent){
				JOptionPane.showMessageDialog(null,  "Both player used "+elapsedTime_player/1000+" sec","Draw!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		elapsedTime_player = 0;
		elapsedTime_opponent = 0;
		//TODO: TEST THIS
	}
	
	public void startNextGame(){
		
	}
	
	
	
}
