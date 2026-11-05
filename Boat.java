package org.cis120;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Boat {
    private int lengthToStart;
    private int lengthLeft;
    private boolean horizontal;

    /**
     * Constructor sets up boat.
     */
    public Boat(int length) {
        this.lengthToStart = length;
        this.lengthLeft = length;
        horizontal = true;
    }

    // want player to change boat orientation by pressing space key
    public void turnBoat() {
        horizontal = !horizontal;
    }

    // board needs orientation
    public boolean boatOrientation() {
        return horizontal;
    }

    public int getLengthToStart() {
        return this.lengthToStart;
    }

    public int getLengthLeft() {
        return this.lengthLeft;
    }

    public void directHit() {
        this.lengthLeft--;
    }

}