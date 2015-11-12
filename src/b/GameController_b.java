package b;
import java.awt.EventQueue;
import java.time.Instant;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import a.GameUI;
import a.NameUI;
import a.WelcomeUI;


public class GameController_b {

	boolean isServer;
	
	final int TURNTIME = 60;
	boolean isFirstPlayer;		//true for server for first game - only used by server to determine who starts first
	long seed;
	String playerName;
	String opponentName;
	int scorePlayer = 0;
	int scoreOpponent = 0;
	boolean activeTurn = false;

	public MainClient mainClient;
	public GameUI gameUI;
	public NameUI nameUI;
	public ConnectUI_b connectUI;
	public WelcomeUI welcomeUI;

//	Timer timer;
	public long elapsedTime_player;
	public long elapsedTime_opponent;
	
	boolean startNextGame_player;
	boolean startNextGame_opponent;
	
	GameState gamestate;
	
	public int serverID = 0;
	public int clientID = 0;

	public enum GameState {
		WELCOME_SCREEN,
		ENTER_NAME,
		ENTER_IP,
		GAME_WAITING,
		GAME_PLAYING,
		GAME_FINISHED
	}

	public static void main(String[] args) {

	}

	public void GameStateUpdate(GameState gs) {
		gamestate = gs;
		System.out.println("GameState changed to "+gs);
		// handle update
		switch(gs) {
		case WELCOME_SCREEN:
			mainClient.changeCard("welcomeUI");
		case ENTER_NAME:
			mainClient.changeCard("nameUI");
			break;
		case ENTER_IP:
			mainClient.changeCard("connectUI");
			break;
		case GAME_WAITING:
			mainClient.changeCard("gameUI");
			waitTurn();
			break;
		case GAME_PLAYING:
			mainClient.changeCard("gameUI");
			startTurn();
			break;
		case GAME_FINISHED:
			mainClient.changeCard("gameUI");
			finishedTurn();
			break;
		}
		//TODO: send data TYPE 1/2
	}

	public void generateSeed(){
		// copied from main of this class
		this.seed = (long) (Math.random()*900);
	}
	
	public void setUIwindow(GameUI window){
		this.gameUI = window;
		window.gc = this;
	}
	public void setPlayerName(String s){
		this.playerName = s;
		gameUI.p1.setText(s);
	}
	public void setOpponentName(String s){
		this.opponentName = s;
		gameUI.p2.setText(s);
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
	private void finishedTurn(){	//both player finished
		activeTurn = false;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.resetAnswerField();
		gameUI.resultLabel.setText("-");
		gameUI.setEnableOperatorButtons(false);
		gameUI.setEnableNumberButtons(false);
		gameUI.setButtons("-");
		gameUI.currentPlayerLabel.setText("-");
		
		gameUI.buttonNextGame.setVisible(true);
		gameUI.buttonNextGame.setEnabled(true);
		
	}
	private void waitTurn(){	//START WAITING TURN (OTHER PLAYER IS PLAYING)
		activeTurn = false;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.resetAnswerField();
		gameUI.setEnableOperatorButtons(false);
		gameUI.setEnableNumberButtons(false);
		gameUI.setButtons("-");
		gameUI.currentPlayerLabel.setText(opponentName);
		
		gameUI.buttonNextGame.setVisible(false);
		gameUI.buttonNextGame.setEnabled(false);
	}

	private void startTurn(){	//STARTING PLAYER'S TURN
		activeTurn = true;
		if(gameUI ==null){
			System.err.println("UIwindow is null in GameController");return;
		}
		gameUI.resetAnswerField();
		gameUI.setEnableOperatorButtons(true);
		gameUI.setEnableNumberButtons(true);
		gameUI.setQuestion(NumberGenerator.generate(seed,true));
		gameUI.currentPlayerLabel.setText(playerName);
		elapsedTime_player = Instant.now().toEpochMilli();
		final Timer timer = new Timer();
		System.out.println("Timer started");
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = TURNTIME;
			public void run() {		
			//	System.out.println("Time left: "+i);
				gameUI.timeLabel.setText(""+i);
				i--;
				if (i<= 0||!activeTurn){
					if(activeTurn)GameController_b.this.endTurn(false);
					System.out.println("Timer Stopped");
					timer.cancel();
				//	return;
				}                
			}
		}, 0, 1000);

