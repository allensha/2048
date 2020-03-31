/*  Name: Allen Sha
 *  PennKey: allensha
 *  Recitation: 217
 *
 * execution: run
 * use WASD to move tiles
 * 
 *  A class that runs the game 2048 by setting
 * up the screen and calling methods from the board class
 *
 */
public class Game2048 {
    public static void main(String[] args) {
        PennDraw.setCanvasSize(500, 625);
        PennDraw.enableAnimation(30);
        //create board
        Board2048 w = new Board2048();
        
        //start game
        w.newGame();
        while (true) {
            //draw board
            w.draw();
            //update board depending on WASD
            if (PennDraw.hasNextKeyTyped()) {
                char key = PennDraw.nextKeyTyped();
                if (key == 'd') {
                    w.moveR();
                }
                else if (key == 'a') {
                    w.moveL();
                }
                else if (key == 's') {
                    w.moveD();
                }
                else if (key == 'w') {
                    w.moveU();
                }
                //update if move has been made
                if (w.getMoveMade()) {
                    w.draw();
                    PennDraw.advance();
                    //quick pause so the user knows which tile was added
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    w.addNewTile();
                    w.draw();
                    w.incrementNumMoves();
                    w.setMoveMade(false);
                }
                
            }
            w.printNumMoves(); 
            PennDraw.advance();
            //output victory screen
            if (w.check2048()) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PennDraw.clear();
                w.printEndGame("You won!");
                w.printNumMoves(); 
                break;
            }
            
            //output defeat screen
            if (w.checkBoardFull() && w.checkNoMoreMoves()) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PennDraw.clear();
                w.printEndGame("You lost!");
                w.printNumMoves(); 
                break;
            }
        }
        PennDraw.advance();
    }
}