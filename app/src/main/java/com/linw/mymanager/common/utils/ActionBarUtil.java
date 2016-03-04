package com.linw.mymanager.common.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Administrator on 2016/1/5.
 */
public class ActionBarUtil {
    public static void hideActionBar(Activity ctx) {
        if (AndroidUtil.hasHoneycomb()) {
            ActionBar actionBar = ctx.getActionBar();
            if (actionBar!=null) {
                actionBar.hide();
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public static void hideActionBar(FragmentActivity ctx) {
        if (AndroidUtil.hasHoneycomb()) {
            ActionBar actionBar = ctx.getActionBar();
            if (actionBar!=null) {
                actionBar.hide();
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public static void initActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            if (android.os.Build.BRAND.toLowerCase().contains("meizu")) {
                actionBar.setDisplayShowHomeEnabled(false);
            } else {
                actionBar.setDisplayShowHomeEnabled(true);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
