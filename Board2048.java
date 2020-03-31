/*  Name: Allen Sha
 *
 * no execution
 * 
 *  A class that represents a 2D array of Tiles, or
 * the board, for a game of 2048. 
 *
 */
import java.util.*; 

public class Board2048 {
    //field variables
    private Tile[][] tiles;
    private boolean moveMade;
    private int numMoves;
    

    /* 
     * constructor, creates new board with tiles
     */
    public Board2048() {
        tiles = new Tile[4][4];
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                tiles[row][col] = new Tile(0, .25 * row + .125, .2 * col + .1);
            }
        }
        moveMade = false;
        numMoves = 0;
    }
    
    /* 
     * getters
     * output: field variables
     */
    public boolean getMoveMade() {
        return moveMade;
    }
    
    public int getNumMoves() {
        return numMoves;
    }
    
    /* 
     * setter
     * input: boolean if move has been made 
     */
    public void setMoveMade(boolean moveMade) {
        this.moveMade = moveMade;
    }
    
    
    /* 
     * increments number of moves
     */
    public void incrementNumMoves() {
        numMoves++;
    }
    
    
    /* 
     * prints number of moves
     */
    public void printNumMoves() {
        PennDraw.text(.5, .9, "Number of Moves Made: " + numMoves);
    }
    
    
    /* 
     * checks if all tiles in board have a nonzero value 
     * output: boolean, whether every tile has a nonzero value
     */
    public boolean checkBoardFull() {
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                if (tiles[row][col].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    /* 
     * checks whether the board is changed if attempt
     * to be moved in every direction
     * output: boolean, whether any tiles can be moved.
     */
    
    public boolean checkNoMoreMoves() {
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                //checks left/right moving tiles
                if (row != 0 && tiles[row - 1][col].getValue() == 0 &&
                    !(tiles[row][col].getValue() == 0)) {
                    return false;
                }
                if (row != 0 && tiles[row][col].getValue() != 0) {
                    if (tiles[row][col].checkEquals(tiles[row - 1][col])) {
                        return false;
                    }
                }
                //checks up/down moving tiles
                if (col != 0 && tiles[row][col - 1].getValue() == 0 &&
                    !(tiles[row][col].getValue() == 0)) {
                    return false;
                }
                if (col != 0 && tiles[row][col].getValue() != 0) {
                    if (tiles[row][col].checkEquals(tiles[row][col - 1])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    /* 
     * checks if any tile has 2048
     * output: boolean, whether there is 2048
     */
    public boolean check2048() {
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                if (tiles[row][col].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //adds a 2 or 4 tile randomly to the board
    public void addNewTile() {
        List<Integer> listRow = new ArrayList<Integer>();
        List<Integer> listCol = new ArrayList<Integer>();
         //find indexes where there are no values
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                if (tiles[row][col].getValue() == 0) {
                    listRow.add(row);
                    listCol.add(col);
                }
            }
        }
        if (listRow.size() == 0) {
            return;//board is filled and you're trying to add a piece
        }
        else {
            //randomly choose one of the indexes in array list, give it 2 or 4
            int index = (int) (Math.random() * listRow.size());
            if (Math.random() < .5) {
                tiles[listRow.get(index)][listCol.get(index)].setValue(2);
            }
            else {
                tiles[listRow.get(index)][listCol.get(index)].setValue(4);
            }
        }
        
    }
    
     //start game with two random tiles
    public void newGame() {
        addNewTile();
        addNewTile();
    }
    
    //resets all the Tile instances pairing to false
    public void resetTilesHit() {
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                tiles[row][col].alreadyPaired = false;
            }
        }
    }
     
    //update functions for each direction
    
    //updates tiles left
    public void moveL() {
        resetTilesHit();
        for (int row = 1; row < tiles.length; ++row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                int x = row;
                //moves a tile left until it either hits the edge or another piece
                while (row != 0 && tiles[row - 1][col].getValue() == 0 &&
                       !(tiles[row][col].getValue() == 0)) {
                    tiles[row - 1][col].setValue(tiles[row][col].getValue());
                    tiles[row][col].setValue(0);
                    moveMade = true;
                    --row;
                }
                //combines with another piece if same value to the left
                if (row != 0 && tiles[row][col].getValue() != 0 && !tiles[row - 1][col].alreadyPaired) {
                    if (tiles[row][col].checkEquals(tiles[row - 1][col])) {
                        tiles[row][col].combine(tiles[row - 1][col]);
                        moveMade = true;
                        //makes sure a tile cannot be paired until the next move
                        tiles[row - 1][col].alreadyPaired = true;
                    }
                }
                row = x;
            }
        }
    }
    
    //updates tiles right
    public void moveR() {
        resetTilesHit();
        for (int row = tiles.length - 2; row >= 0; -- row) {
            for (int col = 0; col < tiles[0].length; ++col) {
                int x = row;
                while (row != 3 && tiles[row + 1][col].getValue() == 0 &&
                       !(tiles[row][col].getValue() == 0)) {
                    tiles[row + 1][col].setValue(tiles[row][col].getValue());
                    tiles[row][col].setValue(0);
                    moveMade = true;
                    ++row;
                }
                if (row != 3 && tiles[row][col].getValue() != 0 && !tiles[row + 1][col].alreadyPaired) {
                    if (tiles[row][col].checkEquals(tiles[row + 1][col])) {
                        tiles[row][col].combine(tiles[row + 1][col]);
                        moveMade = true;
                        tiles[row + 1][col].alreadyPaired = true;
                    }
                }
                row = x;
            }
        }
    }
    
    //updates tiles down
    public void moveD() {
        resetTilesHit();
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = 1; col < tiles[0].length; ++col) {
                int x = col;
                while (col != 0 && tiles[row][col - 1].getValue() == 0 &&
                       !(tiles[row][col].getValue() == 0)) {
                    tiles[row][col - 1].setValue(tiles[row][col].getValue());
                    tiles[row][col].setValue(0);
                    moveMade = true;
                    --col;
                }
                
                if (col != 0 && tiles[row][col].getValue() != 0 && !tiles[row][col - 1].alreadyPaired) {
                    if (tiles[row][col].checkEquals(tiles[row][col - 1])) {
                        tiles[row][col].combine(tiles[row][col - 1]);
                        moveMade = true;
                        tiles[row][col - 1].alreadyPaired = true;
                    }
                }
                col = x;
            }
        }
    }
    
    //updates tiles up
    public void moveU() {
        resetTilesHit();
        for (int row = 0; row < tiles.length; ++row) {
            for (int col = tiles[0].length - 2; col >= 0; --col) {
                int x = col;
                while (col != 3 && tiles[row][col + 1].getValue() == 0 &&
                       !(tiles[row][col].getValue() == 0)) {
                    tiles[row][col + 1].setValue(tiles[row][col].getValue());
                    tiles[row][col].setValue(0);
                    moveMade = true;
                    ++col;
                }
                
                if (col != 3 && tiles[row][col].getValue() != 0 && !tiles[row][col + 1].alreadyPaired) {
                    if (tiles[row][col].checkEquals(tiles[row][col + 1])) {
                        tiles[row][col].combine(tiles[row][col + 1]);
                        moveMade = true;
                        tiles[row][col + 1].alreadyPaired = true;
                    }
                }
                col = x;
            }
        }
    }
    
    //draws the board by displaying all tiles
    public void draw() {
        PennDraw.clear();
        drawGrid();
        for (Tile[] tRow : tiles) {
            for (Tile t: tRow) {
                if (t.getValue() == 0) continue;
                t.draw();
            }
        }
    }
    
    //helper function for draw that prints grid
    private void drawGrid() {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.line(0, .2, 1, .2);
        PennDraw.line(0, .4, 1, .4);
        PennDraw.line(0, .6, 1, .6);
        PennDraw.line(0, .8, 1, .8);
        PennDraw.line(.25, 0, .25, .8);
        PennDraw.line(.5, 0, .5, .8);
        PennDraw.line(.75, 0, .75, .8);
    }
    
    //prints end game message
    public void printEndGame(String message) {
        PennDraw.text(.5, .5, message);
    }
}
