package atmView;

/**
 * Class containing all messages displayed by the View.
 * 
 * @author Daniele
 */

public class MessageATM {
    public static String empty = "";
    public static String welcome = "Bienvenido.\n"
                + "Para interactuar con este cajero,\n"
                + "introduzca su tarjeta.\n";
    
    public static String pinIn = "Bienvenido.\n"
                + "Para continuar con este cajero,\n"
                + "introduzca su pin seguido del botón Aceptar.\n\n"
                + "PIN: ";
    public static String pinFail = "\nPin incorrecto.\n"
                + "Intentos restantes: ";
    public static String pinStop = "\nPin incorrecto.\n"
                + "No más intentos, retirar la tarjeta\n";
    
    public static String cardRequest = "Introducir Tarjeta";
    public static String cardIn = "Tarjeta introducida";
    public static String cardRetire = "Pulse para retirar tarjeta";
    
    public static String menu = "Menú principal.\n\n" 
                + "Seleccione la operación que desea realizar.\n\n"
                + "- Retirar dinero                         Consultar saldo -\n\n"
                + "- Consultar transacciones                           Salir -";
    
    public static String moneyIn = "Introduzca la cantidad de dineroque se desea\n"
                + "retirar utilizando el teclado numérico,\n"
                + "seguido del botón Aceptar.\n"
                + "Pulsar el botón Cancelar para volver al menú.\n"
                + "La cifra debe ser un múltiplo de 10.\n\n"
                + "Euro: ";
    public static String moneyOut = "Desembolso de dinero.\n"
                + "Retirar de la ventanilla de abajo.";
    public static String moneyCount = "Desembolso de dinero en ";
    public static String moneyReady = "Pulse para retirar el dinero";
    public static String moneyNo = "Saldo insuficiente.\n";
    public static String moneyWrong = "La cifra introducida no es un múltiplo de 10.\n";
    
    public static String balance = "Este es su saldo disponible\n\n"
                + "Euro: ";
    public static String balanceExit = "\n\nPulsar el botón Cancelar para volver al menú.";
    
    public static String transaction = "Sus transacciones se muestran\n"
                + "en la pantalla inferior a la derecha.\n\n"
                + "Pulsar el botón Cancelar para volver al menú.";
    
    public static String retry = "Intentar de nuevo.";
    
    public static String end = "Operación terminada, retirar la tarjeta.\n";
}
