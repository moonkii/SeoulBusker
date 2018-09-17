package com.teamnoname.streetartzone.Schedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by iyeonghan on 2018. 8. 29..
 */

public class Schedule extends Activity implements ClickListener{
    Calendar calendar;         //현재 날짜를 가져올 수 있는 클래스.
    int nowDate,nowMonth,nowYear;                        //현재 날짜.
    int selDate,selMonth,selYear;                        //사용자에 의해 선택된 날짜.
    ArrayList<Contestitem> contests = new ArrayList<>();                            //공연시간 데이터를 담을 컨테이너.

    RecyclerView schedule_rv;
    RecyclerView date_rv;
    TextView showMonth ;
    ImageView nextMonthBtn,prevMonthBtn;


    //어댑터
    ScheduleRecyclerViewAdapter schedule_adapter;
    DateRecyclerViewAdapter date_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //데이터 초기화 (데모)
        setDataSet();
        //뷰 초기화.
        viewInit();
        //날짜 초기화.
        dateInit();
        //현재 날짜 상단과 좌측에 표기
        initNowMonthOnTop();
        //공연 rv 설정
        setScheduleRecyclerView();
        //date rv 설정
        setDateRecyclerView();


}

private void initNowMonthOnTop(){
        showMonth.setText(nowYear+"년 "+nowMonth+"월");

}

private void dateInit(){
    //날짜 받아서 초기화
    calendar = Calendar.getInstance();
    nowYear = calendar.get(calendar.YEAR);
    nowMonth = calendar.get(calendar.MONTH);
    nowDate = calendar.get(calendar.DATE);

    //맨 처음 화면에서 선택 되는것은 현재 날짜
    selYear = nowYear;
    selMonth = nowMonth;
    selDate = nowDate;
}



//뷰 초기화
private void viewInit(){
    //textView
    showMonth = (TextView)findViewById(R.id.schedule_activity_btn_nowmonth);
    //button
    nextMonthBtn = (ImageView)findViewById(R.id.schedule_activity_btn_nextmonth);
    prevMonthBtn = (ImageView)findViewById(R.id.schedule_activity_btn_prevmonth);


    //이전 월 버튼 클릭
    prevMonthBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //1월이면 년도를 변경 후에 12월로 변경..
            if(selMonth==1){
                selYear-=1;
                selMonth=12;
            }
            //아니면 -1해줌
            else{
                selMonth-=1;
            }
            //변경된 월수에 맞춰 상단 날짜 변경.
            showMonth.setText(selYear+"년 "+selMonth+"월");
            changedMonth();

            //현재월인지 확인하기
            //현재월이면 오늘 날짜로 표시해주고 아니면 1일로 포커싱
            //해당 날짜로 리사이클러뷰 포커싱 하기.
            Log.i("Schedule","selMonth : "+selMonth);
            Log.i("Schedule","nowMonth : "+nowMonth);
            if(nowMonth==selMonth){
            selDate = nowDate;
            }else{
            selDate = 1;
            }
            focusItemByDate(selDate);

//            if(selMonth==nowMonth){
//                //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
//                date_adapter.setCurrentMonth(nowMonth,nowDate);
//                Log.i("Schedule","날짜변경 : "+date_adapter.getSelected_month());
//                //ArrayList안에 있는 데이터도 모두 현재월의 데이터로 변경해줘야함.
//                //그 이후 스케쥴 노티
//                schedule_adapter.notifyDataSetChanged();
//            }else{
//                //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
//                date_adapter.setSelected_month(selMonth);
//                //ArrayList안에 있는 데이터도 모두 해당월의 데이터로 변경해줘야함.
//                //그 이후 스케쥴 노티
//                schedule_adapter.notifyDataSetChanged();
//
//            }



        }
    });

    //다음 월 버튼 클릭.
    nextMonthBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //12월이면 년도를 변경 후에 11월로 변경..
            if(selMonth==12){
                selYear+=1;
                selMonth=1;
            }
            //아니면 +1해줌
            else{
                selMonth+=1;
            }
            Log.i("Schedule","selMonth : "+selMonth);
            Log.i("Schedule","nowMonth : "+nowMonth);
//            //변경된 월수에 맞춰 상단 날짜 변경.
            showMonth.setText(selYear+"년 "+selMonth+"월");

            //월에 맞게 변경.
            changedMonth();

            //현재월인지 확인하기
            //현재월이면 오늘 날짜로 표시해주고 아니면 1일로 포커싱
            //해당 날짜로 리사이클러뷰 포커싱 하기.
            if(nowMonth==selMonth){
                selDate = nowDate;
            }else{
                selDate = 1;
            }

            focusItemByDate(selDate);
