/*************
 * 游戏界面
 *  setMouseGone(flase;XinShuLiang);//设置心 三
 *
 *
 *
 */
package com.example.dadishu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class game_page extends Activity {
    //    定义
    public static TextView textView_nandu,/*难度*/
            textView_time,/*时间 */
            textView_fenshu;//分数 右上角那仨
    public static int delay_time = 2500;//设置延迟时间 老鼠从出现到消失的时间
    public static ImageView game_pagexin1, game_pagexin2, game_pagexin3;//左上角心
    public static ImageView mouse0, mouse1, mouse2, mouse3, mouse4;//定义 老鼠 那五张图片
    //  Button btn_game_zanting;//右上角暂停
    Button btnMouse0, btnMouse1, btnMouse2, btnMouse3, btnMouse4;//能点击到的按钮
    public static boolean[] mouseFlag = {false, false, false, false, false};//表示老鼠是否出现
    public static int temp_int_num = 0;
    public static Animation
            mouseMoveAnimation0,
            mouseMoveAnimation1,
            mouseMoveAnimation2,
            mouseMoveAnimation3,
            mouseMoveAnimation4;//声明动画
    public static int mouseChuXianTime;
    public static int XinShuLiang = 3;//心数量
    public static int time_daojishi = 15;
    int daoijshisanmiaogeidingshiqikaiqi = 3;//倒计时三秒给倒计时开始
    public static boolean timerFlag = true;//给定时器一个flag表示定时器能否执行

//    public int[] mouse;

//    public static ImageView[] mouse;

    Handler handler0 = new Handler();
    Handler handler1 = new Handler();
    Handler handler2 = new Handler();
    Handler handler3 = new Handler();
    Handler handler4 = new Handler();

    Runnable runnable0 = new Runnable() {
        @Override
        public void run() {
            mouse0.clearAnimation();
            mouseFlag[0] = false;
            System.out.println("定时器让1消失");
            changeShengming(true);// 设置心
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            mouse1.clearAnimation();
            mouseFlag[1] = false;
            System.out.println("定时器让2消失");
            changeShengming(true);// 设置心
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            mouse2.clearAnimation();
            mouseFlag[2] = false;
            System.out.println("定时器让3消失");
            changeShengming(true);// 设置心
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            mouse3.clearAnimation();
            mouseFlag[3] = false;
            System.out.println("定时器让4消失");
            changeShengming(true);// 设置心
        }
    };
    Runnable runnable4 = new Runnable() {
        @Override
        public void run() {
            mouse4.clearAnimation();
            mouseFlag[4] = false;
            System.out.println("定时器让5消失");
            changeShengming(true);// 设置心
        }
    };

    public static Timer timer_mouseDisplay = new Timer();//声明控制老鼠出现的定时器
    Timer timerDaojishi = new Timer();
    int timeFordaojishi = 3;
    public static game_page instance;
    //在oncreate中添加

    static boolean setXinFlag = true;//控制心是否能被减少

    TextView gamePageDaoJiShi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让 home、返回、菜单键 一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
//
        setContentView(R.layout.game_page);
        instance = this;
        //
        textView_nandu = findViewById(R.id.textView_nandu);//难度
        textView_time = findViewById(R.id.textView_time);//时间
        textView_fenshu = findViewById(R.id.textView_fenshu);//分数
//倒计时：
        gamePageDaoJiShi = findViewById(R.id.gamePageDaoJiShi);
        gamePageDaoJiShi.setGravity(View.VISIBLE);
//        生命
        game_pagexin1 = findViewById(R.id.game_page_xin1);
        game_pagexin2 = findViewById(R.id.game_page_xin2);
        game_pagexin3 = findViewById(R.id.game_page_xin3);

        //按钮
//        btn_game_zanting = findViewById(R.id.btn_game_zanting);

        textView_time.setText("15s");//给时间15s

//        获取老鼠
        mouse0 = findViewById(R.id.mouse1);
        mouse1 = findViewById(R.id.mouse2);
        mouse2 = findViewById(R.id.mouse3);
        mouse3 = findViewById(R.id.mouse4);
        mouse4 = findViewById(R.id.mouse5);

//难度改变 根据 startgame 中选择的难度进行更改

        //获取老鼠移动的动画 根据难度
        switch (start_game.gamenandu) {
            case 1:
                textView_nandu.setText("简单");
                mouseChuXianTime = 1000;
                delay_time = 2500;
                break;
            case 2:
                textView_nandu.setText("普通");
                mouseChuXianTime = 750;
                delay_time = 2000;
                break;
            case 3:
                textView_nandu.setText("困难");
                mouseChuXianTime = 500;
                delay_time = 1200;
                break;
        }
//老鼠出现动画的设置
        mouseMoveAnimation0 = AnimationUtils.loadAnimation(game_page.this, R.anim.mouse_move_simple);
        mouseMoveAnimation0.setFillAfter(true);//设置动画持续到最后一帧

        mouseMoveAnimation1 = AnimationUtils.loadAnimation(game_page.this, R.anim.mouse_move_simple);
        mouseMoveAnimation1.setFillAfter(true);//设置动画持续到最后一帧

        mouseMoveAnimation2 = AnimationUtils.loadAnimation(game_page.this, R.anim.mouse_move_simple);
        mouseMoveAnimation2.setFillAfter(true);//设置动画持续到最后一帧

        mouseMoveAnimation3 = AnimationUtils.loadAnimation(game_page.this, R.anim.mouse_move_simple);
        mouseMoveAnimation3.setFillAfter(true);//设置动画持续到最后一帧

        mouseMoveAnimation4 = AnimationUtils.loadAnimation(game_page.this, R.anim.mouse_move_simple);
        mouseMoveAnimation4.setFillAfter(true);//设置动画持续到最后一帧


        btnMouse0 = findViewById(R.id.btnMouse1);
        btnMouse1 = findViewById(R.id.btnMouse2);
        btnMouse2 = findViewById(R.id.btnMouse3);
        btnMouse3 = findViewById(R.id.btnMouse4);
        btnMouse4 = findViewById(R.id.btnMouse5);

        //第一个老鼠对应的点击事件
        btnMouse0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMouseGone(0, true);
            }
        });
        btnMouse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMouseGone(1, true);
            }
        });
        btnMouse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMouseGone(2, true);
            }
        });
        btnMouse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMouseGone(3, true);
            }
        });
        btnMouse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMouseGone(4, true);
            }
        });
        //老鼠对应的点击完成
