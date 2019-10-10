package cn.app.lock.com.Service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.app.lock.com.LoginActivity;
import cn.app.lock.com.MainActivity;
import cn.app.lock.com.R;
import cn.app.lock.com.SrHanTengApplication;
import cn.app.lock.com.utils.CommonUtils;

/**
 * Created by xueqiaoming on 2017/10/18.
 */

public class LockService extends Service {


    private final String TAG = "LockService";

    private Handler mHandler = null;
    private final static int LOOPHANDLER = 0;
    private HandlerThread handlerThread = null;

    private final List<String> lockName = new ArrayList<String>();

    private boolean isUnLockActivity = false;

    //每隔100ms检查一次
    private static long cycleTime = 100;


    @Override
    public void onCreate() {
        super.onCreate();





        handlerThread = new HandlerThread("count_thread");
        handlerThread.start();
        //这里只是做了一个例子：只对360手机卫士做锁机制
        lockName.add("com.eg.android.AlipayGphone");
        lockName.add("com.tencent.mobileqq");
        lockName.add("com.miui.home");

        //开始循环检查
        mHandler = new Handler(handlerThread.getLooper()) {
            public void dispatchMessage(android.os.Message msg) {
                switch (msg.what) {
                    case LOOPHANDLER:
                        Log.i(TAG,"do something..."+(System.currentTimeMillis()/1000));
                        /**
                         * 这里需要注意的是：isLockName是用来判断当前的topActivity是不是我们需要加锁的应用
                         * 同时还是需要做一个判断，就是是否已经对这个app加过锁了，不然会出现一个问题
                         * 当我们打开app时，启动我们的加锁界面，解锁之后，回到了app,但是这时候又发现栈顶app是
                         * 需要加锁的app,那么这时候又启动了我们加锁界面，这样就出现死循环了。
                         * 可以自行的实验一下
                         * 所以这里用isUnLockActivity变量来做判断的
                         */
                        if(isLockName() && !isUnLockActivity){
                            Log.i(TAG, "locking...");
                            Intent intent = new Intent(LockService.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //调用了解锁界面之后，需要设置一下isUnLockActivity的值
//                            isUnLockActivity = true;


                        }
                        break;
                }
                mHandler.sendEmptyMessageDelayed(LOOPHANDLER, cycleTime);
            }
        };
        mHandler.sendEmptyMessage(LOOPHANDLER);
    }

    /**
     * 判断当前的Activity是不是我们开启解锁界面的app
     * @return
     */
    private boolean isLockName(){
        ActivityManager mActivityManager;
        mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        String packageName = topActivity.getPackageName();


        String sss = getLauncherTopApp(SrHanTengApplication.mContext);
        Log.e("xqm:",sss);



        //如果当前的Activity是桌面app,那么就需要将isUnLockActivity清空值

        List<String> listString = CommonUtils.getMapNames(getApplicationContext());

        if(listString.contains(packageName)){
            isUnLockActivity = false;
        }
        Log.v("LockService", "packageName == " + packageName);
        if("com.eg.android.AlipayGphone".equals(sss) || "com.tencent.mobileqq".equals(sss)){
            return true;
        }
        return false;
    }

    /**
     * 返回所有桌面app的包名
     * @return
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        //属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo){
            names.add(ri.activityInfo.packageName);
            System.out.println(ri.activityInfo.packageName);
        }
        return names;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器

        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))//设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("要显示的内容") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification);// 开始前台服务


        return Service.START_STICKY;
    }





    public static String getLauncherTopApp(Context context) {



        UsageStatsManager sUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        long endTime = calendar.getTimeInMillis();
//        calendar.add(Calendar.YEAR, -1);
//        long startTime = calendar.getTimeInMillis();
//        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> appTasks = activityManager.getRunningTasks(1);
            if (null != appTasks && !appTasks.isEmpty()) {
                return appTasks.get(0).topActivity.getPackageName();
            }
        } else {
            long endTime = System.currentTimeMillis();
            long beginTime = endTime - 10000;
            if (sUsageStatsManager == null) {
                sUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            }
            String result = "";
            UsageEvents.Event event = new UsageEvents.Event();
            UsageEvents usageEvents = sUsageStatsManager.queryEvents(beginTime, endTime);
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    result = event.getPackageName();
                }
            }
            if (!android.text.TextUtils.isEmpty(result)) {
                return result;
            }
        }
        return "";
    }








}
