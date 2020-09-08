package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.repository.HttpGift;

public class Gift implements HttpGift.PostGift {
    private GShapeSprite   blackOverlay;
    private Group          gr                   = new Group();
    private Group          ChilGr               = new Group();
    private int            maxAmount            = 6;
    private int            countAmount          = 0;
    private Label          lbCount;
    private HttpGift       httpGift             = new HttpGift();
    private Inventory      inventory;
    private JsonValue      Data;
    private Array<Image>   arrIconCard          = new Array<>();
    private Array<Image>   arrOverlap           = new Array<>();
    private Array<Label>   arrLabelCard         = new Array<>();
    private int            Type                 =-1;
    private String         reciMegaID           ="";
    private Image          Loadding;
    private TextField      megaIDTf;


    Gift(JsonValue Data,Inventory inventory){
        httpGift.setIGetdata(this);
        this.inventory = inventory;
        this.Data      = Data;
        ///////// black over /////////
        GStage.addToLayer(GLayer.top,gr);
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.9f);
        gr.addActor(blackOverlay);
        ///////// list item /////////
        ///////// manh /////////////
        for (int i = 1; i < 5; i++) {
            Image icon2 = GUI.createImage(TextureAtlasC.InventoryAtlas, "icPiece" + i);
            icon2.setPosition(GStage.getWorldWidth() / 2 - icon2.getWidth() * 3.3f + (icon2.getWidth()*1.1f) * i, GStage.getWorldHeight()*0.1f);
            gr.addActor(icon2);
            //////// label //////
            Label lbAmount = new Label("0", new Label.LabelStyle(BitmapFontC.Font_brown_thin, null));
            lbAmount.setFontScale(0.5f);
            lbAmount.setOrigin(Align.center);
            lbAmount.setAlignment(Align.center);
            lbAmount.setPosition(icon2.getX() + icon2.getWidth() * 0.18f, icon2.getY() + icon2.getHeight() * 0.83f, Align.center);
            gr.addActor(lbAmount);
            //////// img overlap /////////
            Image overlap = GUI.createImage(TextureAtlasC.InventoryAtlas,"overlap");
            overlap.setOrigin(Align.center);
            overlap.setPosition(icon2.getX()+icon2.getWidth()/2,icon2.getY()+icon2.getHeight()/2,Align.center);
            gr.addActor(overlap);
            overlap.setVisible(false);
            arrOverlap.add(overlap);
            /////// array///////
            arrIconCard.add(icon2);
            arrLabelCard.add(lbAmount);
        }
        ///////// the /////////////
        for (int i = 1; i < 5; i++) {
            ///////// icon///////
            Image icon = GUI.createImage(TextureAtlasC.InventoryAtlas, "icCard" + i);
            icon.setPosition(GStage.getWorldWidth() / 2 - icon.getWidth() * 3.3f + (icon.getWidth()*1.1f) * i, GStage.getWorldHeight()*0.3f);
            gr.addActor(icon);
            //////// label //////
            Label lb = new Label("0", new Label.LabelStyle(BitmapFontC.font_white, null));
            lb.setFontScale(0.4f);
            lb.setOrigin(Align.center);
            lb.setAlignment(Align.center);
            lb.setPosition(icon.getX() + icon.getWidth() * 0.2f, icon.getY() + icon.getHeight() * 0.8f, Align.center);
            gr.addActor(lb);
            //////// img overlap /////////
            Image overlap = GUI.createImage(TextureAtlasC.InventoryAtlas,"overlap");
            overlap.setOrigin(Align.center);
            overlap.setPosition(icon.getX()+icon.getWidth()/2,icon.getY()+icon.getHeight()/2,Align.center);
            gr.addActor(overlap);
            overlap.setVisible(false);
            arrOverlap.add(overlap);

            /////// array///////
            arrIconCard.add(icon);
            arrLabelCard.add(lb);
        }


