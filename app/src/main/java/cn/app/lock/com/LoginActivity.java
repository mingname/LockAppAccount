package cn.app.lock.com;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.app.lock.com.config.ZxtUrls;
import cn.app.lock.com.http.AsyncHttpResponseHandler;
import cn.app.lock.com.http.JsonHttpResponseHandler;
import cn.app.lock.com.http.RequestParams;
import cn.app.lock.com.utils.CommonUtils;
import cn.app.lock.com.utils.MD5;
import cn.app.lock.com.utils.TPYHttpClient;
import cn.app.lock.com.utils.ToastUtil;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fs_login);

        sendLoginRequestZXT();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return false;
    }





    /**
     * 发送支付请求
     *
     */
    private void sendLoginRequestZXT() {
        RequestParams params = new RequestParams();



        String times = CommonUtils.getTimestamp();
        String md5Sign = "userid=49543&orderid="+times+"&bankid=zhifubao-wap&keyvalue=QrEZpDp5gbp0LpLbMeUWm76mrY4J5UZCeXZ1xScj";
        Log.e("md5加密内容",md5Sign);
        String md5SignOk = MD5.MD5Encryption(md5Sign);

        String urlParams = "?userid=49543&orderid="+times+"&money=10&hrefurl=https://www.tye-net.com/zotyeusermanage&url=https://www.tye-net.com/zotyeusermanage&bankid=zhifubao-wap&sign="+md5SignOk+"&ext=";




        JSONObject jsonObj  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //创建jsonObj1
        try {
            jsonObj.put("type", "zx08150322zx");
            jsonObj.put("crashEvent", "10");
            jsonObj.put("turnoverEvent", "10");
            jsonObj.put("overspeedEvent", "10");
            jsonObj.put("fastspeedupEvent", "10");
            jsonObj.put("fastspeedcutEvent", "10");
            jsonObj.put("fastwheelEvent", "10");
            jsonObj.put("fastchangelaneEvent", "10");
            jsonObj.put("fatiguedrivingEvent", "10");
            jsonObj.put("totalCount", "10");

            jsonObj.put("totalTime", "javagao0322");
            jsonObj.put("totalMileage", "10");
            jsonObj.put("driverId", "qq");
            jsonObj.put("numberplate", "787279826");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("jsonObj:"+jsonObj);

        //往JSONArray中添加JSONObject对象。发现JSONArray跟JSONObject的区别就是JSONArray比JSONObject多中括号[]
        jsonArray.put(jsonObj);
        System.out.println("jsonArray:"+jsonArray);

        JSONObject jsonObj4  = new JSONObject();
        try {
            jsonObj4.put("driverScore", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("jsonObj4:"+jsonObj4);

        ByteArrayEntity entity = null;
        try {
            Log.e("hbjson------||| ",jsonObj4.toString());
            entity = new ByteArrayEntity(jsonObj4.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }





        TPYHttpClient.post(getApplicationContext(), ZxtUrls.WEB_ULR, entity,"application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("rs",response.toString());
                String sss = response.toString();

                if(sss != null && sss != ""){
                    handleResultHbTxString(response);
                }else{
                    ToastUtil.show("服务器返回数据为空！");
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtil.show("获取失败");
            }

        });
    }


    private void handleResultHbTxString(JSONObject resultString) {


    }






}
