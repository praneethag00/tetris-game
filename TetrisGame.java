/*
 *  Tetris game is responsible for responding to moves and updating game
 * board and status. It is also tracks the rows on the board to detect when
 * row have been filled, removing them and tracking player score.
 * The game must also have end detection logic.
 */

/**
 * TetrisGame contains logic used to direct game.
 * @@author: Praneetha Gobburi
 * @@date: 11-25-2020
 */

import java.util.Random;

public class TetrisGame {
    private TetrisBrick currentPiece;
    private int[][] board;
    private int numberRows;
    private int numberCols;
    private int score = 0;
    private int state;
    private int brick;
    private int counter;
    private int counterIncr = 10;
    // the incr of one, two, three, and four rows
    private int oneRowIncr = 100;
    private int twoRowIncr = 300;
    private int threeRowIncr = 500;
    private int fourRowIncr = 1000;

    public TetrisGame() {
        this.numberRows = 18;
        this.numberCols = 12;
        this.board = new int[numberRows][numberRows];
        this.score = 0;
        this.state = 0;

        generateRandomPiece();

    }

    public void generateRandomPiece()
    {
        checkLines();
        int bri = new Random().nextInt(7) + 1 ;
        this.brick = bri;
        this.counter += counterIncr;
        this.brick = counter + bri;
        switch(bri)
        {
            case 1:
                this.currentPiece = new ElBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 2:
                this.currentPiece = new JayBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 3:
                this.currentPiece = new EssBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 4:
                this.currentPiece = new ZeeBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 5:
                this.currentPiece = new StackBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 6:
                this.currentPiece = new SquareBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
            case 7:
                this.currentPiece = new LongBrick();
                setPositionIntoBoard(this.currentPiece, brick);
                break;
        }
    }

    public void setPositionIntoBoard(TetrisBrick Piece, int brickNum)
    {
        int[][] position = Piece.getPosition();
        for(int row = 0; row < position.length; row++) {
            int[] pos_row = position[row];
            int at_x = pos_row[0];
            int at_y = pos_row[1];
            if (row < getNumberRows()) {
                for (int x = 0; x < getNumberRows(); x++) {
                    this.board[at_x][at_y] = brickNum;
                }
            }
        }
    }

    public void removeCurrentPosition() {
        int[][] position = this.currentPiece.getPosition();
        for (int i = 0; i < position.length; i++) {
            int row = position[i][0];
            int col = position[i][1];
            this.board[row][col] = 0;
        }
    }

    public void callMove(String move)
    {
        boolean outOfCol = false;
        int[][] position = currentPiece.getPosition();
        for(int row = 0; row < position.length; row++) {
            int at_x = position[row][0];
            int at_y = position[row][1];
            if (move.equals("left")) {
                if(at_y <= 0) {
                    outOfCol = true;
                    break;
                }
            }
            else if(move.equals("right")){
                if (at_y >= numberCols - 1) {
                    outOfCol = true;
                    break;
                }
            }
        }
        if(outOfCol == false) {
            boolean collided = validateMove(move);
            switch (move) {
                case "left":
                    if (!collided) {
                        removeCurrentPosition();
                        currentPiece.moveLeft();
                        setPositionIntoBoard(currentPiece, getBrick());
                    }
                    break;
                case "right":
                    if (!collided) {
                        removeCurrentPosition();
                        currentPiece.moveRight();
                        setPositionIntoBoard(currentPiece, getBrick());
                    }
                    ;
                    break;
                case "rotate":
                    removeCurrentPosition();
                    if (getBrick() % counterIncr != 6)
                        rotate();
                    break;
            }

        }
    }

    public void makeMove()
    {
        boolean isOutOfBounds = checkOutOfBounds();
        if (isOutOfBounds) {
            generateRandomPiece();
        } else {
            removeCurrentPosition();
            boolean collided = validateMove("down");
            if (collided) {
                setPositionIntoBoard(currentPiece, getBrick());
                generateRandomPiece();
            }
            else
                currentPiece.moveDown();
            setPositionIntoBoard(currentPiece, getBrick());
        }
    }


    private boolean checkOutOfBounds() {
        int[][] position = currentPiece.getPosition();
        boolean outOfBounds = false;
        for(int row = 0; row < position.length; row++) {
            int[] pos_row = position[row];
            int at_x = pos_row[0];
            int at_y = pos_row[1];
            if (at_x >= numberRows - 1) {
                outOfBounds = true;
                break;
            }
        }
        return outOfBounds;
    }

