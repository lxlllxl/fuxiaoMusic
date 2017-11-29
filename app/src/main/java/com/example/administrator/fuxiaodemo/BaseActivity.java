package com.example.administrator.fuxiaodemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	activitycollect.addActivity(this);
	}
	@Override
	protected void onDestroy() {
	super.onDestroy();
	activitycollect.removeActivity(this);
	}

}
