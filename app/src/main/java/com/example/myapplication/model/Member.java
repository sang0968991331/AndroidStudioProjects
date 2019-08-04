package com.example.myapplication.model;

import java.util.HashMap;
import java.util.Map;

public class Member {

    public String id;
    public String name;
    public String email;
    public String sdt;
    public String ngaysinh;
    public String diachi;
    public String congty;
    public String thongtin;
    public String group;

    public Member( String name, String email, String sdt, String ngaysinh, String diachi, String congty, String thongtin, String group) {

        this.name = name;
        this.email = email;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.congty = congty;
        this.thongtin = thongtin;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getCongty() {
        return congty;
    }

    public void setCongty(String congty) {
        this.congty = congty;
    }

    public String getThongtin() {
        return thongtin;
    }

    public void setThongtin(String thongtin) {
        this.thongtin = thongtin;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Member( ) {


    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("email", this.email);
        result.put("sdt", this.sdt);
        result.put("dia chi", this.diachi);
        result.put("ngay sinh", this.ngaysinh);
        result.put("cong ty", this.email);
        result.put("thong tin khac", this.thongtin);
        result.put("group", this.group);



        return result;
    }
}