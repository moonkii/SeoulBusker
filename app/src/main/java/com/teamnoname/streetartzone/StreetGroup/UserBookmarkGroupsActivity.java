package com.teamnoname.streetartzone.StreetGroup;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.teamnoname.streetartzone.Data.UserBookMark;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class UserBookmarkGroupsActivity extends AppCompatActivity {

    private ImageView img_backBtn;
    private RecyclerView recycler_bookmarkGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

class UserBookMarkGroupListAdapter extends RealmRecyclerViewAdapter<UserBookMark,UserBookMarkGroupListAdapter.ItemViewHolder>{



    public UserBookMarkGroupListAdapter(@Nullable OrderedRealmCollection<UserBookMark> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}