//            //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
//            date_adapter.setSelected_month(selDate);
//            date_adapter.notifyDataSetChanged();
//            //ArrayList안에 있는 데이터도 모두 현재월의 데이터로 변경해줘야함
//
//            //그 이후 노티
//            schedule_adapter.notifyDataSetChanged();
//
//
//            //nowMonth==selMonth 라면 ListView의 포커싱을 현재 nowDate로 .

        }
    });


}

public void changedMonth(){
    if(selMonth==nowMonth&&selYear==nowYear){
        //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
        date_adapter.setCurrentMonth(nowMonth,nowDate);
        Log.i("Schedule","날짜변경 : "+date_adapter.getSelected_month());
        //ArrayList안에 있는 데이터도 모두 현재월의 데이터로 변경해줘야함.
        //그 이후 스케쥴 노티
        schedule_adapter.notifyDataSetChanged();
    }else{
        //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
        date_adapter.setSelected_month(selMonth);
        //ArrayList안에 있는 데이터도 모두 해당월의 데이터로 변경해줘야함.
        //그 이후 스케쥴 노티
        schedule_adapter.notifyDataSetChanged();

    }
}

//현재 arraylist에 있는 date를 찾아서 몇번째 포지션에 있는지 확인하기
public void focusItemByDate(int date){
    Log.i("Schedule","처음date : "+date);
for(int i=0;i<contests.size();i++){
    String fullDate = contests.get(i).getDate();
    String[] devidedDate = fullDate.split("-");

    int thisdate = Integer.parseInt(devidedDate[2]);

    //현재 날짜와 동일한 애를 포커싱 해줌.
    if(thisdate==date){
        schedule_rv.scrollToPosition(i);
        break;
    }
    Log.i("Schedule","date : "+date+"thisdate : "+thisdate);

}
}

    public void setScheduleRecyclerView(){
        schedule_rv = (RecyclerView)findViewById(R.id.schedule_rv_contests);
        schedule_adapter= new ScheduleRecyclerViewAdapter(contests,Schedule.this);
        schedule_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        schedule_rv.setAdapter(schedule_adapter);
    }
    public void setDateRecyclerView(){
        date_rv = (RecyclerView)findViewById(R.id.schedule_rv_date);
        date_adapter= new DateRecyclerViewAdapter(selMonth,selDate,Schedule.this);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        date_rv.setLayoutManager(lm);

        date_rv.setAdapter(date_adapter);
//        date_rv.scrollToPosition(nowDate+1);
        date_rv.smoothScrollToPosition(nowDate+1);
        Log.i("Schedule","setScheduleRecyclerView DATE : "+nowDate);

    }
    public void setDataSet(){
        contests.add(new Contestitem(1,1,"안녕",1,"1993-10-1","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",1,"1993-10-1","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",1,"1993-10-1","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",1,"1993-10-1","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-2","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-2","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-2","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-2","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-2","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-3","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-3","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-3","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-3","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-3","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",4,"1993-10-10","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-11","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-12","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-13","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-14","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-15","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-11","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-16","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-20","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-20","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-20","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
    }


    @Override
    public void setOnItemClickForDate(int selectedDate) {
            Log.i("Schedule","날짜 : "+selectedDate);
            //날짜 구해서 해당 날짜로 아이템 포커싱
            focusItemByDate(selectedDate);
    }

    @Override
    public void setOnItemClickForSchedule(final int selectedPosition) {
        Log.i("Schedule","선택된 포지션 : "+selectedPosition);
        //아이템 클릭시,
        MenuDialog dialog = new MenuDialog(Schedule.this,
                //왼쪽 리스너
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //공연단 소개 페이지로 넘기기.
                        Intent intent = new Intent();

                        //팀 소개를 보여줄 것이므로 팀넘버를 넘겨준다.
                        intent.putExtra("teamnum",contests.get(selectedPosition).getTeamName());
                        startActivity(intent);
                    }
                },
                //오른쪽 리스너
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //맵으로 넘기기
                        Intent intent = new Intent();

                        //공연 위치를 보여줘야 하므로 공연 넘버를 넘겨줌
                        intent.putExtra("contestnum",contests.get(selectedPosition).getContestnum());
                        startActivity(intent);
                    }
                });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }
}





