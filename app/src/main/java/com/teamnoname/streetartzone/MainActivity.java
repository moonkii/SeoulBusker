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

import com.teamnoname.streetartzone.Schedule.Schedule;
import com.bumptech.glide.Glide;
import com.teamnoname.streetartzone.StreetGroup.StreetGroupAcitivty;
import com.teamnoname.streetartzone.StreetStage.StageInfo;
import com.teamnoname.streetartzone.StreetStage.StreetStageAcitivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import io.realm.Realm;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    AutoScrollViewPager autoScrollViewPager;
    MainBannerAdapter mainBannerAdapter;
    ArrayList<Integer> arrayList_banner;
    private Realm realm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm.init(this);
        realm = Realm.getDefaultInstance();
        sharedPreferences = getSharedPreferences("GET_STAGE", MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        setBannerData();
        setBannerViewPager();


        if (!sharedPreferences.getBoolean("isStageData", false)) {
            getStageInfoData();
        }


    }

    private void getStageInfoData() {
        GetStageInfoAsync getStageInfoAsync = new GetStageInfoAsync(realm);
        try {
            final Elements[] trs = getStageInfoAsync.execute().get();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    for (int i = 0; i < trs.length; i++) {
                        Elements tr = trs[i];
                        Elements tds = tr.select("td");

                        int index = 5, j = 0;
                        while (index < tds.size()) {
                            if (j > 4 && index < tds.size()) {
                                int seq = Integer.valueOf(tds.get(index).text());
                                String district = tds.get(index + 1).text();
                                String placeName = tds.get(index + 2).text();
                                String address = tds.get(index + 3).text();

                                Log.e("Main", tds.get(index).text() + district + placeName + address + "다음--");

                                StageInfo info = new StageInfo();
                                info.setSeq(seq);
                                info.setAddress(address);
                                info.setDistrict(district);
                                info.setPlaceName(placeName);
                                realm.copyToRealm(info);

                                index += 4;
                            }
                            j++;
                        }
                    }
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        preferenceEditor.putBoolean("isStageData",true);
        preferenceEditor.commit();

    }

    public void setBannerData() {
        arrayList_banner = new ArrayList<>();
        arrayList_banner.add(R.drawable.home_banner1);
        arrayList_banner.add(R.drawable.home_banner2);
        arrayList_banner.add(R.drawable.home_banner3);
    }

    public void setBannerViewPager() {
        autoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.main_bannerViewPager);
        mainBannerAdapter = new MainBannerAdapter(arrayList_banner, MainActivity.this);
        autoScrollViewPager.setAdapter(mainBannerAdapter);
        autoScrollViewPager.setInterval(3000);
        autoScrollViewPager.startAutoScroll();
    }


    public void setMainBtn(View view) {

        switch (view.getId()) {

            case R.id.main_btn_group:
                //공연팀 버튼
                startActivity(new Intent(MainActivity.this, StreetGroupAcitivty.class));

                break;

            case R.id.main_btn_schedule:
                //공연일정 버튼
                startActivity(new Intent(MainActivity.this, Schedule.class));
                break;


            case R.id.main_btn_stage:
                //공연 장소 버튼
                startActivity(new Intent(MainActivity.this, StreetStageAcitivity.class));


                break;

            case R.id.main_btn_ticket:
                //내티켓 버튼


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
        View view = layoutInflater.inflate(R.layout.main_banner_item, null);
        ImageView imgV_banner = (ImageView) view.findViewById(R.id.main_banner_img);
        Glide.with(context).load(arrayList_banner.get(position)).into(imgV_banner);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList_banner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

class GetStageInfoAsync extends AsyncTask<Void, Void, Elements[]> {
    private Realm realm;

    public GetStageInfoAsync(Realm realm) {
        this.realm = realm;
    }

    @Override
    protected Elements[] doInBackground(Void... voids) {
        org.jsoup.nodes.Document stageInfo1 = null;
        org.jsoup.nodes.Document stageInfo2 = null;
        try {
            stageInfo1 = Jsoup
                    .connect("https://seoulbusking.com/bbs/board.php?bo_table=art_location&page=2&page=1")
                    .get();

            stageInfo2 = Jsoup
                    .connect("https://seoulbusking.com/bbs/board.php?bo_table=art_location&page=2&page=2")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements[] trs = new Elements[2];
        trs[0] = stageInfo1.getElementsByTag("tr");
        trs[1] = stageInfo2.getElementsByTag("tr");

        return trs;
    }
}
