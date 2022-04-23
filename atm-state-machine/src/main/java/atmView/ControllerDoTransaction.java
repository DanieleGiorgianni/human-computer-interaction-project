package atmView;

import atmModel.Model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import org.apache.commons.validator.routines.IBANValidator;

/**
 * ControllerDoTransaction Class.
 *
 * @author Daniele
 */
public class ControllerDoTransaction {
    
    private Model model;
    private ViewDoTransaction view;
    private StatusATM status;
    private String name;
    private String iban;
    private String detail;
    private String money;
    
    /**
     * Constructor of ControllerDoTransaction
     * 
     * @param model Model
     * @param view ViewDoTransaction
     */
    public ControllerDoTransaction(Model model, ViewDoTransaction view) {
        this.model = model;
        this.view = view;
        this.status = StatusATM.DO_TRANSACTION;
        this.name = "";
        this.iban = "";
        this.detail = "";
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
     * Resets the View to the main transaction screen: do transaction name message,
     * card insertion possibility off and money withdrawal deactivated.    
    */
    public void resetView() {
        view.updateScreen1(MessageATM.doTransactionName);
        view.updateCardHolder(MessageATM.cardRequest, false);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the Controller, making it ready for the first user interaction.  
     */
    public void resetController() {
        this.name = "";
        this.iban = "";
        this.detail = "";
        this.money = "";
        setStatus(StatusATM.DO_TRANSACTION);
    }
    
    /**
     * Defines which function to call depending on the button pressed 
     * and the state of the Controller.
     * 
     * @param button Object pressed by the user
     */
    public void buttonPressed(Object button) {
        switch (status) {
            case DO_TRANSACTION:
                nameInsertion(button);
                break;
            case DO_TRANSACTION_IBAN:
                ibanInsertion(button);
                break;
            case DO_TRANSACTION_DETAIL:
                detailInsertion(button);
                break;
            case DO_TRANSACTION_MONEY:
                moneyInsertion(button);
                break;
            case CONFIRMATION:
                confirmationCheck(button);
                break;
            case EXIT:
                setStatus(StatusATM.DO_TRANSACTION);
                view.showMenuInit();
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of name insertion.Once the name is entered 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void nameInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                resetController();
                resetView();
                view.showMenuInit();
                break;
            case "Borrar":
                name = deleteOneDigit(name);
                view.updateScreen1(MessageATM.doTransactionName + name);
                break;
            case "Aceptar":
                // name entered
                if(!name.isEmpty()) {
                    // name valid
                    if(name.contains(" ") && name.length() > 2) {
                        setStatus(StatusATM.DO_TRANSACTION_IBAN);
                        view.updateScreen1(MessageATM.doTransactionIban);
                    }
                    else {
                        view.updateScreen1(MessageATM.doTransactionName + name +
                            MessageATM.nameInvalid);
                        name = "";
                    }
                }
                else {
                    view.updateScreen1(MessageATM.doTransactionName + name +
                            MessageATM.nameError);
                    name = "";
                }
                break;
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "F":
            case "G":
            case "H":
            case "I":
            case "J":
            case "K":
            case "L":
            case "M":
            case "N":
            case "O":
            case "P":
            case "Q":
            case "R":
            case "S":
            case "T":
            case "U":
            case "V":
            case "W":
            case "X":
            case "Y":
            case "Z":
                // name insertion
                name += btnText;
                view.updateScreen1(MessageATM.doTransactionName + name);
                break;
            case "_":
                // blank space insertion 
                // only if the string contains at least a char
                if(!name.isEmpty()) {
                    name += " ";
                    view.updateScreen1(MessageATM.doTransactionName + name);
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of data insertion.Once the pin is validated 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void ibanInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                resetController();
                resetView();
                break;
            case "Borrar":
                iban = deleteOneDigit(iban);
                view.updateScreen1(MessageATM.doTransactionIban + iban);
                break;
            case "Aceptar":
                // iban check
                boolean isIbanValid = IBANValidator.getInstance().isValid(iban);
                if (isIbanValid) {
                    setStatus(StatusATM.DO_TRANSACTION_DETAIL);
                    view.updateScreen1(MessageATM.doTransactionDetail);
                }
                // iban incorrect
                else {
                    view.updateScreen1(MessageATM.doTransactionIban + iban + MessageATM.ibanError);
                    iban = "";
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
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "F":
            case "G":
            case "H":
            case "I":
            case "J":
            case "K":
            case "L":
            case "M":
            case "N":
            case "O":
            case "P":
            case "Q":
            case "R":
            case "S":
            case "T":
            case "U":
            case "V":
            case "W":
            case "X":
            case "Y":
            case "Z":
                // iban insertion
                iban += btnText;
                view.updateScreen1(MessageATM.doTransactionIban + iban);
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of detail insertion.Once the pin is validated 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
    */
    public void detailInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                resetController();
                resetView();
                break;
            case "Borrar":
                detail = deleteOneDigit(detail);
                view.updateScreen1(MessageATM.doTransactionDetail + detail);
                break;
            case "Aceptar":
                // detail entered
                if(!detail.isEmpty()) {
                    if(detail.contains(" ") && detail.length() > 1) {
                        setStatus(StatusATM.DO_TRANSACTION_MONEY);
                        view.updateScreen1(MessageATM.doTransactionMoney);
                    }
                    else {
                        view.updateScreen1(MessageATM.doTransactionDetail + detail +
                            MessageATM.detailInvalid);
                        detail = "";
                    }
                }
                else {
                    view.updateScreen1(MessageATM.doTransactionDetail + detail +
                            MessageATM.detailError);
                    detail = "";
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
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
            case "F":
            case "G":
            case "H":
            case "I":
            case "J":
            case "K":
            case "L":
            case "M":
            case "N":
            case "O":
            case "P":
            case "Q":
            case "R":
            case "S":
            case "T":
            case "U":
            case "V":
            case "W":
            case "X":
            case "Y":
            case "Z":
                // detail insertion
                detail += btnText;
                view.updateScreen1(MessageATM.doTransactionDetail + detail);
                break;
            case "_":
                // blank space insertion
                // only if the string contains at least a char
                if(!detail.isEmpty()) {
                    detail += " ";
                    view.updateScreen1(MessageATM.doTransactionDetail + detail);
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of money insertion.Once the required money is available and 
     * in the correct format (multiples of 10 and not 0), it sends the money
     * and updates the status of the Controller.
     * 
     * @param button Object pressed by the user
     */
    public void moneyInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                resetController();
                resetView();
                break;
            case "Borrar":
                money = deleteOneDigit(money);
                view.updateScreen1(MessageATM.doTransactionMoney + money);
                break;
            case "Aceptar":
                // money check
                int m = Integer.parseInt(money);
                // enough money
                if ((model.getMoney()-m) >= 0) {
                    if (m == 0) {
                        view.updateScreen1(MessageATM.doTransactionMoney + money + "\n" 
                                + MessageATM.moneyZero + MessageATM.retry);
                    }
                    // multiple of 10
                    else if (m%10 == 0) {
                        setStatus(StatusATM.CONFIRMATION);
                        view.updateScreen1("Enviar " + money + " Euro a " + name + "\n"
                                + "IBAN " + iban + "\n" 
                                + MessageATM.doTransactionConfirm);
                    }
                    else {
                        view.updateScreen1(MessageATM.doTransactionMoney + money + "\n" 
                                + MessageATM.moneyWrong + MessageATM.retry);
                        money = "";
                    }
                }
                // not enough money
                else {
                    // multiple of 10
                    if (m%10 == 0) {
                        view.updateScreen1(MessageATM.doTransactionMoney + money + "\n" 
                                + MessageATM.moneyNo + MessageATM.retry);
                    }
                    else {
                        view.updateScreen1(MessageATM.doTransactionMoney + money + "\n" 
                                + MessageATM.moneyNo + MessageATM.moneyWrong 
                                + MessageATM.retry);
                    }
                    money = "";
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
                // money insertion
                money += btnText;
                view.updateScreen1(MessageATM.doTransactionMoney + money);
                break;
            default:
                break;
        }
    }
    
    /**
     * Checks if the user wants to proceed with the transaction or not.
     * 
     * @param button Object pressed by the user
     */
    public void confirmationCheck(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            // Envio dinero
            case "Option 1":
                int m = Integer.parseInt(money);
                Date date = Calendar.getInstance().getTime();
                // Display a date in day, month, year format
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String today = formatter.format(date);
                String transaction = today + " " + m + " Euro enviados";
                model.setMoney(model.getMoney()-m);
                model.addTransaction(transaction);
                setStatus(StatusATM.EXIT);
                view.updateScreen1(MessageATM.doTransactionOk);
                break;
            // Anulación
            case "Option 3":
                setStatus(StatusATM.DO_TRANSACTION);
                view.showMenuInit();
                break;
            default:
                break;
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
