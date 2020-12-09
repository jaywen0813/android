package com.dpad.telematicsclientapp.util.utils;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;


public class JSObject {
	/*
	 * 绑定的object对象
	 */
	private Context context;
	private Activity activity;
	public JSObject(Context context) {
		this.context = context;
		activity = (Activity) context;
	}

	/*
	 * JS调用android的方法
	 * 
	 * @JavascriptInterface仍然必不可少
	 */
	@JavascriptInterface
	public String JsCallAndroid() {
//		if (MainApplicaton.sIsLogin) {
//			Intent i_main = new Intent(context, LoginActivity.class);
//			context.startActivity(i_main);
//			return "";
//		}
//		Intent i_main = new Intent(context, ShowDataListActivity.class);
//		context.startActivity(i_main);
//		activity.finish();
		return "JS call Andorid";
	}
}