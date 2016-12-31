package com.aucn.tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aucn.tv.utils.AsyncTaskImageLoad;

/**
 * Created by mac on 2016/11/16.
 */

public class VActivity extends Activity{

    @Override
    protected void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.vip);

        ImageView imageView = (ImageView)findViewById(R.id.vipImg);
        Intent i = getIntent();
        String title = (String)i.getExtras().get("title");
        String content = (String)i.getExtras().get("content");
        String img = (String)i.getExtras().get("img");
        LoadImage(imageView, img);
        TextView textViewTitle = (TextView)findViewById(R.id.vipTitle);
        textViewTitle.setText(content.replace(".jpg",""));
        TextView textViewContent = (TextView)findViewById(R.id.vipContent);
        textViewContent.setText(title);
    }
    private void LoadImage(ImageView img, String path){
        if(img.getDrawable() == null){
            //异步加载图片资源
            AsyncTaskImageLoad async = new AsyncTaskImageLoad(img);
            //执行异步加载，并把图片的路径传送过去
            async.execute(path);
        }
    }
}
