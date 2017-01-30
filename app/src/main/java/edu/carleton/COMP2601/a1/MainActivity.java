package edu.carleton.COMP2601.a1;

//Developers: Zachary Seguin, Maxim Kuzmenko

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static edu.carleton.COMP2601.a1.R.string.neutralgame;
import static edu.carleton.COMP2601.a1.R.string.userloss;
import static edu.carleton.COMP2601.a1.R.string.userwin;

//UI updates must occur through methods defined in the MainActivity class.
//All static strings must be defined as resources in the strings.xml file. They must not be hard-coded in either your MainActivity or Game classes.
//There are some predefined strings in strings.xml now
public class MainActivity extends AppCompatActivity {
    private ImageButton[] XObuttons;
    private Button start;
    private EditText editText;
    private int lastMove; //0 if player did the last move, 1 if computer did the last move
    private int temp;
    public static boolean threadActive; //if thread is active, used in the Thread in this class, in Game.isAllFilled and in the click listener
    public static ArrayList<Integer> XMoves;
    public static ArrayList<Integer> OMoves;
    public static ArrayList<Integer> XOMoves; //Will be  full {0,1,2,3,4,5,6,7,8}
    private int gameResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XMoves = new ArrayList<Integer>(); //Stores moves made by player
        OMoves = new ArrayList<Integer>(); //Stores moves made by Computer
        XOMoves = new ArrayList<Integer>(); //Stores remaining available moves (buttons that have not been selected)



        XObuttons = new ImageButton[9];

        for (int i = 0; i < 9; i++) { //findViewById to all XObuttons
            XObuttons[i] = (ImageButton) findViewById(getResources().getIdentifier("imageButton" + (i+1), "id", getPackageName()));
        }

        XOButtonInitClickListeners();

        XObuttonsClickable(false); //Disables buttons until player hits "Start"

        editText = (EditText) findViewById(R.id.editText);
        editText.setText("Click Start button to start game");

        start = (Button) findViewById(R.id.button10);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do start
                if (!start.getText().equals("Running")) { //This fills list with all available buttons.
                    for(int i=0; i<9; i++){
                        XOMoves.add(i);
                    }

                    XObuttonsClickable(false);
                    lastMove = 1; //Variable that determines who plays first (this indicates that the player starts)
                    XMoves.clear();
                    OMoves.clear();
                    start.setText("Running");
                    editText.setText("Game in progress...");
                    startThreadPlaying();
                    threadActive = true;
                }
                else { //the button is clicked while game is happening, restart game
                    endGame();
                }
            }
        });
    }

    //rules: 1. player can only play on their turn, so if player just made a move they will have to wait until the computer makes a move before they can
    //2. if the player doesn't make a move 2 seconds after the computer makes a move, then the computer will move for the player (1st rule still applies though)
    public synchronized void startThreadPlaying() {
        new Thread() {
            public synchronized void run() {
                try {
                    while (threadActive) {
                        if (lastMove == 0) { //If player played last, disable all buttons
                            XObuttonsClickable(false);
                        }
                        else {
                            //System.out.println("YOU CAN CLICK NOW");
                            XObuttonsClickable(true);
                        }

                        Thread.sleep(2000);

                        gameResult = Game.checkGameOver() ;

                        if (gameResult != 0) { //somebody wins
                            endGame();
                            break;
                        }

                        Random r = new Random(); //Picks a random button to click from XOMoves
                        temp = XOMoves.get(r.nextInt(XOMoves.size()));

                        final CountDownLatch latch = new CountDownLatch(1);
                        Runnable uiThread;
                        runOnUiThread(uiThread = new Runnable() {
                            @Override
                            public synchronized void run() {
                                XObuttons[temp].performClick();
                                latch.countDown();
                            }
                        });
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception happened in the main thread");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void XObuttonsClickable(boolean val) {
        if (val == false) {
            System.out.println("Clicking is FALSE");
            for (int i = 0; i < 9; i++) { //Disable all buttons
                XObuttons[i].setClickable(false);
            }
        }
        else {
            System.out.println("Clicking is TRUE");
            for (int i : XOMoves) { //Enable all buttons
                System.out.println(i + " is i");
                XObuttons[i].setClickable(true);
            }
        }
    }

    public void endGame(){
        XOMoves.clear();
        XObuttonsClickable(false);
        threadActive = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setText("Start");
                if (gameResult == 1) {
                    editText.setText(userwin);
                }
                else if (gameResult == 2) {
                    editText.setText(userloss);
                }
                else {
                    editText.setText(neutralgame);
                }
                for(int i=0; i<9; i++){
                    XObuttons[i].setImageResource(R.drawable.tictactoeblank); //set image resource
                    XObuttons[i].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                }
            }
        });
    }

    public void makeMove(int x){
        if(lastMove == 1){
            XObuttons[x].setImageResource(R.drawable.tictactoex); //set image resource
            XObuttons[x].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
            lastMove = 0;
            XMoves.add(x);
            XObuttonsClickable(false);
        }
        else {
            XObuttons[x].setImageResource(R.drawable.tictactoeo); //set image resource
            XObuttons[x].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
            lastMove = 1;
            OMoves.add(x);
        }
        System.out.println("Removing " + Integer.valueOf(x));
        editText.setText("Button " + x + " Pressed");
        XObuttons[x].setClickable(false); //make button unclickable after
        XOMoves.remove(Integer.valueOf(x));
    }
    public void XOButtonInitClickListeners() {
        XObuttons[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(0);
            }
        });

        XObuttons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(1);
            }
        });

        XObuttons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(2);
            }
        });

        XObuttons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(3);
            }
        });

        XObuttons[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(4);
            }
        });

        XObuttons[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(5);
            }
        });

        XObuttons[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(6);
            }
        });

        XObuttons[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(7);
            }
        });

        XObuttons[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makeMove(8);
            }
        });
    }

}
