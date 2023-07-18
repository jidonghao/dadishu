/**********************
 *  作为首页
 *  有按钮：开始游戏
 *  下拉列表：选择难度
 * *********************/

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class start_game extends AppCompatActivity {
    Button btn_startGame;
    public static int gamenandu = 1;//存储难度

    public static boolean startVideoFlag = true;        //声明是否播放视频的flag
    VideoView startGameVideo;                        //开始游戏选难度也就是首页的视频

    //    单选按钮
    RadioGroup radioGroup_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //让 home、返回、菜单键 一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);

        setContentView(R.layout.start_game);

        //设置video
        startGameVideo = findViewById(R.id.startGamevideo);
        startGameVideo.setVisibility(View.VISIBLE);
        startGameVideo.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/startgamevideo"));

        //解决黑屏，方式为设置video的背景，在加载完成后去除背景进行视频播放
        startGameVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            startGameVideo.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
        //解决黑屏，方式为设置video的背景，在加载完成后去除背景进行视频播放^


        if (startVideoFlag) {
            startGameVideo.start();
            System.out.println("startgame视频开始播放了");
            startVideoFlag = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //startGameVideo.setVisibility(View.GONE);
                        }
                    });
                }
            }, 5000);    // 应该5000,五秒视频
        }
        radioGroup_group = findViewById(R.id.radioGroup1);
        radioGroup_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioJiandan:
                        gamenandu = 1;
                        break;
                    case R.id.radioPutong:
                        gamenandu = 2;
                        break;
                    case R.id.radioKunnan:
                        gamenandu = 3;
                        break;
                }
            }
        });
        btn_startGame = findViewById(R.id.btn_startGame);
        btn_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_gamePage = new Intent(start_game.this, game_page.class);
                startActivity(intent_gamePage);
            }
        });
    }

}



