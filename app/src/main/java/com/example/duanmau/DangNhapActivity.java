package com.example.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.DAO.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {
    EditText namedn , matkhaudn;
    Button btndangnhap , btnhuy;


    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        namedn = findViewById(R.id.edt_namedn);
        matkhaudn = findViewById(R.id.edt_matkhaudn);
        btnhuy = findViewById(R.id.btn_huy);
        btndangnhap = findViewById(R.id.btn_dang_nhap);

        ThuThuDAO thuDAO = new ThuThuDAO(this);
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = namedn.getText().toString();
                String matkhau = matkhaudn.getText().toString();

                if(thuDAO.checkDangNhap(user , matkhau)){

                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                    
                }else {
                    Toast.makeText(DangNhapActivity.this, "Usename và mật khẩu không đúng ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}