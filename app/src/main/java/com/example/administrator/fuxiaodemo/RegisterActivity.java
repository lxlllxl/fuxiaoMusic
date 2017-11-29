package com.example.administrator.fuxiaodemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册界面
 * Created by Administrator on 2017/8/5.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText user_ed, pwd_ed, repwd_ed;
    private Button back_bt, register_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        intivite();//初始化界面
    }

    private void intivite() {
        user_ed = (EditText) findViewById(R.id.r_userName);
        pwd_ed = (EditText) findViewById(R.id.r_userPassword);
        repwd_ed = (EditText) findViewById(R.id.r_ReuserPassword);
        //返回按钮的事件监听
        back_bt = (Button) findViewById(R.id.r_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login_register.class);
                startActivity(intent);
            }
        });
        //注册按钮的事件监听
        register_bt = (Button) findViewById(R.id.r_register);
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();
                String strrepwd = repwd_ed.getText().toString();
                //验证两次密码是否输入一致
                if (!strpwd.equals(strrepwd)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                    ((EditText) findViewById(R.id.r_userPassword)).setText("");
                    ((EditText) findViewById(R.id.r_ReuserPassword)).setText("");
                    ((EditText) findViewById(R.id.r_userPassword)).requestFocus();
                    return;

                }
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                ContentValues values = new ContentValues();
                values.put("name", struser);
                values.put("password", strpwd);


                SQLiteDatabase db = dbhelper.getWritableDatabase();
                //查询用户是否已经存在
                Cursor cusror = db.query("info", null, null, null, null, null, null);
                if (cusror.moveToFirst()) {
                    do {
                        String username = cusror.getString(cusror.getColumnIndex("name"));
                        if (username.equals(user_ed.getText().toString())) {
                            Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
                            ((EditText) findViewById(R.id.r_userName)).setText("");
                            return;
                        }


                    } while (cusror.moveToNext());

                }
                cusror.close();
                dbhelper.insertadmin(values);
                Toast.makeText(RegisterActivity.this, "用户注册成功", Toast.LENGTH_LONG).show();
                //用户注册成功，就跳转到登录页面
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
