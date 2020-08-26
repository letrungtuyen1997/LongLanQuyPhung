package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.exSprite.GImageGrayscalable;

import java.awt.Font;

public class GUI {
   public static Button createTextButtonEx(TextureRegion texture, BitmapFont font, String text, int textOffsetX, int  textOffsetY){
      return GUI.createTextButtonWithShadowEx(texture, font, text, Color.WHITE, Color.BROWN, 1f, textOffsetX, textOffsetY);

   }

   public static Button createTextButton(TextureRegion texture, BitmapFont font, String text){
      return GUI.createTextButtonWithShadow(texture, font, text, Color.WHITE, Color.BROWN, 1f);
   }

   public static Button createTextButtonWithShadowEx(TextureRegion texture, BitmapFont font, String text, Color fontColor, Color shadowColor, float fontScale, int textOffsetX, int  textOffsetY){
      Button btn = creatButton(texture);

      Label shawdowlabel = new Label(text, new Label.LabelStyle(font, shadowColor));
      shawdowlabel.setFontScale(fontScale);
      btn.addActor(shawdowlabel);

      Label label = new Label(text, new Label.LabelStyle(font, fontColor));
      label.setFontScale(fontScale);
      btn.addActor(label);



      shawdowlabel.setPosition(btn.getWidth()/2 + textOffsetX,btn.getHeight()/2 + 3+ textOffsetY, Align.center);
      label.setPosition(btn.getWidth()/2+ textOffsetX,btn.getHeight()/2 + textOffsetY, Align.center);

      return btn;
   }

   public static Button createTextButtonWithShadow(TextureRegion texture, BitmapFont font, String text, Color fontColor, Color shadowColor, float fontScale){
      Button btn = creatButton(texture);

      Label shawdowlabel = new Label(text, new Label.LabelStyle(font, shadowColor));
      shawdowlabel.setFontScale(fontScale);
      btn.addActor(shawdowlabel);

      Label label = new Label(text, new Label.LabelStyle(font, fontColor));
      label.setFontScale(fontScale);
      btn.addActor(label);



      shawdowlabel.setPosition(btn.getWidth()/2,btn.getHeight()/2 + 3, Align.center);
      label.setPosition(btn.getWidth()/2,btn.getHeight()/2 , Align.center);

      return btn;
   }

   public static Button creatButton(TextureRegion... var0) {
      TextureRegionDrawable[] var2 = new TextureRegionDrawable[4];
      if(var0 != null) {
         for(int var1 = 0; var1 < Math.min(4, var0.length); ++var1) {
            var2[var1] = new TextureRegionDrawable(var0[var1]);
            if(var1 == 0) {
               TextureRegionDrawable var3 = var2[0];
               var2[3] = var3;
               var2[2] = var3;
               var2[1] = var3;
            }
         }
      }

      Button.ButtonStyle var4 = new Button.ButtonStyle();
      var4.up = var2[0];
      var4.down = var2[1];
      var4.checked = var2[2];
      var4.disabled = var2[3];
      return new Button(var4);
   }

   public static Button createButton(TextureAtlas atlas, String regionname) {
      return creatButton( atlas.findRegion(regionname));
   }

   public static Label createLabel(CharSequence var0, BitmapFont font, Color var1) {
      return new Label(var0, new Label.LabelStyle(font, var1));
   }

   public static Label createLabel(CharSequence var0, Color var1) {
      return new Label(var0, new Label.LabelStyle(new BitmapFont(true), var1));
   }

   public static TextureAtlas.AtlasRegion FindRegionLocalize(TextureAtlas atlas, String regionName){
      String lang = GMain.platform.GetDefaultLanguage();
      String localRegionName = regionName;
      if(!lang.equals("en")){
         localRegionName=regionName+"_"+lang;
      }

      TextureAtlas.AtlasRegion region = atlas.findRegion(localRegionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region localize " + regionName + " not found");
         region = atlas.findRegion(regionName);
         return region;
      }

      return region;
   }
   public static Button createButtonLocalize(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = FindRegionLocalize(atlas, regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return GUI.creatButton(new TextureRegion[]{region});
   }
   public static Image createImageLocalize(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = FindRegionLocalize(atlas, regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return new Image(region);

   }
   public static Image createImage(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return new Image(region);
   }

   public static GImageGrayscalable createImageGrayscalable(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return new GImageGrayscalable(region);
   }
}
