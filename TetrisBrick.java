/* TetrisBrick is a abstract class. It will be inherited by its seven subclasses.
 * There are two abstract methods that TetrisBrick must have that each subclass
 *  must overwrite, initPositions, which allows each shape to start at the appropriate
 *  place, and rotate, which allows each unique shape to follow its unique rotation.
 */

/**
 * TetrisBrick  is the class that contains all of the attributes and methods
 * that all of the different bricks have in common.
 * @author Praneetha Gobburi
 * @date 11-18-2020
 */

import java.awt.*;

public abstract class TetrisBrick
{
    private int[][] position;
    private Color color;
    private int orientation;
    private int numSegments;

    public TetrisBrick(int[][] pos, Color color ) {
        this.position = pos;
        this.color = color;
        this.orientation = 0;
        this.numSegments = 4;
    }

    public abstract void initPosition();

    public abstract int[][] rotate(int prevOrientation);

    public void moveLeft()
    {
        for(int row = 0; row < position.length; row++)
        {
            int[] pos_row = position[row];
            int y = pos_row[1];
            int new_Y = y - 1;
            this.position[row][1] = new_Y;
        }
    }
    public void moveRight()
    {
        for(int row = 0; row < position.length; row++)
        {
            int[] pos_row = position[row];
            int y = pos_row[1];
            int new_Y = y + 1;
            this.position[row][1] = new_Y;
        }
    }

    public void moveDown()
    {
        for(int row = 0; row < position.length; row++)
        {
            int[] pos_row = position[row];
            int x = pos_row[0];
            int new_X = x + 1;
            this.position[row][0] = new_X;
        }
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getNumSegments() {
        return numSegments;
    }

    public void setNumSegments(int numSegments) {
        this.numSegments = numSegments;
    }
}
