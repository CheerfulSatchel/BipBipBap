package com.bipbipbap;

import android.app.Activity;
import android.graphics.Color;

import java.util.Timer;
import java.util.TimerTask;

import metronome.Action;
import metronome.Metronome;

/**
 * Created by Lancewiu on 8/26/2016.
 *
 * Manipulates all the UI.  Intended for use with play.java.
 */
public class GameUI {
    final static int successColor = Color.GREEN;
    final static int defaultColor = Color.WHITE;
    final static int failColor = Color.RED;
    final static int blinkColor = Color.YELLOW;

    //context for drawing values.
    private Activity context;
    private Metronome metronome;
    private defaultAction defAction;

    final private Timer colorTimer = new Timer();
    final private long colorDelay = 100; //Color will change back to default in 0.1 seconds

    private class defaultAction extends Action {
        public int color = blinkColor;
        public int delay = 0; // number of ticks to delay for.

        public void run() {
            if (color != blinkColor && delay > 1) {
                delay--;
                blink(blinkColor);
            } else {
                blink(color);
                if (color != blinkColor) {
                    color = blinkColor;
                }
            }
        }
    }

    public GameUI(Activity activity, Metronome metronome) {
        context = activity;
        this.metronome = metronome;
        defAction = new defaultAction();
        metronome.addAction(defAction);
    }

    private void setActivityBackgroundColor(int color) {
        context.runOnUiThread(()-> {
            context.getWindow().getDecorView().setBackgroundColor(color);
        });
    }

    private int getColor(boolean isSuccess) { return isSuccess ? successColor : failColor; }

    // arbitrarily blink regardless of time.
    public void blink(int color) {
        setActivityBackgroundColor(color);
        colorTimer.schedule(new TimerTask() {
            public void run() {
                setActivityBackgroundColor(defaultColor);
            }
        }, colorDelay);
    }

    public void blinkSuccess() { blink(getColor(true)); }

    public void blinkFail() { blink(getColor(false)); }

    // The next blink color with the metronome will be this color (only for one time)
    public void schedule(int color, int delay) {
        defAction.color = color;
        defAction.delay = delay;
    }

    public void scheduleSuccess(int delay) { schedule(getColor(true), delay); }

    public void scheduleFail(int delay) { schedule(getColor(false), delay); }

    public void resetSchedule() { defAction.color = blinkColor; }

    public void close() { colorTimer.cancel(); }
}
