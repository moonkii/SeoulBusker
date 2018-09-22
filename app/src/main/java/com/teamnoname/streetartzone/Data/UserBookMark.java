package com.teamnoname.streetartzone.Data;

import io.realm.RealmModel;
import io.realm.RealmObject;

public class UserBookMark implements RealmModel {

    int groupSeq;

    public int getGroupSeq() {
        return groupSeq;
    }

    public void setGroupSeq(int groupSeq) {
        this.groupSeq = groupSeq;
    }
}
