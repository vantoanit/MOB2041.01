package com.example.duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.ThanhVienDAO;
import com.example.duanmau.DTO.ThanhVienDTO;
import com.example.duanmau.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {

    private Context context;
    ArrayList<ThanhVienDTO> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVienDTO> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMa_tv.setText("Mã Thành Viên: "+ list.get(position).getMatv());
        holder.txtName_tv.setText("Tên Thành Viên: "+ list.get(position).getHoten());
        holder.txtNgay_tv.setText("Năm Sinh :"+list.get(position).getNamsinh());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCapNhatTT(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = thanhVienDAO.DeleteThongTin(list.get(holder.getAdapterPosition()).getMatv());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành Viên Tồn Tại Phiếu Mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMa_tv, txtName_tv, txtNgay_tv;
        ImageView ivEdit, ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMa_tv = itemView.findViewById(R.id.txtMa_tv);
            txtName_tv = itemView.findViewById(R.id.txtName_tv);
            txtNgay_tv = itemView.findViewById(R.id.txtNgay_tv);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    private void showDialogCapNhatTT(ThanhVienDTO thanhVienDTO){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_thanhvien, null);
        builder.setView(view);


        TextView txt_ma_tv = view.findViewById(R.id.txt_ma_tv);
        EditText edt_ten_tv = view.findViewById(R.id.edt_ten_tv);
        EditText edt_ngay_tv = view.findViewById(R.id.edt_ngay_tv);

        txt_ma_tv.setText("Mã Thành Viên:"+ thanhVienDTO.getMatv());
        edt_ten_tv.setText(thanhVienDTO.getHoten());
        edt_ngay_tv.setText(thanhVienDTO.getNamsinh());

        builder.setNegativeButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String hoten = edt_ten_tv.getText().toString();
                String ngay = edt_ngay_tv.getText().toString();
                int id = thanhVienDTO.getMatv();

                boolean check = thanhVienDAO.UpdateThanhVien(id, hoten, ngay);
                if(check){
                    Toast.makeText(context, "Cập Nhật Thông Tin Thành Công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập Nhật Thông Tin Thất Bại", Toast.LENGTH_SHORT).show();
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
    private void loadData(){
        list.clear();
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}
