package com.example.hhhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText account = (EditText) findViewById(R.id.register_account);
        EditText password = (EditText) findViewById(R.id.register_password);
        EditText passwordEnsure = (EditText) findViewById(R.id.register_passwordEnsure);
        Button register = (Button)findViewById(R.id.register_registerButton);
        String accountString = account.getText().toString();
        final String passwordString = password.getText().toString();
        final String passwordEnsureString = passwordEnsure.getText().toString();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordString.equals(passwordEnsureString)){
                    // 网络操作
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"确认密码与密码不符",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
