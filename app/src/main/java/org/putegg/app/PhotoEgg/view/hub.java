package org.putegg.app.PhotoEgg.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.putegg.app.PhotoEgg.R;

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

    }

}
