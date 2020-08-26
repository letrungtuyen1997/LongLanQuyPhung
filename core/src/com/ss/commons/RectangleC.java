package com.ss.commons;

import com.badlogic.gdx.math.Rectangle;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GStage;


public class RectangleC extends Rectangle {
  private GShapeSprite gShape;
  private GLayerGroup group;
  public RectangleC(float x, float y, float width, float height){
    super(x, y, width, height);
    initGroup();
    initShape();
  }

  private void initGroup(){
    group = new GLayerGroup();
    GStage.addToLayer(GLayer.ui, group);
  }

  private void initShape(){
    gShape = new GShapeSprite();
    gShape.createRectangle(true, x, y, width, height);
    group.addActor(gShape);
    gShape.setColor(0, 0, 0, 0.5f);
  }

  public void showRec(boolean isShow){
    float value = isShow ? 0.5f : 0;
    gShape.setVisible(isShow);
  }

  @Override
  public Rectangle setPosition(float x, float y) {
    gShape.setPosition(x, y);
    return super.setPosition(x, y);
  }

  @Override
  public Rectangle setSize(float width, float height) {
    gShape.setSize(width, height);
    return super.setSize(width, height);
  }
}
