package com.example.myapplication.model;

import java.util.HashMap;
import java.util.Map;

public class LienHe {
    public String id,ngaygoi,giogoi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgaygoi() {
        return ngaygoi;
    }

    public void setNgaygoi(String ngaygoi) {
        this.ngaygoi = ngaygoi;
    }

    public String getGiogoi() {
        return giogoi;
    }

    public void setGiogoi(String giogoi) {
        this.giogoi = giogoi;
    }

    public LienHe(String id, String ngaygoi, String giogoi) {
        this.id = id;
        this.ngaygoi = ngaygoi;
        this.giogoi = giogoi;
    }
    public LienHe() {

    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ngay", this.ngaygoi);
        result.put("gio", this.giogoi);
        return   result;
    }
}
