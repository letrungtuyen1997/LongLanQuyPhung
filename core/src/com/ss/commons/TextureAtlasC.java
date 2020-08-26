package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas Wheel;
  public static TextureAtlas WhellAtlas;
  public static TextureAtlas UiAtlas;
  public static TextureAtlas InventoryAtlas;
  public static TextureAtlas effectAtlas;

  public static void initAtlas(){
    Wheel           = GAssetsManager.getTextureAtlas("whell.atlas");
    WhellAtlas      = GAssetsManager.getTextureAtlas("wheel.atlas");
    UiAtlas         = GAssetsManager.getTextureAtlas("ui.atlas");
    InventoryAtlas  = GAssetsManager.getTextureAtlas("inventory.atlas");
    effectAtlas     = GAssetsManager.getTextureAtlas("effect.atlas");
  }
}
