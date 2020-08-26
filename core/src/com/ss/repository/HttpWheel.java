package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;


public class HttpWheel {
    private int          status;
    private GetData      getData;
    private Utils        utils;
    private String       Uri                = Config.uri+"/api/wheel/get-item";
    private String       UriGetTurn         = Config.uri+"/api/wheel/get-turn";
//    private String       UriTest            = Config.uri+"/api/wheel/hello";

    public interface GetData{
        void FinishGetItemWheel(JsonValue data);
        void FinishGetTurn(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(GetData getdata){
        this.getData = getdata;
    }

    public HttpWheel(){


    }
    public void GetTurnBy(String megaID){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setUrl(UriGetTurn);
        httpPost.setContent("megaID="+megaID+"&token="+Config.token);
        httpPost.setTimeOut(15000);

        Gdx.net.sendHttpRequest (httpPost, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                status = httpResponse.getStatus().getStatusCode();
                System.out.println("here: "+status);
                if(status==200){
                    String data = httpResponse.getResultAsString();
                    JsonValue jv = utils.GetJsV(data);
                    getData.FinishGetTurn(jv);
                }else {
                    getData.Fail("load that bai!!");
                }
                //do stuff here based on response
            }

            public void failed(Throwable t) {
                getData.Fail("load that bai!!");
//                status = "failed";
                //do stuff here based on the failed attempt
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void GetItem(String megaID){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        httpPost.setContent("megaID="+megaID+"&token="+Config.token);
        httpPost.setTimeOut(15000);

        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                status = httpResponse.getStatus().getStatusCode();

                if(status==200){
                    String data = httpResponse.getResultAsString();
                    JsonValue jv = utils.GetJsV(data);
                    getData.FinishGetItemWheel(jv);
                    System.out.println("here wheell: "+status);
                    System.out.println("wheel data: "+httpResponse.getResultAsString());
                }else {

                    getData.Fail("loi mang");
                }
            }

            @Override
            public void failed(Throwable t) {
                getData.Fail("loi mang");

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
