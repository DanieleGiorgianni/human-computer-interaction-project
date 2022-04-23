package atmView;

import atmModel.Model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;

/**
 * ControllerBasic Class.
 *
 * @author Daniele
 */
public class ControllerBasic {
    
    private Model model;
    private ViewBasic view;
    private StatusATM status;
    private String money;
    
    /**
     * Constructor of ControllerBasic
     * 
     * @param model Model
     * @param view ViewBasic
     */
    public ControllerBasic(Model model, ViewBasic view) {
        this.model = model;
        this.view = view;
        this.status = StatusATM.MENU_BASIC;
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
     * Resets the View to the basic menu screen: basic menu message,
     * card insertion possibility off and money withdrawal deactivated.    
     */
    public void resetViewBasic() {
        view.updateScreen1(MessageATM.menuBasic);
        view.updateCardHolder(MessageATM.cardIn, false);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the Controller, setting its status to basic menu.  
     */
    public void resetControllerBasic() {
        setStatus(StatusATM.MENU_BASIC);
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
            case MENU_BASIC:
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
            // Retirar dinero
            case "Option 1":
                setStatus(StatusATM.MONEY);
                view.updateScreen1(MessageATM.moneyIn);
                break;
            // Consultar transacciones
            case "Option 2":
                setStatus(StatusATM.TRANSACTION);
                view.updateScreen1(MessageATM.transaction);
                String[] transactions = model.getTransactions().toArray(new String[0]);
                String message = "";
                for (String transaction : transactions)
                    message = message.concat(transaction + "\n");
                view.updateScreen2(message);
                break;
            // Consultar saldo
            case "Option 3":
                setStatus(StatusATM.BALANCE);
                view.updateScreen1(MessageATM.balance + model.getMoney() 
                        + MessageATM.balanceExit);
                break;
            // Salir
            case "Option 4":
                setStatus(StatusATM.MENU_BASIC);
                view.showMenuInit();
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of money request.Once the required money is available and 
     * in the correct format (multiples of 10 and not 0), it disburses the money
     * and updates the status of the Controller.
     * 
     * @param button Object pressed by the user
     */
    public void moneyRequest(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                setStatus(StatusATM.MENU_BASIC);
                view.updateScreen1(MessageATM.menuBasic);
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
                    if (m == 0) {
                        view.updateScreen1(MessageATM.moneyIn + money + "\n" 
                                + MessageATM.moneyZero + MessageATM.retry);
                    }
                    // multiple of 10
                    else if (m%10 == 0) {
                        Date date = Calendar.getInstance().getTime();
                        // Display a date in day, month, year format
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String today = formatter.format(date);
                        String transaction = today + " " + m + " Euro retirados";
                        model.setMoney(model.getMoney()-m);
                        model.addTransaction(transaction);
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
                setStatus(StatusATM.MENU_BASIC);
                view.updateScreen1(MessageATM.menuBasic);
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
            setStatus(StatusATM.MENU_BASIC);
            view.updateScreen1(MessageATM.menuBasic);
            view.updateScreen2(MessageATM.empty);
        }
    }
    
    /**
     * Delete the last char from a string.
     * 
     * @param string String to remove the last character
     * @return String shortened
     */
    public String deleteOneDigit(String string) {
        if (string.length() == 1 || string.isEmpty())
            return "";
        else
            return string.substring(0, string.length()-1);
    }
}
