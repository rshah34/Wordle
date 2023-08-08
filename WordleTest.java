package org.cis1200.Wordle;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class WordleTest {

    @Test
    public void givenTest() {
        assertNotEquals("CIS 120", "CIS 160");
    }

    @Test
    public void emptyBoardTest() throws IOException {
        Wordle wordle = new Wordle();
        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 4; col++) {
                Tile tile = wordle.getValue(row, col);
                assertNull(tile.getChar());
                assertNull(tile.getColor());
            }
        }
    }

    @Test
    public void lengthOf5StringTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.addCharacters("r");
        wordle.addCharacters("o");
        wordle.addCharacters("h");
        wordle.addCharacters("a");
        wordle.addCharacters("n");
        String word = wordle.input(wordle.getCharacters());
        assertEquals("rohan", word);
    }

    @Test
    public void wordLessThan5Test() throws IOException {
        Wordle wordle = new Wordle();
        wordle.addCharacters("s");
        wordle.addCharacters("h");
        wordle.addCharacters("a");
        wordle.addCharacters("h");
        assertNull(wordle.getValue(wordle.getRow(), 0).getChar());
        assertNull(wordle.getValue(wordle.getRow(), 1).getChar());
        assertNull(wordle.getValue(wordle.getRow(), 2).getChar());
        assertNull(wordle.getValue(wordle.getRow(), 3).getChar());
    }

    @Test
    public void deletingInvalidWordTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.addCharacters("s");
        wordle.addCharacters("h");
        wordle.addCharacters("a");
        wordle.addCharacters("h");
        wordle.setNull(wordle.getRow(), wordle.numberOfCharacters());
        wordle.resetCharacters();
        assertEquals(0, wordle.numberOfCharacters());
    }

    @Test
    public void deleteElementsTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.addCharacters("r");
        wordle.addCharacters("o");
        wordle.addCharacters("h");
        wordle.addCharacters("a");
        wordle.addCharacters("s");
        wordle.deleteWord();
        wordle.delete(wordle.getRow(), wordle.numberOfCharacters() - 1);
        wordle.addCharacters("n");
        assertEquals("rohan", wordle.input(wordle.getCharacters()));
    }

    @Test
    public void gameOverTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.reset();
        assertFalse(wordle.getGameOver());
    }

    @Test
    public void validityOfOfficialWordTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.reset();
        assertTrue(wordle.validWord(wordle.getOfficialWord()));
    }

    @Test
    public void wordValidityTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.addCharacters("r");
        wordle.addCharacters("o");
        wordle.addCharacters("h");
        wordle.addCharacters("a");
        wordle.addCharacters("n");
        String word = wordle.input(wordle.getCharacters());
        boolean[] boolArray = wordle.enterHelper(word);
        assertTrue(boolArray[0]);
        assertFalse(boolArray[1]);
        assertFalse(boolArray[2]);
    }

    @Test
    public void endGameTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.setOfficialWord("funny");
        wordle.addCharacters("f");
        wordle.addCharacters("u");
        wordle.addCharacters("n");
        wordle.addCharacters("n");
        wordle.addCharacters("y");
        String word = wordle.input(wordle.getCharacters());
        wordle.enterHelper(word);
        assertTrue(wordle.getGameOver());
    }

    @Test
    public void colorsTest() throws IOException {
        Wordle wordle = new Wordle();
        wordle.setOfficialWord("spurt");
        wordle.addCharacters("j");
        wordle.addCharacters("o");
        wordle.addCharacters("u");
        wordle.addCharacters("s");
        wordle.addCharacters("t");
        assertEquals(5, wordle.numberOfCharacters());
        wordle.createColor(wordle.getRow(), wordle.getCharacters());
        assertEquals("gray", wordle.getValue(wordle.getRow(), 0).getColor());
        assertEquals("gray", wordle.getValue(wordle.getRow(), 1).getColor());
        assertEquals("green", wordle.getValue(wordle.getRow(), 2).getColor());
        assertEquals("yellow", wordle.getValue(wordle.getRow(), 3).getColor());
        assertEquals("green", wordle.getValue(wordle.getRow(), 4).getColor());
    }

}
