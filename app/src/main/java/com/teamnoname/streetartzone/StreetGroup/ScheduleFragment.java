package com.teamnoname.streetartzone.StreetGroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamnoname.streetartzone.Data.Contest;
import com.teamnoname.streetartzone.Data.GroupData;
import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScheduleFragment extends Fragment {

    ArrayList<ScheduleItem> arrayList_schedule= new ArrayList<>();
    ArrayList<Integer> arrayList_scheduleCategory= new ArrayList<>();
    RecyclerView recyclerView_schedule;
    ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;


    Realm realm = Realm.getDefaultInstance();

    public static ScheduleFragment newInstance(){
        Bundle args = new Bundle();

        ScheduleFragment ScheduleFragment = new ScheduleFragment();
        ScheduleFragment.setArguments(args);
        return ScheduleFragment;
    }

    public ScheduleFragment() {
        //        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_group_schedule,container,false);

        recyclerView_schedule = (RecyclerView) view.findViewById(R.id.group_schedule_recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        setScheduleData();
        setScheduleData2();
        setScheduleRecyclerView();

    }

    public void setScheduleData(){
        arrayList_schedule = new ArrayList<>();
        arrayList_scheduleCategory = new ArrayList<>();

        for(int i=0; i<20 ; i++){
            String date = Integer.toString(i);
            if(i<5){
                arrayList_schedule.add(new ScheduleItem(date,"종로구 | 낙산공원 놀이마당","14:00","16:00","2018","4월"));
            }else if(i<10){
                arrayList_schedule.add(new ScheduleItem(date,"종로구 | 낙산공원 놀이마당","14:00","16:00","2018","5월"));

            }else if(i<15){
                arrayList_schedule.add(new ScheduleItem(date,"종로구 | 낙산공원 놀이마당","14:00","16:00","2018","6월"));
            }else{
                arrayList_schedule.add(new ScheduleItem(date,"종로구 | 낙산공원 놀이마당","14:00","16:00","2018","7월"));
            }


            if(i>0){
                if (!arrayList_schedule.get(i).getMonth().equals(arrayList_schedule.get(i-1).getMonth())) {
                    Log.v("일정구분",": "+i);
                    arrayList_scheduleCategory.add(i);
                }
            }

        }
    }


    public void setScheduleData2(){
        GroupData groupData = realm.where(GroupData.class).equalTo("group_seq",StreetGroupDetailActivity.selectedSeq).findFirst();
        RealmResults<Contest> contest = realm.where(Contest.class).equalTo("teamname",groupData.getGroup_name()).findAllSorted("date");

for (int i=0;i<contest.size();i++){
    String fulldate = contest.get(i).getDate();
    String fulltime = contest.get(i).getTime();
    String []devider = fulldate.split("-");
    String year = devider[0];
    String date = devider[2];
    String []devider2 = fulltime.split("~");
    String starttime = devider2[0];
    String endtime = devider2[1];
    String month = contest.get(i).getMonth();
    String place = contest.get(i).getDistrict()+" | "+contest.get(i).getArea();
    arrayList_schedule.add(new ScheduleItem(date,place,starttime,endtime,year,month));

    if(i>0) {
        if (!arrayList_schedule.get(i).getMonth().equals(arrayList_schedule.get(i - 1).getMonth()))
            arrayList_scheduleCategory.add(i);
    }

}





    }



    public void setScheduleRecyclerView(){

        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(arrayList_schedule,arrayList_scheduleCategory);
        recyclerView_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_schedule.setAdapter(scheduleRecyclerViewAdapter);

    }

}


class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder>{

    ArrayList<ScheduleItem> arrayList_schedule;
    ArrayList<Integer> arrayList_scheduleCategory;


    public ScheduleRecyclerViewAdapter(ArrayList<ScheduleItem> arrayList_schedule, ArrayList<Integer> arrayList_scheduleCategory) {
        this.arrayList_schedule = arrayList_schedule;
        this.arrayList_scheduleCategory = arrayList_scheduleCategory;
    }

    @NonNull
    @Override
    public ScheduleRecyclerViewAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.streetgroup_detail_schedule_item, parent, false);


        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

        holder.tv_schedule_category_month.setText(arrayList_schedule.get(position).getMonth());
        holder.tv_schedule_date.setText(arrayList_schedule.get(position).getDate());
        holder.tv_schedule_place.setText(arrayList_schedule.get(position).getPlace());
        holder.tv_schedule_start.setText(arrayList_schedule.get(position).getStartTime());
        holder.tv_schedule_end.setText(arrayList_schedule.get(position).getEndTime());

        if(holder.getAdapterPosition()==0){
            if(holder.linearLayout_schedule_category.getVisibility()==View.GONE){
                holder.linearLayout_schedule_category.setVisibility(View.VISIBLE);
            }
        }else {

            if(arrayList_scheduleCategory.contains(holder.getAdapterPosition())) {
                Log.v("일정 구분 포지션값 : ",""+holder.getAdapterPosition());
                if (holder.linearLayout_schedule_category.getVisibility() == View.GONE) {
                    holder.linearLayout_schedule_category.setVisibility(View.VISIBLE);
                }
            }else{
                if(holder.linearLayout_schedule_category.getVisibility() !=View.GONE){
                    holder.linearLayout_schedule_category.setVisibility(View.GONE);
                }
            }
        }










    }


    @Override
    public int getItemCount() {
        return (null == arrayList_schedule) ? 0 : arrayList_schedule.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView tv_schedule_date;
        TextView tv_schedule_place;
        TextView tv_schedule_start;
        TextView tv_schedule_end;

        TextView tv_schedule_category_year;
        TextView tv_schedule_category_month;
        LinearLayout linearLayout_schedule_category;


        public ScheduleViewHolder(View itemView) {
            super(itemView);
            tv_schedule_date = (TextView) itemView.findViewById(R.id.group_schedule_date);
            tv_schedule_place = (TextView) itemView.findViewById(R.id.group_schedule_place);
            tv_schedule_start = (TextView) itemView.findViewById(R.id.group_schedule_start);
            tv_schedule_end = (TextView) itemView.findViewById(R.id.group_schedule_end);

            tv_schedule_category_month = (TextView) itemView.findViewById(R.id.group_schedule_category_month);
            tv_schedule_category_year = (TextView) itemView.findViewById(R.id.group_schedule_category_year);
            linearLayout_schedule_category = (LinearLayout) itemView.findViewById(R.id.group_schedule_category);




        }
    }


}



class ScheduleItem {

    String date;
    String place;
    String startTime;
    String endTime;

    String year;
    String month;

    public ScheduleItem(String date, String place, String startTime, String endTime, String year, String month) {
        this.date = date;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.year = year;
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}