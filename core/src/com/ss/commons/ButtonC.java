package com.ss.commons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GUI;

public class ButtonC {
  private TextureAtlas atlas;
  private Group group, parentGroup;
  private String nameImage;
  private Image image;
  private String text;
  private BitmapFont bitmapFont;
  private LabelC label;
  private GShapeSprite gshape;
  private Runnable runnableTU, runnableTD;

  public ButtonC(TextureAtlas atlas, Group parentGroup, String nameImage){
    this.atlas = atlas;
    this.parentGroup = parentGroup;
    this.nameImage = nameImage;
    text = "";

    initGroup();
    initUI();
  }

  private void initGroup(){
    group = new Group();
    parentGroup.addActor(group);
  }

  private void initUI(){
    image = GUI.createImage(atlas, nameImage);
    group.addActor(image);
    group.setSize(image.getWidth(), image.getHeight());
  }

  private void converEventTouchDown(){
    if(runnableTD != null){
      gshape.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnableTD));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
    else{
      System.out.println("Runnable touchDown button is null!!");
    }
  }

  private void convertEventTouchUp(){
    if(runnableTU != null){
      gshape.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnableTU));
        }
      });
    }
    else{
      System.out.println("Runnable touchUp button is null!!");
    }
  }

  public void addBitmapFont(BitmapFont font){
    bitmapFont = font;
  }

  public void addText(String text){
    this.text = text;
    if(label != null){
      label.setText(this.text);
    }
    else{
      if(bitmapFont != null){
        label = new LabelC(this.text, new Label.LabelStyle(bitmapFont, null));
        gshape = new GShapeSprite();
        gshape.createRectangle(true, 0, 0, group.getWidth(), group.getHeight());
        gshape.setColor(0, 0, 0, 0.3f);
        group.addActor(label);
        group.addActor(gshape);

        converEventTouchDown();
        convertEventTouchUp();
      }
      else {
        System.out.println("bitmapFont of Button is null!!");
      }
    }
  }

  public void setPosition(float x, float y){
    group.setPosition(x, y);
  }

  public void addTouchDown(Runnable runnable){
    runnableTD = runnable;
    if(gshape != null){
      gshape.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnable));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
    else {
      image.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          group.addAction(Actions.run(runnable));
          return super.touchDown(event, x, y, pointer, button);
        }
      });
    }
  }

  public void addTouchUp(Runnable runnable){
    runnableTU = runnable;
    if(gshape != null){
      gshape.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnable));
        }
      });
    }
    else {
      image.addListener(new ClickListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
          super.touchUp(event, x, y, pointer, button);
          group.addAction(Actions.run(runnable));
        }
      });
    }
  }

  public void scaleZzAction(boolean isToSmall, float rx, float ry, float dur1, float dur2, Interpolation in1, Interpolation in2, Runnable runnable){
    int ope = isToSmall ? -1 : 1;
    group.setOrigin(Align.center);
    group.addAction(Actions.sequence(
      Actions.scaleBy(ope*rx, ope*ry, dur1, in1),
      Actions.scaleBy(-ope*rx, -ope*ry, dur2, in2),
      Actions.run(runnable)
    ));
  }

  public void setColor(Color color){
    image.setColor(color);
  }

  public void setColor(float r, float g, float b, float a){
    image.setColor(r, g, b, a);
  }
}
