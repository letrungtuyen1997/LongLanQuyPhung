package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.LoadParticle;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.effect.ParticleEffects;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GClipGroup;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.History;
import com.ss.gameLogic.objects.Inventory;
import com.ss.gameLogic.objects.InviteFriend;
import com.ss.gameLogic.objects.Mall;
import com.ss.gameLogic.objects.Mission;
import com.ss.gameLogic.objects.Tutorial;
import com.ss.gameLogic.objects.WheelItem;
import com.ss.gdx.NParticleEffect;
import com.ss.repository.HttpGetToken;
import com.ss.utils.Utils;

import javax.script.ScriptEngine;

public class GameScene extends GScreen implements HttpGetToken.GetUserInfo {
    public      Group           MainGroup           = new Group();
    public      Group           MainGr              = new Group();
    private     Group           grSetting           = new Group();
    private     Group           grCountDown         = new Group();
    private     Image           bg;
    private     Image           btnCollections,btnMission,btnInvite,btnMall,btnHistory,frmTurn,btnTutorial,Loadding;
    private     Label           LbTurn;
    public      int             countTurn           = 0;
    private     String          megaId              = "123456";
    public      WheelItem       wheelItem;
    private     HttpGetToken    httpGetToken        = new HttpGetToken();
    private     GShapeSprite    blackOverlay;
    private     int             count               =0;
    private     int             countHeartbeat      =0;
    private     int             countdown           =0;

  @Override
    public void dispose() {

    }

    @Override
    public void init() {

      httpGetToken.setIGetdata(this);
      SoundEffect.Playmusic(1);
      checkconnect();
      initGroup();
      renderBg();
      final GShapeSprite blackOverlay = new GShapeSprite();
      blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
      blackOverlay.setColor(0,0,0,0.4f);
      MainGroup.addActor(blackOverlay);

      ///// effect //////
      if(Config.remoteEffect){
        GParticleSprite ef = GParticleSystem.getGParticleSystem("tree").create(MainGroup,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
        ef.getEffect().scaleEffect(2);
        ef.getEffect().flipY();
      }
      frmTurn();
      Collection();
      Mission();
//      InviteFr();
      Mall();
      History();
      Tutorial();
      System.out.println("log uri: "+Config.uri);
      System.out.println("check: "+System.currentTimeMillis());
      System.out.println("log date: "+Utils.ConvertDateTime(""+System.currentTimeMillis()));
      System.out.println("check condi_merge: "+Config.condi_merge);
      AwaitData();
      httpGetToken.CheckToken(Config.token);

    }
    void aniIconTitle(Image ic){
        ic.addAction(Actions.sequence(
                Actions.scaleTo(1.2f,0.8f,0.5f),
                Actions.scaleTo(1,1f,0.5f),
                Actions.delay(1f),
                GSimpleAction.simpleAction((d,a)->{
                    aniIconTitle(ic);
                    return true;
                })
        ));
    }

    private void initGroup(){
        GStage.addToLayer(GLayer.ui,MainGroup);
        GStage.addToLayer(GLayer.top,MainGr);
        GStage.addToLayer(GLayer.top,grCountDown);

    }


    @Override
    public void run() {
    }

    @Override
    public void render(float var1) {
        super.render(var1);
//        elements.forEach();
    }

    private void renderBg() {
        Image bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        MainGroup.addActor(bg);
        wheelItem = new WheelItem(this);
    }
    private void Collection(){
        btnCollections = GUI.createImage(TextureAtlasC.UiAtlas,"btnColections");
        btnCollections.setOrigin(Align.center);
        btnCollections.setPosition(btnCollections.getWidth()*0.6f,GStage.getWorldHeight()-btnCollections.getHeight()*0.6f,Align.center);
        MainGroup.addActor(btnCollections);
        ////// event //////
        btnCollections.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnCollections.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                          if(wheelItem.checkWhell==false){
                            new Inventory(GameScene.this);
                          }else {
                            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"vui lòng chờ vòng quay kết thúc",Color.RED);
                            setTouch(Touchable.enabled);
                          }
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }
    private void Mission(){
        btnMission = GUI.createImage(TextureAtlasC.UiAtlas,"btnMission");
        btnMission.setOrigin(Align.center);
        btnMission.setPosition(GStage.getWorldWidth()-btnMission.getWidth()*0.6f,GStage.getWorldHeight()-btnMission.getHeight()*0.6f,Align.center);
        MainGroup.addActor(btnMission);
        ////// event //////
        btnMission.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnMission.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                          if(wheelItem.checkWhell==false){
                            new Mission(GameScene.this,wheelItem);
                          }else {
                            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"vui lòng chờ vòng quay kết thúc",Color.RED);
                            setTouch(Touchable.enabled);
                          }
                            return true;
                        })
                ));




                return super.touchDown(event, x, y, pointer, button);
            }

        });
    }
    private void InviteFr(){
        btnInvite = GUI.createImage(TextureAtlasC.UiAtlas,"btnInvite");
        btnInvite.setOrigin(Align.center);
        btnInvite.setPosition(GStage.getWorldWidth()-btnInvite.getWidth()*0.6f,GStage.getWorldHeight()*0.15f,Align.center);
        MainGroup.addActor(btnInvite);
        ////// event //////
        btnInvite.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnInvite.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            new InviteFriend(GameScene.this);
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }

        });
    }
    private void Mall(){
        btnMall = GUI.createImage(TextureAtlasC.UiAtlas,"btnMall");
        btnMall.setOrigin(Align.center);
        btnMall.setPosition(GStage.getWorldWidth()-btnMall.getWidth()*0.6f,GStage.getWorldHeight()*0.15f,Align.center);
        MainGroup.addActor(btnMall);
        ////// event //////
        btnMall.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnMall.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                          if(wheelItem.checkWhell==false){
                            new Mall(GameScene.this);
                          }else {
                            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"vui lòng chờ vòng quay kết thúc",Color.RED);
                            setTouch(Touchable.enabled);
                          }
