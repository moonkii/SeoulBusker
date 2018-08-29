package com.teamnoname.streetartzone.StreetGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnoname.streetartzone.R;

public class ScheduleFragment extends Fragment {

    public static ScheduleFragment newInstance(){
        Bundle args = new Bundle();

        ScheduleFragment ScheduleFragment = new ScheduleFragment();
        ScheduleFragment.setArguments(args);
        return ScheduleFragment;
    }

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_group_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}