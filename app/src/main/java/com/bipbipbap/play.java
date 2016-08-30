package com.bipbipbap;

import metronome.Metronome;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by James Wu on 7/10/2016.
 */
public class play extends Activity{

    private int bpm = 45;
    private final Metronome metronome = new Metronome(bpm);
    private final GameUI ui = new GameUI(play.this, metronome);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        //SIMULATION VALUES
        Scheduler scheduler = new Scheduler(metronome);
        long score = 0;

        //UI VALUES
        Button tapButton = (Button) findViewById(R.id.button);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        TextView seekBarStatus = (TextView) findViewById(R.id.seekBarStatus);
        TextView difficulty = (TextView) findViewById(R.id.playDifficultyNumber);
        TextView scoreLabel = (TextView) findViewById(R.id.playScoreNumber);


        seekBarStatus.setText("Level: " + seekBar.getProgress() + "/" + seekBar.getMax());
        difficulty.setText(Integer.toString(Settings.DIFFICULTY));
        scoreLabel.setText(Long.toString(score)); //placeholder, doesn't do anything atm.

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int level = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                level = progress;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Start tracking seekbar progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarStatus.setText("Level: " + level + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stop tracking seekbar progress", Toast.LENGTH_SHORT).show();
            }
        });


        //This is the everchanging action star, hank
        EndableAction hank = new EndableAction(scheduler) {
            private int ticks;

            private void reset() {
                ticks = 0;
            }

            public void onStart() {
                reset();
                tapButton.setOnClickListener((View v) -> {
                    ui.blinkFail();
                    completeAction();
                });
            }

            public void run() {
                switch (++ticks) {
                    case 1:
                        tapButton.setOnClickListener((View v) -> {
                            ui.blinkSuccess();
                            ui.resetSchedule();
                            completeAction();
                        });
                        System.out.println("Tap the Whew Wee Button.");
                        ui.scheduleFail(3); // fail on 4th tick
                        break;
                    case 4:
                        tapButton.setOnClickListener((View v) -> {
                            ui.blinkFail();
                        });
                        System.out.println("FAILED");
                        completeAction();
                        break;
                }
                System.out.println("Yee! " + ticks);
            }
        };

        tapButton.setOnClickListener((View v) -> {
            ui.blinkFail();
        });

        scheduler.addEndableAction(hank);
        scheduler.begin();
    }

    public void onBackPressed() {
        ui.close();
        metronome.stop();
        finish();
        super.onBackPressed();
    }
}
