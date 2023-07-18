package com.example.dadishu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class game_page_tishi extends Activity {
    /*提示框：游戏暂停、结束、显示游戏结束的、得分的textview
     */
    public FrameLayout tishiKuang;//tishiKuang Layout
    public Button tishi_btn_1;//leftButton
    public Button tishi_btn_2;//rightButton
    public TextView textViewGameOver, textViewFenShu;
    public static String title = "游戏结束", btn1 = "返回首页", btn2 = "再来一次";
    public static int fenShu;
    public static boolean FlageWinDe;
    static ImageView imageViewWin,imageViewDe;//胜利失败的图片

    int daojishi = 5;
    
    ImageView btn_ImageView;
    Timer timer =new Timer();
    TextView gamePageTishiDaojishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让 home、返回、菜单键 一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        setContentView(R.layout.game_page_tishi);

        tishiKuang = findViewById(R.id.tishiMenu);//显示暂停和结束游戏的窗口
        tishi_btn_1 = findViewById(R.id.btn_game_tishi_fanhuishouye);//leftButton
        tishi_btn_2 = findViewById(R.id.btn_game_tishi_jixuyouxi);//rightButton

        textViewGameOver = findViewById(R.id.tishiMenuTextview);//显示游戏结束四个字的textview
        textViewFenShu = findViewById(R.id.tishiMenuFenShu);//提示框显示得分的textview

        imageViewWin=findViewById(R.id.gamePageTishi_win);
        imageViewDe = findViewById(R.id.gamePageTishi_de);

        gamePageTishiDaojishi = findViewById(R.id.gamePageTishiDaojishi);

        btn_ImageView = findViewById(R.id.btn_tiaoguo);
        btn_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                game_page.instance.finish();
                startActivity(new Intent(game_page_tishi.this, start_game.class));
                game_page.XinShuLiang = 3;//心数量
                game_page.time_daojishi = 15;//倒计时时间
                game_page.temp_int_num = 0;//分数
                game_page.timerFlag = true;
                timer = new Timer();
            }
        });
        tishi_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //必须是返回首页
                game_page.instance.finish();
                startActivity(new Intent(game_page_tishi.this, start_game.class));
                game_page.XinShuLiang = 3;//心数量
                game_page.time_daojishi = 15;//倒计时时间
                game_page.temp_int_num = 0;//分数
                game_page.timerFlag = true;
                start_game.gamenandu = 1;
            }
        });
        tishi_btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只有是再来一次
                game_page.instance.finish();
                startActivity(new Intent(game_page_tishi.this, game_page.class));
                game_page.time_daojishi = 15;
                game_page.XinShuLiang = 3;
                game_page.temp_int_num = 0;//
                game_page.timerFlag = true;
            }
        });
        setGamePageTiShiView();
    }
    /******
     * 用于接收上个传过来的值
     * flag 胜利还是失败
     */
    public static void setTishiView(int fenShu1,Boolean flag) {
        fenShu = fenShu1;

            FlageWinDe=flag;


    }

    /*************************
     * 函数名称：setGamePageTiShiView();
     * 设置暂停、游戏结束 出现的提示框的显示的内容；标题、得分、第一个按钮、第二个按钮
     * 参数：标题，分数，第一个按钮名称，第二个按钮名称
     *
     * 补充：title给gone 整体不显示
     *      分数写-1则不显示分数
     */
    public void setGamePageTiShiView() {
        tishi_btn_1.setText(btn1);//leftButton
        tishi_btn_2.setText(btn2);//rightButton
        //
        if (fenShu == -1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewFenShu.setVisibility(View.GONE);
                }
            });
        } else {
            textViewFenShu.setText("得分：" + fenShu + "");
        }
        if(FlageWinDe){
            imageViewWin.setVisibility(View.VISIBLE);
            imageViewDe.setVisibility(View.GONE);

            tishi_btn_1.setVisibility(View.GONE);
            tishi_btn_2.setVisibility(View.GONE);
            btn_ImageView.setVisibility(View.VISIBLE);
            gamePageTishiDaojishi.setVisibility(View.VISIBLE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gamePageTishiDaojishi.setText(daojishi+"");
                    if(daojishi>0){
                        daojishi--;
                    }else{
                        timer.cancel();
                        game_page.instance.finish();
                        startActivity(new Intent(game_page_tishi.this, start_game.class));
                        game_page.XinShuLiang = 3;//心数量
                        game_page.time_daojishi = 15;//倒计时时间
                        game_page.temp_int_num = 0;//分数
                        game_page.timerFlag = true;
                        timer = new Timer();
                    }
                }
            },0,1000);
        }else{
            gamePageTishiDaojishi.setVisibility(View.GONE);
            imageViewWin.setVisibility(View.GONE);
            imageViewDe.setVisibility(View.VISIBLE);

            tishi_btn_1.setVisibility(View.VISIBLE);
            tishi_btn_2.setVisibility(View.VISIBLE);
            btn_ImageView.setVisibility(View.GONE);
        }
    }

}
