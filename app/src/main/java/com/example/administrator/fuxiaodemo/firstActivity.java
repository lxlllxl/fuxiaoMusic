package com.example.administrator.fuxiaodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
*主页面
 */

public class firstActivity extends AppCompatActivity {
    private Button l_r, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        l_r = (Button) findViewById(R.id.log_reg);
        l_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstActivity.this, Login_register.class);
                startActivity(intent);
            }
        });
        right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
