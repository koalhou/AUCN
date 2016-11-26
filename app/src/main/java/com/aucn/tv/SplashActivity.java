package com.aucn.tv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.aucn.tv.utils.AsyncTaskVideoLoad;

import static com.aucn.tv.config.Config.playLists;


/**
 * Created by mac on 2016/11/16.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.splash);
//        if(Config.allData == null || Config.allData.size() == 0){
        if(!Config.initFinished){
            Config.initPlayListDatas();
        }
        new Handler().postDelayed(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                for(DisplayBase db : playLists) {
                    AsyncTaskVideoLoad v = new AsyncTaskVideoLoad(db.entityId);
                    v.execute();
                }
                SplashActivity.this.finish();
                Toast.makeText(getApplicationContext(), "澳洲中文电视台", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

}
