package com.ss.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.gameLogic.config.Config;
import com.ss.utils.Utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpMission {
    private int                        status;
    private HttpMission.GetMission getData;
    private Utils                      utils;
    private String                     Uri                = Config.uri+"/api/mission/get-all-mission";
    private String                     UriJoinMission     = Config.uri+"/api/mission/join-mission";
    private String                     UriInvite          = "http://mega1-admin-api-dev.yeah1group.com/api/v1/portal/invitation-count";
    private String                     token              = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjYW1wYWlnbl9pZCI6IkxOSlNaR0FCQ3Nma3I4N3I5bTkxIiwidXNlcl9pZCI6MTc5MjYyLCJuYW1lIjoiZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SjFjMlZ5SWpwN0ltbGtJam96TURNM016YzRMQ0p3YUc5dVpTSTZJakE1TURnNE1EWTFPRFFpTENKdVlXMWxJam9pVm5VaUxDSjBlWEJsSWpvaVkzVnpkRzl0WlhJaWZTd2lkR2x0WlNJNk1UQXdNREF3TENKcFlYUWlPakUxT1RJME5qazJNemdzSW1WNGNDSTZNVFU1TlRRMk9UWXpPSDAuNm5sWGphaWhORk5Mb2k5UjRUYktFT2dtRHFFNTdfWDV3VlQzNHFoRzlfRSIsInBob25lIjoiMDk3MzUxMTMyMSIsImlhdCI6MTU5MjQ3ODY3OCwiZXhwIjoxNTkyNDc4NzM4fQ.cQw_UNT5kAyd9fLK4d8pOywv0P3OS2MZoPZ65I6Xb8c";


    public interface GetMission{
        void GetMission(JsonValue data);
        void UpdateMission(JsonValue data);
        void checkInvite(JsonValue data);
        void Fail(String s);

    }

    public void setIGetdata(HttpMission.GetMission getdata){
        this.getData = getdata;
    }

    public HttpMission(){


    }

    public void PostData(){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(Uri);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
        System.out.println("get all mission token: "+Config.token);
        httpPost.setContent("megaID="+Config.megaID+"&token="+Config.token);
        httpPost.setTimeOut(15000);
        System.out.println("mission post data");
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
                    getData.GetMission(jv);
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
    public void PostJoinMisson(int Type){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriJoinMission);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
//        httpPost.setContent("megaID="+"10009");
        httpPost.setContent("megaID="+Config.megaID+"&token="+Config.token+"&type="+Type);
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
                    getData.UpdateMission(jv);
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
    public void PostResultQuestion(int type,String result){
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriJoinMission);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-Type", "x-www-form-urlencoded");
//        httpPost.setContent("megaID="+"10009");
        httpPost.setContent("megaID="+Config.megaID+"&token="+Config.token+"&type="+type+"&results="+result);
        httpPost.setTimeOut(15000);

        Gdx.net.sendHttpRequest(httpPost, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                status = httpResponse.getStatus().getStatusCode();
                //System.out.println("here: "+status);
                if(status==200){
//                    System.out.println(httpResponse.getResultAsString());
                    String data = httpResponse.getResultAsString();
                    System.out.println("check: "+data);
                    JsonValue jv = utils.GetJsV(data);
                    getData.UpdateMission(jv);
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
    public void CheckInvite(){
        Map map = new HashMap<String,String>();
        map.put("user_id","16");
        String test = "{user_id:16}";
        Json js = new Json();
        String value = js.toJson(test);
        Net.HttpRequest httpPost = new Net.HttpRequest(Net.HttpMethods.POST);
        httpPost.setUrl(UriInvite+"?user_id="+16);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", token);

        System.out.println("check value: "+value);
//        httpPost.setContent(value);
        httpPost.setContent(HttpParametersUtils.convertHttpParameters(map));
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
                getData.checkInvite(jv);
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
