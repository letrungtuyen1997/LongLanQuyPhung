package com.ss.core.action.exAction;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class GFontScaleToAction extends TemporalAction {
    private float startX, startY;
    private float endX, endY;

    static public GFontScaleToAction fontScaleTo (float x, float y, float duration, Interpolation interpolation) {
        GFontScaleToAction action = Actions.action(GFontScaleToAction.class);
        action.setScale(x, y);
        action.setDuration(duration);
        action.setInterpolation(interpolation);
        return action;
    }

    protected void begin () {
        startX = ((Label)target).getFontScaleX();
        startY = ((Label)target).getFontScaleY();
    }

    protected void update (float percent) {

        ((Label)target).setFontScale(startX + (endX - startX) * percent, startY + (endY - startY) * percent);
    }

    public void setScale (float x, float y) {
        endX = x;
        endY = y;
    }

    public void setScale (float scale) {
        endX = scale;
        endY = scale;
    }

    public float getX () {
        return endX;
    }

    public void setX (float x) {
        this.endX = x;
    }

    public float getY () {
        return endY;
    }

    public void setY (float y) {
        this.endY = y;
    }
}
