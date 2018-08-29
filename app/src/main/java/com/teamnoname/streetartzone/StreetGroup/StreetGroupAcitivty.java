package com.teamnoname.streetartzone.StreetGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

public class StreetGroupAcitivty extends AppCompatActivity {

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

    }

    public void setGroupRecyclerView(){



        recyclerView_group = (RecyclerView) findViewById(R.id.group_recyclerView);
        recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(arrayList_groupInfo);
        recyclerView_group.setAdapter(recyclerViewAdapter);


    }



}