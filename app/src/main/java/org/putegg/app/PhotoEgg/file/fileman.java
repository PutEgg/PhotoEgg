package org.putegg.app.PhotoEgg.file;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hp on 2018/1/18.
 * 用于文件操作
 */

public class fileman {

    public void fileman(){

    }

    public void createUserHome(String uname){
        //创建新用户个人根目录
        File f = new File("/data/data/org.putegg.app.PhotoEgg/users/"+uname+"/");
            f.mkdirs();
    }

        //获取用户个人根目录绝对路径
    public String getUserHomeString(String uname){
        String a ="/data/data/org.putegg.app.PhotoEgg/users/"+uname+"/";
        return a;
    }

        //创建测试文件
    public void createFile(String userdir,String filename) throws IOException {
        File file = new File(userdir,filename);
        file.createNewFile();
    }

}
