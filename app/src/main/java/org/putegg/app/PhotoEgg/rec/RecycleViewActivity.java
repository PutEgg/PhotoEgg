package org.putegg.app.PhotoEgg.rec;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import org.putegg.app.PhotoEgg.photo.ImageUrlConfig;
import org.putegg.app.PhotoEgg.R;
import org.putegg.app.PhotoEgg.bean.UserViewInfo;
import org.putegg.app.PhotoEgg.photo.GPreviewBuilder;
import org.putegg.app.PhotoEgg.photopicker.PhotoPickerActivity;
import org.putegg.app.PhotoEgg.file.fileman;
import org.putegg.app.PhotoEgg.view.login;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends Activity {
    private ArrayList<UserViewInfo> mThumbViewInfoList = new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView mRecyclerView;
    private TextView noimg;
    private Button addimg;
    private List<String> mResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        String user = login.nowUser;

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





        addimg = (Button)findViewById(R.id.addimg);
        addimg.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(),PhotoPickerActivity.class);
                i.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
                i.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
                i.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 65535);
                startActivityForResult(i, 1);

            }

        });
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                try {
                    showResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void bugpoint(String text){
        Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }


    private void showResult(ArrayList<String> paths) throws IOException {
        if(mResults == null){
            mResults = new ArrayList<>();
        }
        mResults.clear();
        mResults.addAll(paths);
        fileman fman = new fileman();
        for (int x = 0; x<mResults.size(); x++){
            System.out.println(mResults.get(x));
            try {
                fman.copyToThere(mResults.get(x),fman.getUserHomeString(login.nowUser));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //重启本页
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        if (fman.flag!=0){
            Toast.makeText(getApplicationContext(),
                    fman.flag+"个文件已存在，自动跳过",
                    Toast.LENGTH_SHORT).show();
        }
    }



    //初始化数据和控件
    private void init(){
        //准备数据
        List<String> urls = ImageUrlConfig.getUrls();
        if (urls.isEmpty()){
            System.out.println("is fucking empty");

            Toast.makeText(getApplicationContext(),
                    "用户文件夹没有图片",
                    Toast.LENGTH_SHORT).show();
            noimg = (TextView)findViewById(R.id.noimg);
            mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
            mRecyclerView.setVisibility(View.GONE);
            noimg.setVisibility(View.VISIBLE);


            return;
        }
        for (String s : urls) {
            mThumbViewInfoList.add(new UserViewInfo(s));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        MyBaseQuickAdapter adapter=new MyBaseQuickAdapter(this);
        adapter.addData(mThumbViewInfoList);
        mRecyclerView.setAdapter(adapter);
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
               GPreviewBuilder.from(RecycleViewActivity.this)
                       .setData(mThumbViewInfoList)
                       .setCurrentIndex(position)
                       .setSingleFling(true)
                       .setType(GPreviewBuilder.IndicatorType.Number)
                       .start();
           }
       });


    }
    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos;i < mThumbViewInfoList.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.iv);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }
}
