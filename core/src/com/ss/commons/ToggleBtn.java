package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;

public class ToggleBtn {
  private TextureAtlas atlas;
  private Group group;
  private Image shapeOn, shapeOff;
  private String btn1,btn2;
  private boolean value;
  private int id;
  public ToggleBtn(TextureAtlas atlas, Group group, boolean value, int id, String btn1, String btn2){
    this.atlas = atlas;
    this.group = group;
    this.value = value;
    this.id = id;
    this.btn1=btn1;
    this.btn2=btn2;
    initUI();
    addEventClick();
  }

  private void initUI(){
    shapeOn = GUI.createImage(atlas, btn1);
    shapeOff = GUI.createImage(atlas, btn2);
    group.addActor(shapeOn);
    group.addActor(shapeOff);
    setValue(this.value);
  }

  private void setValue(boolean value){
    this.value = value;
    shapeOn.setVisible(this.value);
    shapeOff.setVisible(!this.value);
  }

  private void addEventClick(){
    shapeOn.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        setValue(false);
        if(id == 1){
          SoundEffect.music = false;
//          SoundEffect.pauseM();
        }
        else if(id == 2){
          SoundEffect.mute = true;
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });

    shapeOff.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Play(SoundEffect.click);
        setValue(true);

        if(id == 1){
          SoundEffect.music = true;
//          SoundEffect.unPause();
        }
        else if(id == 2){
          SoundEffect.mute = false;
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  public boolean getValue(){
    return value;
  }

  public void setPosition(float x, float y){
    shapeOff.setPosition(x, y);
    shapeOn.setPosition(x, y);
  }

  public void setActive(boolean isActive){
    shapeOn.setVisible(isActive);
    shapeOff.setVisible(!isActive);
  }

  public float getX(){
    return shapeOn.getX();
  }

  public float getY(){
    return shapeOn.getY();
  }

  public float getWidth(){
    return shapeOn.getWidth();
  }

  public float getHeight(){
    return shapeOn.getHeight();
  }
}
