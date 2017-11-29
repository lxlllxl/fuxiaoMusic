package com.example.administrator.fuxiaodemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 获取本地歌曲列表的页面
 */

public class MainActivity extends BaseActivity{
    //退出时的时间
    private long mExitTime;
    private DrawerLayout mdrawerLayout;
    private ListView listView;
    private ArrayList<Music> musicList;
    //借助handleMessage来更新UI，将本地音乐加载到listview里
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (musicList != null && musicList.size() != 0) {
                String[] musicname = new String[musicList.size()];
                for (int i = 0; i < musicList.size(); i++) {

                    musicname[i] = musicList.get(i).getFileName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.music_item, R.id.i_music_name, musicname);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "您的本地音乐为空，请听网络音乐", Toast.LENGTH_LONG).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//navigation与xml相关联
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        navigationView.setCheckedItem(R.id.message);//设置菜单项的默认选项
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.exit:
                        finish();
                        break;
                    default:

                }
                mdrawerLayout.closeDrawers();//将滑动菜单关闭
                return true;
            }
        });
        intivite();//界面初始化
        localMusic();//得到本地音乐信息
    }

    private void localMusic() {
        //主线程来更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = MainActivity.this.getContentResolver();
                //android把所有多媒体都封装了接口，只需要contentResolver去调用就可以了
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                //查询的额内容信息,排列顺序一定不能错
                String[] obj = new String[]{
                        MediaStore.Audio.Media._ID,    //歌曲id
                        MediaStore.Audio.Media.DISPLAY_NAME,         //文件名
                        MediaStore.Audio.Media.DATA,       //歌曲信息
                        MediaStore.Audio.Media.TITLE         //歌曲名字

                };
                //使用cusor去查询本地信息，得到的信息加载到musicList里
                Cursor cusor = resolver.query(uri, obj, null, null, null);
                if (cusor == null) {

                    return;
                }
                musicList = new ArrayList<>();
                while (cusor.moveToNext()) {

                    Music music = new Music();
                    music.setId(cusor.getInt(0));
                    music.setFileName(cusor.getString(1));
                    music.setData(cusor.getString(2));
                    music.setMusicName(cusor.getString(3));
                    musicList.add(music);
                }
                cusor.close();
                handle.sendEmptyMessage(0x0001);
            }
        }).start();
    }

    private void intivite() {
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (musicList != null && musicList.get(position) != null) {
                    Intent intent = new Intent(MainActivity.this, playActivit.class);
                    intent.putExtra(playActivit.EXTRA_MEDIA_DATA, musicList);
                    intent.putExtra(playActivit.EXTRA_MEDIA_POSITION, position);
                    startActivity(intent);

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //最左侧的按钮叫HomeAsUp,id是home
                mdrawerLayout.openDrawer(GravityCompat.START);

                break;
            case R.id.search:
                Intent intent = new Intent(MainActivity.this, wyyMusic.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //点击两次退出程序
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }
}
