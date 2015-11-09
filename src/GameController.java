import java.awt.EventQueue;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;


public class GameController {
	
	final int TURNTIME = 60;
	long seed;
	String playerName;
	String opponentName;
    int scorePlayer = 0;
    int scoreOpponent = 0;
    boolean activeTurn = false;
    UIwindow window;
    
    Timer timer;
    long elaspedTime;
    
	public static void main(String[] args) {
				
		
		
		

		UIwindow frame = new UIwindow();
		frame.setVisible(true);
		
		
		GameController gc = new GameController();
		gc.setUIwindow(frame);
		gc.playerName = "LENON";
		gc.opponentName = "LEPAN";
		gc.seed = (long) (Math.random()*900);

		
		frame.setName(gc.playerName, gc.opponentName);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		frame.setQuestion(NumberGenerator.generate(gc.seed));	
		gc.startTurn();

	}
   
    public void setUIwindow(UIwindow window){
    	this.window = window;
    	window.gc = this;
    }
    private void init(boolean isFirstPlayer){
    	
    }
    private void startTurn(){
    	if(window ==null){
    		System.err.println("UIwindow is null in GameController");
    		return;
    	}
    	window.setEnableOperatorButtons(true);
    	window.setEnableNumberButtons(true);
    	window.setQuestion(NumberGenerator.generate(seed));
    	activeTurn = true;
    	window.currentPlayerLabel.setText(playerName);
    	elaspedTime = Instant.now().toEpochMilli();
    	timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = TURNTIME;
            public void run() {
                System.out.println("Time left: "+i--);
                window.timeLabel.setText(""+i);
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
    	window.setEnableOperatorButtons(false);
    	window.setEnableNumberButtons(false);
    	if(FinishOnTime){
    		elaspedTime =  Instant.now().toEpochMilli() - elaspedTime;
         	System.out.println("Elasped "+elaspedTime+" ms");    		
    	}else{
    		elaspedTime = Long.MAX_VALUE;
    	}
    	
     	
    }

   
}
