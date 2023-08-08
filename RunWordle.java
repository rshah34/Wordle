package org.cis1200.Wordle;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class RunWordle implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Wordle Unlimited");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board;
        try {
            board = new GameBoard(status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("New Game");
        reset.addActionListener(e -> {
            try {
                board.reset();
                board.requestFocusInWindow();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
            board.requestFocusInWindow();
        });
        control_panel.add(reset);

        final JButton load = new JButton("Load Game");
        load.addActionListener(e -> {
            try {
                board.getGameState();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            board.requestFocusInWindow();
        });
        control_panel.add(load);

        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(e -> {
            try {
                board.setGameState();
                board.requestFocusInWindow();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        });
        control_panel.add(saveGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game

        try {
            board.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JFrame instructions = new JFrame();
        JOptionPane.showMessageDialog(
                instructions, "Welcome to Wordle Unlimited! The rules are pretty simple:" +
                        "\n\nIf you are familiar with a standard Wordle game from NYT, " +
                        "then this game is very similar. \nYou have 6 "
                        +
                        "guesses to reveal the random word. After each individual guess, " +
                        "there will be three potential things "
                        +
                        "that can happen: \n\nFirst, if a letter is colored gray, then " +
                        "that means the letter is not in the word. \nSecond, "
                        +
                        "if a letter is colored yellow, then that means the letter is " +
                        "in the word, but is not in the correct spot. "
                        +
                        "\nFinally, if a letter is colored green, then that means it is the " +
                        "correct letter in the correct spot of that "
                        +
                        "word. \n\nNote that your guess will not go through in the " +
                        "game if the word is not registered in the official "
                        +
                        "Wordle dictionary. \n\nAfter the game ends, there will be a" +
                        " message that appears telling you whether or not"
                        +
                        "you guessed the word. It will also appear on the screen to " +
                        "indicate that the game has ended. \n\nAt the bottom, "
                        +
                        "you can see that you can start a New Game at any time, or " +
                        "you can \"Save\" your progress on one game. Clicking Load "
                        +
                        "Game will give you the game that you previously saved." +
                        "\n\nWatch out for some words that may have " +
                        "multiple of one letter! The game"
                        +
                        "will only tell you if at least one of that letter is present." +
                        "" +
                        "\n\nHave fun playing!"
        );
    }
}