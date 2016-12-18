package com.aucn.tv.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/*
 * 测试类
 * 测试Https接口 post
 * 接收下属客户端上传样本,保存样本文件
 */
public class HttpUtils {

    private static String callName = "---";

    public HttpUtils(String callName){
        this.callName = callName;
    }

    public HttpUtils(){

    }

    private static HttpUtils instance = new HttpUtils();

    public static HttpUtils getInstance(String callName1){
        callName = callName1;
        return new HttpUtils(callName);
//        return instance;
    }
    /**
     * post方式请求服务器(https协议)
     *
     * @param u
     *            请求地址
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    public String get(String u, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException, NoSuchProviderException {

        URL url = new URL(u);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        try {

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            System.out.println("HttpUtils log : " + callName + "calls : " + conn.getResponseCode());
            System.out.println(conn.getResponseMessage());
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                return new String(outStream.toByteArray(),"UTF-8");
            }
            return null;//conn.getResponseMessage();

        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }

        return null;
    }



//    public String getLiveId(){
//        String vid = "";
//        try {
//            HttpUtils hu = new HttpUtils();
//            String updateTodayQuery = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&order=date&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
//            String updateToday = hu.get(updateTodayQuery, "UTF-8");
//            Map<String, Object> updateTodayOri = (Map<String, Object>) JacksonUtils.jsonToMap(updateToday);
//            List<Object> updateTodayitems = (List<Object>) updateTodayOri.get("items");
//            for (Object o : updateTodayitems) {
//                Map<String, Object> vedioDetails = (Map<String, Object>) o;
//                Map<String, String> vvid = (Map<String, String>) vedioDetails.get("id");
//                Map<String, Object> snippet = (Map<String, Object>) vedioDetails.get("snippet");
//                String liveFlag = (String) snippet.get("liveBroadcastContent");
//                if("live".equals(liveFlag)){
//                    vid = vvid.get("videoId");
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return vid;
//    }
}