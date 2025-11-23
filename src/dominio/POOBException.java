package dominio;

public class POOBException extends Exception{
    public static final String PRIMERA_EXCEPCIO = "Probando";
    public POOBException(String message) {
        super(message);
    }
}
