package com.edufy.eddufy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Feeds extends AppCompatActivity {

    EditText editTextFeeds;
    Button button_post;
    FirebaseDatabase firebaseDatabase;
    public BottomNavigationView bottomNavigationView;
    String userId;
    String userEmail;
    ListView posts_list;
    List<PostInfo> postInfosList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        editTextFeeds = findViewById(R.id.editTextFeeds);
        button_post = findViewById(R.id.button_post);
        posts_list = findViewById(R.id.post_list);
        bottomNavigationView = findViewById(R.id.navigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        postInfosList = new ArrayList<>();


        Bundle extra = getIntent().getExtras();
        userId = extra.getString("usedId");
        userEmail = extra.getString("emailSearch");


        button_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference("Posts");
                String id  = databaseReference.push().getKey();
                PostInfo postInfo = new PostInfo(userEmail, editTextFeeds.getText().toString(),userId);
              databaseReference.child(id).setValue(postInfo);
              editTextFeeds.setText("");
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               postInfosList.clear();

                for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {

                    PostInfo user = tutorSnapshot.getValue(PostInfo.class);

                    postInfosList.add(user);
                }
                FeedsAdapter adapter = new FeedsAdapter( Feeds.this, (ArrayList<PostInfo>) postInfosList);
                posts_list.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_search:
                    Intent intent = new Intent(Feeds.this,Search.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("email",userEmail);

                    startActivity(intent);
                    return true;

            }
            return false;
        }
    };




}
