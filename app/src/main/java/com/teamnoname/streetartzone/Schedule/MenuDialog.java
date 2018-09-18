package com.teamnoname.streetartzone.Schedule;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

/**
 * Created by iyeonghan on 2018. 9. 1..
 */

public class MenuDialog extends Dialog {

    Button goMap,goIntroduce;

    View.OnClickListener left,right;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.schedule_dialog);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);



        goMap = (Button)findViewById(R.id.btn_map);
        goIntroduce = (Button)findViewById(R.id.btn_introduce);
        goMap.setOnClickListener(right);
        goIntroduce.setOnClickListener(left);


    }

    public MenuDialog(@NonNull Context context, View.OnClickListener left, View.OnClickListener right) {
        super(context);
        this.left = left;
        this.right = right;
    }

    public MenuDialog(@NonNull Context context, int themeResId, View.OnClickListener left, View.OnClickListener right) {
        super(context, themeResId);
        this.left = left;
        this.right = right;
    }

    public MenuDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, View.OnClickListener left, View.OnClickListener right) {
        super(context, cancelable, cancelListener);
        this.left = left;
        this.right = right;
    }
}
