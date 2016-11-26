package com.aucn.tv.config;


import com.aucn.tv.utils.AsyncTaskPlayListLoad;
import com.aucn.tv.utils.AsyncTaskVideoLoad;
import com.aucn.tv.utils.FileUtil;
import com.aucn.tv.utils.HttpUtils;
import com.aucn.tv.utils.JacksonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    public static Map<String, List<String>> allData = new HashMap<String,List<String>>();

//    public static List<String> UTD_IDS = new ArrayList<String>();
//    public static List<String> UTD_IMGS = new ArrayList<String>();
//    public static List<String> UTD_TITLES = new ArrayList<String>();
//    public static List<String> PL_IDS = new ArrayList<String>();
//    public static List<String> PL_TITLES = new ArrayList<String>();
//    public static List<String> PL_IMGS = new ArrayList<String>();

    public static List<DisplayBase> updateToday = new ArrayList<DisplayBase>();
    public static List<DisplayBase> playLists = new ArrayList<DisplayBase>();

    public static Map<String, List<DisplayBase>> PL_DETAILS = new HashMap<String,List<DisplayBase>>();

    public static String liveId = "";

    public static boolean initFinished = false;
    public static boolean updateTodayFinished = false;

//    public static Map<String,Bitmap> bitMapMaps = new HashMap<String,Bitmap>();

    public static String getLiveVideo (){
        if(liveId == null || "".equals(liveId)){
            String vid = "";
            try {
                HttpUtils hu = new HttpUtils();
                String updateTodayQuery = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&order=date&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
                String updateToday = hu.get(updateTodayQuery, "UTF-8");
                Map<String, Object> updateTodayOri = (Map<String, Object>) JacksonUtils.jsonToMap(updateToday);
                List<Object> updateTodayitems = (List<Object>) updateTodayOri.get("items");
                for (Object o : updateTodayitems) {
                    Map<String, Object> vedioDetails = (Map<String, Object>) o;
                    Map<String, String> vvid = (Map<String, String>) vedioDetails.get("id");
                    Map<String, Object> snippet = (Map<String, Object>) vedioDetails.get("snippet");
                    String liveFlag = (String) snippet.get("liveBroadcastContent");
                    if("live".equals(liveFlag)){
                        vid = vvid.get("videoId");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return vid;
        }
        return "";
    }

    public static void initPlayListDatas() {
        AsyncTaskPlayListLoad atdl = new AsyncTaskPlayListLoad();
        atdl.execute();
    }

    public static List<DisplayBase> getPlayListById(String plid) {
        return PL_DETAILS.get(plid);
    }

    public static void initPlayListDetails() {
        String cachedPls = FileUtil.getCachedPls();
        if("".equals(cachedPls) || cachedPls == null){
            return;
        }
        String pl[] = cachedPls.split("&&");
//        for(DisplayBase db : playLists){
        for(String plid : pl){
            AsyncTaskVideoLoad v = new AsyncTaskVideoLoad(plid);
            v.execute();
        }
    }
}
