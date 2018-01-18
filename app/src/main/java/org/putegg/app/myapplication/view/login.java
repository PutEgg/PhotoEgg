package org.putegg.app.myapplication.view;
import org.putegg.app.myapplication.database.dbadapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.putegg.app.myapplication.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login extends AppCompatActivity {

    public static final String my_prefs = "SharedPreferences";
    private dbadapter dbhelper;
    private EditText username;
    private EditText password;
    private Button loginbutton;
    private Button registerbutton;
    private CheckBox rememberme;
    public static String nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始缓存类
        SharedPreferences mysharedPreferences = getSharedPreferences(my_prefs,0);
        Editor editor = mysharedPreferences.edit();
        editor.putLong("uid",0);
        editor.commit();

        dbhelper = new dbadapter(this);
        dbhelper.open();
        setContentView(R.layout.activity_login);


        doThing();


    }

    private void doThing(){
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginbutton = (Button)findViewById(R.id.login);
        registerbutton = (Button)findViewById(R.id.register);
        rememberme = (CheckBox)findViewById(R.id.rem);

        //登录监听
        loginbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                ulogin(v);
            }
        });

        //注册监听
        registerbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
               uregister(v);
            }
        });

        //是否记住用户监听
        rememberme.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                uremember(v);
            }
        });

        //获取缓存用户数据
        SharedPreferences prefs = getSharedPreferences(my_prefs,0);;
        boolean thisRem = prefs.getBoolean("remember",false);
        if (thisRem == false){
            Editor editor = prefs.edit();
            editor.putString("realname","");
            editor.putString("password","");
            editor.putString("username","");
            editor.commit();
            username.setText("");
            password.setText("");
            rememberme.setChecked(false);
        }
        if (thisRem){
            String thisPass = prefs.getString("password","");
            String realUser = prefs.getString("realname","");
            username.setText(realUser);
            password.setText(thisPass);
            rememberme.setChecked(thisRem);
        }



    }


    //保存用户缓存数据方法
    private void saveloginid(long id,String username,String password,String realname){
        SharedPreferences set = getSharedPreferences(my_prefs,0);
        Editor editor = set.edit();
        editor.putLong("uid",id);
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("realname",realname);
        boolean rememberThis = rememberme.isChecked();
        editor.putBoolean("rememberThis",rememberThis);
        editor.commit();
    }



    //结束程序
    private void exit(){
        finish();
    }

    //rem方法
    private void uremember(View v){
        boolean thisrem = rememberme.isChecked();
        SharedPreferences prefs = getSharedPreferences(my_prefs,0);
        Editor editor = prefs.edit();
        editor.putBoolean("remember",thisrem);
        editor.commit();
    }

    //md5加密方法
    public static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }

        catch (NoSuchAlgorithmException e) {
            return s;
        }
    }

    //调试用TOAST
    public void bugpoint(String text){
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }


    //login实现
    private void ulogin(View v){
        //获取输入数据
        String realuser = username.getText().toString();
        String thisuser = username.getText().toString().toLowerCase();
        String thispass = password.getText().toString();
        //良心加密
        thispass = md5(thispass);

        //查询数据库
        Cursor theUser = dbhelper.getuser(thisuser,thispass);
        if (theUser!=null){
            startManagingCursor(theUser);
            if (theUser.getCount()>0){
                Toast.makeText(getApplicationContext(),
                        "登陆成功",
                        Toast.LENGTH_SHORT).show();
                saveloginid(theUser.getLong(theUser.getColumnIndex(dbadapter.col_id)),thisuser,password.getText().toString(),realuser);
                    stopManagingCursor(theUser);
                    theUser.close();
                    nowUser = thisuser;
                    Intent i = new Intent(v.getContext(),hub.class);
                    startActivity(i);
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "账号或密码错误啊sb",
                        Toast.LENGTH_SHORT).show();
                saveloginid(0,"","","");
            }
            stopManagingCursor(theUser);
            theUser.close();
        }

        else{
            Toast.makeText(getApplicationContext(),
                    "数据库挂了？？？",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //跳到注册页
    private void  uregister(View v){
        Intent i = new Intent(v.getContext(),register.class);
        startActivity(i);
    }


}
