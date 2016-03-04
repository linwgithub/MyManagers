package com.linw.mymanager.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.pollysoft.common.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialog implements DatePicker.OnDateChangedListener {

    private DatePicker datePicker;
    private TextView tvInput;
    private AlertDialog alertDialog;
    private String dateString;
    private long selectTime;

    /*日期选择结果回调接口*/
    private DateChangeCallback dateChangeCallback = null;

    public interface DateChangeCallback{
        public void getCurDate(long selecttime);
    }

    public AlertDialog showDateDIalog(Activity activity,
                                      TextView inputView, long inputDate,
                                      DateChangeCallback dateChangeCallback) {
        this.tvInput = inputView;
        selectTime = inputDate;
        this.dateChangeCallback = dateChangeCallback;
        View pickerView = activity.getLayoutInflater().inflate(R.layout.date_picker_layout, null);
        datePicker = (DatePicker) pickerView.findViewById(R.id.picker_date);
        datePicker.setCalendarViewShown(false);
        initDatePickerTime();

        return alertDialog = new AlertDialog.Builder(activity)
                .setView(pickerView)
                .setPositiveButton("设置", positiveClick)
                .setNegativeButton("取消", negativeClick)
                .show();
    }

    DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            tvInput.setTextColor(Color.rgb(50, 197, 249));
            tvInput.setText(dateString);
            dateChangeCallback.getCurDate(selectTime);
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

    private void initDatePickerTime() {
        long curDate = System.currentTimeMillis();
        selectTime = (selectTime <= 0) ? curDate : selectTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectTime);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                this);
        dateString = setDateStr(selectTime);
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        //获取滚动时间结果
        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), 0,
                0);
        selectTime = calendar.getTimeInMillis();
        dateString = setDateStr(calendar.getTimeInMillis());
    }

    private String setDateStr(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date(date));
    }
}
