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
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.repository.HttpMission;
import com.ss.scenes.GameScene;

public class Mission implements HttpMission.GetMission {
    private HttpMission      httpMission          = new HttpMission();
    private Group            group                = new Group();
    private Image            bg,frm,btnBack,Loadding;
    private GameScene        gameScene;
    public  Group            groupScroll          = new Group();
    private Table            table, tableScroll;
    public  JsonValue        Data;
    public  Array<Image>     arrBtnJoin           = new Array<>();
    public  Array<Image>     arrBtnFinish         = new Array<>();
    public  Array<Integer>   arrType              = new Array<>();
    private Array<JsonValue> arrJsonValue         = new Array<>();
    private GShapeSprite     blackOverlay;
    private WheelItem        wheelItem;
    public  String           ResultQuestions      ="";


    public Mission(GameScene gameScene,WheelItem wheelItem){
      Config.isShow=false;
      SoundEffect.Playmusic(SoundEffect.panel_open);
      this.wheelItem = wheelItem;
      httpMission.setIGetdata(this);
      this.gameScene = gameScene;
      GStage.addToLayer(GLayer.top,group);
      group.setPosition(GStage.getWorldWidth(),0);
      group.addAction(Actions.moveTo(0,0,0.5f, Interpolation.swingOut));
      loadBg();
      back();
      AwaitData();
      httpMission.PostData();



    }
    private void loadBg(){
        bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
        frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
        group.addActor(frm);
        Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmMission");
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
    public void renderListView(){
        groupScroll.setWidth(frm.getWidth());
        groupScroll.setHeight(frm.getHeight()*0.8f);
        groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        ///////// scroll table ////
        table = new Table();
        tableScroll = new Table();
        //System.out.println("in here!!: "+Data);
        int typeTemp=0;
        if(Data !=null && Data.get("result").size!=0){
          int count = Data.get("count").asInt();
         // do{
            for (int i=0;i<Data.get("result").size;i++){
              String      description = Data.get("result").get(i).get("description").asString();
              int         Type        = Data.get("result").get(i).get("type").asInt();
              boolean     Status      = Data.get("result").get(i).get("status").asBoolean();
              int         bonus       = Data.get("result").get(i).get("bonus").asInt();
              JsonValue   arrQestion  = Data.get("result").get(i).get("question");
           //   if(Type==typeTemp){
                tableScroll.row().pad(10);
                Group grT = new Group();
                Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
                tile.setSize(tile.getWidth(),tile.getHeight()*2);
                tile.setOrigin(Align.center);
                grT.addActor(tile);
                ///////////// title 1///////
                Label label = new Label(description,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
                label.setFontScale(0.4f,-0.4f);
                label.setAlignment(Align.left);
                label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
                grT.addActor(label);
              ///////////// title 1///////
              Label label1 = new Label("Phần thưởng: +"+bonus+" lượt",new Label.LabelStyle(BitmapFontC.font_brown,null));
              label1.setFontScale(0.3f,-0.3f);
              label1.setAlignment(Align.left);

              label1.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.5f,Align.left);
              grT.addActor(label1);
              ///////////// title 1///////
              String lb3 = "làm hằng ngày";
              if(Type==6||Type==7){
                lb3="làm một lần!";
              }
              Label label2 = new Label("Trạng thái: "+lb3,new Label.LabelStyle(BitmapFontC.font_brown,null));
              label2.setFontScale(0.3f,-0.3f);
              label2.setAlignment(Align.left);
              label2.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.3f,Align.left);
              grT.addActor(label2);
              if(Type==0||Type==1){
                  label1.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.4f,Align.left);
                  label2.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.2f,Align.left);
              }
                    //////// button join/////////
                Image btn = GUI.createImage(TextureAtlasC.UiAtlas,"btnJoin");
                btn.setScale(0.9f,-0.9f);
                btn.setOrigin(Align.center);
                btn.setPosition(tile.getX()+tile.getWidth()-btn.getWidth(),tile.getY()+tile.getHeight()*0.2f);
                grT.addActor(btn);
                btn.setName(""+Type);
                ////////// button finish/////
                Image btn2 = GUI.createImage(TextureAtlasC.UiAtlas,"btnFinish");
                btn2.setScale(0.9f,-0.9f);
                btn2.setOrigin(Align.center);
                btn2.setPosition(tile.getX()+tile.getWidth()-btn.getWidth(),tile.getY()+tile.getHeight()*0.2f);
                grT.addActor(btn2);
                btn2.setName(""+Type);
                int taget=0;
                if(Type==2||Type==3){
                  if(Type==2)
                    taget=1;
                  if(Type==3)
                    taget=5;
                  ////////// tien trinh ///////
                  Label lb = new Label("tiến độ: "+count+"/"+taget,new Label.LabelStyle(BitmapFontC.font_brown,Color.GREEN));
                  lb.setFontScale(0.3f,-0.3f);
                  lb.setAlignment(Align.left);
                  lb.setPosition(btn2.getX()+btn2.getWidth()*0.2f,btn2.getY()+btn2.getHeight()*1.5f,Align.left);
                  grT.addActor(lb);
                }


              /////////////////////////////
                grT.setSize(tile.getWidth(),tile.getHeight());
                tableScroll.add(grT).center();
                /////// array///////
                arrType.add(Type);
                arrBtnJoin.add(btn);
                arrBtnFinish.add(btn2);
                arrJsonValue.add(arrQestion);
                ////////// setDefault button ////////
                if ((Type==0||Type==1||Type==2||Type==3)){
                  btn.setVisible(false);
                  if(Status==true){
                    btn2.setColor(Color.DARK_GRAY);
                  }
                  if((Type==2 && count<taget)||(Type==3 && count<taget)){
                      btn2.setColor(Color.DARK_GRAY);
                  }
                }else{
                  btn2.setVisible(false);
                }
              }
            //}
            //typeTemp++;

