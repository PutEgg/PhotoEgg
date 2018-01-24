package org.putegg.app.PhotoEgg.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
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
    private Button go;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hub);

        go = (Button)findViewById(R.id.go);
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


        go.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),org.putegg.app.PhotoEgg.rec.RecycleViewActivity.class);
                startActivity(i);
            }

        });


    }

    public void bugpoint(String text){
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

}
