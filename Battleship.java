package org.cis120;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.*;

// COLLECTIONS -> CONSTANTLY SAVE UPDATED 2D ARRAY AFTER EVERY TURN
// FILE I/O -> *RELATED TO COLLECTIONS* WOULD BE SPECIFIC TO IF USER WANTS TO
// CLOSE THE WINDOW,
// SAVES PROGRESS TO FILE I/O BEFORE CLOSING SO USER COULD REOPEN WINDOW

public class Battleship {

    private boolean gameOver;
    private Player player1;
    private Player player2;
    private boolean whoseTurn;

    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        this.gameOver = false;
        this.player1 = new Player();
        this.player2 = new Player();
        this.whoseTurn = true;

    }

    public boolean attackMode() {
        if (whoseTurn) {
            return this.player1.attackMode();
        } else {
            return this.player2.attackMode();
        }
    }

    public void randomlySetUpBoats() {
        if (whoseTurn) {
            this.player1.randomBoatSetUp();
        } else {
            this.player2.randomBoatSetUp();
        }
    }

    public boolean attack(int r, int c) {
        if (checkWinner() == 0 && !gameOver) {
            // whoseTurn = true -> player 1
            if (whoseTurn) {
                if (!this.player1.attack(r, c)) {
                    this.gameOver = true;
                    checkWinner();
                    return false;
                }
                whoseTurn = !whoseTurn;
                return this.player1.attack(r, c);
            // whoseTurn = false -> player 2
            } else {
                if (!this.player2.attack(r, c)) {
                    this.gameOver = true;
                    checkWinner();
                    return false;
                }
                whoseTurn = !whoseTurn;
                return this.player2.attack(r, c);
            }
        } else {
            return false;
        }
    }

    public void readInBoards(String filePath) {
        String correctFilePath = "files/" + filePath;
        FileLineIterator readIn = new FileLineIterator(correctFilePath);
        int rowIndex = 0;
        int[][] p1OwnBoard = new int[10][10];
        int[][] p2OwnBoard = new int[10][10];
        while (rowIndex < 10) {
            String line = readIn.next();
            for (int i = 0; i < 10; i++) {
                String s = String.valueOf(line.charAt(i));
                if (s.equals(",")) {
                    continue;
                } else {
                    int n = Integer.parseInt(s);
                    p1OwnBoard[rowIndex][i] = n;
                }
            }
            rowIndex++;
        }
        rowIndex = 0;
        while (rowIndex < 10) {
            String line = readIn.next();
            for (int i = 0; i < 10; i++) {
                String s = String.valueOf(line.charAt(i));
                if (s.equals(",")) {
                    continue;
                } else {
                    int n = Integer.parseInt(s);
                    p2OwnBoard[rowIndex][i] = n;
                }
            }
            rowIndex++;
        }
        this.player1.updateOwnBoard(p1OwnBoard);
        this.player2.updateOwnBoard(p2OwnBoard);
        this.player1.printOwnBoard();
        this.player2.printOwnBoard();
    }

    public int[][] getBoard() {
        if (whoseTurn) {
            if (this.player1.attackMode()) {
                return this.player1.getMovesBoard();
            } else {
                return this.player1.getOwnBoard();
            }
        } else {
            if (this.player2.attackMode()) {
                return this.player2.getMovesBoard();
            } else {
                return this.player2.getOwnBoard();
            }
        }
    }

    public int getCell(int r, int c) {
        if (whoseTurn) {
            int[][] player1Board = this.player1.getOwnBoard();
            return player1Board[r][c];
        } else {
            int[][] player2Board = this.player2.getOwnBoard();
            return player2Board[r][c];
        }
    }

    public void playTurn(int r, int c) {
        if (attackMode()) {
            attack(r, c);          
        } else {
            randomlySetUpBoats();
        }
    }

    public boolean getCurrentPlayer() {
        return this.whoseTurn;
    }

    public void acceptBoard() {
        // boolean whichPlayer;
        if (whoseTurn) {
            this.player1.changeToAttackMode();
            // whichPlayer = true;
        } else {
            this.player2.changeToAttackMode();
            // whichPlayer = false;
        }
        whoseTurn = !whoseTurn;
    }

    public int checkWinner() {
        if (gameOver) {
            if (whoseTurn) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }
    }

    public void staticPlayers() {
        this.player1.static1Player();
        this.player2.static2Player();
    }

    public void saveGame(String filePath) {
        String fullPath = "files/" + filePath;
        File file = Paths.get(fullPath).toFile();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            // print out player1 2d arr board
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Integer write = this.player1.getOwnBoard()[i][j];
                    String num = write.toString() + ",";
                    if (j == 0 && i != 0) {
                        bw.newLine();
                    }
                    bw.write(num);
                }
            }
            // 1 col divider b/w 2 boards
            bw.newLine();
            // prints out player2 2d arr board
            for (int k = 0; k < 10; k++) {
                for (int m = 0; m < 10; m++) {
                    Integer write = this.player2.getOwnBoard()[k][m];
                    String num = write.toString() + ",";
                    if (m == 0 && k != 0) {
                        bw.newLine();
                    }
                    bw.write(num);
                }
            }
            bw.close();
        } catch (IOException io) {
            System.out.println("Error occured");
        }
    }

    public Player getPlayer() {
        if (whoseTurn) {
            switchTurns();
            return this.player1;        
        } else {
            switchTurns();
            return this.player2;
        }
    }

    public void switchTurns() {
        whoseTurn = !whoseTurn;
    }

    public void printBoard() {
        if (whoseTurn) {
            if (this.player1.attackMode()) {
                this.player1.printMovesBoard();
            } else {
                this.player1.printOwnBoard();
            }
        } else {
            if (this.player2.attackMode()) {
                this.player2.printMovesBoard();
            } else {
                this.player2.printOwnBoard();
            }
        }
    }

    public static void main(String[] args) {
        // will run game
    }

}