        //////////// style  //////////
        TextField.TextFieldStyle tfs = new TextField.TextFieldStyle();
        tfs.font = BitmapFontC.Font_brown_thin;
        tfs.fontColor = Color.WHITE;
        tfs.background = new TextureRegionDrawable(TextureAtlasC.InventoryAtlas.findRegion("textbox"));
        tfs.background.setLeftWidth(tfs.background.getLeftWidth()+10);
        tfs.cursor = new TextureRegionDrawable(TextureAtlasC.InventoryAtlas.findRegion("cursor"));
        tfs.selection = new TextureRegionDrawable(TextureAtlasC.InventoryAtlas.findRegion("overlay"));
        /////////// textfield megaID/////////
        Label lb = new Label("Nhập mã MEGA1 của bạn bè",new Label.LabelStyle(BitmapFontC.font_white,null));
        lb.setFontScale(0.7f);
        lb.setAlignment(Align.center);
        lb.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        gr.addActor(lb);
        Label lb2 = new Label("Ví dụ MEGA19999999999",new Label.LabelStyle(BitmapFontC.font_white,null));
        lb2.setFontScale(0.5f);
        lb2.setAlignment(Align.center);
        lb2.setPosition(GStage.getWorldWidth()/2,lb.getY()+lb.getPrefHeight()*2.5f,Align.center);
        gr.addActor(lb2);
        megaIDTf = new TextField("", tfs);
        megaIDTf.setWidth(400);
        megaIDTf.setHeight(100);
        megaIDTf.getStyle().font.getData().setScale(0.5f);
        megaIDTf.setPosition(0,0,Align.center);
        ChilGr.addActor(megaIDTf);
        ChilGr.setSize(megaIDTf.getPrefWidth(),megaIDTf.getPrefHeight());
        ChilGr.setOrigin(Align.center);
        ChilGr.setPosition(GStage.getWorldWidth()/2,lb2.getY()+lb2.getPrefHeight()*5.5f);
        gr.addActor(ChilGr);

        if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL) {
            megaIDTf.setDisabled(true);
            megaIDTf.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                megaIDTf.setText("");
                super.clicked(event, x, y);
                //GStage.disableInput(true);
                GMain.openTextField("", megaIDTf.getText(), new IPlatform.OnPopupCallback() {
                    @Override
                    public void OnValue(String value) {
                        megaIDTf.setText(value);
                        //GStage.disableInput(false);
                    }
                });


                }
            });
        }else {
            TextField.OnscreenKeyboard keyboard = visible -> {
            //Gdx.input.setOnscreenKeyboardVisible(visible);
            //childGroup2.addAction(Actions.moveTo(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2-300, 0.3f));
            Gdx.input.getTextInput(new Input.TextInputListener() {
                @Override
                public void input(String text) {
                    megaIDTf.setText(text);
                }

                @Override
                public void canceled() {
                    //childGroup2.addAction(Actions.moveTo(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, 0.3f));

                }
            }, "", megaIDTf.getText(), "");

        };
        megaIDTf.setOnscreenKeyboard(keyboard);
        }

