package com.example.duanmau.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {


    public MyDbHelper(Context context){
        super(context , "DuAnMau" , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbThuThu = "CREATE TABLE THUTHU (matt TEXT PRIMARY KEY , hoten TEXT , matkhau TEXT, loaitaikhoan TEXT)";
        db.execSQL(tbThuThu);

        String tbThanhVien = "CREATE TABLE THANHVIEN (matv INTEGER PRIMARY KEY AUTOINCREMENT , hoten TEXT , namsinh TEXT)";
        db.execSQL(tbThanhVien);

        String tbLoaiSach = "CREATE TABLE LOAISACH( maloai INTEGER PRIMARY KEY AUTOINCREMENT , tenloai TEXT)";
        db.execSQL(tbLoaiSach);

        String tbSach = "CREATE TABLE SACH(masach INTEGER PRIMARY KEY AUTOINCREMENT , tensach TEXT , giathue INTEGER , maloai INTEGER REFERENCES LOAISACH(maloai))";
        db.execSQL(tbSach);

        String tbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm INTEGER PRIMARY KEY AUTOINCREMENT , matv INTEGER REFERENCES THANHVIEN(matv) , matt TEXT REFERENCES THUTHU(matt), masach INTEGER REFERENCES SACH(masach) , ngay TEXT , trasach INTEGER , tienthue INTEGER)";
        db.execSQL(tbPhieuMuon);


        db.execSQL("INSERT INTO LOAISACH VALUES (1 , 'Trinh Thám') , (2 ,'Tiểu Thuyết') , (3 , 'Hài Hước')");
        db.execSQL("INSERT INTO SACH VALUES (1 , 'Conan' , 5500 , 1) , (2 , 'Du Ký' , 4500 ,1) , (3,'Đoremon' , 6500 ,3) ");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu01' , 'Nguyen Van A' , 'abc123', 'Admin'), ('thuthu02','Nguyen Van B','123','thủ thư')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Văn Toàn','2004'), (2, 'Phạm Thị B','2000')");

        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'thuthu01',1,'20/05/2022',1,4500),(2,2,'thuthu01',2,'12/02/2022',0,6500) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }

    }
}
