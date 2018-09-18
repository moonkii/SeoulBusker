package com.teamnoname.streetartzone.Schedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by iyeonghan on 2018. 8. 29..
 */

public class Schedule extends Activity{
    Calendar calendar;         //현재 날짜를 가져올 수 있는 클래스.
    int nowDate,nowMonth,nowYear;                        //현재 날짜.
    int selDate,selMonth,selYear;                        //사용자에 의해 선택된 날짜.
    ArrayList<Contestitem> contests = new ArrayList<>();                            //공연시간 데이터를 담을 컨테이너.

    RecyclerView rv;
    TextView showMonth ;
    Button nextMonthBtn,prevMonthBtn;


    //어댑터
    ScheduleRecyclerViewAdapter schedule_adapter;
    DateRecyclerViewAdapter date_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    nextMonthBtn = (Button)findViewById(R.id.schedule_activity_btn_nextmonth);
    prevMonthBtn = (Button)findViewById(R.id.schedule_activity_btn_prevmonth);


//    rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//            //아이템 클릭시,
//        new MenuDialog(Schedule.this,
//                //왼쪽 리스너
//                new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        //공연단 소개 페이지로 넘기기.
//                     Intent intent = new Intent();
//
//                     //팀 소개를 보여줄 것이므로 팀넘버를 넘겨준다.
//                     intent.putExtra("teamnum",contests.get(position).getTeamName());
//                     startActivity(intent);
//                    }
//                },
//                //오른쪽 리스너
//                new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //맵으로 넘기기
//                Intent intent = new Intent();
//
//                //공연 위치를 보여줘야 하므로 공연 넘버를 넘겨줌
//                intent.putExtra("contestnum",contests.get(position).getContestName());
//                startActivity(intent);
//            }
//        }).show();
//        }
//    });


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
            //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
            date_adapter.setMonth(selDate);
            date_adapter.notifyDataSetChanged();
            //ArrayList안에 있는 데이터도 모두 현재월의 데이터로 변경해줘야함.

            //그 이후 노티
            schedule_adapter.notifyDataSetChanged();

            //nowMonth==selMonth 라면 ListView의 포커싱을 현재 nowDate로 .

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
            //변경된 월수에 맞춰 상단 날짜 변경.
            showMonth.setText(selYear+"년 "+selMonth+"월");
            //리스트뷰를 해당 월에 맞춰 변경해 줘야함.
            date_adapter.setMonth(selDate);
            date_adapter.notifyDataSetChanged();
            //ArrayList안에 있는 데이터도 모두 현재월의 데이터로 변경해줘야함

            //그 이후 노티
            schedule_adapter.notifyDataSetChanged();


            //nowMonth==selMonth 라면 ListView의 포커싱을 현재 nowDate로 .

        }
    });


}
    public void setScheduleRecyclerView(){

        schedule_adapter= new ScheduleRecyclerViewAdapter(contests);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(schedule_adapter);

    }
    public void setDateRecyclerView(){

         date_adapter= new DateRecyclerViewAdapter(selMonth);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(lm);
        rv.setAdapter(date_adapter);

    }
    public void setDataSet(){
        contests.add(new Contestitem(1,1,"안녕",1,"1993-10-23","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",2,"1993-10-23","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",3,"1993-10-23","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",4,"1993-10-23","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
        contests.add(new Contestitem(1,1,"안녕",5,"1993-10-23","10:00 ~ 11:00","[성북구] 솔샘로 3길",123.1,123.1));
    }

}


//날짜 어댑터.
class DateRecyclerViewAdapter extends RecyclerView.Adapter<DateRecyclerViewAdapter.DateViewHolder>{
    int month ;

    public DateRecyclerViewAdapter(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }



    //해당 월의 마지막 날짜를 찾아주는 함수.
    int LastDate(int month){
        if(month==2){
            return 28;
        }else if(month==4||month==6||month==9||month==11){
            return 30;
        }else{
            return 31;
        }
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.schedule_date_item,parent,false);
        return new DateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        //Text에 값 초기화
        holder.date.setText(position);
    }

    @Override
    public int getItemCount() {

        //해당 월에 숫자만큼 찍어내기
        return LastDate(month);
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {


        TextView date ;


        public DateViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.schedule_date_txt);

        }
    }
}



//공연 어댑터
class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {

    ArrayList<Contestitem> arrayList_review;


    public ScheduleRecyclerViewAdapter(ArrayList<Contestitem> arrayList) {
        this.arrayList_review = arrayList_review;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.schedule_item_layout, parent, false);


        return new ScheduleViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
    holder.teamname.setText(arrayList_review.get(position).getTeamName());
    holder.place.setText(arrayList_review.get(position).getPlace());
    holder.time.setText(arrayList_review.get(position).getTime());


    //색깔 변동주기

    if(arrayList_review.get(position).getContestType()==1){//기악
//        holder.type_light.setBackgroundColor();
    }else if(arrayList_review.get(position).getContestType()==2){//퍼포먼스

    }else if(arrayList_review.get(position).getContestType()==3){//전통음악

    }



    }

    @Override
    public int getItemCount() {
        return (null == arrayList_review) ? 0 : arrayList_review.size();
    }


    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {


        TextView teamname ;
        TextView place;
        TextView time;
        LinearLayout type_light ;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
         teamname = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_teamname);
         place = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_place);
         time = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_time);
         type_light =(LinearLayout) itemView.findViewById(R.id.schedule_activity_layout_light);


        }
    }
}


//그냥 서버로 부터 데이터를 가져올 때 한번에 다 가져와서 저장한다.
class Contestitem{
    int contestnum,teamnum;
    String teamName;


    int contestType; //1.기악 2.퍼포먼스 3. 전통음악
    String date;
    String time;
    String place;
    double x;
    double y;

    public Contestitem(int contestnum, int teamnum, String teamName, int contestType, String date, String time, String place, double x, double y) {
        this.contestnum = contestnum;
        this.teamnum = teamnum;
        this.teamName = teamName;
        this.contestType = contestType;
        this.date = date;
        this.time = time;
        this.place = place;
        this.x = x;
        this.y = y;
    }

    public int getContestnum() {
        return contestnum;
    }

    public void setContestnum(int contestnum) {
        this.contestnum = contestnum;
    }

    public int getTeamnum() {
        return teamnum;
    }

    public void setTeamnum(int teamnum) {
        this.teamnum = teamnum;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public int getContestType() {
        return contestType;
    }

    public void setContestType(int contestType) {
        this.contestType = contestType;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
