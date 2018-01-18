package org.putegg.app.PhotoEgg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PutEgg on 2018/1/17.
 */

public class dbadapter {

    private static final String login_table = "user";
    public static final String col_id = "id";
    public static final String col_username = "username";
    public static final String col_password = "password";
    public static final String col_realname = "realname";
    private Context context;
    private SQLiteDatabase db;
    private dbhelper dbhelper;

    public dbadapter(Context context){
        this.context = context;
    }
    //get数据库
    public dbadapter open() throws SQLException{
        dbhelper = new dbhelper(context);
        db = dbhelper.getWritableDatabase();
        return this;
    }

    //close数据库
    public void close(){
        dbhelper.close();
    }

    //创建新用户
    public long createUser(String uname,String upass,String relname){
        ContentValues ivalue = createUserTableContentValues(uname,upass,relname);
        return db.insert(login_table,null,ivalue);
    }

    //移除用户
    public boolean deluser(long rowid){
        return db.delete(login_table,col_id+"="+rowid,null) > 0;
    }

    public boolean upusertable(long rowid,String uname,String upass,String relname){
        ContentValues updateValues = createUserTableContentValues(uname,upass,relname);
        return  db.update(login_table,updateValues,col_id+"="+rowid,null) > 0;
    }


    //获取所有用户
    public Cursor getAllusers(){
        return db.query(login_table,new String[]{col_id,col_username,col_password},null,null,null,null,null);
    }

    //获取单个用户
    public Cursor getuser(String uname,String upass){
        Cursor mycursor = db.query(login_table,
                new String[]{col_id,col_username,col_password},
                col_username+"='"+uname+"' and "+
                col_password+"='"+upass+"'",null,null,null,null);
        if (mycursor!=null){
            mycursor.moveToFirst();
        }
        return mycursor;
    }

    //由ID头获取用户
    public Cursor getuserByid(long rowid) throws SQLException{
        Cursor mycursor = db.query(login_table,
                new String[]{col_id,col_username,col_password},
                col_id+"="+rowid,null,null,null,null);
        if (mycursor!=null){
            mycursor.moveToFirst();
        }
        return mycursor;
    }

    //把注册数据封装到键值对
    private ContentValues createUserTableContentValues(String uname, String upass, String realname) {
        ContentValues values = new ContentValues();
        values.put(col_username, uname);
        values.put(col_password, upass);
        values.put(col_realname, realname);
        return values;
    }
}
