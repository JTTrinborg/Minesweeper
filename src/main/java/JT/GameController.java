package JT;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the core Minesweeper game logic — mine placement,
 * tile checking, and reveal operations. The GameWindow class
 * delegates all non-UI logic to this controller.
 */
public class GameController {

    private MineTile[][] board;
    private int numRows;
    private int numCols;
    private int mineCount;
    private ArrayList<MineTile> mineList = new ArrayList<>();
    private Random random = new Random();

    private int tilesClicked = 0;
    private boolean gameOver = false;

    // ------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------
    public GameController(MineTile[][] board, int mineCount) {
        this.board = board;
        this.numRows = board.length;
        this.numCols = board[0].length;
        this.mineCount = mineCount;
    }

    // ------------------------------------------------------------
    // Places mines randomly across the board without duplication
    // ------------------------------------------------------------
    public void setMines() {
        int minesLeft = mineCount;

        while (minesLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);
            MineTile tile = board[r][c];

            if (!mineList.contains(tile)) {
                mineList.add(tile);
                minesLeft--;
            }
        }
    }

    // ------------------------------------------------------------
    // Reveals all mines on the board (called when player loses)
    // ------------------------------------------------------------
    public void revealMines() {
        for (MineTile tile : mineList) {
            tile.setText("💣");
        }
        gameOver = true;
    }

    // ------------------------------------------------------------
    // Recursively checks a tile and its neighbors for mines.
    // If no adjacent mines are found, expands to nearby tiles.
    // ------------------------------------------------------------
    public void checkMine(int r, int c) {
        // Ignore invalid coordinates
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;

        MineTile tile = board[r][c];

        // Skip if already clicked or disabled
        if (!tile.isEnabled()) return;

        tile.setEnabled(false);
        tilesClicked++;

        int minesFound = 0;

        // Count adjacent mines (8 surrounding tiles)
        minesFound += countMine(r-1, c-1);
        minesFound += countMine(r-1, c);
        minesFound += countMine(r-1, c+1);
        minesFound += countMine(r, c-1);
        minesFound += countMine(r, c+1);
        minesFound += countMine(r+1, c-1);
        minesFound += countMine(r+1, c);
        minesFound += countMine(r+1, c+1);

        // If adjacent mines exist, display the count
        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        }
        // Otherwise, reveal neighboring tiles recursively
        else {
            tile.setText("");
            checkMine(r-1, c-1);
            checkMine(r-1, c);
            checkMine(r-1, c+1);
            checkMine(r, c-1);
            checkMine(r, c+1);
            checkMine(r+1, c-1);
            checkMine(r+1, c);
            checkMine(r+1, c+1);
        }
    }

    // ------------------------------------------------------------
    // Returns 1 if a given tile contains a mine, else 0
    // ------------------------------------------------------------
    private int countMine(int r, int c) {
        // Ignore out-of-bounds coordinates
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return 0;
        return mineList.contains(board[r][c]) ? 1 : 0;
    }

    // ------------------------------------------------------------
    // Getters for external access
    // ------------------------------------------------------------
    public ArrayList<MineTile> getMineList() {
        return mineList;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTilesClicked() {
        return tilesClicked;
    }
}
