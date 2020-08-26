package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.data.ResultQuestion;
import com.ss.repository.HttpMission;

public class Surveyquestion implements HttpMission.GetMission {
  private HttpMission              httpMission          = new HttpMission();
  private Group                    group               = new Group();
  private Image                    bg,frm,btnBack,Loadding,btnDone;
  private Group                    groupScroll         = new Group();
  private Table                    table, tableScroll;
  private JsonValue                Data;
  private Array<Array<Image>>      arrBox              = new Array<>();
  private Array<Array<Image>>      arrCheckBox         = new Array<>();
  private Array<Array<Label>>      arrResult           = new Array<>();
  private ScrollPane               Scroll;
  private Array<ResultQuestion>    arrResultQestions   = new Array<>();
  private int                      Type                =0;
  private GShapeSprite             blackOverlay;
  private Mission                  mission;
  private WheelItem                wheelItem;

  Surveyquestion(JsonValue Data,int type, Mission mission, WheelItem wheelItem){
    this.Type = type;
    this.Data = Data;
    this.mission = mission;
    this.wheelItem =wheelItem;
    httpMission.setIGetdata(this);
    GStage.addToLayer(GLayer.top,group);
    loadBg();
    back();
    renderListView();
    eventCheckBox();

  }
  private void loadBg(){
    bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
    bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
    group.addActor(bg);
    frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
    frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
    group.addActor(frm);
    Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmQuestions");
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
//        SoundEffect.Play(SoundEffect.click);
        SoundEffect.Playmusic(SoundEffect.click);
        super.touchUp(event, x, y, pointer, button);
        group.addAction(Actions.sequence(
                Actions.moveTo(-GStage.getWorldWidth(),0,0.5f, Interpolation.swingIn),
                GSimpleAction.simpleAction((d, a)->{
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
    groupScroll.setHeight(frm.getHeight()*0.8f);
    groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
    ///////// scroll table ////
    table = new Table();
    tableScroll = new Table();
    System.out.println("in here!!: "+Data);
    if(Data !=null && Data.size!=0){
      for (int i=0;i<Data.size;i++){
        String            description     = Data.get(i).get("description").asString();
        int               Type            = Data.get(i).get("type").asInt();
        boolean           input           = Data.get(i).get("input").asBoolean();
        JsonValue         arrQ            = Data.get(i).get("option");
        Array<Image>      chilBox         = new Array<>();
        Array<Image>      chilCheckBox    = new Array<>();
        Array<Label>      chilResult      = new Array<>();
        ResultQuestion    resultQuestion  = new ResultQuestion();

        System.out.println("type:"+Type);
        tableScroll.row().pad(10);
        Group grT = new Group();
        Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tileB");
        grT.addActor(tile);
        ///////////// title ///////
        Label label = new Label(description,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
        label.setFontScale(0.4f);
        label.setAlignment(Align.left);
        label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.2f,Align.left);
        grT.addActor(label);
        if(input==true){
          TextField.TextFieldStyle tfs = new TextField.TextFieldStyle();
          tfs.font = BitmapFontC.font_white;
          tfs.fontColor = Color.WHITE;
          tfs.background = new TextureRegionDrawable(TextureAtlasC.UiAtlas.findRegion("input"));
          tfs.background.setLeftWidth(tfs.background.getLeftWidth()+10);
          tfs.cursor = new TextureRegionDrawable(TextureAtlasC.InventoryAtlas.findRegion("cursor"));
          tfs.selection = new TextureRegionDrawable(TextureAtlasC.InventoryAtlas.findRegion("overlay"));
          TextField resultTf = new TextField("", tfs);
          resultTf.setWidth(tile.getWidth()*0.8f);
          resultTf.setHeight(100);
          resultTf.getStyle().font.getData().setScale(0.5f);
          resultTf.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()/2,Align.center);
          grT.addActor(resultTf);
          int finalI = i;

          if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL) {
            resultTf.setDisabled(true);
            resultTf.addListener(new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                //GStage.disableInput(true);
                GMain.openTextField("moi ban nhap ", resultTf.getText(), new IPlatform.OnPopupCallback() {
                  @Override
                  public void OnValue(String value) {
                    resultTf.setText(value);
                    arrResultQestions.get(finalI).setResult(resultTf.getText());
                    //GStage.disableInput(false);
                  }
                });
              }
            });
          }else {
            resultTf.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
              Gdx.input.getTextInput(new Input.TextInputListener() {
                @Override
                public void input(String text) {
                  resultTf.setText(text);
                  arrResultQestions.get(finalI).setResult(text);
                }

                @Override
                public void canceled() {
                }
              }, "", resultTf.getText(), "");
              return super.touchDown(event, x, y, pointer, button);
            }});
          }

        }else {
          float padX  = 0;
          float padY  = 0;
          int shiftR  = 0;
          System.out.println("check option size: "+Data.get(i).get("option").size);
          for (int i1=0; i1<arrQ.size;i1++){
            padX=0;
            padX = (tile.getWidth()*0.65f)*shiftR;
            if(i1%2==0){
              padY=(tile.getHeight()*0.1f)*i1;
              shiftR=0;
              padX=100;
            }
            shiftR++;

            Label des = new Label(arrQ.get(i1).asString(),new Label.LabelStyle(BitmapFontC.font_white,null));
            des.setFontScale(0.4f);
            des.setAlignment(Align.left);
            des.setPosition(tile.getX()+padX,tile.getY()+tile.getHeight()*0.4f+padY);
            grT.addActor(des);
            /////////////// box //////////
            Image box = GUI.createImage(TextureAtlasC.UiAtlas,"box");
            box.setPosition(des.getX()-box.getWidth()*0.6f,des.getY()+20,Align.center);
            grT.addActor(box);
            ////////////////// check box /////////
            Image chekbox = GUI.createImage(TextureAtlasC.UiAtlas,"checkBox");
            chekbox.setOrigin(Align.center);
            chekbox.setPosition(box.getX()+box.getWidth()/2,box.getY()+box.getHeight()/2,Align.center);
            grT.addActor(chekbox);
            chekbox.setVisible(false);
            //////////// array chil//////////
            chilBox.add(box);
            chilCheckBox.add(chekbox);
            chilResult.add(des);


          }
        }

