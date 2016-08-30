package com.bipbipbap;

/**
 * Created by Lancewiu on 8/26/2016.
 * This is where the settings live.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends Activity {
    public static int DIFFICULTY = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        findViewById(R.id.button);
        SeekBar difficultySlider = (SeekBar) findViewById(R.id.difficultySlider);
        TextView difficultyNum = (TextView) findViewById(R.id.difficultyValue);

        difficultyNum.setText(Integer.toString(DIFFICULTY));

        difficultySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficultyNum.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                DIFFICULTY = seekBar.getProgress();
                difficultyNum.setText(Integer.toString(DIFFICULTY));
            }
        });
    }
}
