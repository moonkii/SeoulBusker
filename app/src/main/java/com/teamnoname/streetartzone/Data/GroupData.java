package com.teamnoname.streetartzone.Data;

import io.realm.RealmObject;

public class GroupData extends RealmObject {

    String group_name;
    String group_genre;
    String group_info;
    String group_titleImg;

    public GroupData(String group_name, String group_genre, String group_info, String group_titleImg) {
        this.group_name = group_name;
        this.group_genre = group_genre;
        this.group_info = group_info;
        this.group_titleImg = group_titleImg;
    }


    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_genre() {
        return group_genre;
    }

    public void setGroup_genre(String group_genre) {
        this.group_genre = group_genre;
    }

    public String getGroup_info() {
        return group_info;
    }

    public void setGroup_info(String group_info) {
        this.group_info = group_info;
    }

    public String getGroup_titleImg() {
        return group_titleImg;
    }

    public void setGroup_titleImg(String group_titleImg) {
        this.group_titleImg = group_titleImg;
    }
}
