package com.example.administrator.fuxiaodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 由主页面进入登录与注册的页面
 */

public class Login_register extends AppCompatActivity {
private Button login_bt,register_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        login_bt=(Button)findViewById(R.id.login);
        register_bt=(Button)findViewById(R.id.register);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_register.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_register.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
