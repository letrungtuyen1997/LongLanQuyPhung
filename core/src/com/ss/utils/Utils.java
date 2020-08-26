package com.ss.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.platform.IPlatform;
import com.ss.GMain;

import java.util.Date;

public class Utils {
    public static String result ="";
    private Utils(){

    }
    public static JsonValue GetJsV(String s){
        JsonReader JReader= new JsonReader();
        return JReader.parse(s);
    }

    public static String ConvertDateTime(String s){
//        System.out.println("milisecond: "+s);
//        String result="";
        if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL) {
            result = GMain.FormatDate(s);
        }else {
            Date date = new Date(Long.parseLong(s));
            result = date.toString();

        }
        return result;
    }
}
