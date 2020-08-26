package com.ss.core.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class SoundEffects {

    public static HashMap<String, Music> s;
//    private static FileHandle background = Gdx.files.internal("sound/background.mp3");
//    private static FileHandle out_card = Gdx.files.internal("sound/out_card.mp3");
//    private static FileHandle get_card = Gdx.files.internal("sound/get_card.mp3");
//    private static FileHandle funny_bomb = Gdx.files.internal("sound/funny_bomb.mp3");
//    private static FileHandle funny_egg = Gdx.files.internal("sound/funny_egg.mp3");
//    private static FileHandle funny_knife = Gdx.files.internal("sound/funny_knife.mp3");
//    private static FileHandle funny_tomato = Gdx.files.internal("sound/funny_tomato.mp3");
//    private static FileHandle click_button = Gdx.files.internal("sound/click_button.mp3");
//    private static FileHandle u_bai = Gdx.files.internal("sound/u_bai.mp3");
//    private static FileHandle laser_off = Gdx.files.internal("sound/laseroff.mp3");
//    private static FileHandle divide_card = Gdx.files.internal("sound/divide_card.mp3");
//    private static FileHandle sort_card = Gdx.files.internal("sound/sap_bai.mp3");
//    private static FileHandle ha_phom = Gdx.files.internal("sound/ha_phom.mp3");
//    private static FileHandle win = Gdx.files.internal("sound/win.mp3");
//    private static FileHandle lose = Gdx.files.internal("sound/lose.mp3");
//    private static FileHandle door_bell = Gdx.files.internal("sound/doorbell.mp3");
//    private static FileHandle tick_chip = Gdx.files.internal("sound/tick_chip.mp3");
//    private static FileHandle eat_card = Gdx.files.internal("sound/eat_card.mp3");
//    private static FileHandle ohh = Gdx.files.internal("sound/ohh.mp3");

    public static boolean isMute = false;

    public static void initSound() {
        s = new HashMap<>();
//        s.put("sound_background", Gdx.audio.newMusic(background));
//        s.put("get_card", Gdx.audio.newMusic(get_card));
//        s.put("out_card", Gdx.audio.newMusic(out_card));
//        s.put("funny_bomb", Gdx.audio.newMusic(funny_bomb));
//        s.put("funny_egg", Gdx.audio.newMusic(funny_egg));
//        s.put("funny_knife", Gdx.audio.newMusic(funny_knife));
//        s.put("funny_tomato", Gdx.audio.newMusic(funny_tomato));
//        s.put("click_button", Gdx.audio.newMusic(click_button));
//        s.put("u_bai", Gdx.audio.newMusic(u_bai));
//        s.put("laser_off", Gdx.audio.newMusic(laser_off));
//        s.put("divide_card", Gdx.audio.newMusic(divide_card));
//        s.put("sort_card", Gdx.audio.newMusic(sort_card));
//        s.put("ha_phom", Gdx.audio.newMusic(ha_phom));
//        s.put("win", Gdx.audio.newMusic(win));
//        s.put("lose", Gdx.audio.newMusic(lose));
//        s.put("door_bell", Gdx.audio.newMusic(door_bell));
//        s.put("tick_chip", Gdx.audio.newMusic(tick_chip));
//        s.put("eat_card", Gdx.audio.newMusic(eat_card));
//        s.put("ohh", Gdx.audio.newMusic(ohh));
    }
}
