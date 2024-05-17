package com.example.duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.DTO.TopDTO;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ThongKeDAO {
    MyDbHelper myDbHelper;
    public ThongKeDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    public ArrayList<SachDTO> getTop10(){
        ArrayList<SachDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
                list.add(new SachDTO(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/", "");
        ngayketthuc =  ngayketthuc.replace("/", "");
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) BETWEEN ? AND ?", new String[]{ngaybatdau, ngayketthuc});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }


}
