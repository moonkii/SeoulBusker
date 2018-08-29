package com.teamnoname.streetartzone.StreetGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamnoname.streetartzone.R;

public class InfoFragment extends Fragment {


    public static InfoFragment newInstance(){
        Bundle args = new Bundle();

        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(args);
        return infoFragment;
    }


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_group_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
