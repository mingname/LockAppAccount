package cn.app.lock.com.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import cn.app.lock.com.SrHanTengApplication;


/**
 * Created by junjun on 2016/5/17.
 */
public class CommonUtils {

	/**
	 * 判断当前网络是否可用 不可用
	 *
	 * @return
	 */
	public static boolean isNetworkConnection() {
		boolean isConnect = false;
		ConnectivityManager manager = (ConnectivityManager) SrHanTengApplication.getInstance().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			isConnect = manager.getActiveNetworkInfo().isAvailable();
		}
		return isConnect;
	}

	/**
	 * 判断SDCard是否存在
	 *
	 * @return
	 */
	public static boolean isSDCardExist() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}

	/**
	 * 获得SDCard路径
	 *
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		if (isSDCardExist()) {
			sdDir = Environment.getExternalStorageDirectory();
		}
		return sdDir.toString();
	}



	/**
	 * 获取手机的唯一标示
	 */
	public static String getUdid() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimestamp() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * 获取时间戳加密
	 */
	public static String getTimestampAES() {
		return System.currentTimeMillis() + "zxtDfxec12fc";
	}

	/**
	 * 参数排序
	 */
	public static String[] sort(String[] array) {
		Arrays.sort(array);
		return array;
	}


	/**
	 * 拨打电话
	 *
	 * @param context
	 */
	public static void makeCall(Context context, String phone) {
		if (!TextUtils.isEmpty(phone)) {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} else {
			Toast.makeText(context, "无效的电话号码", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 转换时间为时分秒
	 *
	 * @param time
	 * @return
	 */
	public static String secToTime(long time) {
		String timeStr = null;
		long hour = 0;
		long minute = 0;
		long second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(long i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Long.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	public static long timeChangeMin(String time) {
		long min = 0;
		String[] times = time.split(":");
		String hourString = times[0];
		String minString = times[1];
		if (hourString.substring(0, 1).equals("0")) {
			hourString = hourString.substring(1, 2);
		}
		min = Integer.parseInt(hourString) * 60 + Integer.parseInt(minString);
		return min;
	}


	public static List<String> getMapNames(Context context) {
		List<String> mapList = new ArrayList<>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> list2 = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : list2) {
			String packageName = packageInfo.packageName;


			String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
			mapList.add(packageName+":"+appName );


			/*if (packageName.equals("com.baidu.BaiduMap")) {
				String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
				mapList.add(appName);
			}*/
		}

		return mapList;
	}

	/**
	 * final Activity activity  ：调用该方法的Activity实例
	 * long milliseconds ：震动的时长，单位是毫秒
	 * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
	 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
	 */

	public static void Vibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}
	public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
		Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}



	public static void getSystemMusic(final Context context, Uri uri){

		MediaPlayer mMediaPlayer = MediaPlayer.create(context, uri);
		mMediaPlayer.setLooping(false);
		try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mMediaPlayer.start();
	}





}
