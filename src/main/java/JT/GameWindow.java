package JT;

import javax.swing.*;
import java.awt.*;

/**
 * Responsible for rendering the GUI and connecting user input
 * with the GameController logic.
 */
public class GameWindow {

    int tileSize = 70;
    int numRows = 8;
    int numCols = numRows;
    int boardWith = numCols * tileSize;
    int boardHeight = numRows * tileSize;

    // Creating the Game Window
    JFrame frame = new JFrame("Minesweeper");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    int mineCount = 10;
    MineTile[][] board = new MineTile[numRows][numCols];

    GameController controller;

    // ------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------
    GameWindow() {
        frame.setSize(boardWith, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //Adding the menu
        frame.setJMenuBar(new Menu(this));

        // Header label setup
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Minesweeper! Total Mines: " + mineCount);
        textLabel.setOpaque(true);

        // Add label to window
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textLabel, BorderLayout.NORTH);

        // Setup grid panel
        boardPanel.setLayout(new GridLayout(numRows, numCols));
        frame.add(boardPanel);

        // Create controller
        controller = new GameController(board, mineCount);

        // Create shared click handler
        TileClickHandler clickHandler = new TileClickHandler(controller, this, numRows, numCols);

        // Initialize the board
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;

                // Style each tile
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));

                // Assign click handler
                tile.addMouseListener(clickHandler);

                boardPanel.add(tile);
            }
        }

        // Place mines after all tiles are ready
        controller.setMines();

        // Show the window
        frame.setVisible(true);
    }

    /**
     * Updates the status label text at the top of the window.
     *
     * @param text The message to display (e.g., "Game Over!", "Congrats!")
     */
    public void updateStatusText(String text) {
        textLabel.setText(text);

        // Show pop-up when game is finished
        if (text.equals("Game Over!") || text.equals("Congrats! You won!")) {
            int option = JOptionPane.showOptionDialog(
                    frame,
                    text + "\nWould you like to play again?",
                    "Game finished",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Restart", "Exit"},
                    "Restart"
            );
            //If user chose Yes, start new game
            if (option == JOptionPane.YES_OPTION) {
                frame.dispose();  // close current window
                new GameWindow();  // start new game

                //If no, Exit app
            } else {
                System.exit(0);  // exit application
            }
        }
    }

    //Restart game
    public void restartGame() {
        frame.dispose();
        new GameWindow();
    }

}
