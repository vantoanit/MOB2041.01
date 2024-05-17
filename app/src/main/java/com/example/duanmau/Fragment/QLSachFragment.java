package com.example.duanmau.Fragment;

import android.content.DialogInterface;
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

import com.example.duanmau.Adapter.SachAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DTO.LoaiSachDTO;
import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSachFragment extends Fragment {
    SachDAO sachDAO;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);

        recyclerView = view.findViewById(R.id.recycleSach);
        FloatingActionButton floadAdd = view.findViewById(R.id.float_add);

         sachDAO = new SachDAO(getContext());
         loadData();


        floadAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLog();
            }
        });
        return view;
    }

    private void loadData(){
        ArrayList<SachDTO> list = sachDAO.getDSDauSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SachAdapter sachAdapter = new SachAdapter(getContext(), list, getDSLoaiSach(), sachDAO);
        recyclerView.setAdapter(sachAdapter);
    }

    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText editTien = view.findViewById(R.id.edtTien);
        Spinner spinner = view.findViewById(R.id.spl_loaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getDSLoaiSach(), android.R.layout.simple_list_item_1,new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spinner.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tensach = edtTenSach.getText().toString();
                int tien =Integer.parseInt( editTien.getText().toString());
                HashMap<String , Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = sachDAO.InsertSach(tensach, tien, maloai);
                if(check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private ArrayList<HashMap<String , Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSachDTO> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap <String , Object>> listtHM = new ArrayList<>();

        for (LoaiSachDTO dto : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai",dto.getId());
            hs.put("tenloai", dto.getTenloai());
            listtHM.add(hs);
        }

        return listtHM;
    }
}
