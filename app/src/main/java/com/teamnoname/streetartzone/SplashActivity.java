package com.teamnoname.streetartzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.teamnoname.streetartzone.Data.StageInfo;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity {


    private Realm realm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        realm = Realm.getDefaultInstance();
        sharedPreferences = getSharedPreferences("GET_STAGE", MODE_PRIVATE);
        preferenceEditor = sharedPreferences.edit();

        if (!sharedPreferences.getBoolean("isStageData", false)) {
            getStageInfoData();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //3초후에 메인 액티비티로 이동
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent go_MainActivity = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(go_MainActivity);
                finish();
            }
        },3000);
    }

    //공연장 정보를 가져오는 메소드
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

                                Log.e("Main", tds.get(index).text()+"/" + district+"/" + placeName+"/" + address + "다음--");

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

