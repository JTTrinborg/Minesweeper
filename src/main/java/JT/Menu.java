package JT;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Menu bar on top of the game for the MineSweeper Game
public class Menu extends JMenuBar {
    private final GameWindow window;


    //CONSTRUCTOR
    public Menu(GameWindow window) {
        this.window = window;

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exitGame = new JMenuItem("Exit Game");

        //Adding the items to the menu
        gameMenu.add(newGame);
        gameMenu.add(exitGame);

        //Adding the menu option to the menu bar
        add(gameMenu);

        //Adding listner to the New Game Option
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.restartGame();
            }
        });

        //Adding listner for the Exit Game Option
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
