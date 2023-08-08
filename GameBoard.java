package org.cis1200.Wordle;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Wordle wordle; // model for the game
    private JLabel status; // current status text
    private boolean typingAbility;
    private int row;
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 750;
    private Save save = new Save();

    public GameBoard(JLabel statusInit) throws IOException {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        wordle = new Wordle();
        status = statusInit;
        typingAbility = true;
        row = 0;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JComponent newJComponent = (JComponent) e.getSource();
                newJComponent.requestFocusInWindow();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (typingAbility && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (wordle.getRow() <= 5) {
                        wordle.deleteWord();
                        wordle.delete(wordle.getRow(), wordle.numberOfCharacters());
                    } // the case where someone presses delete
                }

                if (typingAbility && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (wordle.getRow() <= 5) {
                        String word = wordle.input(wordle.getCharacters());
                        if (wordle.validWord(word)) {
                            boolean[] boolArray = wordle.enterHelper(word);
                            if (!boolArray[0]) {
                                typingAbility = false; // essentially, if the game has ended, then
                                                       // we can no longer type
                                if (boolArray[1]) { /*
                                                     * now we repaint and consider the value of the
                                                     * final element in
                                                     * the array
                                                     */
                                    repaint();
                                    if (boolArray[2]) {
                                        JFrame winMessage = new JFrame();
                                        /*
                                         * essentially, the use of the bool-array
                                         * is to have 3 different elements we can
                                         * use for comparisons for when the enter
                                         * key is pressed. this is just my way
                                         * of visualizing everything and while it's
                                         * most definitely not clean, it gets the job
                                         * done
                                         */
                                        JOptionPane.showMessageDialog(
                                                winMessage,
                                                "Nice, you win! The word was \"" +
                                                        wordle.getOfficialWord() + "\"!"
                                        );
                                    } else {
                                        JFrame loseMessage = new JFrame();
                                        JOptionPane.showMessageDialog(
                                                loseMessage,
                                                "Sorry, you lose. The word was \""
                                                        + wordle.getOfficialWord() + "\"."
                                        );
                                    }
                                }
                            }
                        }
                    } else {
                        wordle.setNull(wordle.getRow(), wordle.numberOfCharacters());
                        wordle.resetCharacters();
                    }
                }

                if (typingAbility && Character.isLetter(e.getKeyChar())) {
                    char c = e.getKeyChar(); // recognition of a key being typed
                    if (wordle.getRow() < 6) {
                        if (wordle.numberOfCharacters() < 5) {
                            char c1 = Character.toUpperCase(c);
                            wordle.insertVal(
                                    wordle.getRow(), wordle.numberOfCharacters(), "" + c1, null
                            );
                            wordle.addCharacters(c1 + "");
                        }
                    }
                }
                repaint();
            }
        });
    }

    public void reset() throws IOException { // resetting the board for new game
        wordle.reset();
        typingAbility = true;
        row = 0;
        repaint();
    }

    public void setGameState() throws IOException { // for saving a game to the file
        save.savingToFile(wordle);
        repaint();
    }

    public void getGameState() throws IOException { // for loading the game previously saved
        wordle = save.getGame();
        if (wordle.getGameOver()) {
            typingAbility = false;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) { // the gui

        Font font = new Font("Serif", Font.BOLD, 24);
        Font font2 = new Font("Ariel", Font.BOLD, 48);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        String title = "Wordle Unlimited";

        g.setFont(font);
        g2.setStroke(new BasicStroke(3));
        g2.drawString(title, 162, 56);

        g2.drawRect(154, 27, 200, 1);
        g2.drawRect(154, 67, 200, 1);
        g2.drawRect(154, 27, 1, 40);
        g2.drawRect(354, 27, 1, 40);

        g2.drawLine(0, 150, 0, 750);
        g2.drawLine(100, 150, 100, 750);
        g2.drawLine(200, 150, 200, 750);
        g2.drawLine(300, 150, 300, 750);
        g2.drawLine(400, 150, 400, 750);
        g2.drawLine(500, 150, 500, 750);

        g2.drawLine(0, 150, 498, 150);
        g2.drawLine(0, 250, 498, 250);
        g2.drawLine(0, 350, 498, 350);
        g2.drawLine(0, 450, 498, 450);
        g2.drawLine(0, 550, 498, 550);
        g2.drawLine(0, 650, 498, 650);
        g2.drawLine(0, 750, 498, 750);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = wordle.getValue(i, j);
                if (tile.getColor() != null) {
                    switch (tile.getColor()) {
                        case "green":
                            g.setColor(Color.green);
                            break;
                        case "yellow":
                            g.setColor(Color.yellow);
                            break;
                        case "gray":
                            g.setColor(Color.gray);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + tile.getColor());
                    }
                    g2.drawRect(j * 100 + 4, (i * 100) + 154, 94, 94);
                    g.fillRect((j * 100 + 4), (i * 100) + 154, 94, 94);
                    g.setColor(Color.black);
                    g.setFont(font2);
                    g.drawString(tile.getChar(), (j * 100) + 34, (i * 100) + 215);
                } else if (tile.getChar() != null) {
                    g.setColor(Color.black);
                    g.setFont(font2);
                    g.drawString(tile.getChar(), (j * 100) + 34, (i * 100) + 215);

                }
            }
        }
        if (!typingAbility) {
            g.setColor(Color.black);
            g.setFont(font);
            g.drawString(wordle.getOfficialWord().toUpperCase(), 212, 96);
            g2.drawRect(199, 67, 100, 1);
            g2.drawRect(199, 107, 105, 1);
            g2.drawRect(199, 67, 1, 40);
            g2.drawRect(304, 67, 1, 40);
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
