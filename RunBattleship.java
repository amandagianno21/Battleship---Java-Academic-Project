package org.cis120;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunBattleship implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Battleship");
        frame.setLocation(500, 500);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        status.repaint();

        // Game board
        final Game board = new Game(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        // JButton for instructions
        final JButton instructions = new JButton("Getting Started");
        Object explanation = "BATTLESHIP INSTRUCTIONS\n"
                + "Each player gets 5 ships - one of each length from 1-5 units.\n"
                + "Each player places each of their ships on their board however "
                + "he/she wants so long as each player's ships do not overlap.\n"
                + "Then both players take turns picking one spot at a time on an initially"
                + " empty board attempting to hit and eventually sink the other person's ships\n"
                + "Whoever sinks all of the other person's ships first wins!\n"
                + "Best of luck!";

        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(instructions, explanation);
            }
        });
        control_panel.add(instructions);

        // JButton for saving game
        final JButton save = new JButton("Save Game");
        Object saveInstructions = "Please input a valid csv file to save the game to.";
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = JOptionPane.showInputDialog(save, saveInstructions);
                board.getBattleship().saveGame(filePath);
            }
        });
        control_panel.add(save);

        final JButton uploadSaved = new JButton("Upload Saved Game");
        Object uploadInstructions = "Please input a valid csv file to upload game from.";
        uploadSaved.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = JOptionPane.showInputDialog(uploadSaved, uploadInstructions);
                board.getBattleship().readInBoards(filePath);
            }
        });
        control_panel.add(uploadSaved);

        // final JPanel other_buttons = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);

        // making random board generating button
        final JButton boardGenerator = new JButton("Generate Board");
        boardGenerator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Battleship bs = board.getBattleship();
                bs.randomlySetUpBoats();
                board.repaint();
            }
        });
        status_panel.add(boardGenerator);

        final JButton acceptBoard = new JButton("Accept Board");
        acceptBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Battleship bs = board.getBattleship();
                bs.acceptBoard();
                board.repaint();
            }
        });
        status_panel.add(acceptBoard);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}