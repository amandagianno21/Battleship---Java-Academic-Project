package org.cis120;

import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

public class Player {
    private TreeMap<Integer, Boat> boats;
    private int numShipsAlive;
    private int[][] ownBoard;
    private int[][] movesBoard;
    private int[][] static1;
    private int[][] static2;
    private boolean attackMode = false;

    public Player() {
        this.ownBoard = new int[10][10];
        this.movesBoard = new int[10][10];
        this.static1 = new int[10][10];
        this.static2 = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.ownBoard[i][j] = 0;
                this.movesBoard[i][j] = 0;
            }
        }
        this.numShipsAlive = 5;
        this.boats = new TreeMap<Integer, Boat>();
        Boat size1 = new Boat(1);
        boats.put(size1.getLengthToStart(), size1);
        Boat size2 = new Boat(2);
        boats.put(size2.getLengthToStart(), size2);
        Boat size3 = new Boat(3);
        boats.put(size3.getLengthToStart(), size3);
        Boat size4 = new Boat(4);
        boats.put(size4.getLengthToStart(), size4);
        Boat size5 = new Boat(5);
        boats.put(size5.getLengthToStart(), size5);
    }

    public void printOwnBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 9) {
                    System.out.println(this.ownBoard[i][j]);
                } else {
                    System.out.print(this.ownBoard[i][j] + " ");
                }
            }
        }
        System.out.println("\n");
    }

    public void printMovesBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 9) {
                    System.out.println(this.movesBoard[i][j]);
                } else {
                    System.out.print(this.movesBoard[i][j] + " ");
                }
            }
        }
        System.out.println("\n");
    }

    public void randomBoatSetUp() {
        resetBoard();
        for (int i = 5; i > 0; i--) {
            Boat b = getBoat(i);
            // orientation matters
            double h = Math.random();
            if (h <= 0.5) {
                b.turnBoat();
                this.boats.put(i, b);
            }
            int r = (int) (10 * Math.random());
            int c = (int) (10 * Math.random());
            // need to make sure x & y are valid
            while (r + i > 10 || c + i > 10 || !validBoatPlacement(b, r, c)) {
                r = (int) (10 * Math.random());
                c = (int) (10 * Math.random());
            }
            if (b.boatOrientation()) {
                // will all be in same row
                for (int n = c; n < i + c; n++) {
                    this.ownBoard[r][n] = i;
                }
            } else {
                // will all be in same column
                for (int m = r; m < i + r; m++) {
                    this.ownBoard[m][c] = i;
                }
            }
        }
        // this.attackMode = true;
        printOwnBoard();
    }

    public boolean validBoatPlacement(Boat b, int r, int c) {
        int length = b.getLengthToStart();
        boolean horizontal = b.boatOrientation();
        if (length < 1 || length > 5) {
            return false;
        }
        // also need to include right end conditions but don't know if
        // means mouse click coordinates are >10 or >11
        if (r < 0 || c < 0) {
            return false;
        }
        if (this.ownBoard[r][c] != 0 || !haveUnsunkShips()) {
            return false;
        } else if (horizontal) {
            // doesn't matter what row it's in
            // matters what column trying to place it in
            if (length + c <= 10) {
                for (int i = c; i < length + c; i++) {
                    // already boat there
                    if (this.ownBoard[r][i] != 0) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            // boat is vertical so care about row
            if (length + r <= 10) {
                for (int i = r; i < length + r; i++) {
                    // already boat there
                    if (this.ownBoard[i][c] != 0) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    public void updateMovesBoard(int[][] mostRecent) {
        this.movesBoard = mostRecent;
    }

    public void updateOwnBoard(int[][] readIn) {
        this.ownBoard = readIn;
    }

    // public boolean attack(int[][] otherPlayerBoard, int r, int c) {
    public boolean attack(int r, int c) {
        if (!haveUnsunkShips() || r < 0 || c < 0) {
            return false;
        }
        int boatSize = this.ownBoard[r][c];
        if (boatSize > 0) {
            // hit a ship
            Boat hitBoat = getBoat(boatSize);
            hitBoat.directHit();
            this.movesBoard[r][c] = -this.ownBoard[r][c];

            if (hitBoat.getLengthLeft() == 0) {
                sunkAShip();
            }
            return haveUnsunkShips();
        } else {
            this.movesBoard[r][c] = -10;
            return true;
        }
    }

    public Boat getBoat(int length) {
        System.out.println("hit " + length + " length ship");
        return boats.get(length);
    }

    public boolean haveUnsunkShips() {
        return numShipsAlive > 0;
    }

    public void sunkAShip() {
        if (haveUnsunkShips()) {
            numShipsAlive--;
        }
    }

    public int[][] getOwnBoard() {
        return this.ownBoard;
    }

    public int[][] getMovesBoard() {
        return this.movesBoard;
    }

    public void changeToAttackMode() {
        this.attackMode = true;
    }

    public boolean attackMode() {
        return this.attackMode;
    }

    public void staticBoards() {
        // all boats horizontal in static1
        static1[0][0] = 5;
        static1[0][1] = 5;
        static1[0][2] = 5;
        static1[0][3] = 5;
        static1[0][4] = 5;
        static1[0][5] = 3;
        static1[0][6] = 3;
        static1[0][7] = 3;
        static1[0][8] = 2;
        static1[0][9] = 2;
        static1[1][0] = 1;
        static1[1][1] = 4;
        static1[1][2] = 4;
        static1[1][3] = 4;
        static1[1][4] = 4;

        // all boats vertical in static1
        static2[0][0] = 5;
        static2[1][0] = 5;
        static2[2][0] = 5;
        static2[3][0] = 5;
        static2[4][0] = 5;
        static2[5][0] = 3;
        static2[6][0] = 3;
        static2[7][0] = 3;
        static2[8][0] = 2;
        static2[9][0] = 2;
        static2[0][1] = 1;
        static2[1][1] = 4;
        static2[2][1] = 4;
        static2[3][1] = 4;
        static2[4][1] = 4;

    }

    public void static1Player() {
        staticBoards();
        this.ownBoard = this.static1;
    }

    public void static2Player() {
        staticBoards();
        this.ownBoard = this.static2;
    }

    public void resetBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.ownBoard[i][j] = 0;
                this.movesBoard[i][j] = 0;
            }
        }
    }
}