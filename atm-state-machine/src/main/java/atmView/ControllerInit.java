package atmView;

import atmModel.Model;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * ControllerInit Class.
 *
 * @author Daniele
 */
public class ControllerInit {
    
    private Model model;
    private ViewInit view;
    private StatusATM status;
    private String pin;
    private int pinCounter; // pin insertion attempts
    private final int pinTry; // pin insertion attempts limit
    
    /**
     * Constructor of ControllerInit
     * 
     * @param model Model
     * @param view ViewInit
     */
    public ControllerInit(Model model, ViewInit view) {
        this.model = model;
        this.view = view;
        this.status = StatusATM.CARD;
        this.pin = "";
        this.pinCounter = 0;
        this.pinTry = model.getPinAttempt();
    }
    
    /**
     * Constructor of ControllerInit at specific status
     * 
     * @param model Model
     * @param view ViewInit
     * @param status StatusATM
     */
    public ControllerInit(Model model, ViewInit view, StatusATM status) {
        this.model = model;
        this.view = view;
        this.status = status;
        this.pin = "";
        this.pinCounter = 0;
        this.pinTry = model.getPinAttempt();
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
    public void resetViewInit() {
        view.updateScreen1(MessageATM.welcome);
        view.updateCardHolder(MessageATM.cardRequest, true, false);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the View to the menu screen: initial menu message,
     * card insertion possibility off and money withdrawal deactivated.    
     */
    public void resetViewInitMenu() {
        view.updateScreen1(MessageATM.menuInit);
        view.updateCardHolder(MessageATM.cardIn, false, true);
        view.updateMoneyGiver(MessageATM.empty, false);
    }
    
    /**
     * Resets the Controller, making it ready for the first user interaction.  
     */
    public void resetControllerInit() {
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
            case MENU_INIT:
                menuInitSelection(button);
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
                view.updateCardHolder(MessageATM.cardIn, false, true);
            }
        }
        else
            resetViewInit();
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
                resetViewInit();
                resetControllerInit();
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
                    setStatus(StatusATM.MENU_INIT);
                    view.updateScreen1(MessageATM.menuInit);
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
     * Management of initial menu selection.Once the menu option is selected 
     * it updates the status of the Controller and calls the proper method to
     * show the correct view.
     * 
     * @param button Object pressed by the user
     */
    public void menuInitSelection(Object button) {
        JButton btn = (JButton) button;
        String btnText = btn.getText();
        switch(btnText) {
            // Realizar transacción
            case "Option 1":
                setStatus(StatusATM.MENU_INIT);
                //setStatus(StatusATM.DO_TRANSACTION);
                view.showDoTransaction();
                break;
            // Cambiar PIN
            case "Option 2":
                setStatus(StatusATM.MENU_INIT);
                //setStatus(StatusATM.PIN_CHANGE);
                view.showPinChange();
                break;
            // Operaciones base
            case "Option 3":
                setStatus(StatusATM.MENU_INIT);
                //setStatus(StatusATM.MENU_BASIC);
                view.showMenuBasic();
                break;
            // Salir
            case "Option 4":
                setStatus(StatusATM.EXIT);
                operationEnd();
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
            setStatus(StatusATM.MENU_INIT);
            view.updateScreen1(MessageATM.menuInit);
            view.updateScreen2(MessageATM.empty);
        }
    }
    
    /**
     * Defines the View in termination operation phase.
     */
    public void operationEnd() {
        view.updateScreen1(MessageATM.end);
        view.updateCardHolder(MessageATM.cardRetire, true, true);
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