        /////////////////////////////
        grT.setSize(tile.getWidth(),tile.getHeight());
        grT.setScale(1,-1);
        grT.setOrigin(Align.center);
        tableScroll.add(grT).center();
        ////////// array parent ///////
        arrBox.add(chilBox);
        arrCheckBox.add(chilCheckBox);
        arrResult.add(chilResult);
        resultQuestion.setType(Type);
        resultQuestion.setDescription(description);
        resultQuestion.setResult("");
        arrResultQestions.add(resultQuestion);
      }
    }
    Scroll = new ScrollPane(tableScroll);
    table.setFillParent(true);
    table.add(Scroll).fill().expand();
    groupScroll.setScale(1,-1);
    groupScroll.setOrigin(Align.center);
    groupScroll.addActor(table);
    group.addActor(groupScroll);
    //////////// btn send result ///////////
    btnDone = GUI.createImage(TextureAtlasC.UiAtlas,"btnDone");
    btnDone.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()-btnDone.getHeight(),Align.center);
    group.addActor(btnDone);
    btnDone.addListener(new ClickListener(){
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        SoundEffect.Playmusic(SoundEffect.click);
        if(checkResult()==true){
          Config.Question         = PaserArrtoJs();
          mission.setFinishMission7();
          group.clear();
          group.remove();
        }else {
          notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Vui Lòng Điền Đầy Đủ\nThông Tin",Color.RED);
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
    /////////////// scroll toggle /////////
    Image ScrollBarDown = GUI.createImage(TextureAtlasC.InventoryAtlas,"arrow");
    ScrollBarDown.setOrigin(Align.center);
    ScrollBarDown.setPosition(frm.getX()+frm.getWidth()*0.8f,frm.getY()+frm.getHeight()*0.9f);
    group.addActor(ScrollBarDown);
    Image ScrollBarUp = GUI.createImage(TextureAtlasC.InventoryAtlas,"arrow");
    ScrollBarUp.setRotation(180);
    ScrollBarUp.setOrigin(Align.center);
    ScrollBarUp.setPosition(frm.getX()+frm.getWidth()*0.8f,frm.getY()+frm.getHeight()*0.05f);
    group.addActor(ScrollBarUp);
    aniArrow(ScrollBarDown,1);
    aniArrow(ScrollBarUp,-1);
    eventArrow(ScrollBarDown,"cuộn xuống để trả\nlời thêm câu hỏi!!",0.2f);
    eventArrow(ScrollBarUp,"cuộn lên!!",-0.2f);
//    ScrollBar.addAction(Actions.moveBy(0,DMove));
    group.addAction(
            GSimpleAction.simpleAction((d,a)->{
              if(Scroll.getScrollPercentY()==1){
                ScrollBarDown.setVisible(false);
              }else {
                ScrollBarDown.setVisible(true);
              }
              if(Scroll.getScrollPercentY()==0){
                ScrollBarUp.setVisible(false);
              }else {
                ScrollBarUp.setVisible(true);
              }
//
              return false;
            })
    );
  }
  private void aniArrow(Image btn ,int Wind){
    btn.addAction(Actions.sequence(
            Actions.moveBy(0,Wind*5,0.3f),
            Actions.moveBy(0,Wind*-5,0.3f),
            GSimpleAction.simpleAction((d,a)->{
              aniArrow(btn,Wind);
              return true;
            })

    ));
  }
  private void eventArrow(Image btn,String text, float moveY){
   btn.addListener(new ClickListener(){
     @Override
     public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//       notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,text,Color.RED);
       Scroll.setScrollPercentY(Scroll.getScrollPercentY()+moveY);
       return super.touchDown(event, x, y, pointer, button);
     }
   });
  }

  private String PaserArrtoJs(){
    String s = "[";
    String sEnd = "\"},";
    for (int i=0;i<arrResultQestions.size;i++){
      if(i==arrResultQestions.size-1){
        sEnd = "\"}";
      }
      s+="{\"type\":"+arrResultQestions.get(i).getType()+","+
           "\"description\":"+"\""+arrResultQestions.get(i).getDescription()+"\""+","+
           "\"result\":"+"\""+arrResultQestions.get(i).getResult()+sEnd;

    }
    s+="]";
    return s;
  }
  private boolean checkResult(){
    for (int i=0;i<arrResultQestions.size;i++){
      if(arrResultQestions.get(i).getResult().equals("")){
        return false;
      }
    }
    return true;
  }
  private void eventCheckBox(){
    for (int i=0;i<arrBox.size;i++){
      for (int j=0;j<arrBox.get(i).size;j++){
        int finalI = i;
        int finalJ = j;
        arrBox.get(i).get(j).addListener(new ClickListener(){
          @Override
          public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            setDefault(finalI,finalJ);
            return super.touchDown(event, x, y, pointer, button);
          }
        });
      }
    }
  }
  private void setDefault(int ii, int jj){
    for (int i=0;i<arrCheckBox.size;i++){
      for (int j=0;j<arrCheckBox.get(i).size;j++){
        if(ii==i&&jj==j){
          arrCheckBox.get(i).get(j).setVisible(true);
          arrResultQestions.get(i).setResult(arrResult.get(i).get(j).getText().toString());
        }else{
          if(i==ii){
            arrCheckBox.get(i).get(j).setVisible(false);
          }
        }
      }
    }
  }
  private void notice(float x, float y,String notice,Color color){
    Group gr = new Group();
    group.addActor(gr);
    ////////// label ///////////
    Label lbnotice = new Label(notice,new Label.LabelStyle(BitmapFontC.font_white,color));
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
  public void GetMission(JsonValue data) {

  }

  @Override
  public void UpdateMission(JsonValue data) {
    System.out.println("check result:");
    finishLoad();
    if(data.get("status_code").asInt()==2000){
      mission.updateMission(data);
      group.clear();
      group.remove();
    }else {
      notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);
    }

  }

  @Override
  public void checkInvite(JsonValue data) {

  }

  @Override
  public void Fail(String s) {
    finishLoad();
    notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);

  }
}
