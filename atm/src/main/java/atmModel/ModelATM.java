package atmModel;

import java.util.Arrays;

/**
 * @author daniele
 */
public class ModelATM {
    private int money;
    private String pin;
    private int pinAttempt; // num of pin insertion
    private String[] transactions;
    
    public ModelATM() {
        this.money = 500;
        this.pin = "1234";
        this.pinAttempt = 3;
        this.transactions = new String[2];
        this.transactions[0] = "10/01/2022 100 Euro";
        this.transactions[1] = "12/02/2022 200 Euro";
    }
    
    // --- GETTERS AND SETTES ---
    public int getMoney() {
        //int money = this.money;
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public String getPin() {
        //int pin = this.pin;
        return this.pin;
    }
    
    /*
    public void setPin(String pin) {
        this.pin = pin;
    }*/
    
    public int getPinAttempt() {
        return this.pinAttempt;
    }
    
    /*
    public void setPinAttempt(int pinAttempt) {
        this.pinAttempt = pinAttempt;
    }*/
    
    public String[] getTransactions() {
        //String[] transactions = this.transactions;
        return this.transactions;
    }
    
    public void setTransactions(String[] transactions) {
        this.transactions = Arrays.copyOf(transactions, transactions.length);
    }
}
