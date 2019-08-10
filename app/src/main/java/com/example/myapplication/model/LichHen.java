package com.example.myapplication.model;

import java.util.HashMap;
import java.util.Map;

public class LichHen {
    public String id;
    public String noidung;
    public String ngayhen;
    private String giohen;
    public LichHen() {

    }

    public LichHen( String noidung, String ngayhen, String giohen) {

        this.noidung = noidung;
        this.ngayhen = ngayhen;
        this.giohen = giohen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("noidung", this.noidung);
        result.put("ngay", this.ngayhen);
        result.put("gio", this.giohen);
        return   result;
    }
}