          //}while (typeTemp<Data.get("result").size);

        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);


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
    private void eventJoin(){
        for (Image btn : arrBtnJoin){
            if(btn.isVisible()==true){
                btn.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                      SoundEffect.Play(SoundEffect.click);
                      SoundEffect.Playmusic(SoundEffect.click);
                      System.out.println("button type: "+btn.getName());
                        if(btn.getName().equals("7")){
//                          System.out.println("data question: "+arrJsonValue.get(arrBtnJoin.indexOf(btn,true)));
                          new Surveyquestion(arrJsonValue.get(arrBtnJoin.indexOf(btn,true)),arrType.get(arrBtnJoin.indexOf(btn,true)),Mission.this,wheelItem);
                        }else if(btn.getName().equals("6")) {
                          GShapeSprite darkbg = new GShapeSprite();
                          darkbg.createRectangle(true,0,0,GStage.getWorldWidth(),GStage.getWorldHeight());
                          darkbg.setColor(0,0,0,0.7f);
                          group.addActor(darkbg);
                          Image popup = GUI.createImage(TextureAtlasC.UiAtlas,"popupTutorial");
                          popup.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
                          group.addActor(popup);
                          Image btnShare = GUI.createImage(TextureAtlasC.UiAtlas,"btnShare");
                          btnShare.setPosition(GStage.getWorldWidth()/2,popup.getY()+popup.getHeight()*1.2f,Align.center);
                          group.addActor(btnShare);
                          Image btnExit = GUI.createImage(TextureAtlasC.UiAtlas,"btnBack");
                          btnExit.setPosition(popup.getX()+popup.getWidth(),popup.getY(),Align.center);
                          group.addActor(btnExit);
                          btnExit.addListener(new ClickListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                              SoundEffect.Playmusic(SoundEffect.click);
                              btnExit.remove();
                              darkbg.remove();
                              popup.remove();
                              btnShare.remove();
                              return super.touchDown(event, x, y, pointer, button);
                            }
                          });
                          btnShare.addListener(new ClickListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                              SoundEffect.Playmusic(SoundEffect.click);
                              //Tweens.setTimeout(group,1f,()->{
                                  httpMission.PostJoinMisson(arrType.get(arrBtnJoin.indexOf(btn,true)));
                              //});
                              GMain.shareFb(new IPlatform.OnShareCallback() {
                                @Override
                                public void OnValue(boolean value) {
                                  if(value){
                                    arrBtnFinish.get(arrBtnJoin.indexOf(btn,true)).setVisible(true);
                                    //AwaitData();
                                    //httpMission.PostJoinMisson(arrType.get(arrBtnJoin.indexOf(btn,true)));
                                    btnExit.remove();
                                    darkbg.remove();
                                    popup.remove();
                                    btnShare.remove();
                                  }else {
                                    notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"thất bại",Color.RED);
                                    btnExit.remove();
                                    darkbg.remove();
                                    popup.remove();
                                    btnShare.remove();
                                  }
                                }
                              });
                              return super.touchDown(event, x, y, pointer, button);
                            }
                          });
                        }
                        else {
                          AwaitData();
                          httpMission.PostJoinMisson(arrType.get(arrBtnJoin.indexOf(btn,true)));
                        }
