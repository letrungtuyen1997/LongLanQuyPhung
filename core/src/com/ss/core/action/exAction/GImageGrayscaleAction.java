package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ss.core.exSprite.GImageGrayscalable;


public class GImageGrayscaleAction extends TemporalAction {
    private float from;
    private float to;
    private float dg;
    private GImageGrayscalable me;

    static public GImageGrayscaleAction grayscale (float duration, float from, float to) {
        GImageGrayscaleAction action = Actions.action(GImageGrayscaleAction.class);
        action.setDuration(duration);
        action.from = from;
        action.to = to;
        return action;
    }

    protected void begin () {
        me = null;
        if(this.getActor() instanceof  GImageGrayscalable){
            me = (GImageGrayscalable)this.getActor();
            dg = to - from;
        }
    }

    protected void update (float percent) {
        if(me!=null){
            me.setGrayness(from+(percent*dg));
        }
    }
}
