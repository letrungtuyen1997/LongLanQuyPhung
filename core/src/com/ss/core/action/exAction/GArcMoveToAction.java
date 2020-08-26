package com.ss.core.action.exAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class GArcMoveToAction extends TemporalAction {
   private float degree;
   private float endDegree;
   private float endX;
   private float endY;
   private float originX;
   private float originY;
   private float passX;
   private float passY;
   private float radius;
   private float startDegree;
   private float startX;
   private float startY;

   public static GArcMoveToAction arcMoveTo(float x, float y, float passx, float passy, float dur, Interpolation interpolar) {
      GArcMoveToAction var6 = (GArcMoveToAction)Actions.action(GArcMoveToAction.class);
      var6.setPosition(x, y);
      var6.setPass(passx, passy);
      var6.setDuration(dur);
      var6.setInterpolation(interpolar);
      return var6;
   }

   protected void begin() {
      this.startX = this.actor.getX();
      this.startY = this.actor.getY();
      this.calculateOrigin();
      this.calculateRadius();
      this.calculateDegree();
   }

   public void calculateDegree() {
      this.startDegree = (float)(Math.acos((double)((this.startX - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      if(this.startY > this.originY) {
         this.startDegree = 360.0F - this.startDegree;
      }

      this.endDegree = (float)(Math.acos((double)((this.endX - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      if(this.endY > this.originY) {
         this.endDegree = 360.0F - this.endDegree;
      }

      float var2 = (float)(Math.acos((double)((this.passX - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      float var1 = var2;
      if(this.passY > this.originY) {
         var1 = 360.0F - var2;
      }

      if(this.endDegree >= this.startDegree) {
         if(var1 >= this.startDegree && var1 <= this.endDegree) {
            this.degree = this.endDegree - this.startDegree;
         } else {
            this.degree = -(360.0F - this.endDegree + this.startDegree);
         }
      } else if(var1 >= this.endDegree && var1 <= this.startDegree) {
         this.degree = this.endDegree - this.startDegree;
      } else {
         this.degree = 360.0F - this.startDegree + this.endDegree;
      }



   }

   public void calculateOrigin() {
      float var1 = this.passX - this.startX;
      float var5 = this.endX - this.passX;
      float var2 = this.passX + this.startX;
      float var6 = this.endX;
      float var7 = this.passX;
      float var3 = this.passY - this.startY;
      float var8 = this.endY - this.passY;
      float var4 = this.passY + this.startY;
      this.originX = (((var6 + var7) * var5 + (this.endY + this.passY) * var8) * var3 - (var4 * var3 + var2 * var1) * var8) / ((var5 * var3 - var8 * var1) * 2.0F);
      var5 = -var1 * this.originX / var3;
      this.originY = (var1 * var2 + var4 * var3) / (2.0F * var3) + var5;
   }

   public void calculateRadius() {
      this.radius = (float)Math.sqrt((double)((this.originX - this.startX) * (this.originX - this.startX) + (this.originY - this.startY) * (this.originY - this.startY)));
   }

   public float getX() {
      return this.endX;
   }

   public float getY() {
      return this.endY;
   }

   public void setPass(float var1, float var2) {
      this.passX = var1;
      this.passY = var2;
   }

   public void setPosition(float var1, float var2) {
      this.endX = var1;
      this.endY = var2;
   }

   public void setPosition(float var1, float var2, int alignment) {
      this.endX = var1;
      this.endY = var2;
   }

   public void setX(float var1) {
      this.endX = var1;
   }

   public void setY(float var1) {
      this.endY = var1;
   }

   protected void update(float dt) {
      float cosdeg = MathUtils.cosDeg(this.startDegree + this.degree * dt);
      float sindeg = MathUtils.sinDeg(this.startDegree + this.degree * dt);
      float toX = this.originX + this.radius * cosdeg;
      float toY = this.originY - this.radius * sindeg;
      float rotate = MathUtils.atan2(toY - actor.getY(), toX - actor.getX()) * MathUtils.radiansToDegrees + 90;
      this.actor.setPosition(toX, toY);
      if(rotate == 90f)
         rotate = 180f;
      actor.setRotation(rotate);

   }
}
