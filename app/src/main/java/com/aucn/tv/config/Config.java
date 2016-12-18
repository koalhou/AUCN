package com.aucn.tv.config;


import com.aucn.tv.utils.AsyncLivePeer;
import com.aucn.tv.utils.AsyncTaskPlayListLoad2;
import com.aucn.tv.utils.AsyncTaskVideoLoad1;
import com.aucn.tv.utils.AsyncUpdateTodayDataLoad;
import com.google.android.gms.common.Scopes;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2016/10/18.
 */

public class Config {

    private Config() {
    }

//    public static final String YOUTUBE_API_KEY = "AIzaSyAP1H0PtjMyfu1FZZs10-TEklKgesvEpQw";
    public static final String YOUTUBE_API_KEY = "AIzaSyCbl1b5Ga6q3u3vv41zt4egLAmnatkp5mU";
    public static  final String YOUTUBE_CHANNEL_ID = "UCJnvK2B5QvnT70ZK_an3eMg";
    public static final String SHAIID="129083993623-1ue1oaqml454ko1a9ibos00eqv2g64k5.apps.googleusercontent.com";
    public static final String SHAI="7F:58:9D:6D:6C:48:D7:5E:97:9B:42:BD:53:39:60:75:5C:7B:2A:D4";
    public static final String SINGLE_CODE = "4/w0flzGXxAHxzIxDmOkKs4MxuMmPfp6W40kaua8wXwCo#";
    public static String defaultImgUrl = "https://i.ytimg.com/vi/1AVTGtZn28Q/mqdefault_live.jpg";
    public static final String LIVE_COMMING_URL = "http://www.aucn.tv/comingup";
    public static final String MEMBERSHIP = "http://www.aucn.tv/membership";

    public static List<DisplayBase> updateToday = new ArrayList<DisplayBase>();
    public static List<DisplayBase> playLists = new ArrayList<DisplayBase>();

    public static boolean isNetworkOK = false;
    public static Map<String, List<DisplayBase>> PL_DETAILS = new HashMap<String,List<DisplayBase>>();

    public static String liveId = "";

    public static String GOOGLE_TEST_IP = "8.8.8.8";

    public static boolean initFinished = false;
    public static boolean updateTodayFinished = false;
    public static String SCOPE_SNIPPET = "snippet";
    public static long MAX_RESULTS = 50;

    public static GoogleAccountCredential credential = null;
//    public static Map<String,Bitmap> bitMapMaps = new HashMap<String,Bitmap>();

    public static final String APP_NAME = "AUCNAndroid";
    public static final String[] SCOPES = {Scopes.PROFILE, com.google.api.services.youtube.YouTubeScopes.YOUTUBE};
    private static final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();

    public static YouTube youtube(){
        return new YouTube.Builder(transport, jsonFactory, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {

            }
        }).setYouTubeRequestInitializer(new YouTubeRequestInitializer(Config.YOUTUBE_API_KEY)).setApplicationName("澳洲中文电视台").build();
    }

    public static void initPlayListDatas() {
        AsyncTaskPlayListLoad2 atdl = new AsyncTaskPlayListLoad2();
        atdl.execute();
    }

    public static List<DisplayBase> getPlayListById(String plid) {
        return PL_DETAILS.get(plid);
    }

    public static void initPlayListDetails() {
        for(DisplayBase db : playLists) {
            AsyncTaskVideoLoad1 v = new AsyncTaskVideoLoad1(db.entityId);
            v.execute();
        }
    }

    public static void startLiveSchedule() {
        AsyncLivePeer alp = new AsyncLivePeer();
        alp.execute();
        Runnable runnable = new Runnable() {
            public void run() {
                AsyncLivePeer alp = new AsyncLivePeer();
                alp.execute();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 20, 20, TimeUnit.SECONDS);
    }

    public static void initUpdateTodaySchedule() {
        AsyncUpdateTodayDataLoad aud = new AsyncUpdateTodayDataLoad();
        aud.execute();
        Runnable runnable = new Runnable() {
            public void run() {
                AsyncUpdateTodayDataLoad aud = new AsyncUpdateTodayDataLoad();
                aud.execute();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 30, 200, TimeUnit.SECONDS);
    }


    public static void initAll() {
        initPlayListDatas();
        startLiveSchedule();
        initUpdateTodaySchedule();
    }
}
