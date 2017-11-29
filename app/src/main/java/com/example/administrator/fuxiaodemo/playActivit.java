package com.example.administrator.fuxiaodemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 播放界面
 */

public class playActivit extends AppCompatActivity implements ServiceConnection {
    //实现了服务接口,serviceConnction
    public static final String EXTRA_MEDIA_POSITION = "media_position";
    public static final String EXTRA_MEDIA_DATA = "media_data";
    private TextView music_title, play_time, duraction_time;
    private Button pre_btn, play_btn, next_btn;
    private SeekBar seekBar;
    private musicService service;
    private boolean playispause = false;//是否暂停

    //handleMessge更新UI,后台服务
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int curr = service.getCurrentPosition();
            int dura = service.getduraction();
            //将时间格式化
            playActivit.this.play_time.setText(TimeUtil.formatMilliSecond(curr));
            playActivit.this.duraction_time.setText(TimeUtil.formatMilliSecond(dura));
            seekBar.setMax(dura);//设置进度条的最大时间
            seekBar.setProgress(curr);//设置进度条的开始时间
            music_title.setText(service.getMusicName());//设置音乐名称
            sendEmptyMessageDelayed(0x0001, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        intivite();//初始化界面
        statBindService();//开启服务

    }

    private void statBindService() {
        Intent intent = getIntent();
        Intent serviceIntent = (Intent) intent.clone();
        serviceIntent.setClass(playActivit.this, musicService.class);
        startService(serviceIntent);
        bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
        handler.removeCallbacksAndMessages(null);
    }

    private void intivite() {
        music_title = (TextView) findViewById(R.id.music_title);
        play_time = (TextView) findViewById(R.id.play_time);
        duraction_time = (TextView) findViewById(R.id.durcation_time);
        play_btn = (Button) findViewById(R.id.play_btn);
        pre_btn = (Button) findViewById(R.id.pre_btn);
        next_btn = (Button) findViewById(R.id.next_btn);
        seekBar = (SeekBar) findViewById(R.id.seek_music);
        //播放按钮的事件监听
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playispause) {
                    service.play();
                    play_btn.setBackgroundResource(R.mipmap.ic_pause);
                    playispause=false;


                } else {
                    service.paruse();
                    play_btn.setBackgroundResource(R.mipmap.ic_play);
                    playispause=true;


                }

            }


        });
        //上一首的事件监听
        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.prev();
            }
        });
//下一首的事件监听
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.next();
            }
        });
        //拖动条的事件监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {

                    service.seekto(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        this.service = ((musicService.MusicBind) service).getService();
        handler.sendEmptyMessage(0x0001);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        this.service = null;
    }
}

