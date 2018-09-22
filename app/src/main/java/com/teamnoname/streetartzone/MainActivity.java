package com.teamnoname.streetartzone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamnoname.streetartzone.Data.Contest;
import com.teamnoname.streetartzone.Schedule.Notice;
import com.teamnoname.streetartzone.Schedule.Schedule;
import com.bumptech.glide.Glide;
import com.teamnoname.streetartzone.StreetGroup.StreetGroupAcitivty;
import com.teamnoname.streetartzone.Data.StageInfo;
import com.teamnoname.streetartzone.StreetStage.NearStageActivity;
import com.teamnoname.streetartzone.StreetStage.StreetStageAcitivity;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class MainActivity extends AppCompatActivity {

    AutoScrollViewPager autoScrollViewPager;
    MainBannerAdapter mainBannerAdapter;
    ArrayList<Integer> arrayList_banner;

    private Realm realm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;

    DatabaseReference dbref;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(MainActivity.this);
        realm = Realm.getDefaultInstance();
        sharedPreferences = getSharedPreferences("GET_STAGE", MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        setBannerData();
        setBannerViewPager();
        if(!sharedPreferences.getBoolean("isContestData",false)){
            Log.i("MainActivity","가져오기 시작");
            getContestData();
        }

    }

    private void getContestData(){
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("contestdata");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("MainActivity","onDataChange");
                realm.beginTransaction();
                for(DataSnapshot DB : dataSnapshot.getChildren()){
                   final FcvContest item = DB.getValue(FcvContest.class);
                            String[] devider = item.getDate().split("-");
                            String month = devider[1];
                            Contest contest = new Contest(item.getNum(),item.getTeamname(),item.getDistrict(),item.getArea(),item.getDate(),item.getTime(),month);
                            realm.copyToRealm(contest);

                }
                RealmResults<Contest> a = realm.where(Contest.class).findAll();

                for(int i=0;i<a.size();i++){
                    Log.i("MainActivity","넘버 : "+a.get(i).getNum());
                    Log.i("MainActivity","이름 : "+a.get(i).getTeamname());
                    Log.i("MainActivity","장소 : "+a.get(i).getArea());
                    Log.i("MainActivity","월 : "+a.get(i).getMonth());
                    Log.i("MainActivity","날짜 : "+a.get(i).getDate());
                    Log.i("MainActivity","구 : "+a.get(i).getDistrict());
                    System.out.println();
                }
                realm.commitTransaction();
                Log.i("MainActivity","Commit 완료");
                preferenceEditor.putBoolean("isContestData",true);
                preferenceEditor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                realm.commitTransaction();
            }
        });
    }


    public void setBannerData() {
        arrayList_banner = new ArrayList<>();
        arrayList_banner.add(R.drawable.home_banner1);
        arrayList_banner.add(R.drawable.banner2);
        arrayList_banner.add(R.drawable.home_banner3);
    }

    public void setBannerViewPager(){
        autoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.main_bannerViewPager);
        mainBannerAdapter = new MainBannerAdapter(arrayList_banner,MainActivity.this);
        autoScrollViewPager.setAdapter(mainBannerAdapter);
        autoScrollViewPager.setInterval(3000);
        autoScrollViewPager.startAutoScroll();
    }


    public void setMainBtn(View view) {

        switch (view.getId()){

            case R.id.main_btn_group :
                //공연팀 버튼
                startActivity(new Intent(MainActivity.this, StreetGroupAcitivty.class));

                break;

            case R.id.main_btn_schedule :
                //공연일정 버튼
                startActivity(new Intent(MainActivity.this,Schedule.class));
                break;


            case R.id.main_btn_stage :
                //공연 장소 버튼
                startActivity(new Intent(MainActivity.this, StreetStageAcitivity.class));


                break;

            case R.id.main_btn_ticket :
                //내티켓 버튼


                break;

                //알림 보기
            case R.id.main_bookmark_but :
                startActivity(new Intent(MainActivity.this, Notice.class));
                break;
            case R.id.main_near_stage_btn:
                startActivity(new Intent(MainActivity.this, NearStageActivity.class));
                break;

        }

    }
}

class MainBannerAdapter extends PagerAdapter {

    ArrayList<Integer> arrayList_banner;
    Context context;


    public MainBannerAdapter(ArrayList<Integer> arrayList_banner, Context context) {
        this.arrayList_banner = arrayList_banner;
        this.context = context;

    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.main_banner_item,null);
        ImageView imgV_banner = (ImageView) view.findViewById(R.id.main_banner_img);
        Glide.with(context).load(arrayList_banner.get(position)).into(imgV_banner);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return arrayList_banner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}


 class FcvContest  {
    @PrimaryKey
    int num;
    String teamname;
    String district;
    String area;
    String date;
    String time;

    public FcvContest() {
    }

     public FcvContest(int num, String teamname, String district, String area, String date, String time) {
         this.num = num;
         this.teamname = teamname;
         this.district = district;
         this.area = area;
         this.date = date;
         this.time = time;
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

