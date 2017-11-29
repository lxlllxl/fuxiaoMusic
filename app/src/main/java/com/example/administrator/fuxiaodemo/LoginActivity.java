package com.example.administrator.fuxiaodemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**登陆界面
 * Created by Administrator on 2017/8/5.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText user_ed, pwd_ed;
    private Button back_bt, login_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        invite();//界面初始化
    }

    private void invite() {
        user_ed = (EditText) findViewById(R.id.l_userName);
        pwd_ed = (EditText) findViewById(R.id.l_userPassword);
        //返回按钮的事件监听
        back_bt = (Button) findViewById(R.id.l_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Login_register.class);
                startActivity(intent);
            }
        });
        //登录按钮的事件监听
        login_bt = (Button) findViewById(R.id.l_login);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();
                DBHelper dbhelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                Cursor cursor = db.query("info", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String username = cursor.getString(cursor.getColumnIndex("name"));
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        if (username.equals(struser) && password.equals(strpwd)) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {

                            Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_LONG).show();
                        }

                    } while (cursor.moveToNext());

                }
                cursor.close();
            }
        });
    }
}
