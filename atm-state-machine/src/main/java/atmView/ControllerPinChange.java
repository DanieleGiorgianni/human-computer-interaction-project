package atmView;

import atmModel.Model;
import javax.swing.JButton;

/**
 * ControllerPinChange Class.
 *
 * @author Daniele
 */
public class ControllerPinChange {
    
    private Model model;
    private ViewPinChange view;
    private StatusATM status;
    private String pin;
    
    /**
     * Constructor of ControllerPinChange
     * 
     * @param model Model
     * @param view ViewPinChange
     */
    public ControllerPinChange(Model model, ViewPinChange view) {
        this.model = model;
        this.view = view;
        this.status = StatusATM.CONFIRMATION;
        this.pin = "";
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
     * Resets the View to the pin change confirmation screen: confirmation message,
     * card insertion possibility off and money withdrawal deactivated.    
     */
    public void resetViewPinChange() {
        view.updateScreen1(MessageATM.pinChangeConfirm);
        view.updateCardHolder(MessageATM.cardIn, false);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the Controller, making it ready for the first user interaction.  
     */
    public void resetControllerPinChange() {
        pinReset();
        setStatus(StatusATM.CONFIRMATION);
    }
    
    /**
     * Resets the entered pin to an empty string.
     */
    public void pinReset() {
        this.pin = "";
    }
    
    /**
     * Defines which function to call depending on the button pressed 
     * and the state of the Controller.
     * 
     * @param button Object pressed by the user
     */
    public void buttonPressed(Object button) {
        switch(status) {
            case CONFIRMATION:
                confirmationCheck(button);
                break;
            case PIN_CHANGE:
                pinInsertion(button);
                break;
            case EXIT:
                setStatus(StatusATM.CONFIRMATION);
                view.showMenuInit();
            default:
                break;
        }
    }
    
    /**
     * Checks if the user wants to proceed with the pin change or not.
     * 
     * @param button Object pressed by the user
     */
    public void confirmationCheck(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            // Cambio del pin
            case "Option 1":
                setStatus(StatusATM.PIN_CHANGE);
                view.updateScreen1(MessageATM.pinChange);
                break;
            // Anulación
            case "Option 3":
                setStatus(StatusATM.CONFIRMATION);
                view.showMenuInit();
                break;
            default:
                break;
        }
    }
    
    /**
     * Management of new pin insertion.Once the pin is validated 
     * it updates the status of the Controller.
     * 
     * @param button Object pressed by the user
     */
    public void pinInsertion(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            case "Cancelar":
                setStatus(StatusATM.CONFIRMATION);
                view.showMenuInit();
                break;
            case "Borrar":
                pin = deleteOneDigit(pin);
                view.updateScreen1(MessageATM.pinChange + pin.replaceAll(".", "*"));
                break;
            case "Aceptar":
                // wrong new pin (same, empty, more than 4 digits)
                if (pin.equals(model.getPin()) || pin.equals("") || pin.length() != 4) {
                    view.updateScreen1(MessageATM.pinChange + pin.replaceAll(".", "*")
                            + MessageATM.pinChangeError);
                    pinReset();
                }
                // new pin ok
                else {
                    model.setPin(pin);
                    view.updateScreen1(MessageATM.pinChangeOk);
                    setStatus(StatusATM.EXIT);
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
                view.updateScreen1(MessageATM.pinChange + pin.replaceAll(".", "*"));
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
