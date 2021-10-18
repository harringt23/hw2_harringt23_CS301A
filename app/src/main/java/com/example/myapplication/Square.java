package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/* Square Class
 *
 * This class represents a single square on the board. Each square
 * holds the properties of its position and the number value it has.
 *
 * @author Brynn Harrington
 * @version October 17, 2021
 *
 */
public class Square
{
    /* Instance Variables */
    // track where the square is positioned
    private float sqLeft;
    private float sqTop;

    // initialize square size
    private int sqSize;

    // track what number is on the tile
    private int sqNumber;

    // create paint variables for the text and square color
    private Paint sqColor;
    private final Paint textColor;
    private int totalSquares;

    /* Square Constructor
     * Initializes the instance/member variables for the square object.
     */
    public Square(float left, float top, int row, int col, int number, int total)
    {
        // set the square's coordinates to the parameters passed in
        sqLeft = left;
        sqTop = top;

        // set the square size
        sqSize = 400;

        // set the number of the square and total number of squares
        sqNumber = number;
        totalSquares = total;

        // initialize the paint variables to red
        // (green indicates correct position/assuming initially incorrect)
        sqColor = new Paint();
        sqColor.setColor(Color.RED);

        // initialize the text color to white
        textColor = new Paint();
        textColor.setColor(Color.WHITE);
    }

    /* Square Copy Constructor
     *
     * Creates a deep copy of the square passed in. This method will be
     * utilized to prevent cheating as the user will only have access to
     * a copy of the current game state instead of the actual game state.
     *
     * @param sq - the square to be copied
     */
    public Square(Square initSq)
    {
        // set the square's parameters to the initial square passed in
        this.sqLeft = initSq.sqLeft;
        this.sqTop = initSq.sqTop;
        this.sqSize = initSq.sqSize;
        this.sqNumber = initSq.sqNumber;
        this.totalSquares = initSq.totalSquares;
        this.sqColor = new Paint(initSq.sqColor);
        this.textColor = new Paint(initSq.textColor);
    }

    /* draw
     *
     * Draws the square onto the surface view based on the instance
     * of the canvas passed in.
     *
     * @param canvas - the current instance of the canvas to draw onto
     */
    public void draw(Canvas canvas)
    {
        // draw the square onto the canvas based on the coordinates
        canvas.drawRect(sqLeft, sqTop, sqLeft + sqSize, sqTop + sqSize, sqColor);

        // set the text size to
        textColor.setTextSize(50);

        // draw the number onto the square
        if (sqNumber != totalSquares) {
            canvas.drawText(String.valueOf(sqNumber), sqLeft + sqSize / 2, sqTop + sqSize / 2, textColor);
        }
        else {
            canvas.drawText("", sqLeft + sqSize / 2, sqTop + sqSize / 2, textColor);
        }
    }

    /* getSqNumber
     *
     * Returns the current square number.
     *
     * @return sqNumber - the current square number
     * */
    public int getSqNumber()
    {
        return sqNumber;
    }

    /* setSqNumber
     *
     * Sets the current square number.
     *
     * @param sqNumber - the number to set the square
     * */
    public void setSqNumber(int sqNumber)
    {
        this.sqNumber = sqNumber;
    }

    /* setSqColor
     *
     * Sets the current square color.
     *
     * @param newNumber - the color to change the square to
     * */
    public void setSqColor(Paint newColor)
    {
        sqColor = newColor;
    }
}
