package com.teamnoname.streetartzone.StreetGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

public class StreetGroupAcitivty extends AppCompatActivity implements StreetGroupRecyclerViewAdapter.RecyclerViewItemClickListener {

    RecyclerView recyclerView_group;
    StreetGroupRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<StreetGroupItem> arrayList_groupInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetgroup);

            setGroupData();
            setGroupRecyclerView();
    }


    public void setGroupData(){
        arrayList_groupInfo = new ArrayList<>();

        for (int i=0; i<5 ; i++){
            arrayList_groupInfo.add(new StreetGroupItem(
                    "이미지",
                    "팀명",
                    "장르",
                    "공연팀에 대한 설명입니다공연팀에 대한 설명입니다공연팀에 대한 설명입니다공연팀에 대한 설명입니다",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "5.0",
                    4
            ));
        }





    }

    public void setGroupRecyclerView(){

        recyclerView_group = (RecyclerView) findViewById(R.id.group_recyclerView);
        recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(arrayList_groupInfo,StreetGroupAcitivty.this);
        recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
        recyclerView_group.setAdapter(recyclerViewAdapter);



    }


    @Override
    public void setOnItemClick(int selectedPosition) {
        Log.v("리사이클러뷰 클릭","position L "+selectedPosition);
        startActivity(new Intent(StreetGroupAcitivty.this,StreetGroupDetailActivity.class));


    }
}