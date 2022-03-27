package atmModel;

/**
 * ModelATM Class.
 * 
 * @author Daniele
 */
public class ModelATM {
    private int money;
    private final String pin;
    private final int pinAttempt; // num of pin insertion
    private final String[] transactions;
    
    public ModelATM() {
        this.money = 500;
        this.pin = "1234";
        this.pinAttempt = 3;
        this.transactions = new String[2];
        this.transactions[0] = "10/01/2022 100 Euro";
        this.transactions[1] = "12/02/2022 200 Euro";
    }
    
    // --- GETTERS AND SETTES ---
    /**
     * Returns the available money amount.
     * 
     * @return int available money.
     */
    public int getMoney() {
        return this.money;
    }
    
    /**
     * Sets the new value of available money.
     * 
     * @param money new balance
     */
    public void setMoney(int money) {
        this.money = money;
    }
    
    /**
     * Returns the correct pin.
     * 
     * @return String correct pin
     */
    public String getPin() {
        return this.pin;
    }
    
    /**
     * Returns the maximum number of pin insertion attempts allowed.
     * 
     * @return int maximum pin attempts
     */
    public int getPinAttempt() {
        return this.pinAttempt;
    }
    
    /**
     * Returns an array of strings with all transactions made.
     * 
     * @return String[] transactions made
     */
    public String[] getTransactions() {
        return this.transactions;
    }
}
