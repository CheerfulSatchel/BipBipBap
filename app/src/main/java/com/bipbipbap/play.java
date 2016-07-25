package com.bipbipbap;

import metronome.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by James Wu on 7/10/2016.
 */
public class play extends Activity{

    //Components: Tap button, trace line, Blow into phone,
    Button tapButton;

    Metronome myMetronome;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("AWWW YEEEEAAAAHH ", "POOP");
        myMetronome = new Metronome();
        //myMetronome.start(155);

    }




}
