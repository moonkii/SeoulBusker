package com.teamnoname.streetartzone.StreetGroup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnoname.streetartzone.Data.GroupData;
import com.teamnoname.streetartzone.Data.GroupReviewData;
import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class StreetGroupAcitivty extends AppCompatActivity implements StreetGroupRecyclerViewAdapter.RecyclerViewItemClickListener {

    //ListView
    RecyclerView recyclerView_group;
    StreetGroupRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<StreetGroupItem> arrayList_groupInfo;
    ArrayList<StreetGroupItem> arrayList_groupinfo_instrument;
    ArrayList<StreetGroupItem> arrayList_groupinfo_performance;
    ArrayList<StreetGroupItem> arrayList_groupinfo_tradition;
    ArrayList<StreetGroupItem> arrayList_groupinfo_music;

    String selectedCategory="전체";

    //UI View
    ArrayList<TextView> arrayList_bar;
    TextView bar_category_all;
    TextView bar_category_instrument;
    TextView bar_category_performance;
    TextView bar_category_tradition;
    TextView bar_category_music;

    ImageButton btn_search;
    ImageButton btn_back;


    //DataBases
    DatabaseReference dbReference;
    FirebaseDatabase db;
    String db_groupInfo="groupdata";
    ArrayList<GroupFirebaseData> arrayList_groupData;
    ArrayList<GroupReviewFirebaseData> arrayList_groupReviewData;
    ArrayList<Integer> arrayList_ReviewCount;
    ArrayList<Integer> arrayList_ReviewScore;
    Realm realm;
    RealmResults<GroupData> realmResults_group;
    RealmResults<GroupReviewData> realmResults_review;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetgroup);


            init();
            setGroupData();

    }

    public void init(){
        recyclerView_group = (RecyclerView) findViewById(R.id.group_recyclerView);
        bar_category_all = (TextView) findViewById(R.id.group_bar_all);
        bar_category_instrument = (TextView) findViewById(R.id.group_bar_instrument);
        bar_category_performance = (TextView) findViewById(R.id.group_bar_performance);
        bar_category_tradition = (TextView) findViewById(R.id.group_bar_tradition);
        bar_category_music = (TextView) findViewById(R.id.group_bar_music);

        btn_search = (ImageButton) findViewById(R.id.group_main_btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

        btn_back = (ImageButton) findViewById(R.id.group_main_btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StreetGroupAcitivty.this.finish();
            }
        });


        arrayList_bar = new ArrayList<>();
        arrayList_bar.add(bar_category_all);
        arrayList_bar.add(bar_category_instrument);
        arrayList_bar.add(bar_category_performance);
        arrayList_bar.add(bar_category_tradition);
        arrayList_bar.add(bar_category_music);

        setCategoryBar(0);

        //database
        realm = Realm.getDefaultInstance();
        db = FirebaseDatabase.getInstance();
        arrayList_groupData = new ArrayList<>();
        arrayList_groupReviewData = new ArrayList<>();
        arrayList_ReviewCount = new ArrayList<>();
        arrayList_ReviewScore = new ArrayList<>();
        dbReference = db.getReference(db_groupInfo);

    }

    public void showSearchDialog()
    {
        final EditText edit_search = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("거리공연단 검색");
        builder.setView(edit_search);
        builder.setPositiveButton("검색",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String searchStr = edit_search.getText().toString();
                        
                        boolean isFinding=false;
                        for(int i =0 ; i<arrayList_groupInfo.size() ; i++ ){
                            if(arrayList_groupInfo.get(i).getGroup_name().equalsIgnoreCase(searchStr)){
                                ArrayList<StreetGroupItem> arrayList_temp = new ArrayList<>();
                                arrayList_temp.add(new StreetGroupItem(
                                        arrayList_groupInfo.get(i).getGroup_seq(),
                                        arrayList_groupInfo.get(i).getGroup_img(),
                                        arrayList_groupInfo.get(i).getGroup_name(),
                                        arrayList_groupInfo.get(i).getGroup_category(),
                                        arrayList_groupInfo.get(i).getGroup_detail(),
                                        arrayList_groupInfo.get(i).getGroup_score(),
                                        arrayList_groupInfo.get(i).getGroup_reviewNumber()
                                        
                                ));

                                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_temp,StreetGroupAcitivty.this);
                                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                                recyclerView_group.setAdapter(recyclerViewAdapter);
                                
                                isFinding=true;
                                break;
                            }
                        }
                        
                        if(!isFinding){
                            Toast.makeText(StreetGroupAcitivty.this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
        builder.show();
    }



    public void setGroupData(){

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                realmResults_group = realm.where(GroupData.class).findAll();
                realmResults_review = realm.where(GroupReviewData.class).findAll();


                //group data
                if(realmResults_group !=null && realmResults_group.size()>0){

                    //group data 개수만큼 리뷰 정보 준비
                    for(int i=0; i<realmResults_group.size(); i++){
                        arrayList_ReviewCount.add(0);
                        arrayList_ReviewScore.add(0);
                    }

                }else{
                    for(DataSnapshot groupDB : dataSnapshot.child("group").getChildren()){
                        arrayList_groupData.add(groupDB.getValue(GroupFirebaseData.class));
                    }

                    //group data 개수만큼 리뷰 정보 준비
                    for(int i=0; i<arrayList_groupData.size(); i++){
                        arrayList_ReviewCount.add(0);
                        arrayList_ReviewScore.add(0);
                    }
                }

                //group review data
                for(DataSnapshot groupReviewDB : dataSnapshot.child("groupreview").getChildren()){
                    arrayList_groupReviewData.add(groupReviewDB.getValue(GroupReviewFirebaseData.class));
                    Log.v("디비확인",groupReviewDB.getValue().toString());
                }


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        //group data
                        if(realmResults_group.size()<1){

                            for(int i=0; i<arrayList_groupData.size(); i++){

                                GroupData groupData = realm.createObject(GroupData.class);
                                groupData.setGroup_seq(i);
                                groupData.setGroup_name(arrayList_groupData.get(i).getName());
                                groupData.setGroup_genre(arrayList_groupData.get(i).getGenre());
                                groupData.setGroup_info(arrayList_groupData.get(i).getIntro());
                                groupData.setGroup_titleImg(arrayList_groupData.get(i).getImage());

                            }

                        }




                        //groupReview data
                        for(int i=0; i<arrayList_groupReviewData.size(); i++){

                            if(realmResults_review.size()<i){

                                GroupReviewData groupReviewData = realm.createObject(GroupReviewData.class);
                                groupReviewData.setSocre(arrayList_groupReviewData.get(i).getScore());
                                groupReviewData.setWriter(arrayList_groupReviewData.get(i).getName());
                                groupReviewData.setDate(arrayList_groupReviewData.get(i).getDate());
                                groupReviewData.setContents(arrayList_groupReviewData.get(i).getContents());
                                groupReviewData.setSeq(arrayList_groupReviewData.get(i).getSeq());

                            }

                            //review count
                            int reviewCount =arrayList_ReviewCount.get(arrayList_groupReviewData.get(i).getSeq());
                            arrayList_ReviewCount.set(arrayList_groupReviewData.get(i).getSeq(),reviewCount+1);



                            //review score
                            int reviewScore =arrayList_ReviewScore.get(arrayList_groupReviewData.get(i).getSeq());
                            arrayList_ReviewScore.set(arrayList_groupReviewData.get(i).getSeq(),reviewScore+arrayList_groupReviewData.get(i).getScore());

                        }





                        arrayList_groupInfo = new ArrayList<>();
                        arrayList_groupinfo_instrument = new ArrayList<>();
                        arrayList_groupinfo_performance = new ArrayList<>();
                        arrayList_groupinfo_tradition = new ArrayList<>();
                        arrayList_groupinfo_music = new ArrayList<>();
                        for(int i=0; i<realmResults_group.size() ; i++){
                            GroupData temp = realmResults_group.get(i);


                            arrayList_groupInfo.add(new StreetGroupItem(
                                    temp.getGroup_seq(),
                                    temp.getGroup_titleImg()
                                    ,temp.getGroup_name()
                                    ,temp.getGroup_genre()
                                    ,temp.getGroup_info()
                                    ,arrayList_ReviewScore.get(temp.getGroup_seq())
                                    ,arrayList_ReviewCount.get(temp.getGroup_seq()))
                            );

                            if(temp.getGroup_genre().equals("기악")){
                                arrayList_groupinfo_instrument.add(new StreetGroupItem(
                                        temp.getGroup_seq(),
                                        temp.getGroup_titleImg()
                                        ,temp.getGroup_name()
                                        ,temp.getGroup_genre()
                                        ,temp.getGroup_info()
                                        ,arrayList_ReviewScore.get(temp.getGroup_seq())
                                        ,arrayList_ReviewCount.get(temp.getGroup_seq())));
                            }else if(temp.getGroup_genre().equals("퍼포먼스")){
                                arrayList_groupinfo_performance.add(new StreetGroupItem(
                                        temp.getGroup_seq(),
                                        temp.getGroup_titleImg()
                                        ,temp.getGroup_name()
                                        ,temp.getGroup_genre()
                                        ,temp.getGroup_info()
                                        ,arrayList_ReviewScore.get(temp.getGroup_seq())
                                        ,arrayList_ReviewCount.get(temp.getGroup_seq())));
                            }else if(temp.getGroup_genre().equals("전통")){
                                arrayList_groupinfo_tradition.add(new StreetGroupItem(
                                        temp.getGroup_seq(),
                                        temp.getGroup_titleImg()
                                        ,temp.getGroup_name()
                                        ,temp.getGroup_genre()
                                        ,temp.getGroup_info()
                                        ,arrayList_ReviewScore.get(temp.getGroup_seq())
                                        ,arrayList_ReviewCount.get(temp.getGroup_seq())));
                            }else if(temp.getGroup_genre().equals("음악")){
                                arrayList_groupinfo_music.add(new StreetGroupItem(
                                        temp.getGroup_seq(),
                                        temp.getGroup_titleImg()
                                        ,temp.getGroup_name()
                                        ,temp.getGroup_genre()
                                        ,temp.getGroup_info()
                                        ,arrayList_ReviewScore.get(temp.getGroup_seq())
                                        ,arrayList_ReviewCount.get(temp.getGroup_seq())));
                            }
                        }

                        setGroupRecyclerView();



                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void setGroupRecyclerView(){


        recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupInfo,StreetGroupAcitivty.this);
        recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
        recyclerView_group.setAdapter(recyclerViewAdapter);



    }


    @Override
    public void setOnItemClick(int selectedPosition) {

        Intent intent = new Intent(StreetGroupAcitivty.this,StreetGroupDetailActivity.class);
        intent.putExtra("seq",selectedPosition);
        startActivity(intent);


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

    public void setRecyclerViewNotify(String category){

        switch (category){
            case "전체" :
                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupInfo,StreetGroupAcitivty.this);
                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                recyclerView_group.setAdapter(recyclerViewAdapter);


                break;

            case "기악" :
                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupinfo_instrument,StreetGroupAcitivty.this);
                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                recyclerView_group.setAdapter(recyclerViewAdapter);


                break;
            case "퍼포먼스" :
                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupinfo_performance,StreetGroupAcitivty.this);
                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                recyclerView_group.setAdapter(recyclerViewAdapter);


                break;
            case "전통" :
                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupinfo_tradition,StreetGroupAcitivty.this);
                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                recyclerView_group.setAdapter(recyclerViewAdapter);


                break;

            case "음악" :
                recyclerViewAdapter = new StreetGroupRecyclerViewAdapter(StreetGroupAcitivty.this,arrayList_groupinfo_music,StreetGroupAcitivty.this);
                recyclerView_group.setLayoutManager(new LinearLayoutManager(StreetGroupAcitivty.this));
                recyclerView_group.setAdapter(recyclerViewAdapter);


                break;

        }


    }

    //카레고리 클릭
    public void setCategoryClick(View view) {

        switch (view.getId()){
            case R.id.group_btn_all :
                setCategoryBar(0);
                selectedCategory="전체";
                setRecyclerViewNotify(selectedCategory);
                break;

            case R.id.group_btn_instrument :
                setCategoryBar(1);
                selectedCategory="기악";
                setRecyclerViewNotify(selectedCategory);
                break;


            case R.id.group_btn_performance :
                setCategoryBar(2);
                selectedCategory="퍼포먼스";
                setRecyclerViewNotify(selectedCategory);
                break;

            case R.id.group_btn_tradition :
                setCategoryBar(3);
                selectedCategory="전통";
                setRecyclerViewNotify(selectedCategory);
                break;

            case R.id.group_btn_music :
                setCategoryBar(4);
                selectedCategory="음악";
                setRecyclerViewNotify(selectedCategory);

                break;

        }
    }
}



class GroupFirebaseData{

    String name;
    String genre;
    String intro;
    String image;

    public GroupFirebaseData() {
    }

    public GroupFirebaseData(String name, String genre, String intro, String image) {
        this.name = name;
        this.genre = genre;
        this.intro = intro;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


class GroupReviewFirebaseData{

    int score;
    String name;
    String date;
    String contents;
    int seq;

    public GroupReviewFirebaseData() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}