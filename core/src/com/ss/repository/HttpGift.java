package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

public class HttpGift {
    private int                        status;
    private HttpGift.PostGift postData;
    private Utils                      utils;
    private String                     Uri                = Config.uri+"/api/gift/giving-gift";

    public interface PostGift{
        void PostGift(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(HttpGift.PostGift postData){
        this.postData = postData;
    }

    public HttpGift(){


    }

    public void PostGift(int type,int anount,String reciMegaID){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token+"&type="+type+"&amount="+anount+"&reciMegaID="+reciMegaID);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                System.out.println("here: "+status);
                if(status==200){
//                    System.out.println(httpResponse.getResultAsString());
                    String data = httpResponse.getResultAsString();
//                    System.out.println("check: "+data);

                    JsonValue jv = utils.GetJsV(data);
                    postData.PostGift(jv);
                }else {
                    postData.Fail("loi mang");

                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                postData.Fail("load that bai!!");

            }

            @Override
            public void cancelled() {

            }
        });
    }

}
