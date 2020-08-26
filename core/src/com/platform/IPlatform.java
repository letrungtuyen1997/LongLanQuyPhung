package com.platform;

public interface IPlatform {
    public void log(String str);

    public String GetDefaultLanguage();
    public boolean isVideoRewardReady();
    public void ShowVideoReward(OnVideoRewardClosed callback);
    public void ShowFullscreen();
    public void ShowBanner(boolean visible);
    public void Onshow();
    public void ShareFb();
    public String GetToken();
    public void Resize();


    public int GetConfigIntValue(String name, int defaultValue);
    public String GetConfigStringValue(String name, String defaultValue);

    public void CrashKey(String key, String value);
    public void CrashLog(String log);

    public void TrackCustomEvent(String event);
    public void TrackLevelInfo(String event, int mode, int difficult, int level);
    public void TrackPlayerInfo(String event, int mode, int difficult, int level);
    public void TrackPlaneInfo(String event, int planeid, int level);
    public void TrackVideoReward(String type);
    public void TrackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss);

    /*public void TrackLevelStart(String mode, int difficult, int level);
    public void TrackLevelFailed(String mode, int difficult, int level);
    public void TrackLevelCompleted(String mode, int difficult, int level);
    public void TrackCustomEvent(String event);
    public void TrackPlayerDead(String mode, int difficult,  int level);
    public void TrackPlayerReborn(String mode, int level);
    public void TrackPlayerUpgrade(int planeid, int level);
    public void TrackPlayerUseItem(String mode, int level, String item);
    public void TrackPlayerEnchance(int enchanceid);
    ;*/

    public void ReportScore(long score);
    public void ShowLeaderboard();


    public void Restart();
    public int GetNotifyId();
    public void SetDailyNotification(int id, String header, String content, int days, int hours);
    public void CancelDailyNotification(int id);

    public interface OnVideoRewardClosed{
        public void OnEvent(boolean success);
    }

    public interface OnPopupCallback{
        public void OnValue(String value);
    }
    public interface OnShareCallback{
        public void OnValue(boolean value);
    }
    public interface OnFormatCallback{
        public void OnValue(String value);
    }




}
