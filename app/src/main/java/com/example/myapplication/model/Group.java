package com.example.myapplication.model;

public class Group {
    public String id_group;
    public String name_group;

    public Group(String id_group, String name_group) {
        this.id_group = id_group;
        this.name_group = name_group;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }
}
