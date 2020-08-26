package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CurvesSprite extends Actor {
  private float theta;
  private float radius;
  private Color color;
  private float curvesWeight;
  private Vector2 centerPoint;
  public  CurvesSprite(float theta, float radius, Vector2 centerPoint, float curvesWeight, Color color) {
    this.theta = theta;
    this.radius = radius;
    this.curvesWeight = curvesWeight;
    this.centerPoint = centerPoint;
    this.color = color;
  }
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    CurvesTool.curves(batch, color, centerPoint, radius, theta, curvesWeight);
  }

  public float getTheta() {
    return theta;
  }

  public void setTheta(float theta) {
    this.theta = theta;
  }

  public float getRadius() {
    return radius;
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  public float getCurvesWeight() {
    return curvesWeight;
  }

  public void setCurvesWeight(float curvesWeight) {
    this.curvesWeight = curvesWeight;
  }

  public Vector2 getCenterPoint() {
    return centerPoint;
  }

  public void setCenterPoint(Vector2 centerPoint) {
    this.centerPoint = centerPoint;
  }
}



class CurvesTool {
  private static ShapeRenderer sr = new ShapeRenderer();
  private static final Vector2[][] cp;
  private static Bezier[] paths;
  private static float[] thetas = new float[4];

  static {
    float c = 0.55191502449f;
    cp = new Vector2[4][4];
    cp[0][0] = new Vector2(0,1);
    cp[0][1] = new Vector2(c,1);
    cp[0][2] = new Vector2(1,c);
    cp[0][3] = new Vector2(1,0);

    cp[1][0] = new Vector2(1,0);
    cp[1][1] = new Vector2(1,-c);
    cp[1][2] = new Vector2(c,-1);
    cp[1][3] = new Vector2(0,-1);

    cp[2][0] = new Vector2(0,-1);
    cp[2][1] = new Vector2(-c,-1);
    cp[2][2] = new Vector2(-1,-c);
    cp[2][3] = new Vector2(-1,0);

    cp[3][0] = new Vector2(-1,0);
    cp[3][1] = new Vector2(-1,c);
    cp[3][2] = new Vector2(-c,1);
    cp[3][3] = new Vector2(0,1);

    paths = new Bezier[4];
    paths[0] = new Bezier<>(cp[0]);
    paths[1] = new Bezier<>(cp[1]);
    paths[2] = new Bezier<>(cp[2]);
    paths[3] = new Bezier<>(cp[3]);

    sr.setAutoShapeType(true);
  }

  private static void begin(Batch batch, Color color, float curvesWeight) {
    batch.end();
    //Gdx.gl20.glEnable(3042);
    //Gdx.gl20.glBlendFunc(770, 771);
    sr.setProjectionMatrix(batch.getProjectionMatrix());
    sr.setTransformMatrix(batch.getTransformMatrix());
    Gdx.gl20.glLineWidth(curvesWeight);
    sr.setColor(color);
    sr.begin();
  }

  private static void end(Batch batch) {
    sr.end();
    batch.begin();
  }

  private static void initTheta(float theta) {
    thetas[0] = 0;
    thetas[1] = 0;
    thetas[2] = 0;
    thetas[3] = 0;
    theta -= 0.000001;
    theta %= MathUtils.PI*2;
    int sectors = MathUtils.floor(theta/(MathUtils.PI/2));
    for (int i = 0; i < sectors; i++)
      thetas[i] = MathUtils.PI/2;
    thetas[sectors] = theta - sectors*(MathUtils.PI/2);
  }

  static void curves(Batch batch, Color color, Vector2 cp, float r, float theta, float curvesWeight) {
    begin(batch, color, curvesWeight);
    if (theta <= 0.0) {
      end(batch);
      return;
    }
    initTheta(theta);
    for (int j = 0; j < 4; j++) {
      if (thetas[j] == 0)
        continue;
      float s = thetas[j]*200/MathUtils.PI;
      for(int i = 0; i < s; ++i){
        float t = i /100f; // 70 <=  this <= 100
        Vector2 st = new Vector2();
        Vector2 end = new Vector2();
        paths[j].valueAt(st,t);
        paths[j].valueAt(end, t-0.0199f);//0.01, 0.011, 0.0012
        st.scl(r);end.scl(r);
        sr.line(st.x + cp.x, st.y + cp.y, end.x + cp.x, end.y + cp.y);
      }
    }
    end(batch);
  }
}

/*
    Image c = GUI.createImage(menuAtlas, "circle");
        c.setAlign(Align.center);
        c.setPosition(100,100);
        menuGroup.addActor(c);

        CurvesSprite cs = new CurvesSprite(0, 0, null, 0, null);
        cs.setRadius(47.5f);
        cs.setTheta((2*MathUtils.PI)*(60 - 1)/60 //0);
        cs.setCenterPoint(new Vector2(c.getX() + c.getWidth()/2 - 2,c.getY() + c.getHeight()/2 + 2));
        cs.setCurvesWeight(9.52f);
        cs.setColor(Color.GRAY);
        menuGroup.addActor(cs);
        cs.addAction(GSimpleAction.simpleAction((d, a) -> {
        CurvesSprite s = (CurvesSprite)a;
        s.setTheta(s.getTheta() + 0.03f);
        return false;
        }));
 */
