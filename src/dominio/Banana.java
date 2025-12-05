package dominio;

/**
 * Banana fruit - static fruit worth 100 points.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class Banana extends Fruit {
    
    private static final int BANANA_POINTS = 100;
    
    /**
     * Creates a banana at the specified position.
     * 
     * @param row initial row
     * @param column initial column
     */
    public Banana(int row, int column) {
        super(row, column, BANANA_POINTS);
    }
    
    @Override
    public void update() {
        // Los plátanos son estáticos
    }
    
    @Override
    public String getType() {
        return "BANANA";
    }
}
