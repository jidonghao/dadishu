/*************
 * 游戏界面
 *  setMouseGone(flase;healthyNum);//设置心 三
 */
package com.example.dadishu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    public static TextView textViewDifficult,/*难度*/
            textViewTime,/*时间 */
            textViewFraction;//分数 右上角那仨
    public static int delay_time = 2500;//设置延迟时间 老鼠从出现到消失的时间
    public static ImageView gameHealthy1, gameHealthy2, gameHealthy3;//左上角心
    public static ImageView mouse0, mouse1, mouse2, mouse3, mouse4;//定义 老鼠 那五张图片
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
    public static int healthyNum = 3;//心数量
    public static int countDown = 15;
    int startCountDown = 3;//倒计时三秒给倒计时开始
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
            changeHealthy(true);// 设置心
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            mouse1.clearAnimation();
            mouseFlag[1] = false;
            System.out.println("定时器让2消失");
            changeHealthy(true);// 设置心
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            mouse2.clearAnimation();
            mouseFlag[2] = false;
            System.out.println("定时器让3消失");
            changeHealthy(true);// 设置心
        }
    };
    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            mouse3.clearAnimation();
            mouseFlag[3] = false;
            System.out.println("定时器让4消失");
            changeHealthy(true);// 设置心
        }
    };
    Runnable runnable4 = new Runnable() {
        @Override
        public void run() {
            mouse4.clearAnimation();
            mouseFlag[4] = false;
            System.out.println("定时器让5消失");
            changeHealthy(true);// 设置心
        }
    };

    public static Timer timer_mouseDisplay = new Timer();//声明控制老鼠出现的定时器
    Timer timerCountDown = new Timer();
    int timeForCountDown = 3;
    public static game_page instance;

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

        setContentView(R.layout.game_page);
        instance = this;
        textViewDifficult = findViewById(R.id.textView_nandu);//难度
        textViewTime = findViewById(R.id.textView_time);//时间
        textViewFraction = findViewById(R.id.textView_fenshu);//分数
        // 倒计时：
        gamePageDaoJiShi = findViewById(R.id.gamePageDaoJiShi);
        gamePageDaoJiShi.setGravity(View.VISIBLE);
        // 生命
        gameHealthy1 = findViewById(R.id.game_page_xin1);
        gameHealthy2 = findViewById(R.id.game_page_xin2);
        gameHealthy3 = findViewById(R.id.game_page_xin3);

        textViewTime.setText("15s");//给时间15s

//        获取老鼠
        mouse0 = findViewById(R.id.mouse1);
        mouse1 = findViewById(R.id.mouse2);
        mouse2 = findViewById(R.id.mouse3);
        mouse3 = findViewById(R.id.mouse4);
        mouse4 = findViewById(R.id.mouse5);