    private boolean validateMove(String nextMove) {
        int[][] position = getCurrentPiece().getPosition();
        for(int row = 0; row < position.length; row++) {
            int[] pos_row = position[row];
            if (nextMove.equals("down")) {
                if (board[pos_row[0] + 1][pos_row[1]] != 0) {
                    return true;
                }
            }
            else if (nextMove.equals("left")) {
                if (board[pos_row[0]][pos_row[1] - 1] != 0&& board[pos_row[0]][pos_row[1] - 1] != getBrick()) {
                    return true;
                }
            }
            else if (nextMove.equals("right")) {
                if (board[pos_row[0]][pos_row[1] + 1] != 0 && board[pos_row[0]][pos_row[1] + 1] != getBrick()) {
                    return true;
                }
            }
            else if(nextMove.equals("rotate")){
                if (board[pos_row[0]][pos_row[1]] != 0 && board[pos_row[0]][pos_row[1]] != getBrick()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void rotate()
    {
        int prevOrientation = currentPiece.getOrientation();
        //do a get position and add the original pos to it?
        int orientation = prevOrientation;
        if(orientation < 3)
            orientation +=1;
        else{
            orientation = 0;
        }
        currentPiece.setOrientation(orientation);

        int[][] prevPosition = currentPiece.getPosition();
        int[][] rotatedPiece = currentPiece.rotate(prevOrientation);
        boolean collided = false;
        for(int row = 0; row < rotatedPiece.length; row++)
        {
            int x = rotatedPiece[row][0];
            int y = rotatedPiece[row][1];
            x +=prevPosition[2][0];
            y +=prevPosition[2][1];

            rotatedPiece[row][0] = x;
            rotatedPiece[row][1]  = y;
            //border checking
            if(rotatedPiece[row][1] < 0 || rotatedPiece[row][1] >= numberCols)
            {
                collided = true;
                break;

            }
            else if(rotatedPiece[row][0] >= numberRows|| rotatedPiece[row][0] < 0)
            {
                collided = true;
                break;
            }
        }
        if(!collided) {
            currentPiece.setPosition(rotatedPiece);
        }

        collided = validateMove("rotate");

        if(!collided) {
            setPositionIntoBoard(currentPiece, getBrick());
        }
        else
            currentPiece.setPosition(prevPosition);
    }

    public void checkLines()
    {
        int rowCounter = 0;
        for(int row = 0; row < numberRows; row++)
        {
            boolean isFilled = true;
            for(int col = 0; col < numberCols; col++)
            {
                if(board[row][col] == 0) {
                    isFilled = false;
                }
            }
            if(isFilled) {
                removeLine(row);
                rowCounter++;
                dropRows(row);
            }
        }
        UpdateScore(rowCounter);
    }

    //Update score based on the pdfs
    public void UpdateScore(int row){
        switch(row){
            case 1:
                this.score += oneRowIncr;
                break;
            case 2:
                this.score += twoRowIncr;
                break;
            case 3:
                this.score += threeRowIncr;
                break;
            case 4:
                this.score += fourRowIncr;
                break;
        }
    }


    public void dropRows(int row){
        for(int r = row; r >0; r--)
        {
            for(int col = 0; col < numberCols; col++)
               this.board[r][col] = board[r-1][col];
        }
    }

    public void removeLine(int row){
            for (int col = 0; col < numberCols; col++)
                this.board[row][col] = 0;
    }

    public boolean checkGameOver(){
        int row = 0;
        int col = 6;
            if(board[row][col] != 0 && board[row][col] != getBrick())
                return true;
        return false;
    }

    public int getCounter(){
        return counter;
    }

    public int getCounterIncr(){
        return counterIncr;
    }

    public int getBrick() {
        return brick;
    }

    public TetrisBrick getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPlace(TetrisBrick currentPlace) {
        this.currentPiece = currentPlace;

    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        for (int x = 0; x < getNumberRows(); x++) {
            for(int y = 0; y < getNumberCols(); y++)
            this.board[x][y] = board[x][y];
        }
    }

    public int getNumberRows() {
        return numberRows;
    }
    // no set for number rows because it is not needed

    public int getNumberCols() {
        return numberCols;
    }
    //no set for num of cols because it is not needed

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        String stuff = "" + board.length + " " + board[0].length + "\n";
        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                stuff += board[row][col] + " ";
            }
            stuff += "\n";
        }
        stuff = stuff.substring(0,stuff.length()-1);
        return stuff;
    }
}

