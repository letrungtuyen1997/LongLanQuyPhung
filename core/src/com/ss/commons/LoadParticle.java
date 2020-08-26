package com.ss.commons;

import com.ss.core.exSprite.particle.GParticleSystem;

public class LoadParticle {

  public static void init(){
    new GParticleSystem("tree","effect.atlas",1,1);
    new GParticleSystem("light","effect.atlas",1,1);
    new GParticleSystem("lightYellow","effect.atlas",1,1);
    new GParticleSystem("merge","effect.atlas",1,1);
    new GParticleSystem("effBilli","effect.atlas",1,1);
    new GParticleSystem("Titlecongra","effect.atlas",1,1);
    new GParticleSystem("Titlemerge","effect.atlas",1,1);
  }
}