//难度改变 根据 startGame 中选择的难度进行更改

        //获取老鼠移动的动画 根据难度
        switch (start_game.gameDifficult) {
            case 1:
                textViewDifficult.setText("简单");
                mouseChuXianTime = 1000;
                delay_time = 2500;
                break;
            case 2:
                textViewDifficult.setText("普通");
                mouseChuXianTime = 750;
                delay_time = 2000;
                break;
            case 3:
                textViewDifficult.setText("困难");
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


    /*********************************************
     * 函数名称：setGamePageTips
     * 函数功能：让提示页面出现，显示游戏结束的页面
     **********************************************/
    private void setGamePageTips(boolean flag) {

        timer_mouseDisplay.cancel();
        timerCountDown.cancel();
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

        game_page_tishi.setTipsView(temp_int_num, flag);//传分数
        timer_mouseDisplay = new Timer();
        startActivity(new Intent(game_page.this, game_page_tishi.class));

    }

    /********************************
     * 函数名称：startMouseDisplay
     * 函数随机让mouse出现
     *********************************/
    public void startMouseDisplay() {
        timerCountDown.schedule(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                if (timeForCountDown > 0) {
                    gamePageDaoJiShi.setText((timeForCountDown) + "");
                    timeForCountDown--;

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gamePageDaoJiShi.setVisibility(View.GONE);
                        }
                    });
                }
                startCountDown--;
                if (startCountDown < 0) {
                    if (countDown >= 0) {
                        textViewTime.setText(countDown + "s");
                        countDown--;
                    } else {
                        setGamePageTips(true);
                        setXinFlag = false;
                        System.out.println("定时器里的要跳转");

                    }
                }


            }
        }, 0, 1000);

        timer_mouseDisplay.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        int randomNum = (int) (Math.random() * 10) % 5;
                        int randomNumForMouseBg = (int) (Math.random() * 10) % 5;//产生范围0-2的随机数

                        View[] mouseViews = {mouse0, mouse1, mouse2, mouse3, mouse4};
                        int[] mouseBackgrounds = {
                                R.drawable.game_page_mouse1,
                                R.drawable.game_page_mouse2,
                                R.drawable.game_page_mouse3,
                                R.drawable.game_page_mouse4,
                                R.drawable.game_page_mouse5
                        };

                        if (!mouseFlag[randomNum]) {
                            // 设置背景图片
                            mouseViews[randomNum].setBackgroundResource(mouseBackgrounds[randomNumForMouseBg]);
                        }

                        mouseShow(randomNum);//随机数使老鼠出现
                    }
                });

            }
        }, 3000, mouseChuXianTime); //三秒延时执行

    }

    /*****************************************
     *  函数名称：changeHealthy
     *  左上角心数量更改
     *  num=1,2,3
     * *****************************************/
    public void changeHealthy(boolean flag) {
        System.out.println(" 左上角心数量更改:flag:" + flag + "; setXinFlag:" + setXinFlag);
        if (flag && setXinFlag) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (healthyNum > 0) {
                        healthyNum--;
                    }
                    switch (healthyNum) {
                        case 0:
                            setGamePageTips(false);
                            System.out.println("设置心里的要跳转");
                            //当心没有的时候显示提示框
                            gameHealthy1.setVisibility(View.GONE);
                            gameHealthy2.setVisibility(View.GONE);
                            gameHealthy3.setVisibility(View.GONE);
                            break;
                        case 1:
                            gameHealthy1.setVisibility(View.VISIBLE);
                            gameHealthy2.setVisibility(View.GONE);
                            gameHealthy3.setVisibility(View.GONE);
                            break;
                        case 2:
                            gameHealthy1.setVisibility(View.VISIBLE);
                            gameHealthy2.setVisibility(View.VISIBLE);
                            gameHealthy3.setVisibility(View.GONE);
                            break;
                    }
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHealthy1.setVisibility(View.VISIBLE);
                    gameHealthy2.setVisibility(View.VISIBLE);
                    gameHealthy3.setVisibility(View.VISIBLE);
                }
            });
            healthyNum = 3;
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
     ***************************/
    public void setFenShu(boolean flag) {
        if (flag) {
            textViewFraction.setText((++temp_int_num) + "分");
            //让他加分并显示
        } else {
            temp_int_num = 0;
            textViewFraction.setText("0分");
            //归零
        }

    }

    /************************
     *  函数名称：mouseShow()
     *  老鼠出现
     *  num=1,2,3,4,5
     *************************/
    public void mouseShow(int num) {
        if (healthyNum > 0) {
            switch (num) {
                case 0:
                    if (!mouseFlag[0]) {//判断 放置重复出现
                        mouse0.startAnimation(mouseMoveAnimation0);//让动画出现
                        mouseFlag[0] = true;
                        handler0.postDelayed(runnable0, delay_time); // 延时执行
                    }
                    break;
                case 1:
                    if (!mouseFlag[1]) {
                        mouse1.startAnimation(mouseMoveAnimation1);
                        mouseFlag[1] = true;
                        handler1.postDelayed(runnable1, delay_time); // 延时执行
                    }
                    break;
                case 2:
                    if (!mouseFlag[2]) {//判断 放置重复出现
                        mouse2.startAnimation(mouseMoveAnimation2);//让动画出现
                        mouseFlag[2] = true;
                        handler2.postDelayed(runnable2, delay_time); // 延时执行
                    }
                    break;
                case 3:
                    if (!mouseFlag[3]) {//判断 放置重复出现
                        mouse3.startAnimation(mouseMoveAnimation3);//让动画出现
                        mouseFlag[3] = true;
                        handler3.postDelayed(runnable3, delay_time); // 延时执行
                    }
                    break;
                case 4:
                    if (!mouseFlag[4]) {//判断 放置重复出现
                        mouse4.startAnimation(mouseMoveAnimation4);//让动画出现
                        mouseFlag[4] = true;
                        handler4.postDelayed(runnable4, delay_time); // 延时执行
                    }
                    break;
            }
        }
    }
}


