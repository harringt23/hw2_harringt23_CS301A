package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize a new board view
        BoardView boardView = (BoardView) findViewById(R.id.boardView);

        // set up the on click listener for the reset button
        Button reset = (Button) findViewById(R.id.reset_button);
        reset.setOnClickListener(boardView);

        // set up the touch listener for the board
        boardView.setOnTouchListener(boardView);
    }
}