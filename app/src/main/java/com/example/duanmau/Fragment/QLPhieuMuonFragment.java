package com.example.duanmau.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.PhieuMuonAdapter;
import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.PhieuMuonDTO;
import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.DTO.ThanhVienDTO;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerView;
    ArrayList<PhieuMuonDTO> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);

        recyclerView = view.findViewById(R.id.recycQLPhieuMuon);
        FloatingActionButton actionButton = view.findViewById(R.id.floatAdd);

        loadData();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

       return view;
    }
    private void loadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(list, getContext());
        recyclerView.setAdapter(phieuMuonAdapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_add_phieu_muon,null);
        Spinner spnThanh_Vien = view.findViewById(R.id.spnThanh_Vien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        getDataThanhVien(spnThanh_Vien);
        getDataSach(spnSach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                HashMap<String,Object> hsTV = (HashMap<String, Object>) spnThanh_Vien.getSelectedItem();
                int matv = (int) hsTV.get("matv");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");

                int tien = (int) hsSach.get("giathue");
                themPhieuMuon(matv, masach, tien);

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getDataThanhVien(Spinner spinnerTV){
        ThanhVienDAO  thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVienDTO> list = thanhVienDAO.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVienDTO tv : list){
            HashMap<String, Object> hs =new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),listHM, android.R.layout.simple_list_item_1, new String[]{"hoten"}, new int[]{android.R.id.text1}
        );
        spinnerTV.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spinnerSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<SachDTO> list = sachDAO.getDSDauSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (SachDTO sc : list){
            HashMap<String, Object> hs =new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach", sc.getTensach());
            hs.put("giathue", sc.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),listHM, android.R.layout.simple_list_item_1, new String[]{"tensach"}, new int[]{android.R.id.text1}
        );
        spinnerSach.setAdapter(simpleAdapter);
    }

    private void themPhieuMuon(int matv, int masach, int tien){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");

        // lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuonDTO phieuMuonDTO = new PhieuMuonDTO(matv, matt,masach, ngay, 0,tien);
        boolean kiemtra = phieuMuonDAO.themPhieuMuon(phieuMuonDTO);
        if(kiemtra){
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thành Công", Toast.LENGTH_SHORT).show();
            loadData();
        }else {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thất Bại", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
