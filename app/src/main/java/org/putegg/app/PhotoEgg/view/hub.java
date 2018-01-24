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

//废弃类

public class hub extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



        Intent i = new Intent(this.getApplicationContext(),org.putegg.app.PhotoEgg.rec.RecycleViewActivity.class);
        startActivity(i);



    }



}
