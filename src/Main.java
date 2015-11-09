import java.awt.EventQueue;


public class Main {

	public static void main(String[] args) {
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

}
