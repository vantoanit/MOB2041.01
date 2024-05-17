package com.example.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.DTO.PhieuMuonDTO;
import com.example.duanmau.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class PhieuMuonDAO {
    MyDbHelper myDbHelper;
    public PhieuMuonDAO(Context context){
        myDbHelper = new MyDbHelper(context);
    }

    public ArrayList<PhieuMuonDTO> getDSPhieuMuon(){
        ArrayList<PhieuMuonDTO> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach", null);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuonDTO(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));

            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean thayDoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON", values,"mapm =?",new String[]{String.valueOf(mapm)});
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean themPhieuMuon(PhieuMuonDTO phieuMuonDTO){
        SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // mapm INTEGER PRIMARY KEY AUTOINCREMENT , matv INTEGER REFERENCES THANHVIEN(matv) , matt TEXT REFERENCES THUTHU(matt), masach INTEGER REFERENCES SACH(masach) , ngay TEXT , trasach INTEGER , tienthue INTEGER)
        values.put("matv", phieuMuonDTO.getMatv());
        values.put("matt", phieuMuonDTO.getMatt());
        values.put("masach", phieuMuonDTO.getMasach());
        values.put("ngay", phieuMuonDTO.getNgay());
        values.put("trasach", phieuMuonDTO.getTrasach());
        values.put("tienthue", phieuMuonDTO.getTienthue());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, values);
        if(check ==-1){
            return false;
        }
        return true;
    }
}
