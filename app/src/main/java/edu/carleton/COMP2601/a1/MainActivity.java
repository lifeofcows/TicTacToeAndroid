package edu.carleton.COMP2601.a1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

//UI updates must occur through methods defined in the MainActivity class.
//All static strings must be defined as resources in the strings.xml file. They must not be hard-coded in either your MainActivity or Game classes.
//There are some predefined strings in strings.xml now
public class MainActivity extends AppCompatActivity {
    private ImageButton[] XObuttons;
    private Button start;
    private EditText editText;
    public static int buttonsActive; //# buttons currently showing either X or O
    private boolean movePlayed; //used for the Thread.sleep (last time a move has been played)
    private int lastMove; //0 if player did the last move, 1 if computer did the last move
    public static boolean threadActive; //if thread is active, used in the Thread in this class, in Game.isAllFilled and in the click listener
    public static boolean[] XMoves, OMoves; //array of moves so far (Ex.; [false, false, true, false, true, false, false, false, true] would be positions 2, 4, 8)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XMoves = new boolean[9]; //set all to false in next for loop
        OMoves = new boolean[9]; //set all to false in next for loop

        movePlayed = true;
        buttonsActive = 0;

        XObuttons = new ImageButton[9];

        for (int i = 0; i < 9; i++) { //findViewById to all XObuttons
            XObuttons[i] = (ImageButton) findViewById(getResources().getIdentifier("imageButton" + (i+1), "id", getPackageName()));
        }

        XOButtonInitClickListeners();

        XObuttonsClickable(false);

        editText = (EditText) findViewById(R.id.editText);
        editText.setText("Click Start button to start game");

        start = (Button) findViewById(R.id.button10);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do start
                if (!start.getText().equals("Running")) {
                    for (int i = 0; i < 9; i++) { //done in here in case user wants to restart game
                        XMoves[i] = false;
                        OMoves[i] = false;
                    }
                    start.setText("Running");
                    editText.setText("Game in progress...");
                    XObuttonsClickable(true);
                    startThreadPlaying();
                    threadActive = true;
                }
                else { //the button is clicked while game is happening, restart game
                    start.setText("Start");
                    XObuttonsClickable(false);
                    editText.setText("Game ended.");
                    threadActive = false;
                }
            }
        });
    }

    //rules: 1. player can only play on their turn, so if player just made a move they will have to wait until the computer makes a move before they can
    //2. if the player doesn't make a move 2 seconds after the computer makes a move, then the computer will move for the player (1st rule still applies though)
    public void startThreadPlaying() {
        new Thread() {
            public void run() {
                    try {
                        while (threadActive) {
                            while (movePlayed) { //if move isnt played then break after sleep; else continuously sleep until no moves happen for 2 sec
                                movePlayed = false;
                                Thread.sleep(2000);
                            }

                            //implement game logic here somehow
                            runOnUiThread(new Runnable() { //actually we prob dont even need runOnUiThread since clicklisteners do everything already
                                @Override
                                public void run() {

                                }
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Exception happened in the main thread");
                        e.printStackTrace();
                    }
            }
        }.start();
    }

    public void XObuttonsClickable(boolean val) {
        for (int i = 0; i < 9; i++) {
            XObuttons[i].setClickable(val);
        }
    }

    public void XOButtonInitClickListeners() { //shorten these methods if you know how to, idk how to do it properly
        XObuttons[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[0].setImageResource(R.drawable.tictactoex); //set image resource
                XObuttons[0].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                editText.setText("Button 0 Pressed");
                buttonsActive++;
                XObuttons[0].setClickable(false); //make button unclickable after
                movePlayed = true;
                lastMove = 0;
                XMoves[0] = true;
            }
        });

        XObuttons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[1].setImageResource(R.drawable.tictactoex);
                XObuttons[1].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 1 Pressed");
                buttonsActive++;
                XObuttons[1].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[1] = true;
            }
        });

        XObuttons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[2].setImageResource(R.drawable.tictactoex);
                XObuttons[2].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 2 Pressed");
                buttonsActive++;
                XObuttons[2].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[2] = true;
            }
        });

        XObuttons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[3].setImageResource(R.drawable.tictactoex);
                XObuttons[3].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 3 Pressed");
                buttonsActive++;
                XObuttons[3].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[3] = true;
            }
        });

        XObuttons[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[4].setImageResource(R.drawable.tictactoex);
                XObuttons[4].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 4 Pressed");
                buttonsActive++;
                XObuttons[4].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[4] = true;
            }
        });

        XObuttons[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[5].setImageResource(R.drawable.tictactoex);
                XObuttons[5].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 5 Pressed");
                buttonsActive++;
                XObuttons[5].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[5] = true;
            }
        });

        XObuttons[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[6].setImageResource(R.drawable.tictactoex);
                XObuttons[6].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 6 Pressed");
                buttonsActive++;
                XObuttons[6].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[6] = true;
            }
        });

        XObuttons[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[7].setImageResource(R.drawable.tictactoex);
                XObuttons[7].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 7 Pressed");
                buttonsActive++;
                XObuttons[7].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[7] = true;
            }
        });

        XObuttons[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[8].setImageResource(R.drawable.tictactoex);
                XObuttons[8].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 8 Pressed");
                buttonsActive++;
                XObuttons[8].setClickable(false);
                movePlayed = true;
                lastMove = 0;
                XMoves[7] = true;
            }
        });
    }

}
