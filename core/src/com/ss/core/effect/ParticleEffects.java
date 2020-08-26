package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.io.BufferedReader;

public class ParticleEffects extends Actor {

    private Group group;
    private ParticleEffectPool.PooledEffect poolEffect;
    private ParticleEffectPool pep;
    public ParticleEffect pe;

    public ParticleEffects(Group group, ParticleEffectPool pep, ParticleEffect pe) {
        this.group = group;
        this.pep = pep;
        this.pe = pe;
    }

    @Override
    public boolean remove() {
        if (poolEffect != null)
            poolEffect.free();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        pe.setPosition(getX(), getY());
        pe.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        pe.draw(batch);
    }

    public void Start(float x, float y) {
        pe.reset();
        poolEffect = pep.obtain();
        setX(x);
        setY(y);
        this.setZIndex(1000);
        group.addActor(this);
    }


    public void setScaleEffect(float scaleFactor) {
        pe.scaleEffect(scaleFactor);
    }
}
