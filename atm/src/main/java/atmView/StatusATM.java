package atmView;

/**
 *
 * @author daniele
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
