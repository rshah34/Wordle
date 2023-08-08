package org.cis1200.Wordle;

import java.io.*;

public class Save {

    private final Wordle wordle;

    public Save() throws IOException {
        wordle = new Wordle();
    }

    public void savingToFile(Wordle wordle) throws IOException {
        String officialWord = wordle.getOfficialWord();
        BufferedWriter writer = new BufferedWriter(new FileWriter("files/SavedGame.txt", false));
        writer.write(officialWord);
        writer.newLine();

        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 4; j++) {
                Tile tile = wordle.getValue(i, j);
                String newChar = tile.getChar();
                String newColor = tile.getColor();
                if (tile.getChar() == null) {
                    newChar = "null";
                }
                if (tile.getColor() == null) {
                    newColor = "null";
                }
                String saving = newChar + " " + newColor;
                writer.write(saving);
                writer.newLine();
            }
        }
        writer.close();
        /*
         * this code is for writing the game to the save file. we see that we iterate
         * through the entire 2-d
         * array and then select each individual character & color associated with it
         * (while checking if they're null),
         * and then saving them to the file.
         */
    }

    public Wordle getGame() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files/SavedGame.txt"));
        String officialWord = br.readLine();
        wordle.setOfficialWord(officialWord);

        int a = 0;
        int b = 0;
        boolean bool = true;
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 4; j++) {
                String nextWord = br.readLine();
                String[] word = nextWord.split(" ");
                String newChar = word[0];
                String newColor = word[1];
                if (newChar.equals("null")) {
                    newChar = null;
                }
                if (newColor.equals("null")) {
                    newColor = null;
                }
                if (newChar == null && newColor == null && bool) {
                    a = i;
                    b = j;
                    bool = false;
                }
                wordle.insertVal(i, j, newChar, newColor);
            }
        }
        for (int i = 0; i < b; i++) {
            wordle.addCharacters(wordle.getValue(a, i).getChar());
        }
        for (int i = 0; i < a; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                word.append(wordle.getValue(i, j).getChar());
            }
            wordle.insertGuessedWord(word.toString());
        }
        wordle.setRow(a);
        br.close();
        return wordle;
        /*
         * this code is dedicated to putting the code in the file back into the game.
         * this took so long and I
         * really don't know a more efficient way of completing this, but I actually
         * would love feedback on how to
         * do this in a better way.
         */
    }
}
