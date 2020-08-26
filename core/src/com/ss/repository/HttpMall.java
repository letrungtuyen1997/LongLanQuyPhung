package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

public class HttpMall {
    private int                        status;
    private HttpMall.GetMission getData;
    private Utils                      utils;
    private String                     Uri                  = Config.uri+"/api/notice/get-all-notice";
    private String                     UriGetGift           = Config.uri+"/api/notice/get-gift";

    public interface GetMission{
        void GetMall(JsonValue data);
        void UpdateMall(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(HttpMall.GetMission getdata){
        this.getData = getdata;
    }

    public HttpMall(){


    }

    public void PostData(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        httpPost.setContent("megaID="+Config.megaID+"&token="+Config.token);
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
                    getData.GetMall(jv);
                }else {
                    getData.Fail("loi mang");

                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("load that bai!!");

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public void PostGetGift(Long time){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriGetGift);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+Config.megaID+"&token="+Config.token+"&time="+time);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                System.out.println("here: "+status);
                if(status==200){
//                    System.out.println(httpResponse.getResultAsString());
                    String data = httpResponse.getResultAsString();
                    System.out.println("check: "+data);

                    JsonValue jv = utils.GetJsV(data);
                    getData.UpdateMall(jv);
                }else {
                    getData.Fail("loi mang");

                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("load that bai!!");

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
