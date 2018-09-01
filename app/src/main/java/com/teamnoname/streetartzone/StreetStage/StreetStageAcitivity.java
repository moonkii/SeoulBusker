package com.teamnoname.streetartzone.StreetStage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

public class StreetStageAcitivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map_stageInfo;
    private Spinner sp_district;
    private ArrayList<StageInfo> array_stageInfo;

    private LinearLayout bottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private RecyclerView recycler_stageInfo;
    private StageListAdapter adapter_stageInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetstage);
        array_stageInfo = initData();
        initView();
    }

    private void initView(){
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.streetstage_map_stageinfo);
        mapFragment.getMapAsync(this);

        bottomSheet = (LinearLayout)findViewById(R.id.streetstage_bottom_sheet);
        sp_district = (Spinner)findViewById(R.id.streetstage_spinner_district);

        recycler_stageInfo = (RecyclerView)findViewById(R.id.bottomsheet_recycler_stageinfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_stageInfo.setLayoutManager(linearLayoutManager);
        adapter_stageInfoList = new StageListAdapter(this,array_stageInfo);
        recycler_stageInfo.setAdapter(adapter_stageInfoList);

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                map_stageInfo.clear();
                String spSelectedValue = (String) sp_district.getSelectedItem();
                for(int i =0; i<array_stageInfo.size(); i++){
                    StageInfo data = array_stageInfo.get(i);
                    if(spSelectedValue.equals("전체")){
                        makeMarker(data);
                    }else if(spSelectedValue.equals(data.getDistrict())){
                        makeMarker(data);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private ArrayList<StageInfo> initData(){
        ArrayList<StageInfo> array_stageInfo = new ArrayList<>();
        array_stageInfo.add(new StageInfo("성북구",
                "보문역",
                "서울 성북구 보문로 116",
                37.585811,
                127.019532));
        array_stageInfo.add(new StageInfo("종로구",
                "낙산공원 중앙광장",
                "서울특별시 종로구 동숭동 50-125",
                37.580666,
                127.006982));
        array_stageInfo.add(new StageInfo("성북구",
                "돈암시장",
                "서울특별시 성북구 동소문동5가59-1",
                37.591876,
                127.015670));
        array_stageInfo.add(new StageInfo("종로구",
                "세종대로 차 없는 거리",
                "서울특별시 종로구 세종로 80-9",
                37.572784,
                126.976604));

        return array_stageInfo;
    }

    private void makeMarker(StageInfo data){
        MarkerOptions options = new MarkerOptions();
        options
                .position(new LatLng(data.getLat(),data.getLot()))
                .title(data.getPlaceName());
        map_stageInfo.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map_stageInfo = googleMap;
        LatLng seoul = new LatLng(37.566677, 126.978416);
        map_stageInfo.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,12.3f));

    }


}

class StageListAdapter extends RecyclerView.Adapter<StageListAdapter.ItemViewHolder>{

    private Context context;
    private ArrayList<StageInfo> items;


    public StageListAdapter(Context context, ArrayList<StageInfo> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottomsheet_stageinfo_item,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        StageInfo item = items.get(position);
        holder.address.setText(item.getAddress());
        holder.placeName.setText(item.getPlaceName());
        holder.district.setText(item.getDistrict());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView district;
        private TextView placeName;
        private TextView address;

        public ItemViewHolder(View itemView) {
            super(itemView);
            district = (TextView)itemView.findViewById(R.id.stageinfo_item_tv_district);
            placeName = (TextView)itemView.findViewById(R.id.stageinfo_item_tv_placename);
            address = (TextView)itemView.findViewById(R.id.stageinfo_item_tv_address);
        }
    }
}

