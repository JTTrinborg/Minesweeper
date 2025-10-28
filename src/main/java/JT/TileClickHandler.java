package JT;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles mouse click events on MineTile objects.
 * Delegates game logic to GameController and updates UI through GameWindow.
 */
public class TileClickHandler extends MouseAdapter {

    private final GameController controller;
    private final GameWindow window;
    private final int numRows;
    private final int numCols;

    /**
     * Constructor for the TileClickHandler.
     *
     * @param controller  Reference to the GameController (handles logic)
     * @param window      Reference to the GameWindow (for UI updates)
     * @param numRows     Number of rows on the board
     * @param numCols     Number of columns on the board
     */
    public TileClickHandler(GameController controller, GameWindow window, int numRows, int numCols) {
        this.controller = controller;
        this.window = window;
        this.numRows = numRows;
        this.numCols = numCols;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Ignore clicks when game is over
        if (controller.isGameOver()) return;

        MineTile tile = (MineTile) e.getSource();

        // Handle left click
        if (e.getButton() == MouseEvent.BUTTON1) {
            handleLeftClick(tile);
        }
        // Handle right click
        else if (e.getButton() == MouseEvent.BUTTON3) {
            handleRightClick(tile);
        }
    }

    /**
     * Handles left-click behavior: reveals tiles, checks for mines,
     * and updates the label text based on game outcome.
     */
    private void handleLeftClick(MineTile tile) {
        if (!tile.getText().equals("")) return;

        // If clicked on a mine, reveal all and end game
        if (controller.getMineList().contains(tile)) {
            controller.revealMines();
            window.updateStatusText("Game Over!");
        } else {
            controller.checkMine(tile.r, tile.c);

            // Win condition check
            int safeTiles = numRows * numCols - controller.getMineList().size();
            if (controller.getTilesClicked() == safeTiles) {
                window.updateStatusText("Congrats! You won!");
            }
        }
    }

    /**
     * Handles right-click behavior: toggles flag on or off.
     */
    private void handleRightClick(MineTile tile) {
        if (tile.getText().equals("") && tile.isEnabled()) {
            tile.setText("🚩");
        } else if (tile.getText().equals("🚩")) {
            tile.setText("");
        }
    }
}
