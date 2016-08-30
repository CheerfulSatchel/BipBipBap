package com.bipbipbap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import metronome.Action;
import metronome.Metronome;

/**
 * Created by James Wu on 7/29/2016.
 * Edited by Lancewiu on 8/30/2016.
 *
 * Schedules all endable action objects given a metronome.
 */
public class Scheduler {

    private class FillerAction extends EndableAction {
        public int delay = 0;

        public FillerAction(Scheduler s) {
            super(s);
        }

        public void onStart() {
            delay = 0;
        }

        @Override
        public void run() {
            if (delay > defDelay) {
                nextAction();
            }
            delay++;
            System.out.println("Filler Countdown " + delay);
        }
    }

    private final int defDelay = 3; //4 tick delay between actions as default
    private FillerAction filler;
    private ArrayList<EndableAction> commands;
    private Metronome metronome;
    private Random rand = new Random(System.currentTimeMillis());

    private EndableAction pickNext() {
        return commands.get(rand.nextInt(commands.size()));
    }

    public Scheduler(Metronome metronome) {
        this.metronome = metronome;
        commands = new ArrayList<>();
        filler = new FillerAction(Scheduler.this);
    }

    public Scheduler(Metronome metronome, Collection<? extends EndableAction> c) {
        this(metronome);
        commands.addAll(c);
    }

    public Scheduler(Metronome metronome, EndableAction[] c) {
        this(metronome, Arrays.asList(c));
    }

    public void addEndableAction(EndableAction a) {
        commands.add(a);
    }

    public void begin() {
        rand = new Random(System.currentTimeMillis());
        metronome.addAction(filler);
    }

    private void nextAction() {
        metronome.cancelAction(filler);
        metronome.addAction(pickNext());
    }

    public void nextAction(Action previous) {
        System.out.println("Next Action Called");
        metronome.cancelAction(previous);
        metronome.addAction(filler);
    }
}
