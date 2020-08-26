package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.repository.HttpInventory;
import com.ss.scenes.GameScene;

public class Inventory implements HttpInventory.GetInventory {
    private Group          group                = new Group();
    private Group          groupMerge           = new Group();
    private Group          groupEff             = new Group();
    private Image          bg;
    private Array<Image>   arrIconCard          = new Array<>();
    private Array<Label>   arrLabelCard         = new Array<>();
    private Image          btnGhepCard,btnGive,btnBack,frm,CardBillionaire,Loadding,dragon,btnEvent;
    private Array<Image>   arrbtnGhepPiece      = new Array<>();
    private Array<Image>   arrTileScl           = new Array<>();
    private Array<Label>   arrLbProgress        = new Array<>();
    private GameScene      gameScene;
    private HttpInventory  httpInventory        = new HttpInventory();
    private GShapeSprite   blackOverlay;
    public  JsonValue      Data;
    private Label          lbQuanBilli;
    private int            TypeMergePiece       = 0;
    private int            TypeMergeCard        = 0;
    private boolean        checkMerge           = true;

    public Inventory(GameScene gameScene) {
        Config.isShow=false;
        SoundEffect.Playmusic(SoundEffect.panel_open);
        httpInventory.setIGetdata(this);
        this.gameScene = gameScene;
        GStage.addToLayer(GLayer.top,group);
        GStage.addToLayer(GLayer.top,groupEff);
        GStage.addToLayer(GLayer.top,groupMerge);
        group.setPosition(GStage.getWorldWidth(),0);
        groupEff.setVisible(false);
        group.addAction(Actions.sequence(
                Actions.moveTo(0,0,0.5f, Interpolation.swingOut),
                GSimpleAction.simpleAction((d,a)->{
                    groupEff.setVisible(true);
                 return true;
                })
        ));
        loadBg();
        loadItem();
        loadButton();
//        httpInventory.CallApi();
        setDefault();
        AwaitData();
        httpInventory.PostData();



    }
    private void loadBg(){
        bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
        frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2,Align.center);
        group.addActor(frm);
//        Image darkbg = GUI.createImage(TextureAtlasC.UiAtlas,"darkBg");
//        darkbg.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.53f,Align.center);
//        group.addActor(darkbg);
        dragon = GUI.createImage(TextureAtlasC.UiAtlas,"dragon");
        dragon.setPosition(GStage.getWorldWidth()/2,frm.getY()+dragon.getHeight()*0.82f,Align.center);
        group.addActor(dragon);
        blinkDragon(dragon);
        dragon.setVisible(false);
        Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmInventory");
        Tile.setPosition(frm.getX()+frm.getWidth()/2,frm.getY(),Align.center);
        group.addActor(Tile);

    }
    private void blinkDragon(Image img){
        img.addAction(Actions.sequence(
                Actions.alpha(0,2),
                Actions.alpha(1,2),
                GSimpleAction.simpleAction((d,a)->{
                    blinkDragon(img);
                    return true;
                })
        ));
    }
    private void loadItem() {

        ///////// the ti phu///////
        CardBillionaire = GUI.createImage(TextureAtlasC.InventoryAtlas, "cardBillionaire");
        CardBillionaire.setPosition(GStage.getWorldWidth() / 2, bg.getHeight() * 0.25f, Align.center);
        group.addActor(CardBillionaire);
        ///////// label quantity billi ////////
        lbQuanBilli     = new Label("0",new Label.LabelStyle(BitmapFontC.font_white,null));
        lbQuanBilli.setFontScale(0.7f);
        lbQuanBilli.setAlignment(Align.center);
        lbQuanBilli.setPosition(CardBillionaire.getX()+lbQuanBilli.getPrefWidth(),CardBillionaire.getY()+CardBillionaire.getHeight()-lbQuanBilli.getPrefHeight(),Align.center);
        groupEff.addActor(lbQuanBilli);
        ///////// manh /////////////
        for (int i = 1; i < 5; i++) {
            Image icon2 = GUI.createImage(TextureAtlasC.InventoryAtlas, "icPiece" + i);
            icon2.setPosition(GStage.getWorldWidth() / 2 - icon2.getWidth() * 3 + icon2.getWidth() * i, CardBillionaire.getY() + CardBillionaire.getHeight() * 2.1f);
            group.addActor(icon2);

            //////// label //////
            Label lb = new Label("0", new Label.LabelStyle(BitmapFontC.font_white, null));
            lb.setFontScale(0.4f);
            lb.setOrigin(Align.center);
            lb.setAlignment(Align.center);
            lb.setPosition(icon2.getX() + icon2.getWidth() * 0.18f, icon2.getY() + icon2.getHeight() * 0.8f, Align.center);
            group.addActor(lb);

            ////////// frmScl ///////////
            Image frmscl = GUI.createImage(TextureAtlasC.InventoryAtlas,"frmScl");
            frmscl.setPosition(icon2.getX()+icon2.getWidth()/2,icon2.getY()+icon2.getHeight()*1.2f,Align.center);
            group.addActor(frmscl);

            ///////// tile scl //////
            Image tileScl = GUI.createImage(TextureAtlasC.InventoryAtlas,"tileScl");
            tileScl.setOrigin(Align.left);
            tileScl.setPosition(frmscl.getX()+frmscl.getWidth()/2,frmscl.getY()+frmscl.getHeight()/2,Align.center);
            group.addActor(tileScl);
            tileScl.setScale(0,1);

            //////// label progress ////////
            Label lbProgress =new Label("0/0",new Label.LabelStyle(BitmapFontC.font_white,null));
            lbProgress.setFontScale(0.4f);
            lbProgress.setAlignment(Align.center);
            lbProgress.setPosition(frmscl.getX()+frmscl.getWidth()/2,frmscl.getY()+frmscl.getHeight()/2,Align.center);
            group.addActor(lbProgress);

            //////// button ghep manh /////////
            Image btn = GUI.createImage(TextureAtlasC.InventoryAtlas,"btnMergePiece");
            btn.setSize(btn.getWidth()*0.7f,btn.getHeight()*0.7f);
            btn.setOrigin(Align.center);
            btn.setPosition(icon2.getX()+icon2.getWidth()/2,frmscl.getY()+frmscl.getHeight()*2.1f,Align.center);
            group.addActor(btn);
            btn.setColor(Color.DARK_GRAY);

            ///////// array /////////////////
            arrIconCard.add(icon2);
            arrLabelCard.add(lb);
            arrbtnGhepPiece.add(btn);
            arrTileScl.add(tileScl);
            arrLbProgress.add(lbProgress);
        }
        ///////// the /////////////
        for (int i = 1; i < 5; i++) {
            ///////// icon///////
            Image icon = GUI.createImage(TextureAtlasC.InventoryAtlas, "icCard" + i);
            icon.setPosition(GStage.getWorldWidth() / 2 - icon.getWidth() * 3 + icon.getWidth() * i, CardBillionaire.getY() + CardBillionaire.getHeight()*1.1f );
            group.addActor(icon);
            //////// label //////
            Label lb = new Label("0", new Label.LabelStyle(BitmapFontC.font_white, null));
            lb.setFontScale(0.4f);
            lb.setOrigin(Align.center);
            lb.setAlignment(Align.center);
            lb.setPosition(icon.getX() + icon.getWidth() * 0.22f, icon.getY() + icon.getHeight() * 0.8f, Align.center);
            group.addActor(lb);

            /////// array///////
            arrIconCard.add(icon);
            arrLabelCard.add(lb);
        }
        /////// button  ghep the/////
        btnGhepCard = GUI.createImage(TextureAtlasC.InventoryAtlas,"btnMergeCard");
        btnGhepCard.setSize(btnGhepCard.getWidth()*0.9f,btnGhepCard.getHeight()*0.9f);
        btnGhepCard.setOrigin(Align.center);
        btnGhepCard.setPosition(GStage.getWorldWidth()/2,CardBillionaire.getY() + CardBillionaire.getHeight() * 1.9f,Align.center);
        group.addActor(btnGhepCard);
        btnGhepCard.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                if(btnGhepCard.getColor().equals(Color.DARK_GRAY)){
                    notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"bạn chưa đủ thẻ!",Color.RED);
                }else {
                    AwaitData();
                    httpInventory.MergeCardBilli();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    private void loadButton(){
        /////// button  tang/////
        btnGive = GUI.createImage(TextureAtlasC.InventoryAtlas,"btnGive");
        btnGive.setSize(btnGive.getWidth()*0.9f,btnGive.getHeight()*0.9f);
        btnGive.setOrigin(Align.center);
        btnGive.setPosition(GStage.getWorldWidth()/2-btnGive.getWidth()*0.6f,frm.getY()+frm.getHeight()-btnGive.getHeight(),Align.center);
        group.addActor(btnGive);
        btnGive.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                new Gift(Data,Inventory.this);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        //////// button event//////
        btnEvent = GUI.createImage(TextureAtlasC.InventoryAtlas,"btnEvent");
        btnEvent.setSize(btnEvent.getWidth()*0.9f,btnEvent.getHeight()*0.9f);
        btnEvent.setOrigin(Align.center);
        btnEvent.setPosition(GStage.getWorldWidth()/2+btnEvent.getWidth()*0.6f,frm.getY()+frm.getHeight()-btnGive.getHeight(),Align.center);
        group.addActor(btnEvent);
        btnEvent.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Playmusic(SoundEffect.click);
                new Event(Inventory.this,gameScene);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        /////// button Back //////
        btnBack = GUI.createImage(TextureAtlasC.UiAtlas,"btnBack");
        btnBack.setOrigin(Align.center);
        btnBack.setPosition(frm.getX()+frm.getWidth()-btnBack.getWidth()*0.7f,frm.getY()+btnBack.getHeight()*0.7f,Align.center);
        group.addActor(btnBack);
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                groupMerge.clear();
                groupMerge.remove();
                groupEff.clear();
                groupEff.remove();
                SoundEffect.Playmusic(SoundEffect.panel_close);
                group.addAction(Actions.sequence(
                        Actions.moveTo(-GStage.getWorldWidth(),0,0.5f,Interpolation.swingIn),
                        GSimpleAction.simpleAction((d,a)->{
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
    private void setDefault(){
        CardBillionaire.setColor(.205f, .201f, .201f, 0.8f);
        for(Label lb: arrLabelCard){
            lb.setVisible(false);
        }
        for (Image img : arrIconCard){
            img.setColor(.205f, .201f, .201f, 0.8f);
        }
        btnGhepCard.setColor(Color.DARK_GRAY);
    }
    public void updateInventory(JsonValue data){
        for (Label lb : arrLabelCard){
                lb.setText("0");
                lb.setVisible(false);
                arrIconCard.get(arrLabelCard.indexOf(lb,true)).setColor(.205f, .201f, .201f, 0.8f);
                if(arrLabelCard.indexOf(lb,true)<arrbtnGhepPiece.size){
                    arrbtnGhepPiece.get(arrLabelCard.indexOf(lb,true)).setColor(Color.DARK_GRAY);
                    arrLbProgress.get(arrLabelCard.indexOf(lb,true)).setText("0/"+Config.condi_merge);
                    arrTileScl.get(arrLabelCard.indexOf(lb,true)).setScale(0,1);
                }
        }
        if(data.get("result").size==0|| data==null){
        }else {
            System.out.println("size data: "+data.size);
            for (int i=0;i<data.get("result").size;i++){
                int type   = (data.get("result").get(i).get("type").asInt()-2);
                int amount = data.get("result").get(i).get("amount").asInt();
                int target = data.get("result").get(i).get("condi_merge").asInt();
                System.out.println("type: "+type);
                for (Label lb : arrLabelCard){
                    if(arrLabelCard.indexOf(lb,true)==type){
                        lb.setText(""+amount);
                        lb.setVisible(true);
                        arrIconCard.get(arrLabelCard.indexOf(lb,true)).setColor(Color.WHITE);
                        if(arrLabelCard.indexOf(lb,true)<arrTileScl.size){
                            arrbtnGhepPiece.get(arrLabelCard.indexOf(lb,true)).setName(""+data.get("result").get(i).get("type").asInt());
                          if(amount<target){
                            arrTileScl.get(arrLabelCard.indexOf(lb,true)).setScale((float) amount/target,1);
                            arrLbProgress.get(arrLabelCard.indexOf(lb,true)).setText(amount+"/"+target);
                            arrbtnGhepPiece.get(arrLabelCard.indexOf(lb,true)).setColor(Color.DARK_GRAY);
                          }
                          else {
                              arrTileScl.get(arrLabelCard.indexOf(lb,true)).setScale(1);
                              arrLbProgress.get(arrLabelCard.indexOf(lb,true)).setText(target+"/"+target);
                              arrbtnGhepPiece.get(arrLabelCard.indexOf(lb,true)).setColor(Color.WHITE);
                          }
                        }
                    }
                }
            }

        }
    }
    private void eventMergePiece(){
        for (Image btn : arrbtnGhepPiece){
            btn.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                    SoundEffect.Play(SoundEffect.click);
                    SoundEffect.Playmusic(SoundEffect.click);
                    if(btn.getColor().equals(Color.DARK_GRAY)){
                        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"bạn chưa đủ mảnh",Color.RED);
                    }else{
                        TypeMergePiece = Integer.parseInt(arrbtnGhepPiece.get(arrbtnGhepPiece.indexOf(btn,true)).getName());
                        System.out.println("type merge manh: "+TypeMergePiece);
                        AwaitData();
                        httpInventory.Merge(TypeMergePiece);
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });

        }
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
    private void AniGiveGift(int type,int typeMergePiece){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.8f);
        groupMerge.addActor(blackOverlay);
        /////////// ani piece ///////
        if(typeMergePiece>=2 &&typeMergePiece<=5){
            Array<Image> arrIc = new Array<>();
            Image img = GUI.createImage(TextureAtlasC.InventoryAtlas,"icPiece1");
            img.setSize(img.getWidth()*1.5f,img.getHeight()*1.5f);

            float W = img.getWidth()/2;
            float H = img.getHeight()/2;
            checkMerge = true;
            Array<Vector2> arrPos = new Array<>();
            arrPos.add(new Vector2(-W,-H),new Vector2(-W,H),new Vector2(W,-H),new Vector2(W,H));
            for (int i = 0; i<4 ; i++ ){
                Image ic = GUI.createImage(TextureAtlasC.InventoryAtlas,"icPiece"+(typeMergePiece-1));
                ic.setSize(ic.getWidth()*1.5f,ic.getHeight()*1.5f);
                ic.setOrigin(Align.center);
                ic.setPosition(GStage.getWorldWidth()/2+arrPos.get(i).x,GStage.getWorldHeight()/2+arrPos.get(i).y,Align.center);
                groupMerge.addActor(ic);
                ////// array ////////
                arrIc.add(ic);
            }
            Tweens.setTimeout(group,0.5f,()->{
                SoundEffect.Playmusic(SoundEffect.merge);
                for (Image ic : arrIc){
                    ic.addAction(Actions.sequence(
                            Actions.parallel(
                            Actions.moveBy(-arrPos.get(arrIc.indexOf(ic,true)).x,-arrPos.get(arrIc.indexOf(ic,true)).y,1f,Interpolation.linear),
                            Actions.alpha(0,1f)
                            ),
                            GSimpleAction.simpleAction((d,a)->{
                                if(checkMerge==true){
                                    checkMerge = false;
                                    System.out.println("merge");
                                    AniMerge(type);
                                }
                                return true;
                            })
                    ));
                }
            });


        }
        /////////// button /////////
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                groupMerge.clear();

            }
        });
    }
    private void AniMerge(int Type){
//        SoundEffect.Play(SoundEffect.unlock);
        SoundEffect.Playmusic(SoundEffect.unlock);
        ///////////// effect light ////////
        if(Config.remoteEffect){
            GParticleSprite ef = GParticleSystem.getGParticleSystem("lightYellow").create(groupMerge,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2+100);
            ef.getEffect().scaleEffect(2,3,1);

        }
        Image icon = GUI.createImage(TextureAtlasC.Wheel,"rw"+Type);
        icon.setOrigin(Align.center);
        icon.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        icon.getColor().a =0;
        groupMerge.addActor(icon);
        icon.addAction(Actions.sequence(
                Actions.parallel(
                Actions.scaleTo(2,2,0.5f,Interpolation.swingOut),
                Actions.alpha(1,0.5f)
                )
        ));
        if(Config.remoteEffect){
            GParticleSprite ef2 = GParticleSystem.getGParticleSystem("merge").create(groupMerge,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
            ef2.getEffect().scaleEffect(2);
            GParticleSprite ef3 = GParticleSystem.getGParticleSystem("Titlemerge").create(groupMerge,GStage.getWorldWidth()/2,icon.getY()-200);
            ef3.getEffect().scaleEffect(3);
        }
    }
    private void AniGiveGiftBilli(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.8f);
        groupMerge.addActor(blackOverlay);
        /////////// ani piece ///////
            Array<Image> arrIc = new Array<>();
            Image img = GUI.createImage(TextureAtlasC.InventoryAtlas,"icPiece1");
            img.setSize(img.getWidth()*1.5f,img.getHeight()*1.5f);

            float W = img.getWidth()/2;
            float H = img.getHeight()/2;
            checkMerge = true;
            Array<Vector2> arrPos = new Array<>();
            arrPos.add(new Vector2(-W,-H),new Vector2(-W,H),new Vector2(W,-H),new Vector2(W,H));
            for (int i = 0; i<4 ; i++ ){
                Image ic = GUI.createImage(TextureAtlasC.InventoryAtlas,"icCard"+(i+1));
                ic.setSize(ic.getWidth()*1.5f,ic.getHeight()*1.5f);
                ic.setOrigin(Align.center);
                ic.setPosition(GStage.getWorldWidth()/2+arrPos.get(i).x,GStage.getWorldHeight()/2+arrPos.get(i).y,Align.center);
                groupMerge.addActor(ic);
                ////// array ////////
                arrIc.add(ic);
            }
            Tweens.setTimeout(group,0.5f,()->{
                SoundEffect.Playmusic(SoundEffect.merge);
                for (Image ic : arrIc){
                    ic.addAction(Actions.sequence(
                            Actions.parallel(
                                    Actions.moveBy(-arrPos.get(arrIc.indexOf(ic,true)).x,-arrPos.get(arrIc.indexOf(ic,true)).y,1f,Interpolation.linear),
                                    Actions.alpha(0,1f)
                            ),
                            GSimpleAction.simpleAction((d,a)->{
                                if(checkMerge==true){
                                    checkMerge = false;
                                    System.out.println("merge");
                                    AniMergeBilli();
                                }
                                return true;
                            })
                    ));
                }
            });

        /////////// button /////////
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                groupMerge.clear();

            }
        });
    }
    private void AniMergeBilli(){
        updateBtnMergeCard();
//        SoundEffect.Play(SoundEffect.unlock);
        SoundEffect.Playmusic(SoundEffect.unlock);
        ///////////// effect light ////////
        if(Config.remoteEffect){
            GParticleSprite ef = GParticleSystem.getGParticleSystem("lightYellow").create(groupMerge,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2+100);
            ef.getEffect().scaleEffect(2,3,1);
        }
        Image icon = GUI.createImage(TextureAtlasC.InventoryAtlas,"cardBillionaire");
        icon.setOrigin(Align.center);
        icon.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        icon.getColor().a =0;
        groupMerge.addActor(icon);
        icon.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.scaleTo(2,2,0.5f,Interpolation.swingOut),
                        Actions.alpha(1,0.5f)
                )
        ));
        if(Config.remoteEffect){
            GParticleSprite ef2 = GParticleSystem.getGParticleSystem("merge").create(groupMerge,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
            ef2.getEffect().scaleEffect(2);
            GParticleSprite ef3 = GParticleSystem.getGParticleSystem("Titlemerge").create(groupMerge,GStage.getWorldWidth()/2,icon.getY()-200);
            ef3.getEffect().scaleEffect(3);
        }
    }
    public void updateBtnMergeCard(){
        if(Data.size<4){

        }else{
            if(Data.get("button_merge_card_billion").asBoolean()== true){
                btnGhepCard.setColor(Color.WHITE);
            }else {
                btnGhepCard.setColor(Color.DARK_GRAY);
            }
            if(Data.get("card_billion").asBoolean()==true){
                CardBillionaire.setColor(Color.WHITE);
                btnGhepCard.setVisible(false);
                //////// effect billi//////
                if(Config.remoteEffect){
                    GParticleSprite ef = GParticleSystem.getGParticleSystem("effBilli").create(groupEff,CardBillionaire.getX()+CardBillionaire.getWidth()/2,CardBillionaire.getY()+CardBillionaire.getHeight()*0.55f);
                }
                dragon.setVisible(true);
                lbQuanBilli.setZIndex(100);
                lbQuanBilli.setText("1");
            }else {
                CardBillionaire.setColor(.205f, .201f, .201f, 0.8f);
            }
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

    @Override
    public void Finish(JsonValue data) {
        System.out.println("check jv: "+data);
       finishLoad();
       Data = data;
       updateInventory(data);
       eventMergePiece();
       updateBtnMergeCard();
    }

    @Override
    public void FinishMerge(JsonValue data) {
        finishLoad();
        if(data.get("result").size==0){
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"ghép không thành công",Color.RED);
        }else {
            Data =data;
            updateInventory(data);
            updateBtnMergeCard();
            AniGiveGift(data.get("type").asInt(),TypeMergePiece);
        }
    }

    @Override
    public void FinishMergeBilli(JsonValue data) {
        finishLoad();
        System.out.println("merge card billi: "+data);

        if(data.size < 3){
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi ghép",Color.RED);
        }else {
            Data =data;
            updateInventory(data);
            AniGiveGiftBilli();
        }
    }

    @Override
    public void FinishCheckEvent(JsonValue data) {

    }

    @Override
    public void Fail(String s) {
        finishLoad();
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);
    }
}
