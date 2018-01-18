package org.putegg.app.PhotoEgg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by PutEgg on 2018/1/17.
 * 初始化数据库
 */

public class dbhelper extends SQLiteOpenHelper{
    //定义语句常量
    private static final String db_name = "login";
    private static final int db_version = 1;
    private static final String db_table = "create table user(id integer primary key autoincrement,username text not null, password text not null,realname text not null);";

    public dbhelper(Context context){
        super(context,db_name,null,db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldver, int newver) {
        //后期做数据迁移用
        Log.w(dbhelper.class.getName(),
                "数据库版本从"+oldver+"升级到"+newver+"覆盖所有旧数据");
        db.execSQL("drop table if exists user");
        onCreate(db);
    }
}
