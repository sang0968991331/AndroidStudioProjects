package com.example.myapplication.model;

public class LichHen {
    public String noidung;
    public String ngayhen;
    private String giohen;
    public LichHen() {

    }
    public LichHen(String noidung, String ngayhen, String giohen) {
        this.noidung = noidung;
        this.ngayhen = ngayhen;
        this.giohen = giohen;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgayhen() {
        return ngayhen;
    }

    public void setNgayhen(String ngayhen) {
        this.ngayhen = ngayhen;
    }

    public String getGiohen() {
        return giohen;
    }

    public void setGiohen(String giohen) {
        this.giohen = giohen;
    }
}
