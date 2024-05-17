package com.example.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.DTO.ThanhVienDTO;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    MyDbHelper myDbHelper;

    public SachDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    public ArrayList<SachDTO> getDSDauSach(){
        ArrayList<SachDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai FROM SACH sc, LOAISACH lo WHERE sc.maloai = lo.maloai ", null);
        if(cursor.getCount()!= 0){
            cursor.moveToFirst();
            do{
                list.add(new SachDTO(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4) ));
            }while (cursor.moveToNext());
        }
        return list;

    }


    public boolean InsertSach(String tensach, int giatien, int maloai){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach",tensach);
        values.put("giathue", giatien);
        values.put("maloai", maloai);

        long check = sqLiteDatabase.insert("SACH",null,values);
        if(check== -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean UpdateSach(int masach, String tensach, int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach",tensach);
        values.put("giathue", giathue);
        values.put("maloai", maloai);

        long check = sqLiteDatabase.update("SACH", values, "masach =?", new String[]{String.valueOf(masach)});
        if(check ==-1)
            return false;
        return true;
    }

    public int DeleteSach(int masach){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach =?", new String[]{String.valueOf(masach)});

        if(cursor.getCount()!= 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("SACH", "masach =?", new String[]{String.valueOf(masach)});
        if(check ==-1)
            return 0;
        return 1;
    }




}
