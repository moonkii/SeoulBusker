package com.teamnoname.streetartzone.StreetGroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamnoname.streetartzone.Data.GroupReviewData;
import com.teamnoname.streetartzone.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReviewFragment extends Fragment {

    ArrayList<ReviewItem> arrayList_review;
    RecyclerView recyclerView_review;
    ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    TextView tv_reviewCount;

    //DataBases
    RealmResults<GroupReviewData> realmResults;
    int selectedSeq;
    Realm realm;



    public static ReviewFragment newInstance(){
        Bundle args = new Bundle();

        ReviewFragment reviewFragment = new ReviewFragment();
        reviewFragment.setArguments(args);
        return reviewFragment;
    }

    public ReviewFragment() {
        // Required empty public constructor
        selectedSeq = StreetGroupDetailActivity.selectedSeq;

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults = realm.where(GroupReviewData.class).equalTo("seq",selectedSeq).findAll();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_review,container,false);

        recyclerView_review = (RecyclerView) view.findViewById(R.id.fragment_group_review_recyclerView);
        tv_reviewCount = (TextView) view.findViewById(R.id.group_detail_review_count);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(realmResults!=null){
            String reviewCount = Integer.toString(realmResults.size());
            tv_reviewCount.setText(reviewCount);
        }

        setReviewData();
        setReviewRecyclerView();



    }

    public void setReviewData(){

        arrayList_review= new ArrayList<>();

        for(int i=0; i<realmResults.size(); i++){
            GroupReviewData tempData = realmResults.get(i);
            arrayList_review.add(new ReviewItem(tempData.getWriter(),tempData.getDate(),tempData.getContents(),tempData.getSocre()));
        }



    }


    public void setReviewRecyclerView(){

        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(arrayList_review);
        recyclerView_review.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_review.setAdapter(reviewRecyclerViewAdapter);



    }

}



class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    ArrayList<ReviewItem> arrayList_review;


    public ReviewRecyclerViewAdapter(ArrayList<ReviewItem> arrayList_review) {
        this.arrayList_review = arrayList_review;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.streetgroup_detail_review_item, parent, false);


        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.tv_review_name.setText(arrayList_review.get(position).getReviewer());
        holder.tv_review_date.setText(arrayList_review.get(position).getReveiwDate());
        holder.tv_review_content.setText(arrayList_review.get(position).getReviewContent());


    }

    @Override
    public int getItemCount() {
        return (null == arrayList_review) ? 0 : arrayList_review.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tv_review_name;
        TextView tv_review_date;
        TextView tv_review_content;
        ImageView imgV_review_img;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            tv_review_name = (TextView) itemView.findViewById(R.id.group_detail_review_name);
            tv_review_date = (TextView) itemView.findViewById(R.id.group_detail_review_date);
            tv_review_content = (TextView) itemView.findViewById(R.id.group_detail_review_content);
            imgV_review_img = (ImageView) itemView.findViewById(R.id.group_detail_review_img);
        }
    }
}




class ReviewItem {

    String reviewer;
    String reveiwDate;
    String reviewContent;
    int reviewScore;

    public ReviewItem(String reviewer, String reveiwDate, String reviewContent, int reviewScore) {
        this.reviewer = reviewer;
        this.reveiwDate = reveiwDate;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReveiwDate() {
        return reveiwDate;
    }

    public void setReveiwDate(String reveiwDate) {
        this.reveiwDate = reveiwDate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }
}