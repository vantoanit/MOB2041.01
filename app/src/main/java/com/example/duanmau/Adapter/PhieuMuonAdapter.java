package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.PhieuMuonDAO;
import com.example.duanmau.DTO.PhieuMuonDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private ArrayList<PhieuMuonDTO> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuonDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuon, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText("Mã Phiếu Mượn: "+ list.get(position).getMapm());
        holder.txtMaTV.setText(""+ list.get(position).getMatv());
        holder.txtMaTT.setText("Mã Thủ Thư: "+ list.get(position).getMatt());
        holder.txtMaS.setText(""+ list.get(position).getMasach());
        holder.txtNgayM.setText("Ngày Mượn: "+ list.get(position).getNgay());
        holder.txtTenTV.setText(""+list.get(position).getTentv());
        holder.txttenTT.setText("Tên Thủ Thư:" +list.get(position).getTentt());
        holder.txtTenS.setText("" +list.get(position).getTensach());
        String trangthai = "";
        if(list.get(position).getTrasach()==1){
            trangthai = "Đã trả sách";
            holder.btn_tra_sach.setVisibility(View.GONE);
        }else {
            trangthai = "Chưa trả sách";
            holder.btn_tra_sach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText(""+ trangthai);
        holder.txtTien.setText("Tiền Thuê: "+ list.get(position).getTienthue());

        holder.btn_tra_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if(kiemtra){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPM, txtMaTV, txtMaTT, txtMaS, txtNgayM, txtTrangThai, txtTien , txtTenTV, txttenTT, txtTenS;
        Button btn_tra_sach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtMaS = itemView.findViewById(R.id.txtMaS);
            txtNgayM = itemView.findViewById(R.id.txtNgayM);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTien = itemView.findViewById(R.id.txtTien);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txttenTT = itemView.findViewById(R.id.txttenTT);
            txtTenS = itemView.findViewById(R.id.txtTenS);
            btn_tra_sach = itemView.findViewById(R.id.btn_tra_sach);

        }
    }
}
