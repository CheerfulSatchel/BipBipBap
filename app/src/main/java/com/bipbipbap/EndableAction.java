package com.bipbipbap;

import metronome.Action;

/**
 * Created by Lancewiu on 8/30/2016.
 * A callback addition to Action for Scheduler
 */
public abstract class EndableAction extends Action{
    private Scheduler callback;

    public EndableAction(Scheduler s){
        super();
        callback = s;
    }

    public void completeAction() {
        callback.nextAction(this);
    }
}