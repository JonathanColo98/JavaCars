// Soluzione proposta da Aliaksei Shapiolkin

public class TrafficControllerTurn { 
	
	private static volatile boolean waitingR, waitingL, turn = false;
	private static volatile int entredFromLeft, entredFromRight = 0;
	
	public void enterLeft() {
		synchronized (this) {
			waitingL = true;
			while (entredFromRight > 0 || (waitingR && !turn)) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitingL = false;
			entredFromLeft++;
		}
	}

	public void enterRight() {
		synchronized (this) {		
			waitingR = true;
			while (entredFromLeft > 0 || (waitingL && turn)) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitingR = false;
			entredFromRight++;
		}
	}

	public void leaveLeft() {
		synchronized (this) {
			entredFromRight--;
			turn = true;
			notifyAll();			
		}
	}

	public void leaveRight() {
		synchronized (this) {			
			entredFromLeft--;
			turn = false;
			notifyAll();
		}
	}

}