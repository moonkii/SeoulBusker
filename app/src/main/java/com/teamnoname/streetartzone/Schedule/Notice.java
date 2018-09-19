package com.teamnoname.streetartzone.Schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

/**
 * Created by iyeonghan on 2018. 9. 17..
 */

public class Notice extends Activity implements NoticeClickListener {
    RecyclerView notice_rv;
    NoticeRecyclerViewAdapter notice_adapter;
    ArrayList<Contestitem> contests = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setDataSet();
        setRecyclerView();

    }
    public void setRecyclerView(){
        notice_rv = (RecyclerView)findViewById(R.id.notice_rv);
        notice_adapter = new NoticeRecyclerViewAdapter(contests,Notice.this);
        notice_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notice_rv.setAdapter(notice_adapter);
    }

    //아이템 클릭시.
    @Override
    public void setOnItemClickForNotice(int selectedPosition) {
//        Intent intent = new Intent(Notice.this,);
        Log.i("Notice","클릭" + selectedPosition);
    }

    //삭제 버튼
    @Override
    public void setOnItemClickForDeleteButton(int selectedPosition) {
        //렘에서 삭제.
        //arraylist에서 삭제.
        Log.i("Notice","삭제 : "+selectedPosition);
    }
    public void setDataSet(){

    }

}


interface NoticeClickListener{
    void setOnItemClickForNotice(int selectedPosition);
    void setOnItemClickForDeleteButton(int selectedPosition);
}