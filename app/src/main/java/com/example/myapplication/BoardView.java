package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* BoardView Class
 *
 * This class represents the current view of the board. The listeners
 * are instantiated and responded to in this class to correctly display
 * the board to the user. This class extends both OnClickListener and
 * OnTouchListener to accurately process the data the user is sending to
 * the tablet and implement the correct response according to that.
 *
 * @author Brynn Harrington
 * @version October 4, 2021
 *
 */
public class BoardView extends SurfaceView
        implements View.OnClickListener, View.OnTouchListener
{
    /* Constant Variables */
    // initialize the total number of squares in the game
    private final int sqTotal = 16;

    // initialize the number of squares per row
    private final int sqPerRow = 4;

    // initialize the width, top, and left of the board
    private final int boardWidth = 1000;
    private final float boardTop = 200;
    private final float boardLeft = 100;

    // initialize the size of the board based on the number of squares per row
    private final int sqSize = boardWidth / sqPerRow;


    /* Instance/Member Variables */
    // initialize a new array of squares in the game (16 squares on the board)
    private ArrayList<Square> board;

    // create an array to track positions of squares
    private ArrayList<Integer> sqNumbers;

    // track the coordinates of the square being dragged
    private float userInitLeft, userInitTop, userDragLeft, userDragTop;

    // track where the empty square is
    private float blankSqLeft, blankSqTop;

    // track where the the square user drags
    private int userSqLeft, userSqTop;

    // determine whether the user dragged the square and when count starts
    private boolean userDragged, timeStart;

    // set a new paint variable for the correct and incorrect position
    private Paint correctPosition, incorrectPosition;

    /* BoardView
     *
     * Constructor for BoardView to initialize the instance/member variables.
     */
    public BoardView(Context context, AttributeSet attributeSet)
    {
        // call super to call the parent constructor of SurfaceView
        super(context, attributeSet);

        // initialize the array of possible numbers
        //sqNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        // initialize where user initially drags and ends dragging
/*        userInitLeft = -1;
        userInitTop = -1;
        userDragLeft = -1;
        userDragTop = -1;*/

        // initialize the user's choice of square
        userSqLeft = -1;
        userSqTop = -1;

        // initialize user dragged and time has started to false since game
        // has not begun
        userDragged = false;
        timeStart = false;

        // initialize the surface view to draw
        setWillNotDraw(false);

        // initialize paint for correct and incorrect positions
        correctPosition = new Paint();
        correctPosition.setColor(Color.GREEN);
        incorrectPosition = new Paint();
        incorrectPosition.setColor(Color.RED);

        // initialize the board to a random set up
        initBoard();


    }

    /* initBoard
     *
     * Initialize the board to a random setup / numbers are placed in random
     * positions.
     *
     * TODO - ensure that the board returns properly since instance/member variable
     */
    public void initBoard()
    {
        // initialize the list of numbers that a square can hold as a value
        // 0 represents empty, 15 is the max
        sqNumbers = new ArrayList<Integer>(sqTotal);
        for (int sqNum = 0; sqNum <= 15; sqNum++)
        {
            sqNumbers.add(sqNum);
        }

        // shuffle the board to create random numbering
        Collections.shuffle(sqNumbers);

        // instantiate the list of squares via the randomly shuffled integers
        board = new ArrayList<Square>(sqTotal);

        // iterate through the squares and assign each square a position on the board
        for (int x = 0; x < sqPerRow; x++) 
        {
            for (int y = 0; y < sqPerRow; y++) 
            {
                // find the current number value in array
                int currNum = x * sqPerRow;

                // find the current square's index by the col + currNum
                int sqIndex = y + currNum;
                Square sq = new Square(boardLeft + sqSize * y,
                        boardTop + sqSize * x, sqNumbers.get(currNum));

                // add the square to the list
                board.add(sq);

                // determine the current value of the blank square
                if (sqNumbers.get(currNum) == 0) {
                    blankSqTop = y;
                    blankSqLeft = x;
                }
            }
        }
    }

    /* randomSqNum
     *
     * Returns a random number between 1 and 15 to initialize the board.
     *
     * @return randSq - the random number the square will hold
     */
/*    public int randSqNum() {
        // initialize a random number between 0 and 16
        int randSq = (int) (Math.random() * boardSize);

        // if the number is not zero, then return the random number
        if (sqNumbers[randSq] != 0)
        {
            sqNumbers[randSq] = 0;
            return  randSq + 1;
        }

        // if already chosen, generate a new random number
        return randSqNum();
    }*/

    /* onDraw
     *
     * This method overrides the onDraw method to draw the squares accurately
     * onto the current instance of the canvas. The canvas will be displayed
     * onto the surface view object that the tablet has access to.
     *
     * @param canvas - the canvas that will be drawn onto the surface view
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        // iterate through the board to determine if whether the user dragged or not 
    }

    /* correctPosition
     *
     * This method determines whether the square is in the correct place on the board.
     * If it is in the correct place, the color will be set to green otherwise it will
     * be set/remain red depending on if was initially in the incorrect spot or if it 
     * moved to the wrong spot.
     * 
     * 
     *  */
    public void correctPosition()
    {
     /*   // iterate through the board to determine whether the user is dragging and if
        // so, if the position is correct
        for (int x = 0; x < boardSize; x++)
        {
            for (int y = 0; y < boardSize; y++)
            {
                // if the user did not drag, determine if the positioning is correct
                if (!userDragged)
                {
                    // calculate the current number of the board
                    int currNum = (x * 4) + y + 1;

                    // determine if the square is in the correct position
                    if (board[x][y].getSqNumber() == currNum)
                    {
                        board[x][y].setSqColor(correctPosition);
                    }
                    else board[x][y].setSqColor(incorrectPosition);
                }
            }
        }*/
    }
    
    /* blankSqLoc 
     *
     * Determine the positioning of the blank square.
     */
    public void setBlankSqLoc()
    {
/*        // iterate through the board
        for (int x = 0; x < boardSize; x++)
        {
            for (int y = 0; y < boardSize; y++)
            {
                // if it's the blank space determine the current value of it
                if (board[x][y].getSqNumber() == boardSize * boardSize)
                {
                    blankSqLeft = board[x][y].getSqLeft();
                }
            }
        }*/
    }
    
    @Override
    public void onClick(View view)
    {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        return false;
    }
}
