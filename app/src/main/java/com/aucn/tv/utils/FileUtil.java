package com.aucn.tv.utils;

/**
 * Created by mac on 2016/10/29.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FileUtil {
    private static final String TAG = "FileUtil";
    private static final String CACHE_DIR = "imgs";
    private static final String PL_DIR = "pldir";
    private static final String PL_FILE = "pls";

    public static Map<String,String> fileNames = new HashMap<>();

    public static File cacheFile(String imageUri){
        File cacheFile = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = getFileName(imageUri);
                File dir = new File(sdCardDir.getCanonicalPath() + "/" + CACHE_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                cacheFile = new File(dir, fileName);
                if(!cacheFile.exists()){
                    cacheFile.createNewFile();
                }
//                Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir + ",file:" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "getCacheFileError:" + e.getMessage());
        }

        return cacheFile;
    }

    public static Bitmap getCachedFile(String imageUri){
        Bitmap cacheFile = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = getFileName(imageUri);
                File dir = new File(sdCardDir.getCanonicalPath() + "/" + CACHE_DIR );
                File file = new File(dir, fileName);
                if(file.exists()){
                    FileInputStream fis = new FileInputStream(file);
                    cacheFile = BitmapFactory.decodeStream(fis);
                }
//                Log.i(TAG, "exists:" + file.exists() + ",dir:" + dir + ",file:" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "getCacheFileError:" + e.getMessage());
        }

        return cacheFile;
    }

    public static String getFileName(String path) {
//        int index = path.lastIndexOf("/");
//        return path.substring(index + 1);
        return path.replaceAll("/","-").replace(":","~");
    }

    public static void cachePlayList(String pls){
        File pl = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                File dir = new File(sdCardDir.getCanonicalPath() + "/" + PL_DIR );
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                pl = new File(dir, PL_FILE);
                if(!pl.exists()){
                    pl.createNewFile();
                }else{
                    pl.delete();
                    pl.createNewFile();
                }
                BufferedOutputStream bos = null;
                bos = new BufferedOutputStream(new FileOutputStream(pl));
                Log.i(TAG, "write file to " + pl.getCanonicalPath());

//                byte[] buf = new byte[1024];
                int len = 0;
                // 将网络上的图片存储到本地
                bos.write(pls.getBytes(), 0, len);
                bos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getCachedPls(){
        File pl = null;
        String tempString = "";
        BufferedReader reader = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                File dir = new File(sdCardDir.getCanonicalPath() + "/" + PL_DIR );
                pl = new File(dir, PL_FILE);
                if(!pl.exists()){
                    return "";
                }
                reader = new BufferedReader(new FileReader(pl));
                tempString = reader.readLine();
                reader.close();
            }

        }catch (Exception e){
            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return tempString;
    }
}