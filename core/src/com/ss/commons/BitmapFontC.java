package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ss.core.util.GAssetsManager;

public class BitmapFontC {
  public static BitmapFont font_white;
  public static BitmapFont font_brown;
  public static BitmapFont FontAlert;
  public static BitmapFont Font_brown_thin;
  public static BitmapFont Font_Orange;

  public static void initBitmapFont(){
    font_white      = GAssetsManager.getBitmapFont("font_White.fnt");
    FontAlert       = GAssetsManager.getBitmapFont("alert_font.fnt");
    font_brown      = GAssetsManager.getBitmapFont("font_brown.fnt");
    Font_brown_thin = GAssetsManager.getBitmapFont("font_brown_thin.fnt");
    Font_Orange     = GAssetsManager.getBitmapFont("font_orange.fnt");
  }
}
