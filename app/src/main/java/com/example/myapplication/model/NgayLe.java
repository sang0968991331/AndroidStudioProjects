package com.example.myapplication.model;

public class NgayLe {
    private String ngayle,noidung;

    public NgayLe() {
    }

    public NgayLe(String ngayle, String noidung) {
        this.ngayle = ngayle;
        this.noidung = noidung;
    }

    public String getNgayle() {
        return ngayle;
    }

    public void setNgayle(String ngayle) {
        this.ngayle = ngayle;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
