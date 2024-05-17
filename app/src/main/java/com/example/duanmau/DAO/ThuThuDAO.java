package com.example.duanmau.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.ThanhVienDTO;
import com.example.duanmau.DTO.ThuThuDTO;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    MyDbHelper myDbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context){
        myDbHelper = new MyDbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt =? AND matkhau =?", new String[]{matt, matkhau});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }

    public int capnhatmatkhau(String username, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt =? AND matkhau =?", new String[]{username, oldPass});
        if(cursor.getCount() >0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt =?", new String[]{username});
            if(check == -1){
                return -1;
            }else {
                return 1;
            }
        }
        return 0;
    }








}
