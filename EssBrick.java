/*
 * EssBrick is a subclass that inherits TetrisBrick class and
 * overwrites methods initPosition and rotate.
 * @@author: Praneetha G
 * @@date: 11-25-2020
 */
import java.awt.*;
import java.util.Random;

public class EssBrick extends TetrisBrick {
    public EssBrick() {
        super(new int[][] {{0,6}, {0,7}, {1,5}, {1,6}}, Color.pink);
        initPosition();
    }

    @Override
    public void initPosition() {
        // Generate random position only two variations
        int orientation = new Random().nextInt(1) ;
        int[][] newPosition = generatePosition(orientation);

        super.setOrientation(orientation);
        super.setPosition(newPosition);
    }

    @Override
    public int[][] rotate(int prevOrien) {
        int[][] origPosition = generatePosition(0);
        int[][] rotate = new int[4][2];
        switch(getOrientation())
        {
            case 0:
                for(int row = 0; row < rotate.length; row++)
                {//can cange prev to orig and not ure prev at all in file use it in tet game instead
                    int x = (origPosition[row][0]-origPosition[2][0]);
                    int y = origPosition[row][1] - origPosition[2][1];

                    rotate[row][0] = x;
                    rotate[row][1] = y;
                }
                return rotate;
            case 1:
                for(int row = 0; row < rotate.length; row++)
                {//can cange prev to orig and not ure prev at all in file use it in tet game instead
                    int y = -(origPosition[row][0]-origPosition[2][0]);
                    int x = origPosition[row][1] - origPosition[2][1];

                    rotate[row][0] = x;
                    rotate[row][1] = y;
                }
                return rotate;
            case 2:
                for(int row = 0; row < rotate.length; row++)
                {//can cange prev to orig and not ure prev at all in file use it in tet game instead
                    int y = -(origPosition[row][1] - origPosition[2][1]);
                    int x = -(origPosition[row][0] - origPosition[2][0]);

                    rotate[row][0] = x;
                    rotate[row][1] = y;
                }
                return rotate;
            case 3:
                for(int row = 0; row < rotate.length; row++)
                {//can cange prev to orig and not ure prev at all in file use it in tet game instead
                    int y = (origPosition[row][0]-origPosition[2][0]);
                    int x = -(origPosition[row][1] - origPosition[2][1]);

                    rotate[row][0] = x;
                    rotate[row][1] = y;
                }
                return rotate;

        }
        return rotate;
    }

    public int[][] generatePosition(int orientation)
    {
        switch (orientation) {
            case 0:
                return new int[][] {{0,6}, {0,7}, {1,5}, {1,6}};
            case 1:
                return new int[][] {{0,5}, {1,5}, {1,6}, {2,6}};
        }
        return new int[][] {{0,6}, {0,7}, {1,5}, {1,6}};
    }


}

