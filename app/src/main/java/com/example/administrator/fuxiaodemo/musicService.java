package com.example.administrator.fuxiaodemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.util.List;

/**
 * 后台音乐服务
 */
public class musicService extends Service {
    private MediaPlayer mediaPlayer;
    private List<Music> musicList;
    private int musicPosition;



    //startCommand启动服务时调用该方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getIntentData(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void getIntentData(Intent intent) {
        musicList = (List<Music>) intent.getSerializableExtra(playActivit.EXTRA_MEDIA_DATA);
        musicPosition = intent.getIntExtra(playActivit.EXTRA_MEDIA_POSITION, 0);
        if (musicList != null && musicList.size() != 0) {
            setDataSource(getCurrMusic().getData());

        }

    }

    private Music getCurrMusic() {

        return musicList.get(musicPosition);
    }

    private void setDataSource(String path) {
        setDataSource(Uri.parse(path));//播放资源的路径

    }

    private void setDataSource(Uri uri) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();

        }
        mediaPlayer = new MediaPlayer();//mediaPlayer实例化
        try {
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new Listener());

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    //先建一个类，继承绑定服务
    public class MusicBind extends Binder {
        musicService getService() {
            return musicService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MusicBind();
    }

    //播放
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    //暂停
    public void paruse() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    //拖动进度
    public void seekto(int position) {

        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);

        }
    }

    //得到音乐名字
    public String getMusicName() {

        return getCurrMusic().getMusicName();
    }

    public int getCurrentPosition() {
//得到当前播放的音乐位置
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    //得到总时间
    public int getduraction() {

        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();

        }
        return 0;
    }

    //上一首
    public void prev() {
        musicPosition--;
        if (musicPosition < 0) {
            musicPosition = musicList.size() - 1;
        }
        setDataSource(getCurrMusic().getData());
    }

    public void next() {
        musicPosition++;
        if (musicPosition > musicList.size()) {

            musicPosition = 0;
        }
        setDataSource(getCurrMusic().getData());
    }

    private class Listener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
        }
    }

}
