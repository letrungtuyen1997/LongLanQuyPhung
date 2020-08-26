package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

public class HttpInventory {
    private int                        status;
    private HttpInventory.GetInventory getData;
    private Utils                      utils;
    private String                     Uri                = Config.uri+"/api/inventory/get-inventory";
    private String                     UriMerge           = Config.uri+"/api/merge/merge-item";
    private String                     UriMergeCard       = Config.uri+"/api/merge/merge-card-billion";
    private String                     UriEvent           = Config.uri+"/api/";



    public interface GetInventory{

        void Finish(JsonValue data);
        void FinishMerge(JsonValue data);
        void FinishMergeBilli(JsonValue data);
        void FinishCheckEvent(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(HttpInventory.GetInventory getdata){
        this.getData = getdata;
    }

    public HttpInventory(){


    }

    public void PostData(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
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
                    getData.Finish(jv);
                }else {
                    getData.Fail("loi mang");
                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("loi mang");

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public void Merge(int Type){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriMerge);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token+"&type="+Type);
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
                    getData.FinishMerge(jv);
                }else {
                    getData.Fail("loi mang");
                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("loi mang");

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public void MergeCardBilli(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriMergeCard);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
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
                    getData.FinishMergeBilli(jv);
                }else {
                    getData.Fail("loi mang");
                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("loi mang");

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public void checkEvent(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriEvent);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
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
                    getData.FinishCheckEvent(jv);
                }else {
                    getData.Fail("loi mang");
                }
            }

            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                getData.Fail("loi mang");

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
