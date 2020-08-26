package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.scenes.GameScene;

public class Tutorial {
  private Group        group                = new Group();
  private Group        groupScroll          = new Group();
  private Image        bg,frm,btnBack;
  private GameScene    gameScene;
  private Table        table, tableScroll;

  public Tutorial(GameScene gameScene){
        SoundEffect.Playmusic(SoundEffect.panel_open);
        this.gameScene = gameScene;
        GStage.addToLayer(GLayer.top,group);
        group.setPosition(GStage.getWorldWidth(),0);
        group.addAction(Actions.moveTo(0,0,0.5f, Interpolation.swingOut));
        loadBg();
        back();


    }
    private void loadBg(){
        bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
        frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
        group.addActor(frm);
        Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmTutorial");
        Tile.setPosition(frm.getX()+frm.getWidth()/2,frm.getY(),Align.center);
        group.addActor(Tile);
        renderListView();

    }
    private void back(){
        /////// button Back //////
        btnBack = GUI.createImage(TextureAtlasC.UiAtlas,"btnBack");
        btnBack.setOrigin(Align.center);
        btnBack.setPosition(frm.getX()+frm.getWidth()-btnBack.getWidth()*0.7f,frm.getY()+btnBack.getHeight()*0.7f,Align.center);
        group.addActor(btnBack);
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Playmusic(SoundEffect.panel_close);
                group.addAction(Actions.sequence(
                        Actions.moveTo(-GStage.getWorldWidth(),0,0.5f, Interpolation.swingIn),
                        GSimpleAction.simpleAction((d, a)->{
                            gameScene.setTouch(Touchable.enabled);
                            group.clear();
                            group.remove();
                            return true;
                        })
                ));
            }
        });
    }
  private void renderListView(){
    groupScroll.setWidth(frm.getWidth());
    groupScroll.setHeight(frm.getHeight()*0.85f);
    groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.53f,Align.center);
    ///////// scroll table ////
    table = new Table();
    tableScroll = new Table();
    for(int i=1;i<3;i++){
      tableScroll.row();
      Group grT = new Group();
      Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tableTuto"+i);
      tile.setScale(1,-1);
      tile.setOrigin(Align.center);
      grT.addActor(tile);
      /////////////////////////////
      grT.setSize(tile.getWidth(),tile.getHeight());
      tableScroll.add(grT).center();
    }


    ScrollPane Scroll = new ScrollPane(tableScroll);
    table.setFillParent(true);
    table.add(Scroll).fill().expand();
    groupScroll.setScale(1,-1);
    groupScroll.setOrigin(Align.center);
    groupScroll.addActor(table);
    group.addActor(groupScroll);


  }
}
