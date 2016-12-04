package com.aucn.tv;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.aucn.tv.config.Config;

/**
 * Created by mac on 2016/11/16.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle saved){
        super.onCreate(saved);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                try{
                    Config.startLiveSchedule();
                    Config.initPlayListDatas();
                    Config.initUpdateToday();
                    while (!Config.initFinished){
                        Thread.sleep(1000);
                        System.out.println("waiting for playList init finish...");
                    }
                    Config.initPlayListDetails();
                }catch (Exception e){
                    e.printStackTrace();
                }
                SplashActivity.this.finish();
                Toast.makeText(getApplicationContext(), "澳洲中文电视台", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

}
