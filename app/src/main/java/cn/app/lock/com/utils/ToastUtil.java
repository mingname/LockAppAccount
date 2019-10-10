package cn.app.lock.com.utils;

import android.widget.Toast;

import cn.app.lock.com.SrHanTengApplication;


/**
 * toast 提示util类
 * @author kevliu
 *
 */
public class ToastUtil {

	public static void show(String info) {
		Toast.makeText(SrHanTengApplication.mContext, info, Toast.LENGTH_LONG).show();
	}

	public static void show(int info) {
		Toast.makeText(SrHanTengApplication.mContext, info, Toast.LENGTH_LONG).show();
	}
}
