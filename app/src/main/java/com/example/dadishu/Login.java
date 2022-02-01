package com.example.dadishu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    //    记住密码
    private SharedPreferences preferences;  //利用SharedPreferences
    private SharedPreferences.Editor editor;
    private CheckBox rememberPassword;  //定义记住密码


    Button Btn_login;//声明登录按钮
    EditText Text_user;//用户名
    EditText Text_password;//密码



    String true_user = "admin";
    String true_password = "123456";

    boolean isPasswordRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让 home、返回、菜单键 一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
//
        setContentView(R.layout.login_p2);
        Btn_login = findViewById(R.id.Login);
        Text_user = findViewById(R.id.text_user);
        Text_password = findViewById(R.id.text_password);



        //    记住密码
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPassword = findViewById(R.id.login_checkbox);
//
        isPasswordRemember = preferences.getBoolean("remember_password", false);

        if (isPasswordRemember) {
            System.out.println("设置密码");
            Text_user.setText(true_user);
            Text_password.setText(true_password);
            rememberPassword.setChecked(true);
        } else {
            rememberPassword.setChecked(false);
        }


        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Text_user.getText().toString().trim();
                String password = Text_password.getText().toString().trim();
                if (user.equals(true_user) && password.equals(true_password)) {
                    Intent Intent_shouye = new Intent(Login.this, start_game.class);
                    startActivity(Intent_shouye);

                } else {
                    Toast ts = Toast.makeText(getBaseContext(), "账号密码错误", Toast.LENGTH_LONG);

                    ts.show();
                }
                editor = preferences.edit();
                if (rememberPassword.isChecked()) {
                    editor.putBoolean("remember_password", true);
                } else {
                    editor.putBoolean("remember_password", false);
                }
                editor.apply();

            }
        });
    }


}
