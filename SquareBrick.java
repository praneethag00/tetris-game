/*
 * SquareBrick is a subclass that inherits TetrisBrick class and
 * overwrites methods initPosition and rotate.
 * @@author: Praneetha G
 * @@date: 11-25-2020
 */
import java.awt.*;

public class SquareBrick extends TetrisBrick {
    // orientation 0,1,2,3
    public SquareBrick() {
        super(new int[][] {{0,5}, {0,6}, {1,5}, {1,6}}, Color.blue);
    }

    @Override
    public void initPosition() {
        //square brick stays the same
        int[][] newPosition = {{0,5}, {0,6}, {1,5}, {1,6}};
        super.setPosition(newPosition);
    }

    @Override
    public int[][] rotate(int prevOrientation) {
        //square brick stays the same
        int[][] newPosition = {{0,5}, {0,6}, {1,5}, {1,6}};
        super.setPosition(newPosition);
        return newPosition;
    }
    }

