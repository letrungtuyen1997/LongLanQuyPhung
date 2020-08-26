package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.scenes.GameScene;

public class InviteFriend {
    private Group group                = new Group();
    private Image bg,frm,btnBack;
    private GameScene gameScene;
    public InviteFriend(GameScene gameScene){
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
        Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmInvite");
        Tile.setPosition(frm.getX()+frm.getWidth()/2,frm.getY(),Align.center);
        group.addActor(Tile);

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
                SoundEffect.Playmusic(SoundEffect.panel_close);
                super.touchUp(event, x, y, pointer, button);
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
}
