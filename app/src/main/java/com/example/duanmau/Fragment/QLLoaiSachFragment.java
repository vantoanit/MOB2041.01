package com.example.duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.LoaiSachAdapter;
import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DTO.IteamClick;
import com.example.duanmau.DTO.LoaiSachDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class QLLoaiSachFragment extends Fragment {
    RecyclerView recyclerView;
    LoaiSachDAO loaiSachDAO;
    EditText edt_LoaiSach;
    int maloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlloaisach, container, false);

        recyclerView = view.findViewById(R.id.recycleLoaiSach);
        edt_LoaiSach = view.findViewById(R.id.edt_LoaiSach);
        Button btn_Them = view.findViewById(R.id.btn_Them);
        Button btn_sua = view.findViewById(R.id.btn_Sửa);


        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();

        btn_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = edt_LoaiSach.getText().toString();

                if(loaiSachDAO.themLoaiSach(tenloai)){
                    loadData();
                    edt_LoaiSach.setText("");
                }else {
                    Toast.makeText(getContext(), "Thêm Loai Sách Không Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenloai = edt_LoaiSach.getText().toString();
                LoaiSachDTO  loaiSachDTO = new LoaiSachDTO(maloai, tenloai);
                if(loaiSachDAO.EditLoaiSach(loaiSachDTO)){
                    loadData();
                    edt_LoaiSach.setText("");
                }else {
                    Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<LoaiSachDTO> list = loaiSachDAO.getDSLoaiSach();
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, new IteamClick() {
            @Override
            public void onClickLoaiSach(LoaiSachDTO loaiSachDTO) {
                edt_LoaiSach.setText(loaiSachDTO.getTenloai());
                maloai = loaiSachDTO.getId();

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
