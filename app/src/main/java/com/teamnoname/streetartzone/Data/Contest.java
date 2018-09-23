package com.teamnoname.streetartzone.Data;

import io.realm.RealmObject;

/**
 * Created by iyeonghan on 2018. 9. 17..
 */

public class Contest extends RealmObject {

    int num;
    String teamname;
    String district;
    String area;
    String date;
    String time;
    String month; //얘는 월 바꿀때를 위한거. 월 바꿀때 해당 월만 가져와야 하기 때문임.

    public Contest() {
    }

    public Contest(int num, String teamname, String district, String area, String date, String time,String month) {
        this.num = num;
        this.teamname = teamname;
        this.district = district;
        this.area = area;
        this.date = date;
        this.time = time;
        this.month = month;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
