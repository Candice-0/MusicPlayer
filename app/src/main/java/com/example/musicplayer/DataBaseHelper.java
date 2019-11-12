package com.example.musicplayer;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class DataBaseHelper extends SQLiteOpenHelper {

    public  static final String CREATE_SONGS="create table songs ("
            +"name text)";

    private Context mContext;

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_SONGS);
        Toast.makeText(mContext,"成功创建数据库",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
