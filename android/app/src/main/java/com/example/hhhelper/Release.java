package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Release extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        EditText editTitle = (EditText) findViewById(R.id.release_title);
        EditText editDetail = (EditText)findViewById(R.id.release_detail);
        EditText editDDL = (EditText) findViewById(R.id.release_ddl);
        EditText editBonus = (EditText) findViewById(R.id.release_bonus);
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
