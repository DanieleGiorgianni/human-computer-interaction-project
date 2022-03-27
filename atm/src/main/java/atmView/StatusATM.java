package atmView;

/**
 * Enum containing all states of the Controller.
 * 
 * @author Daniele
 */
public enum StatusATM {
    CARD,   // card insertion
    PIN,    // pin request
    MENU,   // main menu
    MONEY,  // dispense money
    TRANSACTION,    // control transactions
    BALANCE,    // balance check
    EXIT;   // exit
}