//                          GMain.platform.ShareFb();
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);

            }
        });

    }
    private void History(){
        btnHistory = GUI.createImage(TextureAtlasC.UiAtlas,"btnHistory");
        btnHistory.setOrigin(Align.center);
        btnHistory.setPosition(btnHistory.getWidth()*0.6f,GStage.getWorldHeight()*0.15f,Align.center);
        MainGroup.addActor(btnHistory);
        ////// event //////
        btnHistory.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnHistory.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),   
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                          if(wheelItem.checkWhell==false){
                            new History(GameScene.this);
                          }else {
                            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"vui lòng chờ vòng quay kết thúc",Color.RED);
                            setTouch(Touchable.enabled);
                          }
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }
    private void Tutorial(){
        btnTutorial = GUI.createImage(TextureAtlasC.UiAtlas,"btnTutorial");
        btnTutorial.setOrigin(Align.center);
        btnTutorial.setPosition(btnHistory.getX()+btnHistory.getWidth()/2,btnHistory.getY()+btnHistory.getHeight()*1.6f,Align.center);
        MainGroup.addActor(btnTutorial);
        ////// event //////
        btnTutorial.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setTouch(Touchable.disabled);
                btnTutorial.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                          if(wheelItem.checkWhell==false){
                            new Tutorial(GameScene.this);
                          }else {
                            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"vui lòng chờ vòng quay kết thúc",Color.RED);
                            setTouch(Touchable.enabled);
                          }
                            return true;
                        })
                ));
                return super.touchDown(event, x, y, pointer, button);

            }
        });

    }
    private void frmTurn(){
        frmTurn = GUI.createImage(TextureAtlasC.UiAtlas,"frmTurn");
        frmTurn.setPosition(GStage.getWorldWidth()/2,wheelItem.gr.getY()-frmTurn.getHeight()*0.9f,Align.center);
        MainGroup.addActor(frmTurn);
        ////////////// label //////////
        LbTurn = new Label(""+countTurn+" lượt",new Label.LabelStyle(BitmapFontC.Font_Orange,null));
        LbTurn.setFontScale(0.7f);
        LbTurn.setOrigin(Align.center);
        LbTurn.setAlignment(Align.center);
        LbTurn.setPosition(frmTurn.getX()+frmTurn.getWidth()/2,frmTurn.getY()+frmTurn.getHeight()*0.25f,Align.center);
        MainGroup.addActor(LbTurn);
        Image icTitle = GUI.createImage(TextureAtlasC.UiAtlas,"icTitle");
        icTitle.setOrigin(Align.center);
        icTitle.setPosition(GStage.getWorldWidth()/2,frmTurn.getY()-icTitle.getHeight()/2,Align.center);
        MainGroup.addActor(icTitle);
        aniIconTitle(icTitle);
    }

    private void checkconnect(){
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl("https://www.facebook.com/");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

            }

            @Override
            public void failed(Throwable t) {
                Config.checkConnet=false;

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public void setTouch(Touchable touch){
        btnCollections.setTouchable(touch);
//        btnInvite.setTouchable(touch);
        btnHistory.setTouchable(touch);
        btnMall.setTouchable(touch);
        btnMission.setTouchable(touch);
        btnTutorial.setTouchable(touch);
    }
    public void setTurn(int Turn){
        LbTurn.setText(Turn+" lượt");
    }
    private void notice(float x, float y, String notice, Color color){
        Group group = new Group();
        MainGr.addActor(group);
        ////////// label ///////////
        Label lbnotice = new Label(notice,new Label.LabelStyle(BitmapFontC.font_white,color));
        lbnotice.setFontScale(0.6f);
        lbnotice.setAlignment(Align.center);
        lbnotice.setPosition(0,0,Align.center);
        group.addActor(lbnotice);
        group.setPosition(x,y);
        group.addAction(Actions.sequence(
                Actions.moveBy(0,-200,1f),
                GSimpleAction.simpleAction((d, a)->{
                    group.clear();
                    group.remove();
                    return true;
                })

        ));

    }
    private void AwaitData(){
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        MainGr.addActor(blackOverlay);
        ///////// loadding/////////
        Loadding = GUI.createImage(TextureAtlasC.UiAtlas,"loadding");
        Loadding.setOrigin(Align.center);
        Loadding.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        MainGr.addActor(Loadding);
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
    private void noticeAlert(String text){
      if(Config.isShow){
        Group grAlert = new Group();
        GStage.addToLayer(GLayer.top,grAlert);
        GShapeSprite blackSprite = new GShapeSprite();
        GClipGroup clipGroup = new GClipGroup();
        Image frmTxtShow = GUI.createImage(TextureAtlasC.UiAtlas,"frmTxtShow");
        grAlert.addActor(frmTxtShow);
//        blackSprite.createRectangle(true,0,0,GStage.getWorldWidth()*0.9f,60);

//        blackSprite.setColor(0,0,0,0.7f);
//        grAlert.addActor(blackSprite);
        grAlert.setPosition(GStage.getWorldWidth()*0.05f,frmTurn.getY()+frmTurn.getHeight()/3);
        grAlert.setSize(GStage.getWorldWidth()*0.9f,60);
//        grAlert.debug();
        Group gr = new Group();
        grAlert.addActor(gr);
        Label notice = new Label(text,new Label.LabelStyle(BitmapFontC.font_white,null));
        notice.setFontScale(0.7f);
        notice.setOrigin(Align.center);
        notice.setPosition(0,grAlert.getHeight()/2,Align.center);
//      notice.setAlignment(Align.center);
        gr.addActor(notice);
        gr.setPosition(grAlert.getWidth(),0,Align.center);
        clipGroup.setPosition(0,0);
        grAlert.addActor(clipGroup);
        clipGroup.setClipArea(0,0,grAlert.getWidth(),grAlert.getHeight());
        clipGroup.addActor(gr);
        gr.addAction(GSimpleAction.simpleAction((d,a)->{
          gr.setPosition(gr.getX()-Config.veloccity,gr.getY());
          if(gr.getX()+notice.getPrefWidth()<-10){
            // grAlert.setVisible(false);
            grAlert.clear();
            grAlert.remove();
            return true;
          }
          return false;
        }));
      }


    }


    @Override
    public void getInfo(JsonValue data) {
        System.out.println("check user Id: "+data.get("data").get("user_id"));
        GMain.platform.log("MegaID: "+data);
//        if(data.get("code").asInt()==20000){
//            httpGetToken.CheckToken(data.get("data").get("user_id").asString());
//        }else {
//            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi mạng!",Color.RED);
//        }
    }

    @Override
    public void checktoken(JsonValue data) {
        finishLoad();
        if(data.get("status_code").asInt()==2000){
          Config.megaID   = data.get("mega_id").asString();
          Config.token    = data.get("token").asString();
          GMain.platform.log("MegaID2: "+Config.megaID);
          GMain.platform.log("token2: "+Config.token);
          System.out.println("gan token new: "+Config.token);
          AwaitData();
          httpGetToken.GetCountDown();
          wheelItem.UpdateTurn();
          httpGetToken.GetNotice();

        }else {
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi mạng",Color.RED);
        }
    }

  @Override
  public void getNotice(JsonValue data) {
    countHeartbeat=0;
    System.out.println("check get notice: "+data);
    if(data.get("status_code").asInt()==2000){
      if(data.get("visible").asInt()==1){
        noticeAlert(data.get("text").asString());
        MainGroup.addAction(
          GSimpleAction.simpleAction((d,a)->{
            count++;
            if(count==60){
              count=0;
              countHeartbeat++;
              // System.out.println("check heartbeat: "+countHeartbeat);
              if(countHeartbeat==data.get("heartbeat").asInt()){
                httpGetToken.GetNotice();
                return true;
              }
            }
            return false;
          }));
      }else {
        MainGroup.addAction(
                GSimpleAction.simpleAction((d,a)->{
                  count++;
                  if(count==60){
                    count=0;
                    countHeartbeat++;
                    // System.out.println("check heartbeat: "+countHeartbeat);
                    if(countHeartbeat==data.get("heartbeat").asInt()){
                      httpGetToken.GetNotice();
                      return true;
                    }
                  }
                  return false;
                }));
      }
    }else {
    }
  }

  @Override
  public void getCountDown(JsonValue data) {
    if(data.get("status_code").asInt()==2000){
      MainGroup.addAction(
              GSimpleAction.simpleAction((d,a)->{
                countdown++;
                if(countdown==60){
                  grCountDown.clear();
                  countdown=0;
                  long    timeMoment  = TimeUtils.millis();
                  long    timeTarget  = data.get("result").asLong();
                  String  Date         = data.get("day").asString();
                  String[] parts = Date.split("/");
                  String Month = parts[0];
                  String Day   = parts[1];
                  System.out.println("check count down moment: "+timeMoment);
                  System.out.println("check count down: "+timeTarget);
                  if(timeMoment<timeTarget){
                    GShapeSprite darkbg  = new GShapeSprite();
                    darkbg.createRectangle(true,0,0,GStage.getWorldWidth(),GStage.getWorldHeight());
                    darkbg.setColor(0,0,0,0.8f);
                    grCountDown.addActor(darkbg);
                    setTouch(Touchable.disabled);
                    wheelItem.setStatus(true);
                    long timeRest = timeTarget-timeMoment;
                    int day       = (int)(timeRest/86400000);
                    timeRest      = (int)(timeRest%86400000);
                    int h         = (int)(timeRest/3600000);
                    timeRest      = (int)(timeRest%3600000);
                    int minus     = (int)(timeRest/60000);
                    timeRest      = (int)(timeRest%60000);
                    int second    = (int)(timeRest/1000);
                    Label timeCountDown = new Label("còn lại\n "+day+" ngày "+h+":"+minus+":"+second,new Label.LabelStyle(BitmapFontC.FontAlert,null));
                    timeCountDown.setFontScale(0.6f);
                    timeCountDown.setAlignment(Align.center);
                    timeCountDown.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()*0.7f,Align.center);
                    grCountDown.addActor(timeCountDown);
                    Label lbStatus = new Label("Vòng xoay chính thức\nbắt đầu ngày "+Day+"/"+Month,new Label.LabelStyle(BitmapFontC.FontAlert,Color.ORANGE));
                    lbStatus.setFontScale(0.8f);
                    lbStatus.setAlignment(Align.center);
                    lbStatus.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
                    grCountDown.addActor(lbStatus);
                    finishLoad();

                  }else {
                    finishLoad();
                    grCountDown.clear();
                    grCountDown.remove();
                    setTouch(Touchable.enabled);
                    wheelItem.setStatus(false);
                    //AwaitData();
//                    httpGetToken.GetToken();
//                    httpGetToken.CheckToken();
                    return true;
                  }
                }
                return false;
              })
      );
    }else {
      finishLoad();
      notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi kết nối!!",Color.RED);
    }
  }

  @Override
    public void Fail(String s) {
        finishLoad();
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi mạng!!",Color.RED);
    }
}
