package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        EditText editTextNick = (EditText) findViewById(R.id.edit_nickname);
        EditText editTextMail = (EditText) findViewById(R.id.edit_mail);
        EditText editTextDorm = (EditText) findViewById(R.id.edit_dorm);
        EditText editTextOld = (EditText) findViewById(R.id.edit_old_key);
        final EditText editTextNew = (EditText) findViewById(R.id.edit_new_key);
        final EditText editTextYaNew = (EditText) findViewById(R.id.edit_new_key_confirm);
        Button button = (Button) findViewById(R.id.settings_ensure_button);
        Button button1 = (Button) findViewById(R.id.settings_info_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNew.getText().toString().equals(editTextYaNew.getText().toString())){
                    Toast.makeText(SettingsActivity.this,"新密码与确认密码不符",Toast.LENGTH_SHORT).show();
                }
                else{
                    //检查原密码等等
                    //如果无误
                    //上传新密码
                    editor.putBoolean("isEnsured", false); //将登录检查置为否
                    editor.commit();
                    Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传信息
                Toast.makeText(SettingsActivity.this,"个人信息已保存",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
