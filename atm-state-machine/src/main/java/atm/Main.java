package atm;

import atmView.ATMStateMachine;

/**
 * Main Class.
 * 
 * @author Daniele
 */
public class Main {
    
    private static ATMStateMachine atmStateMachine;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {             
        atmStateMachine = new ATMStateMachine();
    }
    
    public static ATMStateMachine getATMStateMachine() {
        return atmStateMachine;
    }
}
