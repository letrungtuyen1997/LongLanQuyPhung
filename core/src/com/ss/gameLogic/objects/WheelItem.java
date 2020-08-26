package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GArcMoveToAction;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.exSprite.particle.GParticleSprite;
import com.ss.core.exSprite.particle.GParticleSystem;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.repository.HttpWheel;
import com.ss.scenes.GameScene;

public class WheelItem implements HttpWheel.GetData {
    public  Group               gr                  = new Group();
    public  boolean             checkWhell          = false;
    private JsonValue           Data;
    private HttpWheel           httpWheel           = new HttpWheel() ;
    private boolean             lock                =  false;
    private int                 vloc                = 0;
    private Image               btnWheel,Loadding;
    private GameScene           gameScene;
    private GShapeSprite        blackOverlay;
    private Group               grEffect            = new Group();
    private Group               group               = new Group();
    private Group               grouplb               = new Group();
    public WheelItem(GameScene gameScene){
      this.gameScene = gameScene;
//      UpdateTurn();
      httpWheel.setIGetdata(this);
      GStage.addToLayer(GLayer.ui,gr);
      GStage.addToLayer(GLayer.top,grEffect);
      WheelItem();
      GStage.addToLayer(GLayer.top,group);
      GStage.addToLayer(GLayer.top,grouplb);
    }
    public void UpdateTurn(){
        AwaitData();
        httpWheel.GetTurnBy(Config.megaID);

    }
    private void WheelItem(){
        FileHandle js = Gdx.files.internal("data/wheel.json");
        String jsonStr = js.readString();
        String jv2 = GMain.platform.GetConfigStringValue("skills",jsonStr);
        JsonReader json = new JsonReader();
        JsonValue jv;
        try {
            jv = json.parse(jv2);
            System.out.println("log:"+jv.get("region").asString());
        }catch (Exception e){
            jv = json.parse(jsonStr);
        }
        for (int i=0;i<jv.size-1;i++)
        {
          JsonValue jvMin = jv.get(i);

          for (int j=i+1;j<jv.size;j++){
            if(jvMin.get("pos").asInt()>jv.get(j).get("pos").asInt()){

              String    region = jvMin.get("region").asString();
              String    id     = jvMin.get("id").asString();
              String    quan   = jvMin.get("quantity").asString();
              String    per    = jvMin.get("percent").asString();
              String    pos    = jvMin.get("pos").asString();

              jv.get(i).get("region").set(jv.get(j).get("region").asString());
              jv.get(i).get("id").set(jv.get(j).get("id").asString());
              jv.get(i).get("quantity").set(jv.get(j).get("quantity").asString());
              jv.get(i).get("percent").set(jv.get(j).get("percent").asString());
              jv.get(i).get("pos").set(jv.get(j).get("pos").asString());

              jv.get(j).get("region").set(region);
              jv.get(j).get("id").set(id);
              jv.get(j).get("quantity").set(quan);
              jv.get(j).get("percent").set(per);
              jv.get(j).get("pos").set(pos);
            }
          }
        }
//      System.out.println("check js set: "+jv);
//        gr.setVisible(false);
        gr.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()*0.6f);
//        gr.setScale(0);

        ////////////// button quay//////////
        btnWheel = GUI.createImage(TextureAtlasC.Wheel,"btnQuay");
        btnWheel.setOrigin(Align.center);
        btnWheel.setPosition(0,GStage.getWorldHeight()*0.32f,Align.center);
        gr.addActor(btnWheel);


