package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.repository.HttpMall;
import com.ss.scenes.GameScene;
import com.ss.utils.Utils;

public class Mall implements HttpMall.GetMission {
    private HttpMall       httpMall             = new HttpMall();
    private Group          group                = new Group();
    private Image          bg,frm,btnBack,Loadding;
    private GameScene      gameScene;
    private Table          table, tableScroll;
    private Group          groupScroll          = new Group();
    private Array<Image>   arrBtnGet            = new Array<>();
    private Array<Integer> arrType              = new Array<>();
    private Array<String>  arrUserSend          = new Array<>();
    private Array<Long>    arrTime              = new Array<>();

    private GShapeSprite   blackOverlay;



    public Mall(GameScene gameScene){
        Config.isShow=false;
        SoundEffect.Playmusic(SoundEffect.panel_open);
        this.gameScene = gameScene;
        httpMall.setIGetdata(this);
        GStage.addToLayer(GLayer.top,group);
        group.setPosition(GStage.getWorldWidth(),0);
        group.addAction(Actions.moveTo(0,0,0.5f, Interpolation.swingOut));
        loadBg();
        back();
        AwaitData();
        httpMall.PostData();


    }
    private void loadBg(){
        bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
        frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
        group.addActor(frm);
        Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmMall");
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
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Playmusic(SoundEffect.panel_close);
                group.addAction(Actions.sequence(
                        Actions.moveTo(-GStage.getWorldWidth(),0,0.5f, Interpolation.swingIn),
                        GSimpleAction.simpleAction((d, a)->{
                            gameScene.setTouch(Touchable.enabled);
                            group.clear();
                            group.remove();
                            Config.isShow=true;
                            return true;
                        })
                ));
            }
        });
    }
    private void renderListView(JsonValue Data){
        groupScroll.setWidth(frm.getWidth());
        groupScroll.setHeight(frm.getHeight()*0.8f);
        groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        ///////// scroll table ////
        table = new Table();
        tableScroll = new Table();
        System.out.println("in here!!: "+Data);
        if(Data !=null && Data.get("result").size!=0){
            for (int i=0;i<Data.get("result").size;i++){
                int         Type        = Data.get("result").get(i).get("type").asInt();
                String      name        = Data.get("result").get(i).get("name").asString();
                int         amount      = Data.get("result").get(i).get("amount").asInt();
                String      user        = Data.get("result").get(i).get("id_user_send").asString();
                long        time        = Data.get("result").get(i).get("time").asLong();
                    System.out.println("type:"+Type);
                    tableScroll.row().pad(10);
                    Group grT = new Group();
                    Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
                    grT.addActor(tile);
                    ///////////// title ///////
                    Label label = new Label("Bạn được "+user+" tặng\n"+amount+" "+name+"\nVào lúc "+ Utils.ConvertDateTime(time+""),new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    label.setFontScale(0.35f,-0.35f);
                    label.setAlignment(Align.left);
                    label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()/2,Align.left);
                    grT.addActor(label);
                    //////// button get/////////
                    Image btn = GUI.createImage(TextureAtlasC.UiAtlas,"btnTake");
                    btn.setScale(0.7f,-0.7f);
                    btn.setOrigin(Align.center);
                    btn.setPosition(tile.getX()+tile.getWidth()-btn.getWidth(),tile.getY()+tile.getHeight()*0.2f);
                    grT.addActor(btn);
                    /////////////////////////////
                    grT.setSize(tile.getWidth(),tile.getHeight());
                    tableScroll.add(grT).center();
                    /////// array///////
                    arrBtnGet.add(btn);
                    arrType.add(Type);
                    arrUserSend.add(user);
                    arrTime.add(time);

            }
        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);


    }
    private void eventBtn(){
        for (Image btn : arrBtnGet){
            btn.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                    SoundEffect.Play(SoundEffect.click);
                    SoundEffect.Playmusic(SoundEffect.click);
                    AwaitData();
                    Long    time     = arrTime.get(arrBtnGet.indexOf(btn,true));
                    httpMall.PostGetGift(time);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
    private void AwaitData(){
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        group.addActor(blackOverlay);
        ///////// loadding/////////
        Loadding = GUI.createImage(TextureAtlasC.UiAtlas,"loadding");
        Loadding.setOrigin(Align.center);
        Loadding.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        group.addActor(Loadding);
        aniload(Loadding);
    }
    void aniload(Image img){
        img.addAction(Actions.sequence(
                Actions.rotateBy(360,1f),
                GSimpleAction.simpleAction((d, a)->{
                    aniload(img);
                    return true;
                })
        ));
    }
    private void finishLoad(){
        Loadding.clear();
        Loadding.remove();
        blackOverlay.clear();
        blackOverlay.remove();
    }
    private void notice(float x, float y,String notice,Color color){
        Group gr = new Group();
        group.addActor(gr);
        ////////// label ///////////
        Label lbnotice = new Label(notice,new Label.LabelStyle(BitmapFontC.Font_Orange,color));
        lbnotice.setFontScale(0.7f);
        lbnotice.setAlignment(Align.center);
        lbnotice.setPosition(0,0,Align.center);
        gr.addActor(lbnotice);
        gr.setPosition(x,y,Align.center);
        gr.addAction(Actions.sequence(
                Actions.moveBy(0,-200,1f),
                GSimpleAction.simpleAction((d,a)->{
                    gr.clear();
                    gr.remove();
                    return true;
                })

        ));

    }

    @Override
    public void GetMall(JsonValue data) {
        System.out.println("check mall: "+data);
        finishLoad();
        renderListView(data);
        eventBtn();
    }

    @Override
    public void UpdateMall(JsonValue data) {
        finishLoad();
        if(data.get("status_code").asInt()==2000){
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Nhận Quà Thành Công!",Color.GREEN);
            groupScroll.clear();
            arrType.clear();
            arrBtnGet.clear();
            arrUserSend.clear();
            arrTime.clear();
            renderListView(data);
            eventBtn();
            if(data==null||data.get("result").size==0){
                gameScene.redDot.setVisible(false);
            }
        }else {
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi",Color.RED);
        }
    }

    @Override
    public void Fail(String s) {
        finishLoad();
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);
    }
}
