package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThuThuDAO;
import com.example.duanmau.Fragment.DoanhThuFragment;
import com.example.duanmau.Fragment.QLLoaiSachFragment;
import com.example.duanmau.Fragment.QLPhieuMuonFragment;
import com.example.duanmau.Fragment.QLSachFragment;
import com.example.duanmau.Fragment.QLThanhVienFragment;
import com.example.duanmau.Fragment.ThongKeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fm;
    QLLoaiSachFragment qlLoaiSachFragment;
    QLPhieuMuonFragment qlPhieuMuonFragment;
    ThongKeFragment thongKeFragment;
    DoanhThuFragment doanhThuFragment;
    QLThanhVienFragment qlThanhVienFragment;
    QLSachFragment qlSachFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.framelayout);
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerLayout);

        SachDAO sachDAO = new SachDAO(this);
        sachDAO.getDSDauSach();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open ,R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        qlLoaiSachFragment = new QLLoaiSachFragment();
        qlPhieuMuonFragment = new QLPhieuMuonFragment();
        thongKeFragment = new ThongKeFragment();
        doanhThuFragment = new DoanhThuFragment();
        qlThanhVienFragment = new QLThanhVienFragment();
        qlSachFragment = new QLSachFragment();

        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.framelayout, qlPhieuMuonFragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.QLPhieuMuon){
                    fm.beginTransaction().replace(R.id.framelayout, qlPhieuMuonFragment).commit();
                }else if(item.getItemId() == R.id.QLLoaiSach){
                    fm.beginTransaction().replace(R.id.framelayout, qlLoaiSachFragment).commit();
                }else if(item.getItemId() == R.id.QLSach){
                    fm.beginTransaction().replace(R.id.framelayout, qlSachFragment).commit();
                } else if(item.getItemId() == R.id.QLThanhVien){
                    fm.beginTransaction().replace(R.id.framelayout,qlThanhVienFragment).commit();
                } else if(item.getItemId() == R.id.top10){
                    fm.beginTransaction().replace(R.id.framelayout,thongKeFragment).commit();
                } else if (item.getItemId() == R.id.DoanhThu){
                    fm.beginTransaction().replace(R.id.framelayout,doanhThuFragment).commit();
                } else if(item.getItemId() ==R.id.DangXuat){
                    Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if(item.getItemId() == R.id.Doimk){
                    showDialogDoiMatKhau();
                }
                getSupportActionBar().setTitle(item.getTitle());
                drawerLayout.close();

                return true;
            }
        });

        // hien thi chuc năng cho admn
        SharedPreferences preferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaiTK = preferences.getString("loaitaikhoan","");
        if(!loaiTK.equals("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.DoanhThu).setVisible(false);
            menu.findItem(R.id.top10).setVisible(false);
        }


    }
    private void showDialogDoiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setNegativeButton("Cập Nhật", null).setPositiveButton("Hủy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_doimatkhau, null);

        EditText edtPassold = view.findViewById(R.id.edtPass_old);
        EditText edtPassnew = view.findViewById(R.id.edtPass_new);
        EditText edtPassrenew = view.findViewById(R.id.edtPass_renew);


        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Passold = edtPassold.getText().toString();
                String Passnew = edtPassnew.getText().toString();
                String Passrenew = edtPassrenew.getText().toString();
                if(Passold.equals("")|| Passnew.equals("")|| Passrenew.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if(Passnew.equals(Passrenew)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt", "");
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capnhatmatkhau(matt, Passold, Passnew);
                        if(check ==1) {
                            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }else if(check ==0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

}