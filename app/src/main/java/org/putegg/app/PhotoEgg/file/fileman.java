package org.putegg.app.PhotoEgg.file;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public int flag;
    public void copyToThere(String filename,String filethere) throws IOException {
        File file = new File(filename);
        String name = file.getName();
        FileInputStream fin = new FileInputStream(file);
        int length = fin.available();
        byte[] buffer = new byte[length];
        fin.read(buffer);
        fin.close();
        String finalthere = filethere+name;
        if (!new File(finalthere).exists()){
        FileOutputStream fout = new FileOutputStream(finalthere);
        fout.write(buffer);
        fout.close();
        }
        else {
            flag++;
        }
    }

    public void delfile(String finalthere){

    }

}
