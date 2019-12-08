package com.edufy.eddufy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        DatabaseReference databaseReference;
//        List<FireBaseFetch> tutorsList;
        List<RegistrationInformation> tutorsList;
        ListView listViewTutors;

    private NavigationView navigationView;
        private InfoAdapter infoAdapter;
        Spinner spinner_selection;
        String path;
    RegistrationInformation fireBaseFetchData;

            public String uid;
    public String userEmail;
    public BottomNavigationView bottomNavigationView;


    //Spinner spinnerSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//       databaseReference = FirebaseDatabase.getInstance().getReference("tutors");

        tutorsList = new ArrayList<>();
        listViewTutors = findViewById(R.id.post_list);
       spinner_selection = findViewById(R.id.spinner_selection);

        Bundle extras = getIntent().getExtras();
        uid = extras.getString("uid");



        userEmail = extras.getString("email");

       //databaseReference = FirebaseDatabase.getInstance().getReference("tutors");

        spinner_selection.setOnItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_feeds:
                    Intent intent = new Intent(Search.this,Feeds.class);
                    intent.putExtra("usedId", uid);
                    intent.putExtra("emailSearch",userEmail);
                    startActivity(intent);
                    return true;


            }
            return false;
        }
    };



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case (0):
                databaseReference = FirebaseDatabase.getInstance().getReference("tutors");
                break;
            case (1):
                databaseReference = FirebaseDatabase.getInstance().getReference("student");
                break;
            default:
//                databaseReference = FirebaseDatabase.getInstance().getReference("tutors");
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tutorsList.clear();

                for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {

                   fireBaseFetchData = tutorSnapshot.getValue(RegistrationInformation.class);

                    tutorsList.add(fireBaseFetchData);
                    Log.e(" firebase", fireBaseFetchData.toString());

                }
                InfoAdapter adapter = new InfoAdapter(Search.this, (ArrayList<RegistrationInformation>) tutorsList, uid);
                listViewTutors.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {




    }
}
