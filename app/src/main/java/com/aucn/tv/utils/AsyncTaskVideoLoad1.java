package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aucn.tv.config.Config.defaultImgUrl;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskVideoLoad1 extends AsyncTask <String, Integer,List<DisplayBase>>{

    private String plId;

    public AsyncTaskVideoLoad1(String playListId){
        this.plId = playListId;
    }

    @Override
    public List<DisplayBase> doInBackground(String... params) {
        HttpUtils hu = HttpUtils.getInstance();
        String query = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+plId+"&maxResults=50&key=" + Config.YOUTUBE_API_KEY;
        List<DisplayBase> thisplvs = new ArrayList<DisplayBase>();
        try {
            String vedioStr = hu.get(query, "UTF-8");
            Gson gson = new Gson();
            Map<String, Object> videosOri = gson.fromJson(vedioStr,Map.class);
            List<Object> videos = (List<Object>) videosOri.get("items");
            for(Object o : videos){
                Map<String, Object> vedioDetails = (Map<String,Object>)o;
                Map<String, Object> snippet = (Map<String,Object>)vedioDetails.get("snippet");
                Map<String,String> vvid = (Map<String,String>) snippet.get("resourceId");
                String vid = vvid.get("videoId");
                String title = (String)snippet.get("title");
                if(title.toLowerCase().contains("delete")){
                    continue;
                }
                Map<String,Object> thumbnails = (Map<String,Object>)snippet.get("thumbnails");
                String tDimg = "";
                if(thumbnails == null){
                    tDimg = defaultImgUrl;
                }else{
                    Map<String,String> medium = (Map<String,String>)thumbnails.get("medium");
                    if(medium == null){
                        Map<String, Object> aDefault = (Map<String, Object>)thumbnails.get("default");
                        tDimg = (String)aDefault.get("url");
                    }else{
                        tDimg = (String)medium.get("url");
                    }
                    if(tDimg == null || "".equals(tDimg)){
                        tDimg = defaultImgUrl;
                    }
                    tDimg = (String)medium.get("url");
                }
//                String[] utd = {vid,title,tDimg};
//                String[] utd = {vid};
                DisplayBase db = new DisplayBase();
                db.entityType = "video";
                db.entityId = vid;
                db.entityTytle = title;
                db.entityImg = tDimg;
                thisplvs.add(db);
                Config.PL_DETAILS.put(plId, thisplvs);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return thisplvs;
    }

    @Override
    protected void onPostExecute(List<DisplayBase> result){
        super.onPostExecute(result);
    }
}
