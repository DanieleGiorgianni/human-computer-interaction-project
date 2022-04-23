package atmView;

/**
 * Enum containing all controller' states.
 * 
 * @author Daniele
 */
public enum StatusATM {
    CARD,   // card insertion
    PIN,    // pin request
    PIN_CHANGE, // pin change
    MENU_INIT,  // initial menu
    MENU_BASIC, // basic option menu
    MONEY,  // dispense money
    TRANSACTION,    // control transactions
    DO_TRANSACTION, // do transactions name request
    DO_TRANSACTION_IBAN, // do transactions IBAN request
    DO_TRANSACTION_DETAIL, // do transactions detail request
    DO_TRANSACTION_MONEY, // do transactions money request
    BALANCE,    // balance check
    CONFIRMATION,   // confirmation
    EXIT;   // exit
}
