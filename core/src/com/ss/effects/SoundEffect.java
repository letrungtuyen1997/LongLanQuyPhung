package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GSound;
import com.ss.gdx.NSound;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
  public static int MAX_COMMON = 38;
  public static Music bgSound = null;
  public static Music bgSound2 = null;
  public static Music bgSound3 = null;
  public static Sound[] commons = null;
//  public static GSound[] commons = null;
  public static boolean music = false;
  public static boolean mute = false;

  public static int bg            = 1;
  public static int click         = 2;
  public static int unlock        = 3;
  public static int wheel_sound   = 4;
  public static int panel_close   = 5;
  public static int panel_open    = 6;
  public static int unlock2       = 7;
  public static int merge         = 8;

  private static Sound[] explode;


  public static void initSound() {

    commons = new Sound[MAX_COMMON];
    commons[click] = GAssetsManager.getSound("click.mp3");
    commons[unlock] = GAssetsManager.getSound("unlock.mp3");
    commons[wheel_sound] = GAssetsManager.getSound("wheel_sound.mp3");
    commons[panel_open] = GAssetsManager.getSound("panel_open.mp3");
    commons[panel_close] = GAssetsManager.getSound("panel_close.mp3");
    commons[unlock2] = GAssetsManager.getSound("unlock2.mp3");
    commons[merge] = GAssetsManager.getSound("merge.mp3");
////        commons[coins] = GAssetsManager.getSound("Coin.mp3");
////        commons[coins].setVolume(2,5);
    bgSound = GAssetsManager.getMusic("bg.mp3");

  }
  public void test(){
    GSound.playSound(commons[click].toString());

  }

  public static long Play(int i) {
    long id = -1;
    if (!mute) {
      id = commons[i].play();
      commons[i].setVolume(id,0.5f);
    }
    return id;
  }
  public static void explode(int level) {
    if(!mute)
      explode[(level > 13) ? 13 : level].play();
  }


  public static void Playmusic(int mode) {
    music = false;
    switch (mode) {
      case 1: {
        GSound.initMusic("bg.mp3");
        GSound.playMusic();
//        GSound.playLoopSound("bg.mp3");
        break;
      }
      case 2: {
        GSound.playSound("click.mp3");
        break;
      }
      case 3: {
        GSound.playSound("unlock2.mp3");
        break;
      }
      case 4: {
        GSound.playSound("wheel_sound.mp3");
        break;
      }
      case 5: {
        GSound.playSound("panel_close.mp3");
        break;
      }
      case 6: {
        GSound.playSound("panel_open.mp3");
        break;
      }
      case 7: {
        GSound.playSound("unlock.mp3");
        break;
      }
      case 8: {
        GSound.playSound("merge.mp3");
        break;
      }
      default:{
//        bgSound.play();
//        bgSound.setLooping(true);
//        bgSound.setVolume(0.2f);
        break;
      }
    }
  }

  public static void Stopmusic(int mode) {
    music = true;
    switch (mode){
      case 1: {
        bgSound.pause();
        break;
      }
      case 2: {
        bgSound2.stop();
        break;
      }
      case 3: {
        bgSound3.pause();
        break;
      }
      default:{
        bgSound.stop();
        break;
      }
    }
  }
}
