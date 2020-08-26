package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.ss.GMain;

import java.util.Locale;
import java.util.MissingResourceException;

public class C {

    public static class remote {
        public static int adsTime = 50;
        static void initRemoteConfig() {

        }
    }

    public static class lang {
        private static I18NBundle locale;
        public static String title = "";
        public static String adsTimeLbl = "";
        public static String popend = "";
        public static String restPend1 = "";
        public static String restPend2 = "";
        public static String score = "";
        public static String alert = "";
        public static String altGmother = "";
        public static String lbwatch = "";
        public static String lbwatch1 = "";
        public static String idcontry="";
        public static String lbtoturial="";
        public static String lbSkill1="";
        public static String lbSkill2="";
        public static String lbSkill3="";
        public static String lbHighSc="";
        public static String lbTarget="";
        public static String lbLv="";
        public static String lbHome="";
        public static String lbRestart="";
        public static String lbbtnNewGame="";
        public static String lbbtnContinus="";
        public static String lbbtnRank="";
        public static String lbCheckConnect="";

        static void initLocalize() {
            String deviceLang = GMain.platform.GetDefaultLanguage();
            System.out.println("language: "+deviceLang);
            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + deviceLang);
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
                idcontry = locale.get("idcontry");
                System.out.println("tryyyyyyyyy");
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
//                idcontry = locale.get("icontry");
                System.out.println("cuuuuuuuu");
            }

            title = locale.get("title");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);
            popend = locale.get("popend");
            restPend1 = locale.get("quantitypopend1");
            restPend2 = locale.get("quantitypopend2");
            score = locale.get("score");
            alert = locale.get("alert");
            altGmother = locale.get("gmother");
            lbwatch = locale.get("lbwatch");
            lbwatch1 = locale.get("lbwatch1");
            lbtoturial = locale.get("lbTutorial");
            lbSkill1 = locale.get("lbSkill1");
            lbSkill2 = locale.get("lbSkill2");
            lbSkill3 = locale.get("lbSkill3");
            lbHighSc = locale.get("lbHighSc");
            lbTarget = locale.get("lbTarget");
            lbLv = locale.get("lbLv");
            lbHome = locale.get("lbHome");
            lbRestart = locale.get("lbRestart");
            lbbtnNewGame = locale.get("lbbtnNewGame");
            lbbtnContinus = locale.get("lbbtnContinus");
            lbbtnRank = locale.get("lbbtnRank");
            lbCheckConnect = locale.get("lbCheckConnect");
        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}
