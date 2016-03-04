package com.linw.mymanager.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class NetworkUtil {

	public static final String NETWORK_TYPE_DISCONNECT = "discon";
	public static final String NETWORK_TYPE_UNKNOWN = "unknown";
	public static final String NETWORK_TYPE_2G = "2g";
	public static final String NETWORK_TYPE_3G = "3g";
	public static final String NETWORK_TYPE_4G = "4g";
	public static final String NETWORK_TYPE_WIFI = "wifi";

	/**
	 * 网络状态 （没有网络状态会有提示框出现）
	 *
	 * @param context
	 */
	public static void checkNetwork(final Activity context) {
		if (!isNetworkAvailable(context)) {
			TextView msg = new TextView(context);
			msg.setText("当前没有可以使用的网络，请设置网络！");
			new AlertDialog.Builder(context)
					.setTitle("网络状态提示")
					.setView(msg)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									// 跳转到设置界面
									context.startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
									context.finish();
								}
							}).create().show();
		}
	}

	/**
	 * 判断网络状态
	 *
	 * @param context
	 *            上下文
	 * @return true 表示有网络 false 表示没有网络
	 */
	public static boolean isNetworkAvailable(Context context) {
		// 获得网络状态管理器
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
			if (info != null) {
				for (NetworkInfo network : info) {
					if (network.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String getNetworkType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return NETWORK_TYPE_DISCONNECT;
		}

		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null || info.isConnected() == false) {
			return NETWORK_TYPE_DISCONNECT;
		}

		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return NETWORK_TYPE_WIFI;
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();
			if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
					|| subType == TelephonyManager.NETWORK_TYPE_EDGE) {
				return NETWORK_TYPE_2G;
			} else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
					|| subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
				return NETWORK_TYPE_3G;
			} else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {
				return NETWORK_TYPE_4G;
			}
		}

		return NETWORK_TYPE_UNKNOWN;
	}

}
