package org.cis1200.Wordle;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.FileReader;

public class Dictionary {
    private final int possibleWordSize = 12972; // length of the dictionary
    private final HashMap<Integer, String> dictionaryWords = new HashMap<>();
    /*
     * in my opinion, using a treemap makes it easiest to add & access words in the
     * dictionary,so I decided this would be best.
     */

    public Dictionary() throws IOException { // constructor
        BufferedReader br = new BufferedReader(new FileReader("files/AllWordleWords.txt"));
        for (int acc = 0; acc < possibleWordSize; acc++) {
            String temp = br.readLine();
            dictionaryWords.put(acc, temp);
        }
        br.close();
    }

    public String chooseWord() { // selects random word
        int rand = (int) (Math.random() * possibleWordSize);
        String randomWord = dictionaryWords.get(rand);
        return randomWord;
    }

    public boolean validWord(String word) { // ensures input word is in the dictionary
        return dictionaryWords.containsValue(word);
    } // checks for validity

}
