package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GSimpleAction extends Action {
   private ActInterface actInterface;

   public static GSimpleAction simpleAction(ActInterface var0) {
//      GSimpleAction var1 = (GSimpleAction)Actions.action(GSimpleAction.class);
      GSimpleAction var1 = new GSimpleAction();
      var1.actInterface = var0;
      return var1;
   }

   public boolean act(float var1) {
      return this.actInterface.act(var1, this.actor);
   }

   @FunctionalInterface
   public interface ActInterface {
      boolean act(float dt, Actor actor);
   }
}

