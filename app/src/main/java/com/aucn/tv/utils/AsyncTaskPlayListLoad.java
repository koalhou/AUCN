package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.io.IOException;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskPlayListLoad extends AsyncTask <String, Integer, String>{

    @Override
    public String doInBackground(String... params) {
        try {
            PlaylistListResponse pls = Config.youtube().playlists().list(Config.SCOPE_SNIPPET).setChannelId(Config.YOUTUBE_CHANNEL_ID).setMaxResults(Config.MAX_RESULTS).execute();
            for(Playlist pl :pls.getItems()){
                DisplayBase db = new DisplayBase();
                db.entityType = "pl";
                db.entityId = pl.getId();
                db.entityTytle = pl.getSnippet().getTitle();
                String img = pl.getSnippet().getThumbnails().getMedium().getUrl();
                if(img == null || "".equals(img)){
                    img = pl.getSnippet().getThumbnails().getDefault().getUrl();
                }
                if(img == null || "".equals(img)){
                    img = Config.defaultImgUrl;
                }
                db.entityImg = img;
                Config.playLists.add(db);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
