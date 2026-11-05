package org.cis120;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class Game extends JPanel {

    private Battleship bs; // model for game
    private JLabel status; // current status text i. e. "player 1 turn" or "player 2 turn"

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;

    /**
     * Initializes the game board.
     */
    public Game(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        bs = new Battleship(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                bs.attack(p.y / 50, p.x / 50);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        bs.reset();
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int winner = bs.checkWinner();
        if (winner == 0) {
            if (bs.getCurrentPlayer()) {
                status.setText("Player 1's Turn");
            } else {
                status.setText("Player 2's Turn");
            }
        }
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        for (int i = 0; i <= 500; i = i + 50) {
            g.setColor(Color.BLACK);
            // vertical lines
            g.drawLine(i, 0, i, 500);
            // horizontal lines
            g.drawLine(0, i, 500, i);
        }

        // g.fillRect(200, 200, 200, 200);
        int[][] board = bs.getBoard();
        if (!bs.attackMode()) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int state = board[i][j];
                    if (state != 0) {
                        // drawing the boats in setUp
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(50 * j, 50 * i, 50, 50);
                    }
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int state = board[i][j];
                    // hit a boat
                    if (state < 0 && state > -6) {
                        g.setColor(Color.RED);
                        g.fillRect(50 * j, 50 * i, 50, 50);
                    }
                    // missed and hit water
                    if (state == -10) {
                        g.setColor(Color.CYAN);
                        g.fillRect(50 * j, 50 * i, 50, 50);
                    }
                }
            }
        }

        updateStatus();
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /**
     * Main method run to start and run the game. Initializes the runnable game
     * class of your choosing and runs it. IMPORTANT: Do NOT delete! You MUST
     * include a main method in your final submission.
     */
    public static void main(String[] args) {
        Runnable game = new RunBattleship(); // Set the game you want to run
                                             // here
        SwingUtilities.invokeLater(game);
    }

    public Battleship getBattleship() {
        return this.bs;
    }

}
