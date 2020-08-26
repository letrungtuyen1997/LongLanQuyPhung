package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

public class Anim extends Actor {

    private Group group;
    private Animation<TextureRegion> anim;
    float elapsedTime;

    public Anim(Group group, String ani, float x, float y) {
        this.anim = new Animation<>(1f/10f, AnimationEffect.anims.get(ani));
        setX(x);
        setY(y);
        group.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        setOrigin(Align.center);
        batch.draw((TextureRegion) anim.getKeyFrame(elapsedTime, true), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }
}