//
        startMouseDisplay();//让老鼠出现
    }


    /***************
     * 函数名称：setGamePageTishi
     * 函数功能：让提示页面出现，显示游戏结束的页面
     ****************/
    private void setGamePageTishi(boolean flag) {

        timer_mouseDisplay.cancel();
        timerDaojishi.cancel();
        mouse0.clearAnimation();
        mouse1.clearAnimation();
        mouse2.clearAnimation();
        mouse3.clearAnimation();
        mouse4.clearAnimation();
        handler0.removeCallbacks(runnable0);
        handler1.removeCallbacks(runnable1);
        handler2.removeCallbacks(runnable2);
        handler3.removeCallbacks(runnable3);
        handler4.removeCallbacks(runnable4);
        setXinFlag = true;

        game_page_tishi.setTishiView(temp_int_num, flag);//传分数
        timer_mouseDisplay = new Timer();
        startActivity(new Intent(game_page.this, game_page_tishi.class));

    }

    /********
     * 函数名称：startMouseDisplay
     * 函数随机让mouse出现
     */
    public void startMouseDisplay() {
        timerDaojishi.schedule(new TimerTask() {
            @Override
            public void run() {

                if (timeFordaojishi > 0) {
                    gamePageDaoJiShi.setText((timeFordaojishi) + "");
                    timeFordaojishi--;

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gamePageDaoJiShi.setVisibility(View.GONE);
                        }
                    });
                }
                daoijshisanmiaogeidingshiqikaiqi--;
                if (daoijshisanmiaogeidingshiqikaiqi < 0) {
                    if (time_daojishi >= 0) {
                        textView_time.setText(time_daojishi + "s");
                        time_daojishi--;
                    } else {
                        setGamePageTishi(true);
                        setXinFlag = false;
                        System.out.println("定时器里的要跳转");

                    }
                }


            }
        }, 0, 1000);

        timer_mouseDisplay.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        int radomNum = (int) (Math.random() * 10) % 5;
                        int radomNumForMouseBg = (int) (Math.random() * 10 )% 5;//产生范围0-2的随机数

                        if(mouseFlag[radomNum]==false){
                            System.out.println("随机更换皮肤");
                            switch (radomNum) {
                                case 0:
                                    switch (radomNumForMouseBg) {
                                        case 0:
                                            mouse0.setBackgroundResource(R.drawable.game_page_mouse1);
                                            break;
                                        case 1:
                                            mouse0.setBackgroundResource(R.drawable.game_page_mouse2);
                                            break;
                                        case 2:
                                            mouse0.setBackgroundResource(R.drawable.game_page_mouse3);
                                            break;
                                        case 3:
                                            mouse0.setBackgroundResource(R.drawable.game_page_mouse4);
                                            break;
                                        case 4:
                                            mouse0.setBackgroundResource(R.drawable.game_page_mouse5);
                                            break;
                                    }
                                    break;
                                case 1:
                                    switch (radomNumForMouseBg) {
                                        case 0:
                                            mouse1.setBackgroundResource(R.drawable.game_page_mouse1);
                                            break;
                                        case 1:
                                            mouse1.setBackgroundResource(R.drawable.game_page_mouse2);
                                            break;
                                        case 2:
                                            mouse1.setBackgroundResource(R.drawable.game_page_mouse3);
                                            break;
                                        case 3:
                                            mouse1.setBackgroundResource(R.drawable.game_page_mouse4);
                                            break;
                                        case 4:
                                            mouse1.setBackgroundResource(R.drawable.game_page_mouse5);
                                            break;
                                    }
                                    break;
                                case 2:
                                    switch (radomNumForMouseBg) {
                                        case 0:
                                            mouse2.setBackgroundResource(R.drawable.game_page_mouse1);
                                            break;
                                        case 1:
                                            mouse2.setBackgroundResource(R.drawable.game_page_mouse2);
                                            break;
                                        case 2:
                                            mouse2.setBackgroundResource(R.drawable.game_page_mouse3);
                                            break;
                                        case 3:
                                            mouse2.setBackgroundResource(R.drawable.game_page_mouse4);
                                            break;
                                        case 4:
                                            mouse2.setBackgroundResource(R.drawable.game_page_mouse5);
                                            break;
                                    }
                                    break;
                                case 3:
                                    switch (radomNumForMouseBg) {
                                        case 0:
                                            mouse3.setBackgroundResource(R.drawable.game_page_mouse1);
                                            break;
                                        case 1:
                                            mouse3.setBackgroundResource(R.drawable.game_page_mouse2);
                                            break;
                                        case 2:
                                            mouse3.setBackgroundResource(R.drawable.game_page_mouse3);
                                            break;
                                        case 3:
                                            mouse3.setBackgroundResource(R.drawable.game_page_mouse4);
                                            break;
                                        case 4:
                                            mouse3.setBackgroundResource(R.drawable.game_page_mouse5);
                                            break;
                                    }
                                    break;
                                case 4:
                                    switch (radomNumForMouseBg) {
                                        case 0:
                                            mouse4.setBackgroundResource(R.drawable.game_page_mouse1);
                                            break;
                                        case 1:
                                            mouse4.setBackgroundResource(R.drawable.game_page_mouse2);
                                            break;
                                        case 2:
                                            mouse4.setBackgroundResource(R.drawable.game_page_mouse3);
                                            break;
                                        case 3:
                                            mouse4.setBackgroundResource(R.drawable.game_page_mouse4);
                                            break;
                                        case 4:
                                            mouse4.setBackgroundResource(R.drawable.game_page_mouse5);
                                            break;
                                    }
                                    break;
                            }
                        }
                        mouseChuxian(radomNum);//随机数使老鼠出现


                    }
                });

            }
        }, 3000, mouseChuXianTime);//三秒延时执行，一秒执行一次

    }

    /*****************************************
     *  函数名称：changeShengming
     *  左上角心数量更改
     *  num=1,2,3
     * *****************************************/
    public void changeShengming(boolean flag) {
        System.out.println(" 左上角心数量更改:flag:"+flag+"; setXinFlag:"+setXinFlag);
        if (flag && setXinFlag) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (XinShuLiang > 0) {
                        XinShuLiang--;
                    }
                    switch (XinShuLiang) {
                        case 0:
                            setGamePageTishi(false);
                            System.out.println("设置心里的要跳转");
                            //当心没有的时候显示提示框
                            game_pagexin1.setVisibility(View.GONE);
                            game_pagexin2.setVisibility(View.GONE);
                            game_pagexin3.setVisibility(View.GONE);
                            break;
                        case 1:
                            game_pagexin1.setVisibility(View.VISIBLE);
                            game_pagexin2.setVisibility(View.GONE);
                            game_pagexin3.setVisibility(View.GONE);
                            break;
                        case 2:
                            game_pagexin1.setVisibility(View.VISIBLE);
                            game_pagexin2.setVisibility(View.VISIBLE);
                            game_pagexin3.setVisibility(View.GONE);
                            break;
                    }
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    game_pagexin1.setVisibility(View.VISIBLE);
                    game_pagexin2.setVisibility(View.VISIBLE);
                    game_pagexin3.setVisibility(View.VISIBLE);
                }
            });
            XinShuLiang = 3;
        }
    }

    /***************************************
     * 函数名称：setMouseGone()
     * mouseNO=0,4
     * 函数功能：让指定老鼠消失
     *****************************************/
    public void setMouseGone(int mouseNO, boolean flag) {
        if (mouseFlag[mouseNO]) {
            if (flag) {
                setFenShu(true);
            }
            mouseFlag[mouseNO] = false;//让他不能重复点击
            //点到之后接着把动画去掉，显示为直接消失
            switch (mouseNO) {
                case 0:
                    mouse0.clearAnimation();
                    handler0.removeCallbacks(runnable0);// 点击时应将延时器关闭
                    break;
                case 1:
                    mouse1.clearAnimation();
                    handler1.removeCallbacks(runnable1);// 点击时应将延时器关闭
                    break;
                case 2:
                    mouse2.clearAnimation();
                    handler2.removeCallbacks(runnable2);// 点击时应将延时器关闭
                    break;
                case 3:
                    mouse3.clearAnimation();
                    handler3.removeCallbacks(runnable3);// 点击时应将延时器关闭
                    break;
                case 4:
                    mouse4.clearAnimation();
                    handler4.removeCallbacks(runnable4);// 点击时应将延时器关闭
                    break;
            }
        }
    }

    /**************************
     *函数名称：setFenShu
     *函数功能 加分或显示0
     * flag = true:加分模式，false:重置
     */
    public void setFenShu(boolean flag) {
        if (flag) {
            textView_fenshu.setText((++temp_int_num) + "分");
            //让他加分并显示
        } else {
            temp_int_num = 0;
            textView_fenshu.setText("0分");
            //归零
        }

    }

    /************************
     *  函数名称：mouseChuxian()
     *  老鼠出现
     *  num=1,2,3,4,5
     *************************/
    public void mouseChuxian(int num) {
        if (XinShuLiang > 0) {
            switch (num) {
                case 0:
                    if (mouseFlag[0] == false) {//判断 放置重复出现
                        mouse0.startAnimation(mouseMoveAnimation0);//让动画出现
                        mouseFlag[0] = true;
                        handler0.postDelayed(runnable0, delay_time); // 延时执行
                    }
                    break;
                case 1:
                    if (mouseFlag[1] == false) {
                        mouse1.startAnimation(mouseMoveAnimation1);
//                    mouse1.setVisibility(View.VISIBLE);
                        mouseFlag[1] = true;
                        System.out.println("出现函数：2出现");
                        handler1.postDelayed(runnable1, delay_time); // 延时执行
                    }
                    break;
                case 2:
                    if (mouseFlag[2] == false) {//判断 放置重复出现
                        mouse2.startAnimation(mouseMoveAnimation2);//让动画出现
                        mouseFlag[2] = true;
                        handler2.postDelayed(runnable2, delay_time); // 延时执行
                    }
                    break;
                case 3:
                    if (mouseFlag[3] == false) {//判断 放置重复出现
                        mouse3.startAnimation(mouseMoveAnimation3);//让动画出现
                        mouseFlag[3] = true;
                        handler3.postDelayed(runnable3, delay_time); // 延时执行
                    }
                    break;
                case 4:
                    if (mouseFlag[4] == false) {//判断 放置重复出现
                        mouse4.startAnimation(mouseMoveAnimation4);//让动画出现
                        mouseFlag[4] = true;
                        handler4.postDelayed(runnable4, delay_time); // 延时执行
                    }
                    break;
            }
        }
    }
}


