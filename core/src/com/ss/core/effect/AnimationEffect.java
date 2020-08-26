package com.ss.core.effect;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ss.GMain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimationEffect {

//    private static TextureAtlas textureAtlas = GMain.textureAtlas;
    public static HashMap<String, TextureRegion[]> anims = new HashMap<>();
    public static TextureRegion[] listFrameGirlAnimation;

    public static void LoadAnimation() {
//        listFrameGirlAnimation = new TextureRegion[21];
//        for (int i = 0; i < 21; i++)
//            listFrameGirlAnimation[i] = textureAtlas.findRegion("girl", i);
//
//        anims.put("girl_anim", listFrameGirlAnimation);
    }
}
