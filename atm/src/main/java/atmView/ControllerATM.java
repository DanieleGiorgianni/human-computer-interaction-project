package atmView;

import atmModel.ModelATM;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * ControllerATM Class.
 * 
 * @author Daniele
 */
public class ControllerATM {

    private ModelATM model;
    private ViewATM view;
    private StatusATM status;
    private String pin;
    private int pinCounter; // pin insertion attempts
    private final int pinTry; // pin insertion attempts limit
    private String money;

    public ControllerATM(ModelATM model, ViewATM view) {
        this.model = model;
        this.view = view;
        this.status = StatusATM.CARD;
        this.pin = "";
        this.pinCounter = 0;
        this.pinTry = model.getPinAttempt();
        this.money = ""; 
    }

    // --- GETTERS AND SETTERS ---
    /**
     * Returns the current state of the Controller.
     * 
     * @return Enum StatusATM of current state
     */
    public StatusATM getStatus() {
        return this.status;
    }

    /**
     * Sets the current state of the controller.
     * 
     * @param status Enum StatusATM of the state to set
     */
    public void setStatus(StatusATM status) {
        this.status = status;
    }

    // --- METHODS ---
    /**
     * Resets the View to the main screen: welcome message,
     * card insertion possibility and money withdrawal deactivated.    
    */
    public void resetView() {
        view.updateScreen1(MessageATM.welcome);
        view.updateCardHolder(MessageATM.cardRequest, true);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the Controller, making it ready for the first user interaction.  
    */
    public void resetController() {
        pinReset();
        pinCounterReset();
        setStatus(StatusATM.CARD);
    }
    
    /**
     * Resets the entered pin to an empty string.
     */
    public void pinReset() {
        this.pin = "";
    }
    
    /**
     * Resets the number of pin insertion attempts to zero.
     */
    public void pinCounterReset() {
        this.pinCounter = 0;
    }
    
    /**
     * Resets the requested money to an empty string.
     */
    public void moneyReset() {
        this.money = "";
    }
    
    /**
     * Defines which function to call depending on the button pressed 
     * and the state of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void buttonPressed(Object button) {
        switch (status) {
            case CARD:
                cardInsertion(button);
                break;
            case PIN:
                pinInsertion(button);
                break;
            case MENU:
                menuSelection(button);
                break;
            case MONEY:
                moneyRequest(button);
                break;
            case BALANCE:
                backToMenu(button);
                break;
            case TRANSACTION:
                backToMenu(button);
                break;
            case EXIT:
                cardRemoval(button);
                break;
            default:
                break;
        }
    }

    /**
     * Check for proper card insertion and set up pin entry screen.
     * 
     * @param button Object pressed by the user
    */
    public void cardInsertion(Object button) {
        if (button instanceof JToggleButton) { 
            JToggleButton btn = (JToggleButton) button;
            if("Introducir Tarjeta".equals(btn.getText())) {
                setStatus(StatusATM.PIN);
                view.updateScreen1(MessageATM.pinIn);
                view.updateCardHolder(MessageATM.cardIn, false);
            }
        }
        else
            resetView();
    }
    
    /**
     * Check for proper card removal and reset View and Controller.
     * 
     * @param button Object pressed by the user
    */
    public void cardRemoval(Object button) {
        if (button instanceof JToggleButton) { 
            JToggleButton btn = (JToggleButton) button;
            if("Pulse para retirar tarjeta".equals(btn.getText())) {
                resetView();
                resetController();
            }
        }
    }
    
    /**
     * Management of pin insertion.Once the pin is validated 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void pinInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                setStatus(StatusATM.EXIT);
                operationEnd();
                break;
            case "Borrar":
                pin = deleteOneDigit(pin);
                view.updateScreen1(MessageATM.pinIn + pin.replaceAll(".", "*"));
                break;
            case "Aceptar":
                // pin check
                if (pin.equals(model.getPin())) {
                    setStatus(StatusATM.MENU);
                    view.updateScreen1(MessageATM.menu);
                }
                // pin incorrect
                else {
                    pinCounter++;
                    if (pinCounter != pinTry) {
                        view.updateScreen1(MessageATM.pinIn + pin.replaceAll(".", "*")
                                + MessageATM.pinFail + (pinTry - pinCounter));
                        pinReset();
                    }
                    else {
                        setStatus(StatusATM.EXIT);
                        view.updateScreen1(MessageATM.pinIn + pin.replaceAll(".", "*")
                                + MessageATM.pinStop);
                        setStatus(StatusATM.EXIT);
                        operationEnd();                    
                        
                    }
                }
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                // pin insertion
                pin += btnText;
                view.updateScreen1(MessageATM.pinIn + pin.replaceAll(".", "*"));
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of menu selection.Once the menu option is selected 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void menuSelection(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            // retirar dinero
            case "Option 1":
                setStatus(StatusATM.MONEY);
                view.updateScreen1(MessageATM.moneyIn);
                break;
            // consultar transacciones
            case "Option 2":
                setStatus(StatusATM.TRANSACTION);
                view.updateScreen1(MessageATM.transaction);
                String[] transactions = model.getTransactions();
                String message = "";
                for (String transaction : transactions)
                    message = message.concat(transaction + "\n");
                view.updateScreen2(message);
                break;

            // consultar saldo
            case "Option 3":
                setStatus(StatusATM.BALANCE);
                view.updateScreen1(MessageATM.balance + model.getMoney() 
                        + MessageATM.balanceExit);
                break;
            // salir
            case "Option 4":
                setStatus(StatusATM.EXIT);
                operationEnd();
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of money request.Once the required money is available and 
     * in the correct format (multiples of 10), it disburses the money and 
     * updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void moneyRequest(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                setStatus(StatusATM.MENU);
                view.updateScreen1(MessageATM.menu);
                break;
            case "Borrar":
                money = deleteOneDigit(money);
                view.updateScreen1(MessageATM.moneyIn + money);
                break;
            case "Aceptar":
                // money check
                int m = Integer.parseInt(money);
                // enough money
                if ((model.getMoney()-m) >= 0) {
                    // multiple of 10
                    if (m%10 == 0) {
                        model.setMoney(model.getMoney()-m);
                        view.updateScreen1(MessageATM.moneyOut);
                        view.updateMoneyGiver(MessageATM.moneyReady, true);
                    }
                    else {
                        view.updateScreen1(MessageATM.moneyIn + money + "\n" 
                                + MessageATM.moneyWrong + MessageATM.retry);
                    }
                }
                // not enough money
                else {
                    // multiple of 10
                    if (m%10 == 0) {
                        view.updateScreen1(MessageATM.moneyIn + money + "\n" 
                                + MessageATM.moneyNo + MessageATM.retry);
                    }
                    else {
                        view.updateScreen1(MessageATM.moneyIn + money + "\n" 
                                + MessageATM.moneyNo + MessageATM.moneyWrong 
                                + MessageATM.retry);
                    }
                }
                moneyReset();
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                // money insertion
                money += btnText;
                view.updateScreen1(MessageATM.moneyIn + money);
                break;
            case "Pulse para retirar el dinero":
                setStatus(StatusATM.MENU);
                view.updateScreen1(MessageATM.menu);
                view.updateMoneyGiver(MessageATM.empty, false);
                break;
            default:
                break;
        }
    }
    
    /**
     * Returns the Controller to the menu state following the pressed object.
     * 
     * @param button Object pressed by the user
    */
    public void backToMenu(Object button) {
        JButton btn = (JButton) button;
        if ("Cancelar".equals(btn.getText())) {
            setStatus(StatusATM.MENU);
            view.updateScreen1(MessageATM.menu);
            view.updateScreen2(MessageATM.empty);
        }
    }
    
    /**
     * Defines the View in termination operation phase.
    */
    public void operationEnd() {
        view.updateScreen1(MessageATM.end);
        view.updateCardHolder(MessageATM.cardRetire, true);
    }
    
    /**
     * Delete the last char from a string.
     * 
     * @param string String to remove the last character
     * @return String shortened
    */
    public String deleteOneDigit(String string) {
        if (string.length() == 1)
            return "";
        else
            return string.substring(0, string.length()-1);
    }
}
