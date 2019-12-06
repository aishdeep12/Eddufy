package com.edufy.eddufy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
//        public EditText editText3;
        private InfoAdapter infoAdapter;
        Spinner spinner_selection;
        String path;
    RegistrationInformation fireBaseFetchData;

public String uid;



    //Spinner spinnerSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//       databaseReference = FirebaseDatabase.getInstance().getReference("tutors");

        tutorsList = new ArrayList<>();
        listViewTutors = findViewById(R.id.listViewTutors);
       spinner_selection = findViewById(R.id.spinner_selection);

        Bundle extras = getIntent().getExtras();

        uid = extras.getString("uid");

       databaseReference = FirebaseDatabase.getInstance().getReference("tutors");

        spinner_selection.setOnItemSelectedListener(this);



    }





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
                databaseReference = FirebaseDatabase.getInstance().getReference("tutors");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tutorsList.clear();

                for (DataSnapshot tutorSnapshot : dataSnapshot.getChildren()) {

                   fireBaseFetchData = tutorSnapshot.getValue(RegistrationInformation.class);

                    tutorsList.add(fireBaseFetchData);
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

        path = "tutors";


    }
}
