package com.teamnoname.streetartzone.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by iyeonghan on 2018. 9. 17..
 */

public class Contest extends RealmObject {
    @PrimaryKey
    int contestnum;
    int teamnum;
    String teamname;
    int contesttype;
    String datetime;
    String place;
    double x;
    double y;

    public Contest() {
    }

    public Contest(int contestnum, int teamnum, String teamname, int contesttype, String datetime, String place, double x, double y) {
        this.contestnum = contestnum;
        this.teamnum = teamnum;
        this.teamname = teamname;
        this.contesttype = contesttype;
        this.datetime = datetime;
        this.place = place;
        this.x = x;
        this.y = y;
    }

    public int getContestnum() {
        return contestnum;
    }

    public void setContestnum(int contestnum) {
        this.contestnum = contestnum;
    }

    public int getTeamnum() {
        return teamnum;
    }

    public void setTeamnum(int teamnum) {
        this.teamnum = teamnum;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getContesttype() {
        return contesttype;
    }

    public void setContesttype(int contesttype) {
        this.contesttype = contesttype;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String date) {
        this.datetime = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
