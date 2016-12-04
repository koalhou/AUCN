package com.open.androidtvwidget.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * 一些网络常用的接口.<br>
 * 记得要添加权限哈.<br>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * 
 * @author hailongqiu
 *
 */
public class NetWorkUtils {

	/**
	 * 网络是否可用
	 */
	public static boolean isNetWorkdetect(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = conn.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	public static boolean isIpReachable(String ip){
		try{
			InetAddress addr = InetAddress.getByName(ip);
			if (addr.isReachable(3000)){
				return true;
			}
			return false;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 判断有线网络.
	 */
	public static boolean checkEthernet(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conn.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
		return networkInfo.isConnected();
	}
	
}
