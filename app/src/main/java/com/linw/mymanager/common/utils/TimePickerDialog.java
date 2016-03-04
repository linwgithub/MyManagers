package com.linw.mymanager.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pollysoft.common.R;

import java.util.Calendar;

public class TimePickerDialog implements TimePicker.OnTimeChangedListener {

    private AlertDialog alertDialog;
    private String timeString;
    private int[] timeArray = new int[2];
    private TextView tvInput;

    /*时间选择结果回调接口*/
    private TimeChangeCallback timeChangeCallback = null;

    public interface TimeChangeCallback{
        public void getCurTime(int[] curTimeArray);
    }

    public AlertDialog showTimePickerDialog(Activity activity, TextView inputView,
                                            int[] inputTime,
                                            TimeChangeCallback timeChangeCallback) {
        this.tvInput = inputView;
        this.timeArray = inputTime;
        this.timeChangeCallback = timeChangeCallback;
        View pickerView = activity.getLayoutInflater().inflate(R.layout.time_picker_layout, null);
        TimePicker timePicker = (TimePicker) pickerView.findViewById(R.id.picker_time);
        timePicker.setOnTimeChangedListener(this);

        initTimePicker(timePicker);

        return alertDialog = new AlertDialog.Builder(activity)
                .setView(pickerView)
                .setPositiveButton("设置", positiveClick)
                .setNegativeButton("取消", negativeClick)
                .show();
    }

    DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            tvInput.setTextColor(Color.rgb(50, 197, 249));
            tvInput.setText(timeString);
            timeChangeCallback.getCurTime(timeArray);
            dialog.dismiss();
        }
    };

    DialogInterface.OnClickListener negativeClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            if (tvInput.getText().equals("")) {
                tvInput.setTextColor(Color.rgb(255, 0, 0));
                tvInput.setText("您还未选择时间");
            }
            dialog.dismiss();
        }
    };

    private void initTimePicker(TimePicker timePicker) {

        long curTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        if (timeArray == null) {
            timeArray = new int[2];
            Log.e("timepacker:", "timeArray.length:" + timeArray.length);
            calendar.setTimeInMillis(curTime);
            timeArray[0] = calendar.HOUR_OF_DAY;
            timeArray[1] = calendar.MINUTE;
        }

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(timeArray[0]);
        timePicker.setCurrentMinute(timeArray[1]);
        timeString = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
    }

    private String[] timeStringSplit() {
        if (!TextUtils.isEmpty(timeString)) {
            String[] timeArray = timeString.split(":");
            return timeArray;
        }
        return null;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        timeArray[0] = hourOfDay;
        timeArray[1] = minute;
        timeString = hourOfDay + ":" + minute;
        Log.e("Time", timeString);
    }
}
