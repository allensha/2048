/*  Name: Allen Sha
 *
 * no execution
 * 
 *  A class that represents a tile in the game 2048, 
 * represented as an int value in a square;
 *
 */

public class Tile {
    //field variables
    private int value;
    private double x;
    private double y;
    private double sizeX;
    private double sizeY;
    //boolean to check if tile has been paired
    public boolean alreadyPaired;
    
     /* 
     * constuctor
     * input: value of tile, x and y
     */
    public Tile(int value, double x, double y) {
        this.value = value;
        this.x = x;
        this.y = y;
        sizeX = .125;
        sizeY = .1;
        alreadyPaired = false;
    }
    
    //draws tile as a square with color and value
    public void draw() {
        int remainder = value;
        remainder = (int) (Math.log10(remainder) / Math.log10(2));
        if (remainder < 5) {
            PennDraw.setPenColor(255 - remainder * 50, remainder * 50, remainder * 50); 
        }
        else {
            PennDraw.setPenColor(255 - remainder * 20, 0, remainder * 20); 
        }
        PennDraw.filledRectangle(x, y, sizeX, sizeY);
        PennDraw.setPenColor(0, 0, 0);
        PennDraw.setFontSize(30);
        PennDraw.text(x, y, "" + value);
    }
    
     /* 
     * checks if a tile value equals another
     * input: Tile other
     * output: boolean, if tile value equals another
     */
    public boolean checkEquals(Tile other) {
        return value == other.getValue();
    }
    
     /* 
     * connects two tiles and adjusts their values
     * input: Tile other
     */
    public void combine(Tile other) {
        other.setValue(2 * value);
        setValue(0);
    }
    
    //getter, returns value
    public int getValue() {
        return value;
    }
    
    //setter, sets value
    public void setValue(int value) {
        this.value = value;
    }
}
