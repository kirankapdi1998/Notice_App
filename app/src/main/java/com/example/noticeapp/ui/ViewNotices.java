package com.example.noticeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.noticeapp.R;
import com.example.noticeapp.data.MyAdpater;
import com.example.noticeapp.model.Notice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewNotices extends AppCompatActivity {

//    private FirebaseRecyclerAdapter adapter;
    private ProgressDialog progress;
    private DatabaseReference mDatabase;
    private List<Notice> uploads,uploadall;
    private MyAdpater ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notices);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        uploads=new ArrayList<>();
        uploadall=new ArrayList<>();
        Query q = FirebaseDatabase.getInstance().getReference("Notices").orderByChild("title");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.v("post",postSnapshot.getValue().toString());
                    Notice upload = postSnapshot.getValue(Notice.class);
                    Log.v("alert","alertmessage");
                    uploads.add(upload);
                }
//                Collections.reverse(uploads);
                uploadall.addAll(uploads);
                Log.v("track","track this line");
                ad = new MyAdpater(ViewNotices.this, uploads);
                recyclerView.setAdapter(ad);
                Log.v("track2","track this line as well");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}