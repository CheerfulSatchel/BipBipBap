package com.bipbipbap;

import metronome.*;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by James Wu on 7/10/2016.
 */
public class play extends Activity{

    Button tapButton;
    //This is the lone metronome
    Metronome metro;
    //This is the everchanging action star, hank
    Action hank;
    //Initial bpm is set to 45
    int bpm = 45;
    //Keepin track of the points won by the user
    int pointsCounter = 0;

    //This is an arraylist holding the commands.
    ArrayList<Action> commands = new ArrayList<Action>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        Log.v("AWWW YEEEEAAAAHH ", "POOP");

        System.out.println("LOOK:" + commands);

        hank = new Action() {
            private boolean incrementTicks = false;
            private int ticks = 0;

            public void run() {
                System.out.println("IT'S THE SOCIAL-JUSTICE-MOBILE");

                if(incrementTicks){
                    ticks++;
                }

                incrementTicks = !incrementTicks;
                System.out.println("Yee! " + ticks);

                if(ticks == 6){
                    System.out.println("Tell the user to do something now.");
                    ticks = 0;
                }
                tapButton = (Button) findViewById(R.id.button);
                tapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.getRootView().setBackgroundColor(Color.GREEN);
                        //onCancel();
                        metro.stop();
                    }
                });
            }
        };

        metro = new Metronome(hank, bpm);
    }
}
