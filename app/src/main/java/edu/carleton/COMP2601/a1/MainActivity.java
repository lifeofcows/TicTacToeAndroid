package edu.carleton.COMP2601.a1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
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
    public static ArrayList<Integer> XOMoves; //Will be full {0,1,2,3,4,5,6,7,8}
    private int gameResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XMoves = new ArrayList<Integer>();
        OMoves = new ArrayList<Integer>();
        XOMoves = new ArrayList<Integer>();



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
                    for(int i=0; i<9; i++){
                        XOMoves.add(i);
                    }

                    for(int i=0; i<XOMoves.size();i++){
                        System.out.println(XOMoves.get(Integer.valueOf(i)));
                    }
                    XObuttonsClickable(false);
                    lastMove = 1;
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
                        if (lastMove == 0) {
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

                        for (int i = 0; i < 9; i++){
                            if(XOMoves.indexOf(i) != -1){ //find next move for comp
                                temp = i;
                                break;
                            }
                        }

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
            for (int i = 0; i < 9; i++) {
                XObuttons[i].setClickable(false);
            }
        }
        else {
            System.out.println("Clicking is TRUE");
            for (int i : XOMoves) {
                System.out.println(i + " is i");
                XObuttons[i].setClickable(true);
            }
        }
    }

    public void endGame(){
        //System.out.println("Ending Game...");
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
    public void DisplayLists(){
      //  System.out.println("Displaying lists...");
       // System.out.println("XOMoves: " + XOMoves);
    }

    public void XOButtonInitClickListeners() { //shorten these methods if you know how to, idk how to do it properly
        XObuttons[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[0].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[0].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(0);
                    XObuttonsClickable(false);
                }
                else {
                    XObuttons[0].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[0].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(0);
                }
                System.out.println("Removing " + Integer.valueOf(0));
                DisplayLists();
                editText.setText("Button 0 Pressed");
                XObuttons[0].setClickable(false); //make button unclickable after
                XOMoves.remove(Integer.valueOf(0));
            }
        });

        XObuttons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[1].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[1].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(1);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[1].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[1].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(1);
                }
                System.out.println("Removing " + Integer.valueOf(1));
                DisplayLists();
                editText.setText("Button 1 Pressed");
                XObuttons[1].setClickable(false);
                XOMoves.remove(Integer.valueOf(1));
            }
        });

        XObuttons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[2].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[2].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(2);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[2].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[2].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(2);
                }
                System.out.println("Removing " + Integer.valueOf(2));
                DisplayLists();
                editText.setText("Button 2 Pressed");
                XObuttons[2].setClickable(false);
                XOMoves.remove(Integer.valueOf(2));
            }
        });

        XObuttons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[3].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[3].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(3);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[3].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[3].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(3);
                }
                System.out.println("Removing " + Integer.valueOf(3));
                DisplayLists();
                editText.setText("Button 3 Pressed");
                XObuttons[3].setClickable(false);
                XOMoves.remove(Integer.valueOf(3));
            }
        });

        XObuttons[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[4].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[4].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(4);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[4].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[4].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(4);
                }
                System.out.println("Removing " + Integer.valueOf(4));
                DisplayLists();
                editText.setText("Button 4 Pressed");
                XObuttons[4].setClickable(false);
                XOMoves.remove(Integer.valueOf(4));
            }
        });

        XObuttons[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[5].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[5].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(5);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[5].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[5].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(5);
                }
                System.out.println("Removing " + Integer.valueOf(5));
                DisplayLists();
                editText.setText("Button 5 Pressed");
                XObuttons[5].setClickable(false);
                XOMoves.remove(Integer.valueOf(5));
            }
        });

        XObuttons[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[6].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[6].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(6);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[6].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[6].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(6);
                }
                System.out.println("Removing " + Integer.valueOf(6));
                DisplayLists();
                editText.setText("Button 6 Pressed");
                XObuttons[6].setClickable(false);
                XOMoves.remove(Integer.valueOf(6));
            }
        });

        XObuttons[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[7].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[7].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(7);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[7].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[7].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(7);
                }
                System.out.println("Removing " + Integer.valueOf(7));
                DisplayLists();
                editText.setText("Button 7 Pressed");
                XObuttons[7].setClickable(false);
                XOMoves.remove(Integer.valueOf(7));
            }
        });

        XObuttons[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastMove == 1){
                    XObuttons[8].setImageResource(R.drawable.tictactoex); //set image resource
                    XObuttons[8].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 0;
                    XMoves.add(8);
                    XObuttonsClickable(false);
                }
                else{
                    XObuttons[8].setImageResource(R.drawable.tictactoeo); //set image resource
                    XObuttons[8].setScaleType(ImageView.ScaleType.FIT_XY); //scale to fit button
                    lastMove = 1;
                    OMoves.add(8);
                }
                System.out.println("Removing " + Integer.valueOf(8));
                DisplayLists();
                editText.setText("Button 8 Pressed");
                XObuttons[8].setClickable(false);
                XOMoves.remove(Integer.valueOf(8));
            }
        });
    }

}
