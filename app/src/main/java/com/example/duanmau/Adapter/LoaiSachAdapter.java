package com.example.duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.LoaiSachDAO;
import com.example.duanmau.DTO.IteamClick;
import com.example.duanmau.DTO.LoaiSachDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSachDTO> list;
    private IteamClick iteamClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSachDTO> list, IteamClick iteamClick) {
        this.context = context;
        this.list = list;
        this.iteamClick = iteamClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtTenLoai.setText("Tên Loại: "+list.get(position).getTenloai());
            holder.txtMaLoai.setText("Mã Loại: "+String.valueOf(list.get(position).getId()));

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                    int check = loaiSachDAO.DeleteLoaiSach(list.get(holder.getAdapterPosition()).getId());
                    switch (check){
                        case 1:
                            list.clear();
                            list = loaiSachDAO.getDSLoaiSach();
                            notifyDataSetChanged();
                            break;
                        case -1:
                            Toast.makeText(context, "Ko thể xóa loại sách này vì đã có loại sách này", Toast.LENGTH_SHORT).show();
                            break;
                        case 0:
                            Toast.makeText(context, "Xóa loại sách ko thành công", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            });

            holder.iv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   LoaiSachDTO loaiSachDTO = list.get(holder.getAdapterPosition());
                   iteamClick.onClickLoaiSach(loaiSachDTO);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai , txtTenLoai;
        ImageView iv_delete , iv_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_edit = itemView.findViewById(R.id.iv_edit);
        }
    }
}
