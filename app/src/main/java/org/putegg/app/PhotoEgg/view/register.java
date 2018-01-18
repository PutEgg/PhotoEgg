package org.putegg.app.PhotoEgg.view;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.putegg.app.PhotoEgg.R;
import org.putegg.app.PhotoEgg.database.dbadapter;

/**
 * Created PutEgg on 2018/1/17.
 */

public class register extends Activity {
    private EditText newUser;
    private EditText newPass;
    private EditText rePass;
    private Button regbtn;
    private Button backbtn;

    private dbadapter dbhelper;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

            SharedPreferences set = getSharedPreferences(login.my_prefs,0);
            Editor editor = set.edit();
            editor.putLong("uid",0);
            editor.commit();

        dbhelper = new dbadapter(this);
        dbhelper.open();
        setContentView(R.layout.activity_register);

        doThing();

    }

    //注册动作
    private void doThing(){

        newUser = (EditText)findViewById(R.id.newname);
        newPass = (EditText)findViewById(R.id.newpass);
        rePass = (EditText)findViewById(R.id.repass);
        regbtn = (Button)findViewById(R.id.regbtn);
        backbtn = (Button)findViewById(R.id.backbtn);

        //监听
        regbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                uRegister(v);
            }
        });

        backbtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                ubacklogin();
            }
        });

    }

    //滚回登录页
    private void ubacklogin(){
        finish();
    }

    //注册
    private void uRegister(View v){
        System.out.println("run");
        //获取注册数据
        String realname = newUser.getText().toString();
        String username = newUser.getText().toString().toLowerCase();
        String password = newPass.getText().toString();
        String repassword = rePass.getText().toString();


        //检查数据合法性
        if (username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),
                    "输入注册信息啊sb",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //检查密码
        if (!password.equals(repassword)){
            Toast.makeText(getApplicationContext(),
                    "两次密码不一样啊sb",
                    Toast.LENGTH_SHORT).show();
            newPass.setText("");
            rePass.setText("");
            return;
        }

        //加密为md5密文
        password = login.md5(password);

        //检查数据库方面情况
        Cursor user = dbhelper.getuser(username,password);
        if (user == null){
            Toast.makeText(getApplicationContext(),
                    "数据库挂了？？？",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            startManagingCursor(user);

            //检查是否已有此用户
            if (user.getCount()>0){
                Toast.makeText(getApplicationContext(),
                        "这账号已经存在了sb",
                        Toast.LENGTH_SHORT).show();
                stopManagingCursor(user);
                user.close();
                return;
            }
            stopManagingCursor(user);
            user.close();

            //创建新用户
            long id = dbhelper.createUser(username,password,realname);
            if (id>0){
                Toast.makeText(getApplicationContext(),
                        "注册成功",
                        Toast.LENGTH_SHORT).show();
                //这里记住的用户名传入真实ID
                saveloginid(id,realname,newPass.getText().toString());
                ubacklogin();
                /*
                *
                *Intent i = new Intent(v.getContext(),hub.class);
                *startActivity(i);
                *finish();
                *
                */
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "创建失败了sb",
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void saveloginid(long id,String username,String password){
        SharedPreferences set = getSharedPreferences(login.my_prefs,0);
        Editor editor = set.edit();
        editor.putLong("uid",id);
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }

}
