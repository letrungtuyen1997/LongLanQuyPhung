package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

public class HttpGetToken {
    private int                        status;
    private HttpGetToken.GetUserInfo getData;
    private Utils                      utils;
    private String                     UriCountDown       = Config.uri+"/api/notice/count-down";
    private String                     Uri                = "https://gameportal1234.herokuapp.com/api/v1/portal/game/user";
    private String                     UriCheckToken      = Config.uri+"/api/user/info-user";
//    private String                     token              = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjYW1wYWlnbl9pZCI6IkxOSlNaR0FCQ3Nma3I4N3I5bTkxIiwidXNlcl9pZCI6MTc5MjYyLCJuYW1lIjoiZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SjFjMlZ5SWpwN0ltbGtJam96TURNM016YzRMQ0p3YUc5dVpTSTZJakE1TURnNE1EWTFPRFFpTENKdVlXMWxJam9pVm5VaUxDSjBlWEJsSWpvaVkzVnpkRzl0WlhJaWZTd2lkR2x0WlNJNk1UQXdNREF3TENKcFlYUWlPakUxT1RJME5qazJNemdzSW1WNGNDSTZNVFU1TlRRMk9UWXpPSDAuNm5sWGphaWhORk5Mb2k5UjRUYktFT2dtRHFFNTdfWDV3VlQzNHFoRzlfRSIsInBob25lIjoiMDk3MzUxMTMyMSIsImlhdCI6MTU5MjQ3ODY3OCwiZXhwIjoxNTkyNDc4NzM4fQ.cQw_UNT5kAyd9fLK4d8pOywv0P3OS2MZoPZ65I6Xb8c";
//    private String                     token              = Config.token;
    private String                     UriNotice          = Config.uri+"/api/notice/get-text-show";

    public interface GetUserInfo {
        void getInfo(JsonValue data);
        void checktoken(JsonValue data);
        void getNotice(JsonValue data);
        void getCountDown(JsonValue data);
        void Fail(String s);

    }
    public void setIGetdata(HttpGetToken.GetUserInfo getData){
        this.getData = getData;
    }

    public HttpGetToken(){

    }

    public void GetToken(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Authorization", token);
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
                    getData.getInfo(jv);
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

    public void CheckToken(String token){
      System.out.println("send token in here: "+token);
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriCheckToken);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("token="+token);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                System.out.println("here: "+status);
                if(status==200){
                    String data = httpResponse.getResultAsString();
                    System.out.println("check get user info: "+data);
                    JsonValue jv = utils.GetJsV(data);
                    getData.checktoken(jv);
                }else {
                    getData.Fail("loi mang");
                }
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

    public void GetNotice(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriNotice);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
        httpPost.setTimeOut(15000);
        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                System.out.println("here: "+status);
                if(status==200){
                    String data = httpResponse.getResultAsString();
                    JsonValue jv = utils.GetJsV(data);
                    getData.getNotice(jv);
                }else {
                    getData.Fail("loi mang");

                }
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
  public void GetCountDown(){
    Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.GET);
    httpPost.setUrl(UriCountDown);
    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//    httpPost.setContent("megaID="+ Config.megaID+"&token="+Config.token);
    httpPost.setTimeOut(15000);
    Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
      @Override
      public void handleHttpResponse(Net.HttpResponse httpResponse) {

        status = httpResponse.getStatus().getStatusCode();
        System.out.println("here: "+status);
        if(status==200){
          String data = httpResponse.getResultAsString();
          JsonValue jv = utils.GetJsV(data);
          getData.getCountDown(jv);
        }else {
          getData.Fail("loi mang");

        }
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
