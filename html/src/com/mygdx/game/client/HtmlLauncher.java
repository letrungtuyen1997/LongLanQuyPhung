package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.platform.IPlatform;
import com.ss.GMain;

public class HtmlLauncher extends GwtApplication {
        int PADDING = 0;
        boolean isHandleResize = false;
        public static GMain game;
        long lastInterstitialTime;
        String globalleaderboardName = "TOPDIEM";
        public static boolean isInited = false;
        @Override
        public GwtApplicationConfiguration getConfig () {


                int height = com.google.gwt.user.client.Window.getClientHeight() - PADDING;
                int width = com.google.gwt.user.client.Window.getClientWidth()- PADDING;

//                if(width < height){
//                        int h = width;
//                        width = height;
//                        height = h;
//                }

                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(width, height);
                Window.enableScrolling(false);
                Window.setMargin("0");
                isHandleResize = true;
                Window.addResizeHandler(new ResizeListener());

                cfg.disableAudio = false;
                cfg.preferFlash = false;
                //cfg.antialiasing = true;



                return cfg;

                //return new GwtApplicationConfiguration(480, 848);
        }


        // END CODE FOR FIXED SIZE APPLICATION

        // UNCOMMENT THIS CODE FOR A RESIZABLE APPLICATION
        // PADDING is to avoid scrolling in iframes, set to 20 if you have problems
        // private static final int PADDING = 0;
        // private GwtApplicationConfiguration cfg;
        //
        // @Override
        // public GwtApplicationConfiguration getConfig() {
        //     int w = Window.getClientWidth() - PADDING;
        //     int h = Window.getClientHeight() - PADDING;
        //     cfg = new GwtApplicationConfiguration(w, h);
        //     Window.enableScrolling(false);
        //     Window.setMargin("0");
        //     Window.addResizeHandler(new ResizeListener());
        //     cfg.preferFlash = false;
        //     return cfg;
        // }
        //
        // class ResizeListener implements ResizeHandler {
        //     @Override
        //     public void onResize(ResizeEvent event) {
        //         int width = event.getWidth() - PADDING;
        //         int height = event.getHeight() - PADDING;
        //         getRootPanel().setWidth("" + width + "px");
        //         getRootPanel().setHeight("" + height + "px");
        //         getApplicationListener().resize(width, height);
        //         Gdx.graphics.setWindowedMode(width, height);
        //     }
        // }
        // END OF CODE FOR RESIZABLE APPLICATION

        @Override
        public ApplicationListener createApplicationListener () {
                return new GMain(new IPlatform() {

                        @Override
                        public void log(String str) {
                                consolelog(str);

                        }

                        @Override
                        public String GetDefaultLanguage() {
                                return null;
                        }

                        @Override
                        public boolean isVideoRewardReady() {
                                return false;
                        }

                        @Override
                        public void ShowVideoReward(OnVideoRewardClosed callback) {

                        }

                        @Override
                        public void ShowFullscreen() {

                        }

                        @Override
                        public void ShowBanner(boolean visible) {

                        }

                        @Override
                        public void Onshow() {
                            if(isInited==false){
                                SetupCanvas();
                                isInited = true;
                            }
                        }

                        @Override
                        public void ShareFb() {
                                shareFb();
                        }

                        @Override
                        public String GetToken() {
                                return getToken();
                        }

                        @Override
                        public void Resize() {
                                SetupCanvas();
                                ResizeCanvas();
                        }

                        @Override
                        public int GetConfigIntValue(String name, int defaultValue) {
                                return 0;
                        }

                        @Override
                        public String GetConfigStringValue(String name, String defaultValue) {
                                return null;
                        }

                        @Override
                        public void CrashKey(String key, String value) {

                        }

                        @Override
                        public void CrashLog(String log) {

                        }

                        @Override
                        public void TrackCustomEvent(String event) {

                        }

                        @Override
                        public void TrackLevelInfo(String event, int mode, int difficult, int level) {

                        }

                        @Override
                        public void TrackPlayerInfo(String event, int mode, int difficult, int level) {

                        }

                        @Override
                        public void TrackPlaneInfo(String event, int planeid, int level) {

                        }

                        @Override
                        public void TrackVideoReward(String type) {

                        }

                        @Override
                        public void TrackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss) {

                        }

                        @Override
                        public void ReportScore(long score) {

                        }

                        @Override
                        public void ShowLeaderboard() {

                        }

                        @Override
                        public void Restart() {

                        }

                        @Override
                        public int GetNotifyId() {
                                return 0;
                        }

                        @Override
                        public void SetDailyNotification(int id, String header, String content, int days, int hours) {

                        }

                        @Override
                        public void CancelDailyNotification(int id) {

                        }
                });
        }
        int canvasWidth;
        int canvasHeight;
        public void SetupCanvas(){
                CanvasElement canvas = getCanvasElement();
                int w = canvas.getWidth();// getInnerWidth();
                int h = canvas.getHeight();//getInnerHeight();
                double dpr  = devicePixelRatio();
                int cw = (int)(w*dpr);
                int ch = (int)(h*dpr);

                if(isHandleResize){
                        w = getInnerWidth();
                        h = getInnerHeight();
                        cw = getFBWidth();
                        ch = getFBHeight();
                }

                canvas.getStyle().setWidth(w, Style.Unit.PX);
                canvas.getStyle().setHeight(h, Style.Unit.PX);
                canvas.setWidth(cw);
                canvas.setHeight(ch);

        }

        public void ResizeCanvas(){
                if(jsResize() == false) {
                        CanvasElement canvas = getCanvasElement();
                        //double dpr = devicePixelRatio();
                        int fw = getInnerWidth();
                        int fh = getInnerHeight();
                        canvas.getStyle().setWidth(fw, Style.Unit.PX);
                        canvas.getStyle().setHeight(fh, Style.Unit.PX);
                        canvas.setWidth(getFBWidth());
                        canvas.setHeight(getFBHeight());
                        //consolelog3("resize l:" + getFBWidth() + " " + getFBHeight());
                }

        }

        class ResizeListener implements ResizeHandler {
                @Override
                public void onResize(ResizeEvent event) {
                        ResizeCanvas();
                }
        }
        private static native double devicePixelRatio() /*-{
            return  $wnd.window.devicePixelRatio;
        }-*/;

        private static native int getInnerWidth() /*-{
            return $wnd.window.innerWidth;
        }-*/;

        private static native int getInnerHeight() /*-{
            return $wnd.window.innerHeight;
        }-*/;

        private static native int getFBWidth() /*-{
            return $wnd.window.innerWidth * $wnd.window.devicePixelRatio;
        }-*/;

        private static native int getFBHeight() /*-{
            return $wnd.window.innerHeight * $wnd.window.devicePixelRatio;
        }-*/;

        private static native void consolelog(String log) /*-{
            $wnd.consolelog(log);
        }-*/;

        private static native boolean jsResize() /*-{
            return $wnd.jsResize();
//              return false;
        }-*/;
        private static native boolean shareFb() /*-{
            return $wnd.shareFB();
        }-*/;
        private static native String getToken() /*-{
            return $wnd.getToken();
        }-*/;
}