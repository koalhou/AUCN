package com.aucn.tv.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;


/**
 * Created by mac on 2016/10/30.
 */

public class AsyncTaskImageLoad extends AsyncTask<String, Integer, Bitmap> {

    private ImageView Image=null;

    public AsyncTaskImageLoad(ImageView img){
        Image=img;
    }
    //运行在子线程中
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bm = null;
        try {
            //先从本地缓存获取，如果未找到缓存，则从web端获取
            bm = FileUtil.getCachedFile(params[0]);
            System.out.println("---=============================="+bm);
            if(bm == null){
                bm = PicUtil.getbitmapAndwrite(params[0]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        if(Image!=null && result!=null) {
            Image.setImageBitmap(result);
        }
        super.onPostExecute(result);
    }
}