		gameUI.buttonNextGame.setVisible(false);
		gameUI.buttonNextGame.setEnabled(false);
	}
	/**called when complete turn, either by finding correct solution or time out
	 * @param FinishOnTime
	 */
	public void endTurn(boolean FinishOnTime){		
		gameUI.timeLabel.setText("-");
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
			JOptionPane.showMessageDialog(gameUI, "Correct Answer!", "", JOptionPane.INFORMATION_MESSAGE);
		}else{
			elapsedTime_player = Long.MAX_VALUE;
			JOptionPane.showMessageDialog(gameUI, "Time is up", "", JOptionPane.INFORMATION_MESSAGE);	
		}
		System.out.println("Elasped Time: "+elapsedTime_player);
		gameUI.timeLabel.setText("-");
		//TODO: SEND DATA (type 3 elasped time)
		
		//need to check that the player is 'server' or 'client'
		//need a boolean for this shit or maybe ku getting confused cuz running both server and client
		//for now ku added the boolean
		if(isServer){
			//flow i
			int total = connectUI.server.socketList.size();
			System.out.println("Total = "+total);
			for (int i = 0;i < total;i++){
				System.out.println("miniserver id ="+connectUI.server.socketList.get(i).id);
				System.out.println("server id ="+this.serverID);
				if(connectUI.server.socketList.get(i).id == this.serverID ){
					connectUI.server.socketList.get(i).sendData("3#"+Long.toString(elapsedTime_player));
				}
			}
			//connectUI.server.sendData("3#"+Long.toString(elapsedTime_player));
			//flow j
			GameStateUpdate(gamestate.GAME_WAITING);
		}else{
			connectUI.client.sendData("3#"+Long.toString(elapsedTime_player));
			GameStateUpdate(gamestate.GAME_WAITING);
		}
		compareScore();
	}

	public void compareScore(){		//update score if both players finished
		if(bothPlayerFinished()){
			double ep = elapsedTime_player/1000.0;
			double eo = elapsedTime_opponent/1000.0;
			if(elapsedTime_player==Long.MAX_VALUE&&elapsedTime_opponent==Long.MAX_VALUE){
				JOptionPane.showMessageDialog(gameUI, "Both players cannot solve","Draw!",  JOptionPane.INFORMATION_MESSAGE);
			}
			else if(elapsedTime_player<elapsedTime_opponent){
				gameUI.p1score.setText(""+(Integer.parseInt(gameUI.p1score.getText())+1));
				if(elapsedTime_opponent==Long.MAX_VALUE){
					JOptionPane.showMessageDialog(gameUI,  "Your time: "+ep+" s\nOpponent time: "+"Cannot solve","You win!", JOptionPane.INFORMATION_MESSAGE);
				}else JOptionPane.showMessageDialog(gameUI,  "Your time: "+ep+" s\nOpponent time: "+eo+" s","You win!", JOptionPane.INFORMATION_MESSAGE);
			}else if(elapsedTime_player>elapsedTime_opponent){
				gameUI.p2score.setText(""+(Integer.parseInt(gameUI.p2score.getText())+1));
				if(elapsedTime_player==Long.MAX_VALUE){
					JOptionPane.showMessageDialog(gameUI, "Opponent time: "+eo+" s\nYour time: "+"Cannot solve","Opponent wins!",  JOptionPane.INFORMATION_MESSAGE);
				}else JOptionPane.showMessageDialog(gameUI, "Opponent time: "+eo+" s\nYour time: "+ep+" s","Opponent wins!",  JOptionPane.INFORMATION_MESSAGE);	
			}else if(elapsedTime_player==elapsedTime_opponent){
				JOptionPane.showMessageDialog(gameUI,  "Both player used "+ep+" s","Draw!", JOptionPane.INFORMATION_MESSAGE);
			}
			//RESET SCORE
			elapsedTime_player = 0;
			elapsedTime_opponent = 0;
			GameStateUpdate(gamestate.GAME_FINISHED);
		}
	
		//TODO: TEST THIS
	}
	public boolean bothPlayerFinished(){
		System.out.println("bothPlayerFinished p:"+elapsedTime_player+" o:"+elapsedTime_opponent);
		if(elapsedTime_player>0&&elapsedTime_opponent>0)return true;
		return false;
	}
	public void startNextGame(){		//when start next game button pushed / received startNextGameData
		
		connectUI.client.sendData("4");	
	}
	
	public void isServer(){
		
	}
	
}