//
        //////// slectbox amount ///////////f
        Label lbAmount = new Label("Chọn số lượng",new Label.LabelStyle(BitmapFontC.font_white,null));
        lbAmount.setFontScale(0.7f);
        lbAmount.setAlignment(Align.center);
        lbAmount.setPosition(GStage.getWorldWidth()/2,ChilGr.getY()+ChilGr.getHeight()*1.5f,Align.center);
        gr.addActor(lbAmount);
        Image frm = GUI.createImage(TextureAtlasC.InventoryAtlas,"textbox");
        frm.setPosition(GStage.getWorldWidth()/2,lbAmount.getY()+lbAmount.getPrefHeight()*3.5f,Align.center);
        gr.addActor(frm);
        ////////// lb count amount ///////
        lbCount = new Label(""+countAmount,new Label.LabelStyle(BitmapFontC.Font_brown_thin,null));
        lbCount.setFontScale(0.6f);
        lbCount.setOrigin(Align.center);
        lbCount.setAlignment(Align.center);
        lbCount.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.5f,Align.center);
        gr.addActor(lbCount);
        //////// btn desc amount///////
        Image btnDesc = GUI.createImage(TextureAtlasC.InventoryAtlas,"icCountDown");
        btnDesc.setPosition(frm.getX()+btnDesc.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        gr.addActor(btnDesc);
        //////// btn inc amount///////
        Image btnInc = GUI.createImage(TextureAtlasC.InventoryAtlas,"icCountUp");
        btnInc.setPosition(frm.getX()+frm.getWidth()-btnInc.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        gr.addActor(btnInc);
        ///////// event desc inc amount /////////
        eventDescInc(btnDesc,"Desc");
        eventDescInc(btnInc,"Inc");

        //////////event click and check conditions /////////////
        for (Image img :arrIconCard){
            img.addListener(new ClickListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
//                    SoundEffect.Play(SoundEffect.click);
                    SoundEffect.Playmusic(SoundEffect.click);
                    if(img.getColor().equals(Color.WHITE)){
                        setChoice(arrIconCard.indexOf(img,true));
                        countAmount=0;
                        lbCount.setText(""+countAmount);

                    }
                    else
                        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"bạn chưa có mảnh nào!!",Color.RED);
                }
            });


        }

        ////////// close tab /////////
        Image btnExit = GUI.createImage(TextureAtlasC.UiAtlas,"btnBack");
        btnExit.setPosition(GStage.getWorldWidth()-btnExit.getWidth(),btnExit.getHeight(),Align.center);
        gr.addActor(btnExit);
        btnExit.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                gr.clear();
                gr.remove();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        //////// update //////
        updateList();
        /////// button give ///////
        Image btnGive = GUI.createImage(TextureAtlasC.InventoryAtlas,"btnGive");
        btnGive.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*2,Align.center);
        gr.addActor(btnGive);
        btnGive.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                if(Type!=-1){
                    reciMegaID = megaIDTf.getText();
                    AwaitData();
                    System.out.println("type: "+Type);
                    System.out.println("amount: "+countAmount);
                    System.out.println("megaID: "+ Config.megaID);
                    System.out.println("reciID: "+ reciMegaID);
                    //label fix here!!!!
                    if(toUpperCaseText(reciMegaID.substring(0,5)).equals("MEGA1"))
                        httpGift.PostGift(Type,countAmount,reciMegaID.substring(5));
                    else
                        httpGift.PostGift(Type,countAmount,reciMegaID);
                }else {
                    notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"bạn chưa chọn thẻ hoặc mảnh",Color.RED);
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }
    private String toUpperCaseText(String text){
        String Upper ="";
        for (int i =0; i<text.length();i++){
            String charter =""+ text.charAt(i);
            Upper+=charter.toUpperCase();
        }
        if(Upper.equals("MEGA1"))
            return Upper;
        else
            return text;

    }


    private void updateList(){
        for (Label lb : arrLabelCard){
            lb.setText("0");
            lb.setVisible(false);
            arrIconCard.get(arrLabelCard.indexOf(lb, true)).setColor(Color.DARK_GRAY);
        }
        if(Data.get("result").size==0|| Data==null){

        }else {
            for (int i=0;i<Data.get("result").size;i++){
                int type   = (Data.get("result").get(i).get("type").asInt()-2);
                int amount = Data.get("result").get(i).get("amount").asInt();
                String name = Data.get("result").get(i).get("name").asString();
                for (Label lb : arrLabelCard){
                    if(arrLabelCard.indexOf(lb,true)==type){
                        lb.setText(""+amount);
                        lb.setVisible(true);
                        lb.setName(""+Data.get("result").get(i).get("type").asInt());
                        arrIconCard.get(arrLabelCard.indexOf(lb,true)).setColor(Color.WHITE);
                    }
                }
            }
        }
    }
    private void eventDescInc(Image btn, String Type){
        btn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                SoundEffect.Play(SoundEffect.click);
                SoundEffect.Playmusic(SoundEffect.click);
                if(Type.equals("Desc")){
                    countAmount--;
                    if(countAmount<0)
                        countAmount=0;
                    lbCount.setText(""+countAmount);
                }else if(Type.equals("Inc")){
                    countAmount++;
                    if(countAmount>maxAmount)
                        countAmount=maxAmount;
                    lbCount.setText(""+countAmount);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }
    private void setChoice(int index){
        for (Image img : arrOverlap){
            if(arrOverlap.indexOf(img,true)==index){
                arrOverlap.get(index).setVisible(true);
                Type = Integer.parseInt(arrLabelCard.get(index).getName());
                maxAmount = Integer.parseInt(arrLabelCard.get(index).getText().toString());
            }else {
                img.setVisible(false);
            }
        }

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
    private void AwaitData(){
        blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, 0,0, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.5f);
        gr.addActor(blackOverlay);
        ///////// loadding/////////
        Loadding = GUI.createImage(TextureAtlasC.UiAtlas,"loadding");
        Loadding.setOrigin(Align.center);
        Loadding.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        gr.addActor(Loadding);
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
    public void PostGift(JsonValue data) {
        finishLoad();
        if(data.get("status_code").asInt()==2000){
            this.inventory.Data = data;
            Data = data;
            inventory.updateInventory(data);
            inventory.updateBtnMergeCard();
            updateList();
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"tặng thành công",Color.GREEN);
        }else {
            notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"mời bạn kiểm tra lại thông tin",Color.RED);
        }
    }

    @Override
    public void Fail(String s) {
        finishLoad();
        notice(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,"Lỗi Mạng",Color.RED);

    }
}
