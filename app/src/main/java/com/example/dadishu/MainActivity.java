package com.example.dadishu;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    VideoView video_shouye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让 home、返回、菜单键 一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);

        setContentView(R.layout.activity_main);

        video_shouye = findViewById(R.id.video_shouye);

        video_shouye.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/video_shouye"));//设置要播放的视频路径
        video_shouye.start();//开始播放视频

        video_shouye.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            video_shouye.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
        //延时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation lAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_donghua);
                video_shouye.startAnimation(lAnimation);//加动画
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        }, 8000);
    }
}
