package atmView;

/**
 * Class containing all messages displayed by the Views.
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
    public static String pinChangeConfirm = "Cambio del pin.\n\n" 
                + "¿Quiere cambiar el pin de su tarjeta?\n\n"
                + "- Sí                                                                     No -";
    public static String pinChange = "Cambio del pin.\n"
                + "Para cambiar el pin de su tarjeta,\n"
                + "introduzca su nuevo pin seguido del botón Aceptar.\n\n"
                + "PIN: ";
    public static String pinChangeError = "\nPin incorrecto introducido!\n"
                + "Igual que el anterior, nulo o diferente de 4 dígitos.\n"
                + "Intenta de nuevo.";
    public static String pinChangeOk = "Pin cambiado con éxito.\n\n"
                + "Pulse cualquier botón para volver a la pantalla de inicio.";
    
    public static String cardRequest = "Introducir Tarjeta";
    public static String cardIn = "Tarjeta introducida";
    public static String cardRetire = "Pulse para retirar tarjeta";
    
    public static String menuInit = "Menú principal.\n\n" 
                + "Seleccione la operación que desea realizar.\n\n"
                + "- Realizar transacción                           Operaciones base -\n\n"
                + "- Cambiar PIN                                                     Salir -";
    public static String menuBasic = "Menú principal.\n\n" 
                + "Seleccione la operación que desea realizar.\n\n"
                + "- Retirar dinero                                      Consultar saldo -\n\n"
                + "- Consultar transacciones                                     Volver -";
    
    public static String moneyIn = "Introduzca la cantidad de dineroque se desea\n"
                + "retirar utilizando el teclado numérico,\n"
                + "seguido del botón Aceptar.\n"
                + "Pulsar el botón Cancelar para volver al menú.\n"
                + "La cifra debe ser un múltiplo de 10 y no cero.\n\n"
                + "Euro: ";
    public static String moneyOut = "Desembolso de dinero.\n"
                + "Retirar de la ventanilla de abajo.";
    public static String moneyCount = "Desembolso de dinero en ";
    public static String moneyReady = "Pulse para retirar el dinero";
    public static String moneyNo = "Saldo insuficiente.\n";
    public static String moneyWrong = "La cifra introducida no es un múltiplo de 10.\n";
    public static String moneyZero = "La cifra introducida no puede ser cero.\n";
    
    public static String balance = "Este es su saldo disponible\n\n"
                + "Euro: ";
    public static String balanceExit = "\n\nPulsar el botón Cancelar para volver al menú.";
    
    public static String transaction = "Sus transacciones se muestran\n"
                + "en la pantalla inferior a la derecha.\n\n"
                + "Pulsar el botón Cancelar para volver al menú.";
    public static String doTransactionName = "Ejecutar transacción bancaria. Paso 1 de 4.\n\n" 
                + "Introduzca el nombre y el apellido del destinatario,\n"
                + "seguido del botón Aceptar.\n\n";
    public static String doTransactionIban = "Ejecutar transacción bancaria. Paso 2 de 4.\n\n" 
                + "Introduzca el IBAN del destinatario,\n"
                + "seguido del botón Aceptar.\n\n";
    public static String doTransactionDetail = "Ejecutar transacción bancaria. Paso 3 de 4.\n\n" 
                + "Introduzca la descripción de la transacción,\n"
                + "seguido del botón Aceptar.\n\n";
    public static String doTransactionMoney = "Ejecutar transacción bancaria. Paso 4 de 4.\n\n" 
                + "Introduzca el dinero que quiere enviar,\n"
                + "seguido del botón Aceptar.\n\n";
    public static String doTransactionConfirm = "¿Quiere enviar este dinero?\n\n"
                + "- Sí                                                                     No -";
    public static String doTransactionOk = "Transacción ejecutada con éxito.\n\n"
                + "Pulse cualquier botón para volver a la pantalla de inicio.";
    public static String ibanError = "\nIBAN incorrecto introducido!\n"
                + "Intenta de nuevo.";
    public static String nameError = "\nIntroducir nombre y apellido!";
    public static String nameInvalid = "\nIntroducir nombre y apellido válido!";
    public static String detailError = "\nIntroducir la descripción de la transacción!";
    public static String detailInvalid = "\nIntroducir descripción válida!";
    
    public static String retry = "Intentar de nuevo.";
    
    public static String end = "Operación terminada, retirar la tarjeta.\n";
}