//                        System.out.println("post : "+arrBtnJoin.indexOf(btn,true));
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }
        }
    }
    private void eventFinish(){
        for (Image btn : arrBtnFinish){
          btn.addListener(new ClickListener(){
              @Override
              public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(btn.isVisible()==true && btn.getColor().equals(Color.WHITE)) {
                  SoundEffect.Playmusic(SoundEffect.click);
                  if(btn.getName().equals("7")){
                    if(Config.Question.equals("")){
                      notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"nhận thất bại",Color.RED);
                    }else{
                      AwaitData();
                      httpMission.PostResultQuestion(arrType.get(arrBtnFinish.indexOf(btn, true)),Config.Question);
                      Config.Question = "";
                    }
                  }else {
                    AwaitData();
                    httpMission.PostJoinMisson(arrType.get(arrBtnFinish.indexOf(btn, true)));
                  }

                }
                  return super.touchDown(event, x, y, pointer, button);
              }
          });
        }
    }
    public void setFinishMission7(){
      if(arrBtnFinish!=null){
        for (Image img : arrBtnFinish ){
          if(img.getName().equals("7")){
            img.setVisible(true);
          }
        }
      }

    }
    private void AniGiveGift(int bonus){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight(), GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.8f);
        group.addActor(blackOverlay);

        ///////// label //////////
        Label lb = new Label("chúc mừng bạn hoàn thành\nnhiệm vụ",new Label.LabelStyle(BitmapFontC.font_white,null));
        lb.setFontScale(0.6f);
        lb.setAlignment(Align.center);
        lb.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        group.addActor(lb);

        /////////// button /////////
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
              super.touchUp(event, x, y, pointer, button);
//              SoundEffect.Play(SoundEffect.click);
              SoundEffect.Playmusic(SoundEffect.click);
              blackOverlay.clear();
              blackOverlay.remove();
              lb.clear();
              lb.remove();
              wheelItem.UpdateTurn();
            }
        });
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
    public void updateMission(JsonValue data){
      finishLoad();
      groupScroll.clear();
      arrBtnFinish.clear();
      arrBtnJoin.clear();
      arrType.clear();
      arrJsonValue.clear();
      Data = data;
      renderListView();
      if(Config.Question.equals("")){

      }else {
        System.out.println("here data Question: "+Config.Question);
        setFinishMission7();
      }
      eventJoin();
      eventFinish();
      if(data.get("bonus").asInt()>0){
        AniGiveGift(data.get("bonus").asInt());
      }else {
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"chưa đủ điều kiện",Color.RED);
      }
    }


    @Override
    public void GetMission(JsonValue data) {
        finishLoad();
      System.out.println("check mission data : "+data);
        if(data.get("result").size==0){
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"lỗi kết nối",Color.RED);
        }else {
          Data = data;
          renderListView();
          if(Config.Question.equals("")){

          }else {
            System.out.println("here data Question: "+Config.Question);
            setFinishMission7();
          }
          eventJoin();
          eventFinish();
        }

    }
    @Override
    public void UpdateMission(JsonValue data) {
      finishLoad();
      GMain.platform.log("update mission: "+data);
      updateMission(data);
    }

    @Override
    public void checkInvite(JsonValue data) {
      System.out.println("check invite: "+data);

    }

  @Override
    public void Fail(String s) {
      finishLoad();
      notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);
    }
}
