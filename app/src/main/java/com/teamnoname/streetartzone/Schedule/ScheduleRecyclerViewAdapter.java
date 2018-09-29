package com.teamnoname.streetartzone.Schedule;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by iyeonghan on 2018. 9. 12..
 */

public
//공연 어댑터
class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {

    ArrayList<Contestitem> arrayListContests;
    ClickListener clickListener;
    ArrayList<Integer> dateCheck = new ArrayList<>();
    Hashtable<Integer,Integer> dateNotice = new Hashtable<>();
    public interface RecyclerViewItemClickListener{
        void setOnItemClick(int selectedPosition);
}

    public ScheduleRecyclerViewAdapter(ArrayList<Contestitem> arrayList,ClickListener clickListener) {
        this.arrayListContests = arrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.schedule_item_layout, parent, false);


        return new ScheduleViewHolder(itemView);
    }


//    //현재의 포지션을 확인하여 어떤 타입의 레이아웃을 보여줄지 결정할 viewtype을 리턴함.
//    //position : 새로 보여질 뷰의 포지션값.. 그때 현재 리사이클러뷰에서 보여지는 몇번쨰 포지션인지 리턴함
//    @Override
//    public int getItemViewType(int position){
//        Log.i("Adapter","getItemViewType position : "+position);
//        Log.i("Adapter","getItemViewType position : "+super.getItemViewType(position));
//
//        arrayListContests.get(position).getDate();
//        //구분선을 어떻게 나눌지 생각해 봐야함.
//        //일을 확인해서
//        return 0;
//    }


    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int position) {

        String fullDate = arrayListContests.get(position).getDate();

        String[] devidedDate = fullDate.split("-");
        int year =Integer.parseInt(devidedDate[0]);
        int month = Integer.parseInt(devidedDate[1]);
        int date = Integer.parseInt(devidedDate[2]);

        String whatDate = null;
        try {
             whatDate = getDateDay(fullDate);
        } catch (Exception e) {

            e.printStackTrace();
        }

//        boolean already = false;    //리사이클러뷰에 일표시를 해줬는지 확인하는 변수
//        //리사이클러뷰에 이미 일표시를 해줬는지 확인하는 과정.
//        for(int i=0;i<dateCheck.size();i++){
//            if(dateCheck.get(i)==date){
//                already = true;
//            }
//        }
        if(dateNotice.get(date)==null){
            dateNotice.put(date,position);
            holder.date.setText(date+"("+whatDate+")");
            holder.date.setVisibility(View.VISIBLE);

        }else if(dateNotice.get(date)==position){
            holder.date.setText(date+"("+whatDate+")");
            holder.date.setVisibility(View.VISIBLE);
        }else{
            holder.date.setVisibility(View.GONE);
        }
//        //안해줬다면, 현재 요일을 입력해주고 visible로 변경.
//        if(already==false){
//            holder.date.setText(date+"("+whatDate+")");
//            holder.date.setVisibility(View.VISIBLE);
//            dateCheck.add(date);
//        }

        if(arrayListContests.get(position).getTeamName().length()>10){
            holder.teamname.setTextSize(15);
        }else {
            holder.teamname.setTextSize(20);
        }
        holder.teamname.setText(arrayListContests.get(position).getTeamName());
        holder.place.setText(arrayListContests.get(position).getPlace());
        holder.time.setText(arrayListContests.get(position).getTime());
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.setOnItemClickForSchedule(position);
            }
        });

        //색깔 변동주기
        if(arrayListContests.get(position).getContestType()==1){//기악
        holder.type_light.setBackgroundColor(Color.parseColor("#C40000"));
        }else if(arrayListContests.get(position).getContestType()==2){//퍼포먼스
            holder.type_light.setBackgroundColor(Color.parseColor("#14C322"));
        }else if(arrayListContests.get(position).getContestType()==3){//전통
            holder.type_light.setBackgroundColor(Color.YELLOW);
        }else{  //4 : 음악.
            holder.type_light.setBackgroundColor(Color.parseColor("#0B427B"));


        }




    }

    @Override
    public int getItemCount() {
        return (null == arrayListContests) ? 0 : arrayListContests.size();
    }


    public  class ScheduleViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView teamname ;
        TextView place;
        TextView time;
        TextView date;
        LinearLayout type_light ;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            teamname = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_teamname);
            place = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_place);
            time = (TextView)itemView.findViewById(R.id.schedule_activity_txtv_time);
            type_light =(LinearLayout) itemView.findViewById(R.id.schedule_activity_layout_light);
            date = (TextView)itemView.findViewById(R.id.schedule_date_txtv);

        }
        public View getItemView(){return this.itemView;}
    }


    //요일 구하는 메소드
    public String getDateDay(String date) throws Exception {


        String day = "" ;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd") ;
        Date nDate = dateFormat.parse(date) ;

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;



        switch(dayNum){
            case 1:
                day = "일";
                break ;
            case 2:
                day = "월";
                break ;
            case 3:
                day = "화";
                break ;
            case 4:
                day = "수";
                break ;
            case 5:
                day = "목";
                break ;
            case 6:
                day = "금";
                break ;
            case 7:
                day = "토";
                break ;

        }



        return day ;
    }

}


//그냥 서버로 부터 데이터를 가져올 때 한번에 다 가져와서 저장한다.
class Contestitem{
    int contestnum;
    String teamName;
    int contestType; //1.기악 2.퍼포먼스 3. 전통 4. 음악
    String date;
    String time;
    String place;

    public Contestitem(int contestnum, String teamName, int contestType, String date, String time, String place) {
        this.contestnum = contestnum;
        this.teamName = teamName;
        this.contestType = contestType;
        this.date = date;
        this.time = time;
        this.place = place;
    }

    public int getContestnum() {
        return contestnum;
    }

    public void setContestnum(int contestnum) {
        this.contestnum = contestnum;
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

}
