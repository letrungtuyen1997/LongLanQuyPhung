package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.ss.core.util.GStage;
public class Config {
    public static float ScreenW = GStage.getWorldWidth();
    public static float ScreenH = GStage.getWorldHeight();
    public static boolean       checkConnet         = true;
    public static boolean       checkWheel          = false;
    public static float         paddingY            =100;
    public static String        megaID              =Gdx.files.internal("uri/mega_id.txt").readString();
    public static String        token               ="";
    public static int           veloccity           =3;
    public static String        uri                 = Gdx.files.internal("uri/uri.txt").readString();
    public static boolean       remoteEffect        = Boolean.parseBoolean(Gdx.files.internal("uri/effect.txt").readString());
    public static int           condi_merge         = Integer.parseInt(Gdx.files.internal("uri/condi_merge.txt").readString());
    public static String        Question            ="";
    public static boolean       isShow              =true;
}
