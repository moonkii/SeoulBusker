package com.teamnoname.streetartzone.StreetGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

public class StreetGroupAcitivty extends AppCompatActivity implements StreetGroupRecyclerViewAdapter.RecyclerViewItemClickListener {

    RecyclerView recyclerView_group;
    StreetGroupRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<StreetGroupItem> arrayList_groupInfo;

    ArrayList<TextView> arrayList_bar;
    TextView bar_category_all;
    TextView bar_category_instrument;
    TextView bar_category_performance;
    TextView bar_category_tradition;
    TextView bar_category_music;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetgroup);


            init();
            setGroupData();
            setGroupRecyclerView();
    }

    public void init(){
        bar_category_all = (TextView) findViewById(R.id.group_bar_all);
        bar_category_instrument = (TextView) findViewById(R.id.group_bar_instrument);
        bar_category_performance = (TextView) findViewById(R.id.group_bar_performance);
        bar_category_tradition = (TextView) findViewById(R.id.group_bar_tradition);
        bar_category_music = (TextView) findViewById(R.id.group_bar_music);


        arrayList_bar = new ArrayList<>();
        arrayList_bar.add(bar_category_all);
        arrayList_bar.add(bar_category_instrument);
        arrayList_bar.add(bar_category_performance);
        arrayList_bar.add(bar_category_tradition);
        arrayList_bar.add(bar_category_music);

        setCategoryBar(0);


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

    public void setCategoryBar(int order){
        //전체 , 기악, 퍼포먼스, 전통, 음악 순
        for(int i=0; i<5 ; i++){

            if(i==order){
                if(arrayList_bar.get(i).getVisibility()==View.INVISIBLE){
                    arrayList_bar.get(i).setVisibility(View.VISIBLE);
                }

            }else{
                if(arrayList_bar.get(i).getVisibility()==View.VISIBLE){
                    arrayList_bar.get(i).setVisibility(View.INVISIBLE);
                }

            }
        }


    }

    //카레고리 클릭
    public void setCategoryClick(View view) {

        switch (view.getId()){
            case R.id.group_btn_all :
                setCategoryBar(0);

                break;

            case R.id.group_btn_instrument :
                setCategoryBar(1);

                break;


            case R.id.group_btn_performance :
                setCategoryBar(2);

                break;

            case R.id.group_btn_tradition :
                setCategoryBar(3);

                break;

            case R.id.group_btn_music :
                setCategoryBar(4);


                break;

        }
    }
}