package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

public class HttpEvent {
    private int                        status;
    private HttpEvent.GetEvent getData;
    private Utils                      utils;
    private String                     UriEvent       = Config.uri+"/api/event/get-all-event";
    private String                     UriEvent2       = Config.uri+"/api/event/get-event";


    public interface GetEvent {
        void getEvent(JsonValue data);
        void getEvent2(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(HttpEvent.GetEvent getData){
        this.getData = getData;
    }

    public HttpEvent(){

    }

    public void GetEvent(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriEvent);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                GMain.platform.log("check get token: "+status);
                System.out.println("here: "+status);
               // if(status==200){
                    String data = httpResponse.getResultAsString();
                    GMain.platform.log("check get token: "+data);

                    JsonValue jv = utils.GetJsV(data);
                    getData.getEvent(jv);
                //}else {
                    //getData.Fail("loi mang");

                //}
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("load that bai!!");
//                postData.Fail("load that bai!!");

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void GetEvent2(int typeEvent, int type ){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriEvent2);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token+"&typeEvent="+typeEvent+"&typeItem="+type);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                GMain.platform.log("check get token: "+status);
                System.out.println("here: "+status);
                // if(status==200){
                String data = httpResponse.getResultAsString();
                GMain.platform.log("check get token: "+data);

                JsonValue jv = utils.GetJsV(data);
                getData.getEvent2(jv);
                //}else {
                //getData.Fail("loi mang");

                //}
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("load that bai!!");
//                postData.Fail("load that bai!!");

            }

            @Override
            public void cancelled() {

            }
        });
    }



}
