package com.teamnoname.streetartzone.StreetGroup;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

public class TicketDialog extends Dialog {

    ImageView imgV_ticket;
    TextView tv_groupName;
    TextView tv_date;
    TextView tv_place;
    TextView tv_time;

    int selectedSeq;


    public TicketDialog(@NonNull Context context, int selectedSeq) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_ticket);

        this.selectedSeq = selectedSeq;

        imgV_ticket = (ImageView) findViewById(R.id.dialog_ticket_image);
        tv_groupName = (TextView) findViewById(R.id.dialog_ticket_groupName);
        tv_date = (TextView) findViewById(R.id.dialog_ticket_date);
        tv_place = (TextView) findViewById(R.id.dialog_ticket_place);
        tv_time = (TextView) findViewById(R.id.dialog_ticket_time);








    }
}
