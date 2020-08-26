package com.ss.interfaces;

public interface IPLatform {
    public void log(String str);

    public String GetDefaultLanguage();
    public boolean isVideoRewardReady();
    public void ShowVideoReward(OnVideoRewardClosed callback);
    public void ShowFullscreen();
    public void ShowBanner(boolean visible);

    public int GetConfigIntValue(String name, int defaultValue);
    public String GetConfigStringValue(String name, String defaultValue);


    public void TrackCustomEvent(String event);
    public void TrackLevelInfo(String event, int mode, int difficult, int level);
    public void TrackPlayerInfo(String event, int mode, int difficult, int level);
    public void TrackPlaneInfo(String event, int planeid, int level);
    public void TrackVideoReward(String type);

    public interface OnVideoRewardClosed{
        public void OnEvent(boolean success);
    }
}
