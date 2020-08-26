package com.ss.core.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GClipGroup extends Group {
   float clipH;
   float clipW;
   float clipX;
   float clipY;
   boolean isClipping;

   public void draw(Batch batch, float parentAlpha) {
      if(!this.isClipping || this.clipW >= 1.0F && this.clipH >= 1.0F) {
         batch.end();
         batch.begin();
         boolean var3;
         if(this.isClipping() && this.clipBegin(this.clipX + this.getX(), this.clipY + this.getY(), this.clipW, this.clipH)) {
            var3 = true;
         } else {
            var3 = false;
         }

         super.draw(batch, parentAlpha);
         if(var3) {
            batch.end();
            this.clipEnd();
            batch.begin();
            return;
         }
      }

   }

   public boolean isClipping() {
      return this.isClipping;
   }

   public void resetClipArea() {
      this.isClipping = false;
   }

   public void setClipArea(float clipX, float clipY, float clipW, float clipH) {
      this.clipX = clipX;
      this.clipY = clipY;
      this.clipW = clipW;
      this.clipH = clipH;
      this.isClipping = true;
   }
}
