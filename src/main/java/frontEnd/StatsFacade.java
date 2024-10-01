package frontEnd;

import java.util.concurrent.CountDownLatch;
import javax.swing.JFrame;

public class StatsFacade {
	public StatsFacade() throws InterruptedException {
		CountDownLatch loginSignal = new CountDownLatch(1);
		PageLog login = PageLog.getInstance();
		login.setCDLatch(loginSignal);
		loginSignal.await();
		login.setVisible(false);
		JFrame frame = MainUI.getInstance();
		frame.setSize(1000, 700);
		frame.pack();
		frame.setVisible(true);
	}

}
