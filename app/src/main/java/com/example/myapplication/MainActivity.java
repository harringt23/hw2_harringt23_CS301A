/* 15-SQUARES PUZZLE GAME
 * This application implements the 15-squares puzzle game in which
 * the rules are to slide one piece at a time until the 4x4 board
 * resembles:
  _________________________
  |  1  |  2  |  3  |  4  |
  _________________________
  |  5  |  6  |  7  |  8  |
  _________________________
  |  9  |  10 | 11  | 12  |
  _________________________
  |  13 | 14  | 15  |     |
  _________________________
 * where the bottom right corner represents an empty slot. The game
 * begins with the numbers randomly shuffled and the user must attempt
 * to slide the pieces into the correct orientation.
 *
 * ENHANCEMENTS
 1. Instead of randomly initialing the puzzle at the start, randomly
    initialize it with the restriction that the puzzle is winnable.
    (There are certain initial configurations for which the puzzle is not winnable).

 * 2. Write your program so that the size of the puzzle can be changed.
   For example, a user could specify a 7x7 puzzle (with 48 numbered squares) or a
   9x9 puzzle (with 80 numbered squares). It is up to you to decide how the user
   should specify the puzzle size. I chose a minimum of 3 and maximum of 10.
 *
 * @author Brynn Harrington
 * @version October 17, 2021
 *
 *
 * NOTE: I have requested and been granted an extension on this assignment
 * due to illness. Dr. Tribelhorn can verify if need be.
 *
 *
 * All code was written by me unless otherwise cited in the comments.
 * x__Brynn Harrington_________
 */
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a new board view
        BoardView boardView = findViewById(R.id.boardView);

        // set up the on click listener for the reset button
        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(boardView);

        // set up the touch listener for the board
        boardView.setOnTouchListener(boardView);

        // set up the seek bar for the board
        SeekBar seekBar = findViewById(R.id.numSqBoard);
        seekBar.setOnSeekBarChangeListener(boardView);
    }
}