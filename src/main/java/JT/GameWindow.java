package JT;

import javax.swing.*;
import java.awt.*;


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

    MineTile[][] board = new MineTile[numRows][numCols];

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
        textLabel.setText("Minesweeper");
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

                tile.setFocusable(false);
                tile.setMargin(new Insets(0,0,0,0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                tile.setText("1");
                boardPanel.add(tile);
            }
        }

        frame.setVisible(true);
    }
}
