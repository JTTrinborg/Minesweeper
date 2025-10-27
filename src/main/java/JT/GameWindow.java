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

    //CONSTRUCTOR
    GameWindow(){
        frame.setVisible(true);
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
    }
}
