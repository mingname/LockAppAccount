package cn.app.lock.com;

import android.app.Application;
import android.content.Context;

/**
 * Created by xueqiaoming on 2017/9/12.
 */

public class SrHanTengApplication extends Application {
    private static SrHanTengApplication mInstance;

    public static SrHanTengApplication getInstance() {
        return mInstance;
    }

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

    }






}
