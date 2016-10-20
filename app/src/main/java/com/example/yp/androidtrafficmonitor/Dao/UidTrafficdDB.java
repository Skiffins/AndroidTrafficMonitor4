package com.example.yp.androidtrafficmonitor.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yp.androidtrafficmonitor.beans.AppInfo;
import com.example.yp.androidtrafficmonitor.utils.ArrayListUtil;

import java.util.ArrayList;

/**
 * Created by yp on 2016/9/26.
 */
public class UidTrafficdDB {

    private final static String mainDBName = "MainTraffic";
    private final static String viceDBName ="ViceTraffic";


    /*public static boolean isExistUid(Context context, int uid){

        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,"appTraffic");
        //dbOpenHelper.getReadableDatabase();获取只读的数据库
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();//获取可读可写的数据库
        Cursor cursor = sqLiteDatabase.query("appTraffic",new String[]{"uid"},null,null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
               //Log.v("DBuid",""+cursor.getColumnIndex("uid"));
               if(cursor.getInt(cursor.getColumnIndex("uid"))==uid){
                   return true;
            }
            //释放游标，防止访问数据量过大，导致出错。数据量过大要人工释放
        }
        cursor.close();
        sqLiteDatabase.close();
    }
        return false;

    }*/


    public static  void update (Context context, AppInfo appInfo,String DBName){
            DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DBName);
            //dbOpenHelper.getReadableDatabase();获取只读的数据库
            SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();//获取可读可写的数据库
            ContentValues values = new ContentValues();
            values.put("packageName", appInfo.packageName);
            values.put("appName", appInfo.appName);
            values.put("traffic", appInfo.traffic);
            sqLiteDatabase.update(DBName, values, "uid=?", new String[]{String.valueOf(appInfo.uid)});
            sqLiteDatabase.close();
        }

    public static  void insert(Context context, AppInfo appInfo, String DBName) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DBName);
        //dbOpenHelper.getReadableDatabase();获取只读的数据库
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();//获取可读可写的数据库
        ContentValues values = new ContentValues();
        values.put("uid", appInfo.uid);
        values.put("packageName", appInfo.packageName);
        values.put("appName", appInfo.appName);
        values.put("traffic", appInfo.traffic);
        sqLiteDatabase.replace(DBName, null, values);
        sqLiteDatabase.close();
    }


    public static boolean isExistUid(Context context, AppInfo appInfo){
        boolean flag = false;
        Log.v("Flg---------------","query");

        ArrayList<AppInfo>appInfoArrayList = new ArrayList<>();
        AppInfo appInfo2;

        DBOpenHelper dbOpenHelper = new DBOpenHelper(context,viceDBName);
        //dbOpenHelper.getReadableDatabase();获取只读的数据库
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();//获取可读可写的数据库

        Cursor cursor = sqLiteDatabase.query(viceDBName,null,"traffic>0",null,null,null,null);
        if(cursor!=null){
            while (cursor.moveToNext()) {
                appInfo2 = new AppInfo();
                appInfo2.uid = cursor.getInt(cursor.getColumnIndex("uid"));
                appInfo2.packageName = cursor.getString(cursor.getColumnIndex("packageName"));
                appInfo2.appName = cursor.getString(cursor.getColumnIndex("appName"));
                appInfo2.traffic = cursor.getInt(cursor.getColumnIndex("traffic"));
                appInfoArrayList.add(appInfo2);
                if(cursor.getString(cursor.getColumnIndex("appName")).equals(appInfo.appName)||
                        cursor.getString(cursor.getColumnIndex("packageName")).equals(appInfo.packageName)||
                        cursor.getInt(cursor.getColumnIndex("uid"))==appInfo.uid

                        ){
                    flag = true;
                }
            }
            //释放游标，防止访问数据量过大，导致出错。数据量过大要人工释放
            cursor.close();
        }
        sqLiteDatabase.close();

        ArrayListUtil.appInfoArrayList = appInfoArrayList;
        return flag;
    }

    public static void saveToMainDB(Context context, AppInfo appInfo){
        DBOpenHelper mainDBHelper = new DBOpenHelper(context,mainDBName);
        DBOpenHelper viceDBHelper = new DBOpenHelper(context,viceDBName);
        SQLiteDatabase mainDB = mainDBHelper.getWritableDatabase();
        SQLiteDatabase viceDB = viceDBHelper.getReadableDatabase();
        ArrayList<AppInfo>appInfos = new ArrayList<>();

    }

    public static void initViceDB(Context context){

    }

}
