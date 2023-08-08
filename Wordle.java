package org.cis1200.Wordle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.IOException;

public class Wordle {

    private Tile[][] gameboard; // the actual board
    private String officialWord; // the word to be guessed
    private boolean isEndOfGame; // checks for the end
    private LinkedList<String> characters; // stores the characters being typed
    private HashSet<String> pastWords; // stores the entire guess for accessing during comparison
    private Dictionary dictionary; // utilizing the dictionary (comparison purposes)
    private int row; // used for the board

    public Wordle() throws IOException {
        reset(); // this is done so that we can play multiple games
    }

    public void reset() throws IOException { // essentially the constructor
        gameboard = resetBoard();
        isEndOfGame = false;
        dictionary = new Dictionary();
        row = 0;
        officialWord = getGuess();
        pastWords = new HashSet<>();
        characters = new LinkedList<>();
    }

    public String getOfficialWord() {
        return officialWord;
    } // get method for the wordle word

    public int getRow() {
        return row;
    }

    public void addRow() {
        row++;
    }

    public void setRow(int row) {
        this.row = row;
    } // these 3 are standard row methods

    public LinkedList<String> getCharacters() {
        return characters;
    }

    public int numberOfCharacters() {
        return characters.size();
    }

    public void addCharacters(String newWord) {
        characters.add(newWord);
    }

    public void resetCharacters() {
        characters = new LinkedList<>();
    } // standard characters methods

    public String input(LinkedList<String> lst) { /*
                                                   * this function is used to build a string given
                                                   * certain characters. I use this whenever a word
                                                   * is
                                                   * fully typed in order for
                                                   */
        StringBuilder s = new StringBuilder();
        for (String value : lst) {
            s.append(value);
        }
        return s.toString();
    }

    public Tile getValue(int row, int col) {
        return gameboard[row][col];
    } // receives a specific tile

    public void insertGuessedWord(String word) {
        pastWords.add(word.toLowerCase());
    } //

    public void deleteWord() { // when an invalid word typed for example, the game board
                               // will delete it.
        LinkedList<String> lst = new LinkedList<>();
        for (int i = 0; i < characters.size() - 1; i++) {
            lst.add(characters.get(i));
        }
        characters = lst;
    }

    public void insertVal(int row, int col, String getChar, String color) { /*
                                                                             * inserting a specific
                                                                             * value into
                                                                             * some spot on the
                                                                             * board. mainly used
                                                                             * for
                                                                             * the coloring after
                                                                             * comparison
                                                                             */
        gameboard[row][col].setChar(getChar);
        gameboard[row][col].setColor(color);
    }

    public String getGuess() { // actually takes in the guess
        return dictionary.chooseWord();
    } // returns the chosen word

    public boolean validWord(String word) { // checks the validity of a word
        return dictionary.validWord(word.toLowerCase());
    } // ensures validity

    private Tile[][] resetBoard() { // after game is over, everything is cleared for a new game to
                                    // begin
        Tile[][] tile = new Tile[6][5];
        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 4; col++) {
                tile[row][col] = new Tile(null, null);
            }
        }
        return tile;
    }

    public void setNull(int row, int num) { // for resetting/invalid things
        for (int i = 0; i < num; i++) {
            gameboard[row][i].setColor(null);
            gameboard[row][i].setChar(null);
        }
    }

    public void delete(int row, int col) { // deleting a character previously typed
        gameboard[row][col].setChar(null);
    } // sets a specific element null

    public void setOfficialWord(String officialWord) { // sets the official word for the sake of
                                                       // saving the game
        this.officialWord = officialWord;
    } /*
       * setting official word to
       * be guessed (used for saving
       * the game!)
       */

    public void createColor(int row, LinkedList<String> lst) { /*
                                                                * the color function; this function
                                                                * is used
                                                                * for determining which letters are
                                                                * assigned
                                                                * to which specific color depending
                                                                * on their status
                                                                * in comparison to the official
                                                                * word. this function
                                                                * actually works for the entire row
                                                                * for the green and
                                                                * gray colors, and for yellows it
                                                                * places them on specific
                                                                * letters.
                                                                */
        LinkedList<String> charList = new LinkedList<>();
        for (char c : officialWord.toCharArray()) {
            charList.add("" + c);
        }
        HashMap<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (lst.get(i).toLowerCase().equals(charList.get(i))) {
                    if (colorMap.containsKey(lst.get(i).toLowerCase())) {
                        if (gameboard[row][colorMap.get(lst.get(i).toLowerCase())].getColor()
                                .equals("yellow")) {
                            gameboard[row][colorMap.get(lst.get(i).toLowerCase())]
                                    .setColor("gray");
                        }
                    }
                    gameboard[row][i].setColor("green");
                    colorMap.put(lst.get(i).toLowerCase(), i);
                } else if (lst.get(i).toLowerCase().equals(charList.get(j))) {
                    if (!colorMap.containsKey(lst.get(i).toLowerCase())) {
                        gameboard[row][i].setColor("yellow");
                    }
                    colorMap.put(lst.get(i).toLowerCase(), i);
                }
            }
            if (gameboard[row][i].getColor() == null) {
                gameboard[row][i].setColor("gray");
            }
        }
    }

    public void makeGameOver(boolean bool) { // makes the game state whatever bool equals
        isEndOfGame = bool;
    } // sets the state of the game

    public boolean getGameOver() { // returns the value of whether the game is ended
        return isEndOfGame;
    }

    public boolean checkGameOver(String word) { /*
                                                 * uses the makeGameOver functions to check whether
                                                 * the game should actually be over
                                                 */
        if (word.toLowerCase().equals(this.officialWord)) {
            makeGameOver(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean[] enterHelper(String word) { // helper function for enter key case in gameboard!!
        insertGuessedWord(word);
        createColor(getRow(), getCharacters());

        if (checkGameOver(word)) {
            return new boolean[] { false, true, true };
        } else {
            resetCharacters();
            addRow();
            if (getRow() == 6) {
                return new boolean[] { false, true, false };
            }
        }
        return new boolean[] { true, false, false };
    }

}