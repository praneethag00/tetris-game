/*
 * Tetris Display is responsible for the event listeners for key clicks and for
 *telling the game which moves were made by translating the key clicks.
 *This class is also responsible for the animation.
 */
/**
 * TetrisDisplay creates the graphics for the game display
 * by extending a Jpanel
 * @@author: Praneetha Gobburi
 * @@date: 11-23-2020
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {
    private TetrisGame game;
    private int start_x;
    private int start_y;
    private int cell_size;
    private JTextField textField, over;
    //private Font font;

    public TetrisDisplay( TetrisGame gam) {
        game = gam;
        this.start_x = 180;
        this.start_y = 0;
        this.cell_size = 40;

        //TestField
        Font font = new Font("Courier", Font.BOLD,24);
        Font font1 = new Font("Courier", Font.BOLD, 75);
        textField = new JTextField("Score: "+game.getScore(), 8);
        textField.setFont(font);
        this.add(textField);

        over = new JTextField("GAME OVER", 8);
        over.setFont(font1);
        over.setVisible(false);

        setBackground(Color.BLACK);
        int time = 500;
        boolean checkGame = true;
        final Timer timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.checkGameOver()) {

                    over.setVisible(true);
                    revalidate();
                    ((Timer) e.getSource()).stop();
                }
                else {
                    game.makeMove();
                    repaint();
                }
            }
        });

        timer.start();

        //key pressed is causing a continuous error i am going to change it to keyReleased
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                interpretKeyString(ke);
            }
        });

        this.add(over);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void interpretKeyString(KeyEvent ke)
    {
        int keyCode = ke.getKeyCode();
        switch( keyCode ) {
            case KeyEvent.VK_LEFT:
                game.callMove("left");
                repaint();
                break;
            case KeyEvent.VK_RIGHT :
                game.callMove("right");
                repaint();
                break;
            case KeyEvent.VK_UP:
                game.callMove("rotate");
                repaint();
                break;
            case KeyEvent.VK_DOWN:

        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        over.setLocation(150,250);
        textField.setText("Score: "+ game.getScore());
        textField.setLocation(0,0);
        int rows = game.getNumberRows();
        int cols = game.getNumberCols();
        int[][] board = game.getBoard();
        int cellsizeX = game.getNumberCols() * cell_size;
        int cellsizeY = game.getNumberRows() * cell_size;
        Color color = Color.white;
        g.setColor(color);
        g.fillRect(start_x, start_y, cellsizeX, cellsizeY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(board[row][col] > 0) {
                    int x = col * cell_size + start_x;
                    int y = row * cell_size;
                    int brick = board[row][col] % game.getCounterIncr();
                    switch (brick){
                        case 1:
                            color = Color.orange;
                            break;
                        case 2:
                            color = Color.green;
                            break;
                        case 3:
                            color = Color.pink;
                            break;
                        case 4:
                            color = Color.cyan;
                            break;
                        case 5:
                            color = Color.magenta;
                            break;
                        case 6:
                            color = Color.blue;
                            break;
                        case 7:
                            color = Color.red;
                            break;
                    }
                    g.setColor(color);
                    g.fillRect(x, y, cell_size, cell_size);
                    g.setColor(Color.BLACK);
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setStroke(new BasicStroke(2));
                    g.drawRect(x,y, cell_size,cell_size);
                }
            }
        }
    }

}
