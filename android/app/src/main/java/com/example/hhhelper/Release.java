package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.nio.file.ProviderMismatchException;
import java.util.Calendar;
import java.util.Timer;

public class Release extends AppCompatActivity {
    private SharedPreferences preferences;
    private String myID;
    private Calendar mCalendar;
    private int mDate;
    private int mMonth;
    private int mYear;

    private int mHour;
    private int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendar = Calendar.getInstance();
        mDate = mCalendar.get(Calendar.DATE);
        mMonth = mCalendar.get(Calendar.MONTH)+1;
        mYear = mCalendar.get(Calendar.YEAR);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        setContentView(R.layout.activity_release);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        myID = preferences.getString("userID","");
        final EditText editTitle = (EditText) findViewById(R.id.release_title);
        final EditText editDetail = (EditText)findViewById(R.id.release_detail);
        final EditText editBonus = (EditText) findViewById(R.id.release_bonus);
        final TextView ddlDate = (TextView)findViewById(R.id.release_ddl_date);
        ddlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Release.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                ddlDate.setText(year+"."+month+"."+dayOfMonth);
                            }
                        },mYear,mMonth,mDate);
                datePickerDialog.show();
            }
        });

        final TextView ddlTime = (TextView)findViewById(R.id.release_ddl_time);
        ddlTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Release.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ddlTime.setText(hourOfDay+" : "+minute);
                            }
                        },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });


        final String submit_ddl = ddlDate.getText().toString().replace('.','-')+"-"+
                ddlTime.getText().toString().replace(':','-');

        Button releaseButton = (Button) findViewById(R.id.release_button);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                //如果成功
                if(Ticket.createBackendTicket(editTitle.getText().toString(),myID,editBonus.getText().toString(),submit_ddl,editDetail.getText().toString())!=null){
                    Intent intent = new Intent(Release.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Release.this,"创建订单失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
