package com.example.duanmau.DTO;

public class LoaiSachDTO {
    private int id;
    private String tenloai;

    public LoaiSachDTO(int id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    public LoaiSachDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
