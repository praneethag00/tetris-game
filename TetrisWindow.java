/*
 * Tetris Window is responsible for the action listeners needed to run the menus.
 * The primary functionality of this class in part 1 is to instantiate both the
 * TetrisGame and the TetrisDisplay objects, and to provide a contain for the GameDisplay
 * to be housed in and seen.
 */
/**
 * TetrisWindow is a container for the game board by extending JFrame.
 * @@author: Praneetha Gobburi
 * @@date: 11-25-2020
 */

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class TetrisWindow extends JFrame implements ActionListener
{
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_width;
    private int win_height;
    private int numScores = 10;
    JMenu gameMenu, color, size;
    JMenuItem save, newgame, topTenScores, retrieve, clear;
    String fname = "TetrisGame.dat";
    String scoresFName = "Tetris Scores.dat";


    public TetrisWindow() {
        this.win_width = 720;
        this.win_height = 820;
        this.setTitle("Tetris Game");
        //file name when saved

        JMenuBar mb = new JMenuBar();

        //new menu and menu item buttons
        gameMenu = new JMenu("Game");
        mb.add(gameMenu);

        color = new JMenu("Color");
        mb.add(color);

        size = new JMenu("Size");
        mb.add(size);

        save = new JMenuItem("Save");
        save.addActionListener(this);
        gameMenu.add(save);

        newgame = new JMenuItem("New Game");
        newgame.addActionListener(this);
        gameMenu.add(newgame);

        retrieve = new JMenuItem("Open/Retrieve");
        retrieve.addActionListener(this);
        gameMenu.add(retrieve);

        topTenScores = new JMenuItem("Top Ten");
        topTenScores.addActionListener(this);
        gameMenu.add(topTenScores);

        clear = new JMenuItem("Clear Scores");
        clear.addActionListener(this);
        gameMenu.add(clear);

        this.setJMenuBar(mb);
        this.setSize(win_width, win_height);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                File fileCon = new File(scoresFName);
                try
                {
                    String score = ""+game.getScore();
                    FileWriter outWriter = new FileWriter(fileCon, true);
                    BufferedWriter bw = new BufferedWriter(outWriter);
                    bw.newLine();
                    bw.append(score);
                    bw.close();
                    outWriter.close();
                } catch(IOException ioe)
                {
                    System.err.println("Error saving scores to file");
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame();
        display = new TetrisDisplay(game);
        this.add(display);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        //code for saving game
        if(ae.getSource().equals(save)){
            game.removeCurrentPosition();
            File fileConnection = new File(fname);
            try
            {
                FileWriter outWriter = new FileWriter(fileConnection);
                outWriter.write(game.toString());
                outWriter.close();
            } catch(IOException ioe)
            {
                System.err.println("Error save to file");
            }

            // Code to save scores
        }

        //code to open game
        if(ae.getSource().equals(retrieve)){
            game.removeCurrentPosition();
            File fileConnection = new File(fname);
            try
            {
                Scanner inScann = new Scanner(fileConnection);
                int rows = inScann.nextInt();
                int cols = inScann.nextInt();
                int[][] retBoard = new int[rows][cols];
                for(int row = 0; row < rows; row++)
                {
                    for(int col = 0; col < cols; col++)
                        retBoard[row][col] = inScann.nextInt();
                }
                game.setBoard(retBoard);
                display.repaint();
            } catch(Exception e)
            {
                System.err.print("Error occurred during retrieve from file ");
            }
        }

        //code for new game
        if(ae.getSource().equals(newgame)){
            int rows = game.getNumberRows();
            int cols = game.getNumberCols();
            int[][] newGameBoard = new int[rows][cols];
            for(int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++)
                    newGameBoard[row][col] = 0;
            }
            game.setBoard(newGameBoard);
        }

        //code to show top ten high scores
        if(ae.getSource().equals(topTenScores)) {
           String highScore =  "";
            File fileCon = new File(scoresFName);
            int counter = 0;
            int temp = 0;
            try {
                int[] scoreArr = new int[numScores];
                Scanner inScan = new Scanner(fileCon);
                while (counter < numScores && inScan.hasNextInt()) {
                        scoreArr[counter] = inScan.nextInt();
                    counter++;
                }
                if (counter < numScores) {
                    for(int i = counter; i < numScores; i++)
                        scoreArr[i] = 0;
                }
                for (int i = 0; i <numScores; i++) {
                    for (int j = i+1; j <numScores; j++) {
                        if(scoreArr[i] < scoreArr[j]) {      //swap elements if not in order
                            temp = scoreArr[i];
                            scoreArr[i] = scoreArr[j];
                            scoreArr[j] = temp;
                        }
                    }
                }
                for (int i = 0; i <numScores; i++)
                    highScore += scoreArr[i] +"\n";
                JOptionPane.showMessageDialog(null, highScore,"Top Ten High Scores",1);
            } catch (Exception e) {
                System.err.print("Error occurred during retrieve from file ");
            }
        }

        //code to clear out score file
        if(ae.getSource().equals(clear)){
            File fileCon = new File(scoresFName);
            try {
                PrintWriter writer = new PrintWriter(fileCon);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                System.err.print("Error occurred during clear from file ");
            }

        }
    }

    public static void main (String[] args)
    {
        new TetrisWindow();
    }
}
