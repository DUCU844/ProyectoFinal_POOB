package dominio;

/**
 * Grape fruit - static fruit worth 50 points.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Grape extends Fruit {
	
private static final int GRAPE_POINTS = 50;
    
    /**
     * Creates a grape at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Grape(int row, int column) {
        super(row, column, GRAPE_POINTS);
    }
    
    @Override
    public void update() {
        // Las uvas son estáticas
    }
    
    @Override
    public String getType() {
        return "GRAPE";
    }

}
