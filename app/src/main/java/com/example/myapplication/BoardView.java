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
    // initialize the width, top, and left of the board
    private final int boardWidth = 1000;
    private final float boardTop = 200;
    private final float boardLeft = 100;

    // initialize the background color of the board
    private final Paint backgroundColor = new Paint();

    // set a new paint variable for the correct and incorrect position
    private final Paint correctPosition = new Paint();
    private final Paint incorrectPosition = new Paint();

    /* Instance/Member Variables */
    // initialize the size of the rows and board
    private int sqPerRow;
    private final int sqTotal = sqPerRow * sqPerRow;

    // initialize a new array of squares in the game (16 squares on the board)
    private ArrayList<Square> board;

    // create a boolean to track if the game is over
    private boolean solved;

    // track where the empty square is
    private float blankSqLeft, blankSqTop;
    private int blankSqIndex;



    /* BoardView
     *
     * Constructor for BoardView to initialize the instance/member variables.
     *
     * @param context - interface providing global information
     * @param attributeSet - the current attribute set from XML documents
     */
    public BoardView(Context context, AttributeSet attributeSet)
    {
        // call super to call the parent constructor of SurfaceView
        super(context, attributeSet);

        // initialize the surface view to draw
        setWillNotDraw(false);

        // initialize paint for correct and incorrect positions
        correctPosition.setColor(Color.GREEN);
        incorrectPosition.setColor(Color.RED);

        // initialize paint for the background color of the board
        backgroundColor.setColor(Color.BLACK);

        // initialize the size of the rows
        sqPerRow = 4;

        // initialize the board to a random set up
        initBoard();

        // initialize solved to false
        solved = false;

    }

    /* initBoard
     *
     * Initialize the board to a random setup / numbers are placed in random
     * positions.
     *
     */
    public void initBoard()
    {
        // initialize the list of numbers that a square can hold as a value
        ArrayList<String> sqNumbers = new ArrayList<>(sqTotal);

        // initialize the first index as an empty string
        for (int sqNum = 1; sqNum < sqTotal; sqNum++)
        {
            sqNumbers.add(String.valueOf(sqNum));
        }

        // shuffle the board to create random numbering
        Collections.shuffle(sqNumbers);

        // initialize the size of the board based on the number of squares per row
        int sqSize = boardWidth / sqPerRow;

        // instantiate the list of squares via the randomly shuffled integers
        board = new ArrayList<>(sqTotal);

        // iterate through the squares and assign each square a position on the board
        for (int x = 0; x < sqPerRow; x++) 
        {
            for (int y = 0; y < sqPerRow; y++) 
            {
                // find the current number value in array
                int currNum = x * sqPerRow;

                // find the current square's index by the col + currNum
                int sqIndex = y + currNum;

                // add the new square to the board
                board.add(new Square(boardLeft + sqSize * y,
                        boardTop + sqSize * x, sqNumbers.get(sqIndex)));

                // determine the current value of the blank square
                if (sqNumbers.get(currNum).equals(""))
                {
                    blankSqTop = y;
                    blankSqLeft = x;
                    blankSqIndex = currNum;
                }
            }
        }

        // determine if any squares are in the correct position
        sqCorrectPosition();
    }

    /* sqCorrectPosition
     *
     * This method determines whether the square is in the correct place on the board.
     * If it is in the correct place, the color will be set to green otherwise it will
     * be set/remain red depending on if was initially in the incorrect spot or if it
     * moved to the wrong spot.
     *
     */
    public void sqCorrectPosition()
    {
        // reset solved to true
        solved = true;

        // initialize a variable to track the current square number
        Square currSq;

        // determine if the current index is the current value
        for (int sqNum = 0; sqNum < sqTotal - 1; sqNum++)
        {
            // get the current square
            currSq = board.get(sqNum);

            // determine if the square is in the correct position
            if (!currSq.getSqNumber().equals(String.valueOf(sqNum + 1)))
            {
                // if not empty set the current square to red
                currSq.setSqColor(incorrectPosition);
                // set solved to false
                solved = false;
            }
            else currSq.setSqColor(correctPosition);
        }
        // verify the last position is empty
        if (!board.get(sqTotal).getSqNumber().equals(""))
        {
            // if not empty set the square to red
            board.get(sqTotal).setSqColor(incorrectPosition);

            // set solved to false
            solved = false;
        }

    }

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
        // draw the background of the board onto the surface view
        canvas.drawRect(boardLeft, boardTop, boardLeft +boardWidth,
                boardTop + boardWidth, backgroundColor);

        // draw the squares onto the canvas
        for(Square sq: board) sq.draw(canvas);
    }

    /* onClick
     *
     * This method overrides the onClick method to read when the user has
     * clicked on the reset button. By doing this, the board will be re-initialized
     * and the current view will be invalidated so the new canvas may be drawn.
     *
     * @param view - the view that will be overwritten
     */
    @Override
    public void onClick(View view)
    {
        // generate the new board
        initBoard();

        // invalidate the current view
        invalidate();
    }

    /* onTouch
     *
     * This method overrides the onTouch method to correctly calculate where the
     * user is attempting to move the square. Since there is only one position
     * the user can possible move the square to (as there is only one empty square)
     * the coordinates the user presses on can be used to determine if it is next
     * to an empty, and if it is, swap the squares.
     *
     * @param view - the current view where the user's touch will be found
     * @param motionEvent - the motion event of the user
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        // verify the user pressed the screen
        if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            // determine the number of the square pressed down
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            // check if a swap is possible
            if (checkSwap(x, y))
            {
                // invalidate the current view
                invalidate();

                // return true
                return true;
            }

        }

        // if invalid touch or unable to swap keep view the same
        return false;
    }

    /* checkSwap
     *
     * This method verifies that the square is able to be swapped based on the
     * numeric value of the index passed in.
     *
     * @param x - the x coordinate where the user pressed
     * @param y - the y coordinate where the user pressed
     * @return whether the swap was successful
     */
    public boolean checkSwap(float xTap, float yTap)
    {
        // determine the index of the square the user tapped w/ respect to board
        int xBoard = (int) ((xTap - boardLeft) / sqPerRow);
        int yBoard = (int) ((yTap - boardTop) / sqPerRow);

        // determine if the board coordinates are valid
        if (xBoard >= sqPerRow || yBoard <= sqPerRow) return false;

        // otherwise determine the square tapped
        int sqIndex = (int)(xBoard + ( yBoard * sqPerRow));
        Square sqTapped = board.get(sqIndex);

        // verify the square is not empty
        if (sqTapped.getSqNumber().equals("")) return false;

        // verify the blank and tapped square are in the same row
        // check left
        if (sqTapped.getSqLeft() <= blankSqLeft
                && sqTapped.getSqTop() == blankSqTop) {
            // swap the squares
            swap(sqIndex, sqTapped);

            // return true for successful swap
            return true;
        }

        // check top
        if (sqTapped.getSqLeft() == blankSqLeft
                && sqTapped.getSqTop() <= blankSqTop) {
            // swap the squares
            swap(sqIndex, sqTapped);

            // return true for successful swap
            return true;
        }

        // check right
        if (sqTapped.getSqLeft() >= blankSqLeft
                && sqTapped.getSqTop() == blankSqTop) {
            // swap the squares
            swap(sqIndex, sqTapped);

            // return true for successful swap
            return true;
        }

        // check below
        if (sqTapped.getSqLeft() == blankSqLeft
                && sqTapped.getSqTop() >= blankSqTop) {
            // swap the squares
            swap(sqIndex, sqTapped);

            // return true for successful swap
            return true;
        }

        // if unsuccessful return false
        return false;
    }

    /* swap
     *
     * This is a helper method for swapping the squares.
     *
     * @param sqIndex - the index of the square that was tapped
     * @param sqTapped - the square that was tapped
     */
    public void swap(int sqIndex, Square sqTapped)
    {
        // get the square to swap
        Square swap = new Square(board.get(blankSqIndex));

        // swap the values of tapped index with the blank square
        board.get(blankSqIndex).setSqNumber(board.get(sqIndex).getSqNumber());
        board.get(sqIndex).setSqNumber("");

        //TODO: ensure this function resets the correct blank coordinates
        // determine if the new board is correct
        sqCorrectPosition();
    }

}
