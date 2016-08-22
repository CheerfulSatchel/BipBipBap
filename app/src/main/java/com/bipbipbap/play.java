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

/**
 * Created by James Wu on 7/10/2016.
 */
public class play extends Activity{

    Button tapButton;
    Metronome myMetronome;
    int bpm = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        Log.v("AWWW YEEEEAAAAHH ", "POOP");

        Action act = new Action() {
            public void run(){
                System.out.println("IT'S THE SOCIAL-JUSTICE-MOBILE");
            }
        };
        myMetronome = new Metronome(act, 125);

        tapButton = (Button) findViewById(R.id.button);
        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getRootView().setBackgroundColor(Color.GREEN);
                myMetronome.stop();
            }
        });
    }



}
