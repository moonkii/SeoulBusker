package com.teamnoname.streetartzone.StreetGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.teamnoname.streetartzone.R;

public class StreetGroupDetailActivity extends AppCompatActivity {

    //뷰
    FragmentAdapter frag_adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageButton btn_back;

    public static int selectedSeq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetgroup_detail);

        Intent intent = getIntent();
        selectedSeq = intent.getExtras().getInt("seq");

        frag_adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.group_detail_viewpager);
        viewPager.setAdapter(frag_adapter);
        tabLayout = (TabLayout) findViewById(R.id.group_detail_tab);
        tabLayout.setupWithViewPager(viewPager);

        btn_back = (ImageButton) findViewById(R.id.group_detail_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StreetGroupDetailActivity.this.finish();
            }
        });
    }
}


class FragmentAdapter extends FragmentPagerAdapter {

    int fragmentPageNumber=3;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //페이지 호출
        switch (position){

            case 0 :
                return InfoFragment.newInstance();

            case 1 :
                return ScheduleFragment.newInstance();

            case 2 :
                return ReviewFragment.newInstance();

        }

        return null;
    }

    @Override
    public int getCount() {
        return fragmentPageNumber;
    }



    @Override
    public CharSequence getPageTitle(int position) {//프래그먼트 제목 지정 메소드 시작

        switch (position){ //프래그먼트 제목 지정 switch 시작
            case 0 :
                return "소개";

            case 1 :
                return "공연 일정";

            case 2 :
                return "공연 후기";

        }

        return null;

    }


}
