package com.teamnoname.streetartzone.StreetGroup;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

public class StreetGroupRecyclerViewAdapter extends RecyclerView.Adapter<StreetGroupRecyclerViewAdapter.RecyclerViewHolder> {

    private static String TAG="StreetGroupRecyclerViewAdapter";
    private ArrayList<StreetGroupItem> arrayList_item;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public StreetGroupRecyclerViewAdapter(ArrayList<StreetGroupItem> arrayList_item,RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.arrayList_item = arrayList_item;
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }


    @NonNull
    @Override
    public StreetGroupRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //아이템 뷰 생성
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.streetgroup_item, parent, false);


        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.tv_groupCategory.setText(arrayList_item.get(position).getGroup_category());
        holder.tv_groupName.setText(arrayList_item.get(position).getGroup_name());
//        holder.imgV_titleImg.setImageResource(arrayList_item.get(position).getGroup_img());

        final int itemPosition = position;

        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewItemClickListener.setOnItemClick(itemPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null == arrayList_item) ? 0 : arrayList_item.size();
    }

    public interface RecyclerViewItemClickListener{
        void setOnItemClick(int selectedPosition);
    }


    //공연팀 리사이클러뷰 뷰홀더
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {


        View itemView;
        ImageView imgV_titleImg;
        TextView tv_groupName;
        TextView tv_groupCategory;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgV_titleImg = (ImageView) itemView.findViewById(R.id.group_item_img);
            tv_groupCategory = (TextView) itemView.findViewById(R.id.group_item_category);
            tv_groupName = (TextView) itemView.findViewById(R.id.group_item_name);


        }

        public View getItemView(){return this.itemView;}
    }

}


//공연팀 리사이클러뷰 아이템 클래스
class StreetGroupItem {

    private String group_img;
    private String group_name;
    private String group_category;


    public StreetGroupItem(String group_img, String group_name, String group_category) {
        this.group_img = group_img;
        this.group_name = group_name;
        this.group_category = group_category;
    }


    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_category() {
        return group_category;
    }

    public void setGroup_category(String group_category) {
        this.group_category = group_category;
    }
}