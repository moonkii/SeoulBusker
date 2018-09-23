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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.teamnoname.streetartzone.Data.GroupData;
import com.teamnoname.streetartzone.Data.StageInfo;
import com.teamnoname.streetartzone.Data.UserBookMarkGroup;
import com.teamnoname.streetartzone.R;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class StreetGroupDetailActivity extends AppCompatActivity {

    //뷰
    FragmentAdapter frag_adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageButton btn_back;
    ImageButton btn_bookmark;
    TextView tv_title;

    //DB
    Realm realm;
    RealmResults<GroupData> groupData;

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
        tv_title = (TextView) findViewById(R.id.group_detail_title);

        btn_back = (ImageButton) findViewById(R.id.group_detail_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StreetGroupDetailActivity.this.finish();
            }
        });

        btn_bookmark = (ImageButton)findViewById(R.id.group_detail_img_bookmark);
        btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //즐찾 테이블에 해당 그룹 시퀀스 저장.
                if(realm.where(UserBookMarkGroup.class).equalTo("groupSeq",selectedSeq).findFirst() ==null){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            long now = System.currentTimeMillis();
                            UserBookMarkGroup group = new UserBookMarkGroup();
                            group.setGroupSeq(selectedSeq);
                            group.setAddDate(new Date(now));
                            realm.copyToRealm(group);
                        }
                    });
                }else{
                    Toast.makeText(StreetGroupDetailActivity.this,"이미 북마크에 추가된 공연단 입니다.",Toast.LENGTH_SHORT).show();
                }


            }
        });


        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                groupData = realm.where(GroupData.class).equalTo("group_seq",selectedSeq).findAll();
                tv_title.setText(groupData.get(0).getGroup_name());

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