        ///////// Wheel ///////////
        if(Config.checkWheel==false){
            Config.checkWheel=true;
            Wheel.wheelTex = TextureAtlasC.Wheel.findRegion("wheel");
            Wheel.wheelTex.flip(false,true);
            Wheel.wheelTick = Gdx.audio.newSound(Gdx.files.internal("sound/wheel_sound.mp3"));
//            Wheel.wheelTick = GAssetsManager.getSound("wheel_sound.mp3");
            Wheel.pointer = TextureAtlasC.WhellAtlas.findRegion("pointer");
            Wheel.pointer.flip(false,true);
            Wheel.wheelDot = TextureAtlasC.WhellAtlas.findRegion("dot");
            Wheel.lightDot = TextureAtlasC.WhellAtlas.findRegion("lightdot");
            Wheel.wheelText = BitmapFontC.FontAlert;
            Wheel.TEXT_SPACE = 5f;
            Wheel.PARTITION = 10;
            Wheel.ITEM_SCALE= -1f;
            Wheel.ITEM_FLOAT= 0.6f;
            for (JsonValue ob: jv){
                Wheel.wheelItems.add(Wheel.WheelItem.newInst(TextureAtlasC.Wheel.findRegion(
                        ob.get("region").asString()),
                        ob.get("id").asInt(),
                        ob.get("quantity").asInt(),
                        ""+ob.get("quantity").asInt(),
                        ob.get("percent").asInt()));
            }
            Wheel.inst().setWheelListener(new Wheel.EventListener() {
                @Override
                public boolean start() {
                    return false;
                }

                @Override
                public void end(Wheel.WheelItem item) {
                  System.out.println("done: "+item.id);
                  SoundEffect.Playmusic(SoundEffect.unlock2);
                  AniGiveGift(item.id);
                }

                @Override
                public void error(String msg) {
                    System.out.println("loi!!!");
                    btnWheel.setTouchable(Touchable.enabled);
                    gameScene.setTouch(Touchable.enabled);
                    checkWhell=false;

                }
            });
            Wheel.inst().init();
            Wheel.inst().setPosition(-Wheel.wheelTex.getRegionWidth()/2,-Wheel.wheelTex.getRegionWidth()/2);
        }
        btnWheel.setOrigin(Align.center);
        btnWheel.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
              SoundEffect.Playmusic(SoundEffect.click);

//              SoundEffect.Play(SoundEffect.click);
//              gameScene.setTouch(Touchable.disabled);
                btnWheel.setTouchable(Touchable.disabled);
                btnWheel.addAction(Actions.sequence(
                        Actions.scaleTo(0.9f,0.9f,0.1f),
                        Actions.scaleTo(1f,1f,0.1f),
                        GSimpleAction.simpleAction((d,a)->{
                            Wheel.inst().lock=false;
                            System.out.println("click");
                            if(gameScene.countTurn>0){
                                Wheel.inst().rollWheel(5000,0);
                                httpWheel.GetItem(Config.megaID);
                                checkWhell=true;
                            }else {
                                emptyTurn();
                            }
                            return true;
                        })
                ));

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        ///////// animation btn wheel //////
        aniBtnWheel(btnWheel);
//        Wheel.wheelItems.add(Wheel.WheelItem.newInst(TextureAtlasC.Fottergame.findRegion("SoVang"), 0, 2, "800", 1000));
        //////// listener ////////

