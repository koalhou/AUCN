//package com.aucn.tv.utils;
//
//import android.os.AsyncTask;
//
//import com.aucn.tv.config.Config;
//import com.aucn.tv.config.VideoData;
//import com.google.android.gms.common.Scopes;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.YouTubeRequestInitializer;
//import com.google.api.services.youtube.model.PlaylistItemListResponse;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import static com.aucn.tv.config.Config.credential;
//
//
///**
// * Created by mac on 2016/11/6.
// */
//
//public class AsyncLoad extends AsyncTask <String, Integer, String>{
//
//    public static final String APP_NAME = "aucnandroid-148011";
//    public static final String[] SCOPES = {Scopes.PROFILE, com.google.api.services.youtube.YouTubeScopes.YOUTUBE};
//
//    private  GoogleAccountCredential c;
//    private YouTube you;
//    public AsyncLoad(YouTube youTube){
//        c = credential;
//        you = youTube;
//    }
//    @Override
//    public String doInBackground(String... params) {
//        try {
////            ChannelListResponse clr = youtube.channels().list("contentDetails").setMine(true).execute();
////
////            // Get the user's uploads playlist's id from channel list
////            // response
////            String uploadsPlaylistId = clr.getItems().get(0).getContentDetails().getRelatedPlaylists().getUploads();
//
//            List<VideoData> videos = new ArrayList<VideoData>();
//
//            // Get videos from user's upload playlist with a playlist
//            // items list request
//            PlaylistItemListResponse pilr = youtube.playlistItems().list("snippet").setPlaylistId("PLQhLBocOuHsdM2bA86E9BEldnp-aG3Zzf").setMaxResults(20l).execute();
//            System.out.println(pilr);
////            LiveBroadcastListResponse lis = youtube.liveBroadcasts().list("snippet").execute();
////            System.out.println(lis);
//            List<String> videoIds = new ArrayList<String>();
//
//            // Iterate over playlist item list response to get uploaded
//            // videos' ids.
////            for (PlaylistItem item : pilr.getItems()) {
////                videoIds.add(item.getContentDetails().getVideoId());
////            }
//
//            // Get details of uploaded videos with a videos list
//            // request.
////            VideoListResponse vlr = youtube.videos().list("id,snippet,status").setId(TextUtils.join(",", videoIds)).execute();
//
//            // Add only the public videos to the local videos list.
////            for (Video video : vlr.getItems()) {
////                if ("public".equals(video.getStatus().getPrivacyStatus())) {
////                    VideoData videoData = new VideoData();
////                    videoData.setVideo(video);
////                    videos.add(videoData);
////                }
////            }
//
//            // Sort videos by title
//            Collections.sort(videos, new Comparator<VideoData>() {
//                @Override
//                public int compare(VideoData videoData,VideoData videoData2) {
//                    return videoData.getTitle().compareTo(videoData2.getTitle());
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        YouTube.LiveBroadcasts bc = youtube.liveBroadcasts();
////        System.out.println(bc);
//        return "";
//    }
//
//    @Override
//    protected void onPostExecute(String result){
//        FileUtil.cachePlayList(result);
//        super.onPostExecute(result);
//    }
//}
