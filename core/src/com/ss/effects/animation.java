package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ss.assetManager.XAssetsManager;
import com.ss.commons.Tweens;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GStage;

public class animation extends Actor {
  Batch batch;
  public Animation<TextureRegion> tg;
  float stateTime = 0;
  float x, y;
  GLayerGroup group = new GLayerGroup();

  boolean loop=false;
  public animation(float x, float y, String key, int col , int row, float duration, boolean isFlipX, boolean loop){
    GStage.addToLayer(GLayer.top,group);
    tg = XAssetsManager.getAnimation(key,col,row,duration);
    batch = new SpriteBatch();
    for (int i=0;i<tg.getKeyFrames().length;i++){
      tg.getKeyFrames()[i].flip(isFlipX,true);
    }
    this.x = x-tg.getKeyFrames()[0].getRegionWidth()/2;
    this.y = y-tg.getKeyFrames()[0].getRegionHeight()/2;
    if(loop==true){
      setLoop(duration*row*col);
    }
  }
  @Override
  public void draw(Batch batch, float parentAlpha) {
    TextureRegion t = tg.getKeyFrame(stateTime);
    stateTime += Gdx.graphics.getDeltaTime();
    if((Math.floor(stateTime)<= tg.getFrameDuration()*tg.getKeyFrames().length)){
      batch.draw(t,x,y);
      return;
    }
    if(group.isPause()){
      TextureRegion t1 = tg.getKeyFrame(1);
      batch.draw(t1,x,y);
    }else {
      stateTime=0;
    }
  }
  void setLoop(float duration){
    start();
    Tweens.setTimeout(group,duration,()->{
      setLoop(duration);
    });

  }
  public void start(){
    stateTime=0;
  }
  public void stop(){
    stateTime =tg.getKeyFrames().length+1;
  }
  public void setPause(Boolean pause){
    group.setPause(pause);
  }
}