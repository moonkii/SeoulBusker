package com.teamnoname.streetartzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.teamnoname.streetartzone.Schedule.Schedule;
import com.teamnoname.streetartzone.StreetGroup.StreetGroupAcitivty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void setMainBtn(View view) {

        switch (view.getId()){

            case R.id.main_btn_group :
                //공연팀 버튼
                startActivity(new Intent(MainActivity.this, StreetGroupAcitivty.class));

                break;

            case R.id.main_btn_schedule :
                //공연일정 버튼
                startActivity(new Intent(MainActivity.this, Schedule.class));
                break;


            case R.id.main_btn_stage :
                //공연 장소 버튼


                break;

            case R.id.main_btn_ticket :
                //내티켓 버튼


                break;

        }

    }
}
