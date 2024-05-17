package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DAO.ThongKeDAO;
import com.example.duanmau.R;

import java.util.Calendar;

public class DoanhThuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_doanhthu, container, false);

        EditText edtstart = view.findViewById(R.id.edt_Start);
        EditText edtend = view.findViewById(R.id.edt_End);
        Button btnthongke = view.findViewById(R.id.btn_thongKe);
        TextView txtketqua = view.findViewById(R.id.txtKetQua);

        Calendar calendar = Calendar.getInstance();
        edtstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay ="";
                        String thang = "";
                        if(i2<10){
                            ngay ="0"+i2;
                        }else {
                            ngay = String.valueOf(i2);
                        }

                        if((i1 +1) <10){
                            thang ="0"+(i1+1);
                        }else {
                            thang = String.valueOf((i1+1));
                        }
                        edtstart.setText(i+"/"+thang+"/"+ngay);

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                        );
                datePickerDialog.show();

            }
        });

        edtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay ="";
                                String thang = "";
                                if(i2<10){
                                    ngay ="0"+i2;
                                }else {
                                    ngay = String.valueOf(i2);
                                }

                                if((i1 +1) <10){
                                    thang ="0"+(i1+1);
                                }else {
                                    thang = String.valueOf((i1+1));
                                }
                                edtend.setText(i+"/"+thang+"/"+ngay);

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

            }
        });

        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                String ngaybatdau = edtstart.getText().toString();
                String ngayketthuc = edtend.getText().toString();
                int doanhthu = thongKeDAO.getDoanhThu(ngaybatdau , ngayketthuc);
                txtketqua.setText(doanhthu+"VND");

            }
        });

      return view;
    }
}
