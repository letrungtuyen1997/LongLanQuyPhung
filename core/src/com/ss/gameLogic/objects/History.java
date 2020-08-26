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
import com.ss.repository.HttpHistory;
import com.ss.scenes.GameScene;
import com.ss.utils.Utils;

public class History implements HttpHistory.GetHistory {
    private Group           group                       = new Group();
    private Image           bg,frm,btnBack,Loadding;
    private GameScene       gameScene;
    private Group           groupScroll                 = new Group();
    private Group           groupScroll1                = new Group();
    private Group           groupScroll2                = new Group();
    private Table           table, tableScroll;
    private Table           table1, tableScroll1;
    private Table           table2, tableScroll2;
    private HttpHistory     httpHistory                 = new HttpHistory();
    private GShapeSprite    blackOverlay;
    private Array<Image>    arrBtnOn                    = new Array<>();
    private Array<Image>    arrBtnOff                   = new Array<>();
    private Image           Tile;


    public History(GameScene gameScene){
        Config.isShow=false;
        SoundEffect.Playmusic(SoundEffect.panel_open);
        GStage.addToLayer(GLayer.ui,group);
        httpHistory.setIGetdata(this);
        this.gameScene = gameScene;
        group.setPosition(GStage.getWorldWidth(),0);
        group.addAction(Actions.moveTo(0,0,0.5f, Interpolation.swingOut));
        loadBg();
        back();
        AwaitData();
        httpHistory.GetHistory();




    }
    private void loadBg(){
        bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
        frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
        group.addActor(frm);
        Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmHistory");
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
                            Config.isShow=true;
                            return true;
                        })
                ));
            }
        });

    }
    private void renderListView(JsonValue Data){
        for (int i=1;i<4;i++){
            ///// btn on //////
            Image btn = GUI.createImage(TextureAtlasC.UiAtlas,"btnOn"+i);
            btn.setPosition(frm.getX()+frm.getWidth()*0.04f+(btn.getWidth()*1.1f*(i-1)),Tile.getY()+Tile.getHeight()*1.7f);
            group.addActor(btn);
            arrBtnOn.add(btn);
            ///// btn off ////
            Image btnOff = GUI.createImage(TextureAtlasC.UiAtlas,"btnOff"+i);
            btnOff.setPosition(btn.getX(), btn.getY());
            group.addActor(btnOff);
            arrBtnOff.add(btnOff);
        }
        ///// event btn select /////
        eventBtn();
        ////////// gr the cao/////////
        groupScroll.setWidth(frm.getWidth());
        groupScroll.setHeight(frm.getHeight()*0.75f);
        groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.57f,Align.center);
        //////// gr manh linh thu //////
        groupScroll1.setWidth(frm.getWidth());
        groupScroll1.setHeight(frm.getHeight()*0.75f);
        groupScroll1.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.57f,Align.center);
        /////// gr the linh thu  ///////
        groupScroll2.setWidth(frm.getWidth());
        groupScroll2.setHeight(frm.getHeight()*0.75f);
        groupScroll2.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.57f,Align.center);
        ///////// scroll table the cao////
        table = new Table();
        tableScroll = new Table();
        if(Data !=null && Data.get("result").size!=0){
            for (int i=0;i<Data.get("result").size;i++){
                int         type            = Data.get("result").get(i).get("type").asInt();
                String      description     = Data.get("result").get(i).get("name").asString();
                String      Datetime        = Data.get("result").get(i).get("date").asString();
                if(type==0||type==1){
                    tableScroll.row().pad(10);
                    Group grT = new Group();
                    Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
                    grT.addActor(tile);
                    ///////////// title ///////
                    Label label = new Label(description,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    label.setFontScale(0.4f,-0.4f);
                    label.setAlignment(Align.left);
                    label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
                    grT.addActor(label);
                    //////////// datetime //////////
                    Label date = new Label(Utils.ConvertDateTime(Datetime),new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    date.setFontScale(0.4f,-0.4f);
                    date.setAlignment(Align.left);
                    date.setPosition(label.getX(),tile.getY()+tile.getHeight()*0.3f,Align.left);
                    grT.addActor(date);
                    //////////// add row////////
                    grT.setSize(tile.getWidth(),tile.getHeight());
                    tableScroll.add(grT).center();
                }

            }
        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);

        ///////// scroll table manh linh thu////
        table1 = new Table();
        tableScroll1 = new Table();
        if(Data !=null && Data.get("result").size!=0){
            for (int i=0;i<Data.get("result").size;i++){
                int         type            = Data.get("result").get(i).get("type").asInt();
                String      description = Data.get("result").get(i).get("name").asString();
                String      Datetime        = Data.get("result").get(i).get("date").asString();
                if(type==2||type==3||type==4||type==5){
                    tableScroll1.row().pad(10);
                    Group grT = new Group();
                    Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
                    grT.addActor(tile);
                    ///////////// title ///////
                    Label label = new Label(description,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    label.setFontScale(0.4f,-0.4f);
                    label.setAlignment(Align.left);
                    label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
                    grT.addActor(label);
                    //////////// datetime //////////
                    Label date = new Label(Utils.ConvertDateTime(Datetime),new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    date.setFontScale(0.4f,-0.4f);
                    date.setAlignment(Align.left);
                    date.setPosition(label.getX(),tile.getY()+tile.getHeight()*0.3f,Align.left);
                    grT.addActor(date);
                    //////////// add row////////
                    grT.setSize(tile.getWidth(),tile.getHeight());
                    tableScroll1.add(grT).center();
                }
            }
        }
        ScrollPane Scroll1 = new ScrollPane(tableScroll1);
        table1.setFillParent(true);
        table1.add(Scroll1).fill().expand();
        groupScroll1.setScale(1,-1);
        groupScroll1.setOrigin(Align.center);
        groupScroll1.addActor(table1);
        group.addActor(groupScroll1);

        ///////// scroll table the linh thu////
        table2 = new Table();
        tableScroll2 = new Table();
        if(Data !=null && Data.get("result").size!=0){
            for (int i=0;i<Data.get("result").size;i++){
                int         type            = Data.get("result").get(i).get("type").asInt();
                String      description = Data.get("result").get(i).get("name").asString();
                String      Datetime        = Data.get("result").get(i).get("date").asString();
                if(type==6||type==7||type==8||type==9){
                    tableScroll2.row().pad(10);
                    Group grT = new Group();
                    Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
                    grT.addActor(tile);
                    ///////////// title ///////
                    Label label = new Label(description,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    label.setFontScale(0.4f,-0.4f);
                    label.setAlignment(Align.left);
                    label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
                    grT.addActor(label);
                    //////////// datetime //////////
                    Label date = new Label(Utils.ConvertDateTime(Datetime),new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                    date.setFontScale(0.4f,-0.4f);
                    date.setAlignment(Align.left);
                    date.setPosition(label.getX(),tile.getY()+tile.getHeight()*0.3f,Align.left);
                    grT.addActor(date);
                    //////////// add row////////
                    grT.setSize(tile.getWidth(),tile.getHeight());
                    tableScroll2.add(grT).center();
                }
            }
        }
        ScrollPane Scroll2 = new ScrollPane(tableScroll2);
        table2.setFillParent(true);
        table2.add(Scroll2).fill().expand();
        groupScroll2.setScale(1,-1);
        groupScroll2.setOrigin(Align.center);
        groupScroll2.addActor(table2);
        group.addActor(groupScroll2);
    }
    private void eventBtn(){
        ///////// set default /////
        if(arrBtnOff.get(0)!=null){
            arrBtnOff.get(0).setVisible(false);
        }
        groupScroll.setVisible(true);
        groupScroll1.setVisible(false);
        groupScroll2.setVisible(false);
        for (int i=0; i<arrBtnOff.size;i++){
            int finalI = i;
            arrBtnOff.get(i).addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    SetSelect(finalI);
                    SelectView(finalI);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
    private void SetSelect(int index){
        if(arrBtnOff!=null){
            for (int i=0;i<arrBtnOff.size;i++){
                arrBtnOff.get(i).setVisible(true);
            }
            for (int i=0;i<arrBtnOff.size;i++){
                if(index==i){
                    arrBtnOff.get(i).setVisible(false);
                }
            }
        }
    }
    private void SelectView(int index){
        if(index==0){
            groupScroll.setVisible(true);
            groupScroll1.setVisible(false);
            groupScroll2.setVisible(false);
        }else if(index==1){
            groupScroll.setVisible(false);
            groupScroll1.setVisible(true);
            groupScroll2.setVisible(false);
        }else if(index==2){
            groupScroll.setVisible(false);
            groupScroll1.setVisible(false);
            groupScroll2.setVisible(true);
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
    public void GetHistory(JsonValue data) {
//        System.out.println("check: "+data);
        renderListView(data);
        finishLoad();

    }

    @Override
    public void Fail(String s) {
        finishLoad();
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);
    }
}
