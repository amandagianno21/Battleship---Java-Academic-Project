package org.cis120;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BattleShipTest {
    @Test
    public void testResets() {
        Player p = new Player();
        p.randomBoatSetUp();
        p.resetBoard();
        int[][] resetOwnBoard = p.getOwnBoard();
        int[][] resetMovesBoard = p.getMovesBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(0, resetOwnBoard[i][j]);
                assertEquals(0, resetMovesBoard[i][j]);
            }
        }
    }

    @Test
    public void testStaticPlayers() {
        Battleship bs = new Battleship();
        bs.staticPlayers();
        Player p1 = bs.getPlayer();
        Player p2 = bs.getPlayer();
        p1.static1Player();
        p2.static2Player();
        bs.attack(0, 0);
        bs.attack(0, 0);
        bs.attack(0, 1);
        bs.attack(5, 0);
        bs.attack(0, 2);
        bs.attack(0, 7);
        bs.attack(0, 3);
        bs.attack(4, 9);
        bs.attack(0, 4);
        bs.attack(6, 0);
        bs.attack(0, 5);
        bs.attack(2, 8);
        bs.attack(0, 6);
        bs.attack(5, 5);
        bs.attack(0, 7);
        bs.attack(9, 3);
        bs.attack(0, 8);
        bs.attack(4, 4);
        bs.attack(0, 9);
        bs.attack(2, 3);
        bs.attack(1, 0);
        bs.attack(8, 9);
        bs.attack(1, 1);
        bs.attack(2, 6);
        bs.attack(1, 2);
        bs.attack(3, 3);
        bs.attack(1, 3);
        bs.attack(7, 1);
        bs.attack(1, 4);
        p1.printMovesBoard();
        p2.printMovesBoard();
        assertEquals(1, bs.checkWinner());

    }

}