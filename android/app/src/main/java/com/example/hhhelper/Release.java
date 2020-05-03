package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Release extends AppCompatActivity {
    private Calendar mCalendar;
    private int mDate;
    private int mMonth;
    private int mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendar = Calendar.getInstance();
        mDate = mCalendar.get(Calendar.DATE);
        mMonth = mCalendar.get(Calendar.MONTH)+1;
        mYear = mCalendar.get(Calendar.YEAR);
        setContentView(R.layout.activity_release);
        EditText editTitle = (EditText) findViewById(R.id.release_title);
        EditText editDetail = (EditText)findViewById(R.id.release_detail);
        EditText editBonus = (EditText) findViewById(R.id.release_bonus);
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
        Button releaseButton = (Button) findViewById(R.id.release_button);
        releaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                //如果成功
                Intent intent = new Intent(Release.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
