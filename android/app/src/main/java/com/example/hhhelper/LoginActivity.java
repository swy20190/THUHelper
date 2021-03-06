package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        final EditText account = (EditText) findViewById(R.id.login_account);
        final EditText password = (EditText) findViewById(R.id.login_password);
        boolean isRemembered = preferences.getBoolean("isRemembered", false);
        if(isRemembered){//自动填入账号密码
            String rememberedAccount = preferences.getString("account","");
            String rememberedPassword = preferences.getString("password","");
            account.setText(rememberedAccount);
            password.setText(rememberedPassword);
        }
        Button loginButton = (Button) findViewById(R.id.login_button);
        final CheckBox rememberInfo = (CheckBox)findViewById(R.id.login_remember);
        final TextView toRegister = (TextView)findViewById(R.id.login_toregister);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查密码
                String accountString = account.getText().toString();
                String passwordString = password.getText().toString();
                //通过网络检查账号密码是否匹配，没有网你玩个锤子
                String uid = getUserId(accountString, passwordString);
                if(uid.equals("invalid")){
                    Toast.makeText(LoginActivity.this,"账户与密码不符",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(rememberInfo.isChecked()){
                        editor.putBoolean("isRemembered",true);
                    }
                    editor.putString("userID",uid);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private String getUserId(String account, String password){
        //TODO
        //通过后台检查账户与密码
        String ret = "";
        if(account.equals("beast")&&password.equals("114514")){
            ret = "uid114514";
            editor.putBoolean("isEnsured",true);
            editor.commit();
        }
        else{
            ret = "invalid";
        }
        return ret;
    }
}
