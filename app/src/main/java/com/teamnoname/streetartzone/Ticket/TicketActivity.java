package com.teamnoname.streetartzone.Ticket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.teamnoname.streetartzone.R;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class TicketActivity extends AppCompatActivity{

    FeatureCoverFlow coverFlow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
    }
}
