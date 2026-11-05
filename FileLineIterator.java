package org.cis120;

import java.io.BufferedReader;
import java.util.Iterator;
import java.io.FileReader;
// import java.io.IOException;
// import java.io.FileNotFoundException;
import java.io.*;
import java.util.NoSuchElementException;

// FOR GAME!!!

/**
 * FileLineIterator provides a useful wrapper around Java's provided
 * BufferedReader and provides practice with implementing an Iterator. Your
 * solution should not read the entire file into memory at once, instead reading
 * a line whenever the next() method is called. See Java's documentation for
 * BufferedReader to learn how to construct one given a path to a file. Then,
 * think about how you can use BufferedReader's methods within this class to
 * implement our desired functionality.
 * <p>
 * Note: Any IOExceptions thrown by readers should be caught and handled
 * properly.
 */
public class FileLineIterator implements Iterator<String> {

    private BufferedReader lineReader;
    private String currLine = new String();
    private String nextLine;
    private boolean hasNext;

    /**
     * Creates a FileLineIterator for the file located at filePath. Fill out the
     * constructor so that a user can instantiate a FileLineIterator. Feel free
     * to create and instantiate any variables that your implementation requires
     * here. See recitation and lecture notes for guidance.
     * <p>
     * If an IOException is thrown by the BufferedReader or FileReader, then
     * hasNext should return false.
     *
     * @param filePath - the path to the CSV file to be turned to an Iterator
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public FileLineIterator(String filePath) {

        if (filePath == null) {
            throw new IllegalArgumentException("Null file path");
        }

        try {
            this.lineReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            this.hasNext = false;
            throw new IllegalArgumentException("File doesn't exist");
        }

        String nextLine = null;
        try {
            nextLine = this.lineReader.readLine();
        } catch (IOException e) {
            this.hasNext = false;
        }

        if (this.nextLine == null) {
            this.hasNext = false;
        }
        this.nextLine = nextLine;
        this.hasNext = true;
    }

    /**
     * Returns true if there are lines left to read in the file, and false
     * otherwise.
     * <p>
     * If there are no more lines left, this method should close the
     * BufferedReader.
     *
     * @return a boolean indicating whether the FileLineIterator can produce
     *         another line from the file
     */
    @Override
    public boolean hasNext() {
        if (this.hasNext) {
            this.currLine = this.nextLine;
            return this.hasNext;
        } else {
            try {
                this.lineReader.close();
            } catch (IOException io) {
                return false;
            }
        }
        return this.hasNext;
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException
     * if there are no more strings left to return (i.e. hasNext() is false).
     * <p>
     * This method also advances the iterator in preparation for another
     * invocation. If an IOException is thrown during a next() call, the next
     * time next() is called, it should throw a NoSuchElementException.
     *
     * @return the next line in the file
     * @throws java.util.NoSuchElementException if there is no more data in the
     *                                          file
     */
    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("no more data in file");
        } else {
            try {
                String x = this.lineReader.readLine();
                if (x != null) {
                    this.nextLine = x;
                } else {
                    this.hasNext = false;
                }
            } catch (IOException io) {
                this.hasNext = false;
            }
        }
        return this.currLine;
    }
}
