public class TrafficController {

private volatile int enteredFromLeft = 0;
private volatile int enteredFromRight = 0;
private volatile int waitingLeft = 0;
private volatile int waitingRight = 0;
private volatile int turn = 0; // turn = 0: precedence from Left, turn = 1: precedence from Right

    synchronized void enterLeft() {  waitingLeft++;
                                     while(enteredFromRight>0 || (waitingRight>0 & turn==1)) try{ wait(); } catch (InterruptedException e) {};
                                     waitingLeft--;
                                     enteredFromLeft++;}

    synchronized void enterRight() { waitingRight++;
                                     while(enteredFromLeft>0 || (waitingLeft>0 & turn==0)) try{ wait(); } catch (InterruptedException e) {};
                                     waitingRight--;
                                     enteredFromRight++;}

    synchronized void leaveLeft() {  enteredFromRight--;
                                     if (waitingLeft>0) turn=0;
                                     if (enteredFromRight==0){
                                       notifyAll();} }

    synchronized void leaveRight() { enteredFromLeft--;
                                     if (waitingRight>0) turn=1;
                                     if (enteredFromLeft==0){
                                       notifyAll(); } }
}
