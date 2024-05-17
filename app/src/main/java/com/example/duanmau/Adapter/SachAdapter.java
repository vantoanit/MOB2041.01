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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DAO.SachDAO;
import com.example.duanmau.DTO.SachDTO;
import com.example.duanmau.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SachDTO> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<SachDTO> list, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  inflater = ((Activity)context).getLayoutInflater();
        View  view = inflater.inflate(R.layout.item_recycle_sach, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Mã Sách: "+list.get(position).getMasach());
        holder.txtTenSach.setText("Tên Sách: "+ list.get(position).getTensach());
        holder.txtGiaThue.setText("Giá Thuê: "+list.get(position).getGiathue());
        holder.txtMaLoai.setText("Mã Loại: "+list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên Loại: "+list.get(position).getTenloai());

        holder.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLog(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = sachDAO.DeleteSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa ko thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "KO xóa đc sách này vì sách có trong phiếu mượn", Toast.LENGTH_SHORT).show();
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
        TextView txtMaSach, txtTenSach, txtGiaThue, txtMaLoai, txtTenLoai;
        ImageView ivSua , ivXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMa_Sach);
            txtTenSach = itemView.findViewById(R.id.txtTen_Sach);
            txtGiaThue = itemView.findViewById(R.id.txtGia_Thue_Sach);
            txtMaLoai = itemView.findViewById(R.id.txtMa_Loai);
            txtTenLoai = itemView.findViewById(R.id.txtTen_Loai);
            ivSua = itemView.findViewById(R.id.ivSua);
            ivXoa = itemView.findViewById(R.id.ivXoa);
        }
    }

    private void showDiaLog(SachDTO sachDTO){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spl_loaiSach = view.findViewById(R.id.spl_loaiSach);
        TextView txtMa_bock = view.findViewById(R.id.txtMa_bock);

        txtMa_bock.setText("Mã Sách"+ sachDTO.getMasach());
        edtTenSach.setText(sachDTO.getTensach());
        edtTien.setText(String.valueOf(sachDTO.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spl_loaiSach.setAdapter(simpleAdapter);
        int index = 0;
        int postion = -1;
        for(HashMap<String, Object> item : listHM){
            if((int) item.get("maloai") == sachDTO.getMaloai()){
                postion = index;
            }
            index++;
        }
        spl_loaiSach.setSelection(postion);

        builder.setNegativeButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String tensach = edtTenSach.getText().toString();
                int tien =Integer.parseInt( edtTien.getText().toString());
                HashMap<String , Object> hs = (HashMap<String, Object>) spl_loaiSach.getSelectedItem();
                int maloai = (int) hs.get("maloai");

                boolean check = sachDAO.UpdateSach( sachDTO.getMasach(), tensach, tien, maloai);
                if(check){
                    Toast.makeText(context, "Cập Nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập Nhật thất bại", Toast.LENGTH_SHORT).show();
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
        list = sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
}
