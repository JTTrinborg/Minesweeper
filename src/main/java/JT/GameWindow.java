package JT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


public class GameWindow {

    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows;
    int boardWith = numCols * tileSize;
    int boardHeight = numRows * tileSize;

    //Creating the Game Window
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int mineCount = 10;
    MineTile[][] board = new MineTile[numRows][numCols];
    ArrayList<MineTile> mineList;
    Random random = new Random();

    int tilesClicked = 0; //Keeps track on all the tiles clicked
    boolean gameOver = false;

    //CONSTRUCTOR
    GameWindow(){
        frame.setSize(boardWith, boardHeight);
        frame.setLocationRelativeTo(null); // Ensures that the board opens in the center of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ensure that the x button in the top corner exits the program
        frame.setLayout(new BorderLayout());

        //Settings for the text label
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper! Total Mines: " + Integer.toString(mineCount));
        textLabel.setOpaque(true);

        //Adding the text panel and text label to the frame
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textLabel, BorderLayout.NORTH);

        //Adding the grid to the game window
        boardPanel.setLayout(new GridLayout(numRows, numCols));
        //boardPanel.setBackground(Color.BLUE);
        frame.add(boardPanel);

        //Adding the MineTiles to the grid
        for (int r = 0; r < numRows; r++){
            for (int c = 0; c < numCols; c++){
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                //Styling the minetiles
                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                //Checking for the click handling
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //If game over. Don't handle clicks
                        if (gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();

                        //Left Click
                        if (e.getButton() == MouseEvent.BUTTON1){
                            if(tile.getText() == ""){
                                if(mineList.contains(tile)) {
                                    revealMines();
                                }
                                else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        }
                        //Right Click
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            //If tile does not have flag, set flag
                            if (tile.getText() == "" && tile.isEnabled()){
                                tile.setText("🚩");
                            }
                            //If tile has flag, remove flag
                            else if (tile.getText() == "🚩") {
                                tile.setText("");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }
//Makes everything visible after the components are loaded
        frame.setVisible(true);

        //Placing the mines
        setMines();
    }

    //Function for setting the mines
    void setMines(){
        mineList = new ArrayList<MineTile>();

        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);

            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft -= 1;
            }
        }
    }

    //Function for revealing the mines
    void revealMines() {
        for (int i = 0; i < mineList.size(); i++){
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }

        gameOver = true;
        textLabel.setText("Game over");
    }

    //Function for Checking the mines
    void checkMine(int r, int c) {
        //If out of bounds
        if (r < 0 || r >= numRows || c < 0 || c >= numCols){
            return;
        }

        MineTile tile = board[r][c];
        //If tile is already clicked on
        if (!tile.isEnabled()){
            return;
        }

        tile.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        //Checking Top 3 Minetiles for mines
        minesFound += countMine(r-1, c-1); //Top Left
        minesFound += countMine(r-1, c); //Top middle
        minesFound += countMine(r-1, c+1); //Top Right

        //Checking Left and Right for mines
        minesFound += countMine(r, c-1); //Left
        minesFound += countMine(r, c+1); //Right

        //Checking Bottom Minetiles for mines
        minesFound += countMine(r+1, c-1); //Bottom left
        minesFound += countMine(r+1, c); // Bottom Middle
        minesFound += countMine(r+1, c+1); // Bottom Right

        //Setting the text on how many mines is close to that square
        if (minesFound > 0){
            tile.setText(Integer.toString(minesFound));
        }
        //No mines close. Empty MineTile
        else {
            tile.setText("");
            //Checking Top 3 neighbour Tiles
            checkMine(r-1,c-1); //Checking top left neighbour Tile
            checkMine(r-1,c); // Top Middle
            checkMine(r-1,c+1); // Top Right
            //Check left and right neighbour tiles
            checkMine(r,c-1); // Left
            checkMine(r,c+1); // Right
            //Checking Bottom 3 Neighbours
            checkMine(r+1,c-1); // Bottom Left
            checkMine(r+1,c); //Bottom Middle
            checkMine(r+1,c+1); // Bottom Right
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Congrats! U won!");
        }
    }

    int countMine(int r, int c){
        //If out of bounds
        if (r < 0 || r >= numRows || c < 0 || c >= numCols){
            return 0;
        }
        //If contain Mine
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        //If don't contain mine
        return 0;
    }

}
