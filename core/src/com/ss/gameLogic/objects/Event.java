package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.ss.repository.HttpEvent;
import com.ss.scenes.GameScene;
import com.ss.utils.Utils;

public class Event implements HttpEvent.GetEvent {
  private HttpEvent        httpEvent            = new HttpEvent();
  private Group            group                = new Group();
  private Group            groupScroll          = new Group();
  private Table            table, tableScroll;
  private Image            bg,frm,btnBack,Loadding;
  private GShapeSprite     blackOverlay;
  private Array<Image>     arrBtn               = new Array<>();
  private Array<Integer>   arrTypeEvent         = new Array<>();
  private Array<Integer>   arrType              = new Array<>();
  private Inventory        inventory;
  private GameScene        gameScene;


  Event(Inventory inventory,GameScene gameScene){
    this.gameScene = gameScene;
    this.inventory = inventory;
    httpEvent.setIGetdata(this);
    GStage.addToLayer(GLayer.top,group);
    group.setPosition(GStage.getWorldWidth(),0);
    group.addAction(Actions.moveTo(0,0,0.5f, Interpolation.swingOut));
    loadBg();
    back();
    AwaitData();
    httpEvent.GetEvent();
  }
  private void loadBg(){
    bg = GUI.createImage(TextureAtlasC.UiAtlas,"bg");
    bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
    group.addActor(bg);
    frm = GUI.createImage(TextureAtlasC.UiAtlas,"bg2");
    frm.setPosition(bg.getX()+bg.getWidth()/2,bg.getY()+bg.getHeight()/2, Align.center);
    group.addActor(frm);
    Image Tile = GUI.createImage(TextureAtlasC.UiAtlas,"frmEvent");
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
                  group.clear();
                  group.remove();
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
    if(Data.get("status_code").asInt() ==2000){
      for (int i=0;i<Data.get("lsEventSwap").size;i++){
        int         type         = Data.get("lsEventSwap").get(i).get("type").asInt();
        String      name         = Data.get("lsEventSwap").get(i).get("name").asString();
        int         condi_swap   = Data.get("lsEventSwap").get(i).get("condi_swap").asInt();
        int         total_amount = Data.get("lsEventSwap").get(i).get("total_amount").asInt();
        int         bonus        = Data.get("lsEventSwap").get(i).get("bonus").asInt();
        tableScroll.row().pad(10);
        Group grT = new Group();
        Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
        grT.addActor(tile);
        ///////////// title ///////
        Label label = new Label("đổi "+condi_swap+" "+name+" để được "+bonus+" lượt quay",new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
        label.setFontScale(0.35f,-0.35f);
        label.setAlignment(Align.left);
        label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
        grT.addActor(label);
        /////////// progress  ///////////
        Label progress = new Label("tiến độ: "+total_amount+"/"+condi_swap,new Label.LabelStyle(BitmapFontC.Font_brown_thin,Color.RED));
        progress.setFontScale(0.35f,-0.35f);
        progress.setAlignment(Align.left);
        progress.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.2f,Align.left);
        grT.addActor(progress);
        /////////// button nhan //////////
        Image btn =GUI.createImage(TextureAtlasC.UiAtlas,"btnTake");
        btn.setScale(1,-1);
        btn.setSize(btn.getWidth()*0.7f,btn.getHeight()*0.7f);
        btn.setOrigin(Align.center);
        btn.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()*0.24f,Align.center);
        grT.addActor(btn);
        if(total_amount<condi_swap){
          btn.setColor(Color.DARK_GRAY);
        }
        //////////// add row////////
        //if(total_amount>0){
          grT.setSize(tile.getWidth(),tile.getHeight());
          tableScroll.add(grT).center();
        //}
        ////////// array //////////
        arrBtn.add(btn);
        arrTypeEvent.add(0);
        arrType.add(type);
      }
      for (int i=0;i<Data.get("lsEventMerge").size;i++){
        int         type         = Data.get("lsEventMerge").get(i).get("type").asInt();
        String      name         = Data.get("lsEventMerge").get(i).get("name").asString();
        int         condi_merge  = Data.get("lsEventMerge").get(i).get("condi_merge").asInt();
        int         total_amount = Data.get("lsEventMerge").get(i).get("merged").asInt();
        int         bonus        = Data.get("lsEventMerge").get(i).get("bonus").asInt();
        tableScroll.row().pad(10);
        Group grT = new Group();
        Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
        grT.addActor(tile);
        ///////////// title ///////
        Label label = new Label("ghép "+condi_merge+" "+name+" để được "+bonus+" lượt quay",new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
        label.setFontScale(0.35f,-0.35f);
        label.setAlignment(Align.left);
        label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
        grT.addActor(label);
        /////////// progress  ///////////
        Label progress = new Label("tiến độ: "+total_amount+"/"+condi_merge,new Label.LabelStyle(BitmapFontC.Font_brown_thin,Color.RED));
        progress.setFontScale(0.35f,-0.35f);
        progress.setAlignment(Align.left);
        progress.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.2f,Align.left);
        grT.addActor(progress);
        /////////// button nhan //////////
        Image btn =GUI.createImage(TextureAtlasC.UiAtlas,"btnTake");
        btn.setScale(1,-1);
        btn.setSize(btn.getWidth()*0.7f,btn.getHeight()*0.7f);
        btn.setOrigin(Align.center);
        btn.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()*0.24f,Align.center);
        grT.addActor(btn);
        if(total_amount<condi_merge){
          btn.setColor(Color.DARK_GRAY);
        }
        //////////// add row////////
        //if(total_amount>0){
          grT.setSize(tile.getWidth(),tile.getHeight());
          tableScroll.add(grT).center();
        //}

        ////////// array //////////
        arrBtn.add(btn);
        arrTypeEvent.add(1);
        arrType.add(type);
      }
      for (int i=0;i<Data.get("lsEventDonate").size;i++){
        int         type           = Data.get("lsEventDonate").get(i).get("type").asInt();
        String      name           = Data.get("lsEventDonate").get(i).get("name").asString();
        int         condi_donate   = Data.get("lsEventDonate").get(i).get("condi_donate").asInt();
        int         total_amount   = Data.get("lsEventDonate").get(i).get("donated").asInt();
        int         bonus          = Data.get("lsEventDonate").get(i).get("bonus").asInt();
        tableScroll.row().pad(10);
        Group grT = new Group();
        Image tile = GUI.createImage(TextureAtlasC.UiAtlas,"tile");
        grT.addActor(tile);
        ///////////// title ///////
        Label label = new Label("Tặng "+condi_donate+" "+name+" để được "+bonus+" lượt quay",new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
        label.setFontScale(0.35f,-0.35f);
        label.setAlignment(Align.left);
        label.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.7f,Align.left);
        grT.addActor(label);
        /////////// progress  ///////////
        Label progress = new Label("tiến độ: "+total_amount+"/"+condi_donate,new Label.LabelStyle(BitmapFontC.Font_brown_thin,Color.RED));
        progress.setFontScale(0.35f,-0.35f);
        progress.setAlignment(Align.left);
        progress.setPosition(tile.getX()+20,tile.getY()+tile.getHeight()*0.2f,Align.left);
        grT.addActor(progress);
        /////////// button nhan //////////
        Image btn =GUI.createImage(TextureAtlasC.UiAtlas,"btnTake");
        btn.setScale(1,-1);
        btn.setSize(btn.getWidth()*0.7f,btn.getHeight()*0.7f);
        btn.setOrigin(Align.center);
        btn.setPosition(tile.getX()+tile.getWidth()/2,tile.getY()+tile.getHeight()*0.24f,Align.center);
        grT.addActor(btn);
        if(total_amount<condi_donate){
          btn.setColor(Color.DARK_GRAY);
        }
        //////////// add row////////
        //if(total_amount>0){
          grT.setSize(tile.getWidth(),tile.getHeight());
          tableScroll.add(grT).center();
        //}

        ////////// array //////////
        arrBtn.add(btn);
        arrTypeEvent.add(2);
        arrType.add(type);
      }

    }else {
      Label lb = new Label("sự kiện chưa diễn ra!!",new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
      lb.setFontScale(0.7f);
      lb.setAlignment(Align.center);
      lb.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
      group.addActor(lb);

    }
    ScrollPane Scroll = new ScrollPane(tableScroll);
    table.setFillParent(true);
    table.add(Scroll).fill().expand();
    groupScroll.setScale(1,-1);
    groupScroll.setOrigin(Align.center);
    groupScroll.addActor(table);
    group.addActor(groupScroll);
    eventButton();
  }
  private void eventButton(){
    for (Image img: arrBtn){
      img.addListener(new ClickListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
          int typeEvent = arrTypeEvent.get(arrBtn.indexOf(img,true));
          int type      = arrType.get(arrBtn.indexOf(img,true));

          System.out.println("check type: "+ arrType.get(arrBtn.indexOf(img,true))+"    type event: "+arrTypeEvent.get(arrBtn.indexOf(img,true)));
          if(img.getColor().equals(Color.WHITE)){
            AwaitData();
            httpEvent.GetEvent2(typeEvent,type);
          }else {
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"chưa đủ điều kiện",Color.RED);
          }
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
  private void notice(float x, float y, String notice, Color color){
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
  public void getEvent(JsonValue data) {
    finishLoad();
    System.out.println("check data get event: "+data);
    renderListView(data);

  }

  @Override
  public void getEvent2(JsonValue data) {
    finishLoad();
    groupScroll.clear();
    renderListView(data);
    inventory.Data = data;
    inventory.updateInventory(data);
    notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"nhận thành công",Color.GREEN);
    gameScene.countTurn = data.get("total_turn").asInt();
    this.gameScene.setTurn((data.get("total_turn").asInt()));

  }

  @Override
  public void Fail(String s) {

  }
}
