package atmView;

import atmModel.ModelATM;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * @author daniele
 */
public class ControllerATM {

    private ModelATM model;
    private ViewATM view;
    private StatusATM status;
    private String pin;
    private int pinCounter;
    private final int pinTry;
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
    public StatusATM getStatus() {
        return this.status;
    }

    public void setStatus(StatusATM status) {
        this.status = status;
    }

    // --- METHODS ---
    public void resetView() {
        view.updateScreen1(MessageATM.welcome);
        view.updateCardHolder(MessageATM.cardRequest, true);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    public void resetController() {
        pinReset();
        pinCounterReset();
        setStatus(StatusATM.CARD);
    }
    
    public void pinReset() {
        this.pin = "";
    }
    
    public void pinCounterReset() {
        this.pinCounter = 0;
    }
    
    public void moneyReset() {
        this.money = "";
    }
    
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
    
    public void cardRemoval(Object button) {
        if (button instanceof JToggleButton) { 
            JToggleButton btn = (JToggleButton) button;
            if("Pulse para retirar tarjeta".equals(btn.getText())) {
                resetView();
                resetController();
            }
        }
    }
    
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
    
    public void backToMenu(Object button) {
        JButton btn = (JButton) button;
        if ("Cancelar".equals(btn.getText())) {
            setStatus(StatusATM.MENU);
            view.updateScreen1(MessageATM.menu);
            view.updateScreen2(MessageATM.empty);
        }
    }
    
    public void operationEnd() {
        view.updateScreen1(MessageATM.end);
        view.updateCardHolder(MessageATM.cardRetire, true);
    }
    
    public String deleteOneDigit(String string) {
        if (string.length() == 1)
            return "";
        else
            return string.substring(0, string.length()-1);
    }
}
