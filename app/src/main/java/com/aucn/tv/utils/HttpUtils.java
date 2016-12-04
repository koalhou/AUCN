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

    public HttpUtils(){

    }

    private static HttpUtils instance = new HttpUtils();

    public static HttpUtils getInstance(){
        return new HttpUtils();
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
    public static String get(String u, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException, NoSuchProviderException {

//        try {
////            String charset = "UTF-8";
//            String url = "https://accounts.google.com/o/oauth2/device/code";
//            URLConnection connection = new URL(url).openConnection();
//            connection.setDoOutput(true); // Triggers POST.
//            connection.setRequestProperty("Accept-Charset", charset);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//            String query = "client_id=129083993623-1ue1oaqml454ko1a9ibos00eqv2g64k5.apps.googleusercontent.com&scope=email%20profile";
//            OutputStream output = connection.getOutputStream();
//            output.write(query.getBytes(charset));
//            InputStream response = connection.getInputStream();
//            System.out.println(response);
//            if (response != null) {
//                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int len = 0;
//                while ((len = response.read(buffer)) != -1) {
//                    outStream.write(buffer, 0, len);
//                }
//                response.close();
//                System.out.println( new String(outStream.toByteArray(),"UTF-8"));
//            }
////            String x = response.read(query);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        try {
////            String charset = "UTF-8";
//            String url = "https://www.googleapis.com/oauth2/v4/token";
//            URLConnection connection = new URL(url).openConnection();
//            connection.setDoOutput(true); // Triggers POST.
//            connection.setRequestProperty("Accept-Charset", charset);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//            String query = "client_id=129083993623-1ue1oaqml454ko1a9ibos00eqv2g64k5.apps.googleusercontent.com&client_secret=gongsiyouxiang&code=AH-1Ng0zXRZclPVfjLmiZ_KpkC4uTerxLBYNsEpFND9dOBIAFeWQMTeGoJFpteZViVTo-jgVfNXnGyKGGhk7FrjX3zaDAST0JQ&grant_type=http://oauth.net/grant_type/device/1.0";
//            OutputStream output = connection.getOutputStream();
//            output.write(query.getBytes(charset));
//            InputStream response = connection.getInputStream();
//            System.out.println(response);
//            if (response != null) {
//                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int len = 0;
//                while ((len = response.read(buffer)) != -1) {
//                    outStream.write(buffer, 0, len);
//                }
//                response.close();
//                System.out.println( new String(outStream.toByteArray(),"UTF-8"));
//            }
////            String x = response.read(query);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try {
            URL url = new URL(u);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            System.out.println(conn.getResponseCode());
            System.out.println(conn.getResponseMessage());
            InputStream is = conn.getInputStream();
            if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                return new String(outStream.toByteArray(),"UTF-8");
            }
            return null;//conn.getResponseMessage();

        }  catch (IOException e) {
            e.printStackTrace();
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