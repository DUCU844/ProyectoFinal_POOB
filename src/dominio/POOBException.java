package dominio;

public class POOBException extends Exception{
	
	public static final String INVALID_POSITION = "Posición inválida en el tablero";
    public static final String INVALID_MOVEMENT = "Movimiento no permitido";
    public static final String GAME_OVER = "El juego ha terminado";
	
    public POOBException(String message) {
        super(message);
    }
}
