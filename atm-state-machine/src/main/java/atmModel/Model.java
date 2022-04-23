package atmModel;

import java.util.ArrayList;

/**
 * Model Class.
 * 
 * @author Daniele
 */
public class Model {
    private int money;
    private String pin;
    private final int pinAttempt; // num of pin insertion
    private ArrayList<String> transactions;
    
    public Model() {
        this.money = 500;
        this.pin = "1234";
        this.pinAttempt = 3;
        this.transactions = new ArrayList<String>();
        transactions.add("10/01/2022 100 Euro");
        transactions.add("12/02/2022 200 Euro");
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
     * Sets the new pin value.
     * 
     * @param pin new pin
     */
    public void setPin(String pin) {
        this.pin = pin;
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
    public ArrayList<String> getTransactions() {
        return this.transactions;
    }
    
    /**
     * Adds a transaction to array transactions
     * 
     * @param transaction String to add
     */
    public void addTransaction(String transaction) {
        this.transactions.add(transaction);
    }
}
