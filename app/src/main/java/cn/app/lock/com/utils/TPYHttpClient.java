package cn.app.lock.com.utils;


import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;

import cn.app.lock.com.config.ZxtUrls;
import cn.app.lock.com.http.AsyncHttpClient;
import cn.app.lock.com.http.AsyncHttpResponseHandler;
import cn.app.lock.com.http.RequestParams;
import cn.app.lock.com.http.ResponseHandlerInterface;


public class TPYHttpClient {
	private static final String THIS_FILE = "TPYHttpClient";

	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(40000);
		client.addHeader("Content-Type","application/json");
	}

	public static void post(String urlString, AsyncHttpResponseHandler res) {
		client.post(getAbsoluteUrl(urlString), res);
	}

	public static void post(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.post(getAbsoluteUrl(urlString), params, res);
	}


	public static void postUrl(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.post(urlString, params, res);
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(getAbsoluteUrl(urlString), res);
	}

	public static void getUrl(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.get(getAbsoluteUrl(urlString), params, res);
	}

	public static void post(Context context, String urlString, RequestParams params, AsyncHttpResponseHandler res) {
		client.post(context,urlString,null,params,null,res);
	}

	public static void post(Context context, String urlString, HttpEntity entity, String contentType,
                            ResponseHandlerInterface res) {

		client.post(context,urlString,null,entity,"application/json",res);
	}


	public static AsyncHttpClient getClient() {
		return client;
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		Log.e("relativeUrl------","" + ZxtUrls.WEB_ULR + relativeUrl);
		return ZxtUrls.WEB_ULR + relativeUrl;
	}
}
