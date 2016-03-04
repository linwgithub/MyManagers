package com.linw.mymanager.madicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linw.mymanager.R;
import com.linw.mymanager.common.utils.SharedPrefUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOGIN = 0x01;
    private static final int REQUEST_CODE_GUIDE = 0x10;
    private static final String LOG_TAG = "MainActivity";

    private SharedPrefUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = SharedPrefUtil.getSingleInstance(this);
        setContentView(R.layout.activity_main);

        loading();
    }

    private void loading() {

    }
}
