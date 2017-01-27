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
public class MainActivity extends AppCompatActivity {
    private ImageButton[] XObuttons;
    private Button start;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                start.setText("Running");
                editText.setText("Game in progress...");
                XObuttonsClickable(true);
            }
        });
    }

    public void XObuttonsClickable(boolean val) {
        for (int i = 0; i < 9; i++) {
            XObuttons[i].setClickable(val);
        }
    }

    public void XOButtonInitClickListeners() {
        XObuttons[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[0].setImageResource(R.drawable.tictactoex);
                XObuttons[0].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 0 Pressed");
            }
        });

        XObuttons[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[1].setImageResource(R.drawable.tictactoex);
                XObuttons[1].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 1 Pressed");
            }
        });

        XObuttons[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[2].setImageResource(R.drawable.tictactoex);
                XObuttons[2].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 2 Pressed");
            }
        });

        XObuttons[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[3].setImageResource(R.drawable.tictactoex);
                XObuttons[3].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 3 Pressed");
            }
        });

        XObuttons[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[4].setImageResource(R.drawable.tictactoex);
                XObuttons[4].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 4 Pressed");
            }
        });

        XObuttons[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[5].setImageResource(R.drawable.tictactoex);
                XObuttons[5].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 5 Pressed");
            }
        });

        XObuttons[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[6].setImageResource(R.drawable.tictactoex);
                XObuttons[6].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 6 Pressed");
            }
        });

        XObuttons[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[7].setImageResource(R.drawable.tictactoex);
                XObuttons[7].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 7 Pressed");
            }
        });

        XObuttons[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XObuttons[8].setImageResource(R.drawable.tictactoex);
                XObuttons[8].setScaleType(ImageView.ScaleType.FIT_XY);
                editText.setText("Button 8 Pressed");

            }
        });
    }

}
