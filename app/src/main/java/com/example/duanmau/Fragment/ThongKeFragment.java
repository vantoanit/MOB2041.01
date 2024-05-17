package com.example.duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.Adapter.ThongKeAdapter;
import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class ThongKeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        RecyclerView recycler_top10 = view.findViewById(R.id.recycThong_Ke);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<SachDTO> list = thongKeDAO.getTop10();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_top10.setLayoutManager(linearLayoutManager);
        ThongKeAdapter adapter = new ThongKeAdapter(getContext(),list);
        recycler_top10.setAdapter(adapter);

        return view;
    }
}