        gr.addActor(Wheel.inst());
        /////// pointer/////
        Image pointer = GUI.createImage(TextureAtlasC.Wheel,"kim");
        pointer.setPosition(0,0,Align.center);
        gr.addActor(pointer);
    }
    private void loadFail(){
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        gr.addActor(blackOverlay);
        Label lb = new Label("lỗi mạng!!",new Label.LabelStyle(BitmapFontC.Font_Orange,null));
        lb.setPosition(0,0,Align.center);
        gr.addActor(lb);
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                blackOverlay.remove();
                lb.remove();

            }
        });
    }
    private void emptyTurn(){
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth()/2,-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        gr.addActor(blackOverlay);
        Label lb = new Label("Hết lượt hãy làm Nhiệm Vụ\nđể thêm lượt",new Label.LabelStyle(BitmapFontC.font_white,null));
        lb.setFontScale(0.7f);
        lb.setOrigin(Align.center);
        lb.setAlignment(Align.center);
        lb.setPosition(0,0,Align.center);
        gr.addActor(lb);
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
              super.touchUp(event, x, y, pointer, button);
//              SoundEffect.Play(SoundEffect.click);
              SoundEffect.Playmusic(SoundEffect.click);
              blackOverlay.remove();
                lb.remove();
                Wheel.inst().lock=true;
                gameScene.setTouch(Touchable.enabled);
                btnWheel.setTouchable(Touchable.enabled);
                new Mission(gameScene,WheelItem.this);
            }
        });
    }
    private void aniBtnWheel(Image btn){
        btn.addAction(Actions.sequence(
                Actions.scaleTo(0.8f,0.8f,0.5f, Interpolation.swing),
                Actions.scaleTo(1f,1f,0.5f),
                Actions.delay(1),GSimpleAction.simpleAction((d,a)->{
                    aniBtnWheel(btn);
                    return true;
                })
        ));
    }
  private void AniGiveGift(int type){
//    SoundEffect.Play(SoundEffect.unlock);
    final GShapeSprite blackOverlay = new GShapeSprite();
    blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
    blackOverlay.setColor(0,0,0,0.9f);
    grEffect.addActor(blackOverlay);
    //////// effect light yellow ///////
    if(Config.remoteEffect){
      GParticleSprite ef =  GParticleSystem.getGParticleSystem("lightYellow").create(grEffect,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2+100);
      ef.getEffect().scaleEffect(2,3,1);
    }
    ////////// icon////////
    Image icon = GUI.createImage(TextureAtlasC.Wheel,"rw"+type);
    icon.setSize(icon.getWidth()*2,icon.getHeight()*2);
    icon.setOrigin(Align.center);
    icon.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
    grEffect.addActor(icon);
    icon.setScale(0);

    if(Config.remoteEffect){
      GParticleSprite ef2 =  GParticleSystem.getGParticleSystem("tree").create(grEffect,GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
      ef2.getEffect().flipY();
      ef2.getEffect().scaleEffect(2);
    }

    //////// efect take gift ///////
    if(Config.remoteEffect){
        GParticleSprite ef = GParticleSystem.getGParticleSystem("Titlecongra").create(grEffect,GStage.getWorldWidth()/2,icon.getY()-150);
        ef.getEffect().scaleEffect(3);
    }else {
      ///////// label //////////
      Label lb = new Label("Chúc Mừng Bạn",new Label.LabelStyle(BitmapFontC.Font_Orange,null));
      lb.setPosition(GStage.getWorldWidth()/2,icon.getY()-200,Align.center);
      grEffect.addActor(lb);
    }


    /////////// button /////////
    Image btnGive = GUI.createImage(TextureAtlasC.UiAtlas,"btnTake");
    btnGive.setOrigin(Align.center);
    btnGive.setPosition(GStage.getWorldWidth()/2,icon.getY()+icon.getHeight()*1.5f,Align.center);
    grEffect.addActor(btnGive);
    btnGive.getColor().a = 0;
    icon.addAction(Actions.sequence(
            Actions.scaleTo(1,1,1f,Interpolation.swingOut),
            GSimpleAction.simpleAction((d,a)->{
              btnGive.addAction(Actions.sequence(
                      Actions.alpha(1,0.5f),
                      GSimpleAction.simpleAction((d1,a1)->{
                        btnGive.addListener(new ClickListener(){
                          @Override
                          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                            SoundEffect.Play(SoundEffect.click);
                            SoundEffect.Playmusic(SoundEffect.click);
//                            gameScene.setTouch(Touchable.enabled);
                            btnWheel.setTouchable(Touchable.enabled);
                            checkWhell=false;
                            btnGive.setVisible(false);
                            AniTakeGift(icon);
                            return super.touchDown(event, x, y, pointer, button);
                          }
                        });
                        return true;
                      })
              ));
              return true;
            })

    ));
  }
  private void AniTakeGift(Image ic){
    ic.addAction(Actions.sequence(
            Actions.scaleTo(1.5f,1.5f,0.2f,Interpolation.swingOut),
            Actions.parallel(
                    Actions.moveTo(-100,GStage.getWorldHeight()-100,0.5f,Interpolation.swingIn),
                    Actions.scaleTo(0f,0f,0.5f)
            ),
            GSimpleAction.simpleAction((d,a)->{
              grEffect.clear();
              return true;
            })
    ));
  }

  private void notice(float x, float y,String notice,Color color){
    Group group = new Group();
    gr.addActor(group);
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
  public void setStatus(boolean set){
      if(set==true){
        btnWheel.setVisible(false);

      }else {
        btnWheel.setVisible(true);
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
    public void FinishGetItemWheel(JsonValue data) {
        System.out.println("check log: "+data);
        System.out.println("check log2: "+data.get("type").asInt());
            Wheel.inst().stopWheel((data.get("type").asInt()));
            gameScene.countTurn = data.get("turn").asInt();
            this.gameScene.setTurn(gameScene.countTurn);

    }

    @Override
    public void FinishGetTurn(JsonValue data) {
      System.out.println("check log test: "+data);
      finishLoad();
      gameScene.countTurn = data.get("result").asInt();
      this.gameScene.setTurn((data.get("result").asInt()));
    }
    @Override
    public void Fail(String s) {
      finishLoad();
      loadFail();
      Wheel.inst().lock=true;
      Wheel.inst().stopWheel(-1);
    }


}
