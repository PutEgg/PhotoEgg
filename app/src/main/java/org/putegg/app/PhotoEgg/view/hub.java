package org.putegg.app.PhotoEgg.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import org.putegg.app.PhotoEgg.file.fileman;
import org.putegg.app.PhotoEgg.R;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by PutEgg on 2018/1/17.
 */

public class hub extends Activity{
    private TextView banner;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hub);

        banner = (TextView)findViewById(R.id.ojbk);
        String user = login.nowUser;
        banner.setText(Html.fromHtml(user));
        banner.setMovementMethod(LinkMovementMethod.getInstance());

        fileman fileman = new fileman();
        String userHomeString = fileman.getUserHomeString(user);
        File userhome = new File(userHomeString);
        if (!userhome.exists()){
            fileman.createUserHome(user);
            bugpoint("初次登陆，成功创建用户文件夹");
        }
        else if (userhome.exists()){
            bugpoint("用户文件夹已找到");
        }
        try {
            fileman.createFile(userHomeString,user+".txt");
            bugpoint("成功创建测试文件");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void bugpoint(String text){
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

}
