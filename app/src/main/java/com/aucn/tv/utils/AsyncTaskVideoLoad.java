package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskVideoLoad extends AsyncTask <String, Integer,List<DisplayBase>>{

    private String plId;

    public AsyncTaskVideoLoad(String playListId){
        this.plId = playListId;
    }

    @Override
    public List<DisplayBase> doInBackground(String... params) {
        try {
            List<DisplayBase> dbs = new ArrayList<>();
            PlaylistItemListResponse plil = Config.youtube().playlistItems().list(Config.SCOPE_SNIPPET).setPlaylistId(plId).setMaxResults(Config.MAX_RESULTS).execute();
            for(PlaylistItem pl :plil.getItems()){
                DisplayBase db = new DisplayBase();
                db.entityType = "pl";
                db.entityId = pl.getSnippet().getResourceId().getVideoId();
                db.entityTytle = pl.getSnippet().getTitle();
                String img = Config.defaultImgUrl;
                if(pl != null){
                    PlaylistItemSnippet ps = pl.getSnippet();
                    if(ps != null){
                        ThumbnailDetails td = ps.getThumbnails();
                        if(td != null){
                            Thumbnail tn = td.getMedium();
                            if(tn != null){
                                String url = tn.getUrl();
                                if(url!=null){
                                    img = url;
                                }
                            }else{
                                Thumbnail tn1 = td.getDefault();
                                String url1 = tn1.getUrl();
                                if(url1 != null){
                                    img = url1;
                                }
                            }
                        }
                    }
                }

                db.entityImg = img;
                dbs.add(db);
            }
            return dbs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<DisplayBase> result){
        Config.PL_DETAILS.put(plId, result);
        super.onPostExecute(result);
    }
}
