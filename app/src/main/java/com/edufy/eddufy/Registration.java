package com.edufy.eddufy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Registration extends AppCompatActivity implements OnItemSelectedListener{
    public Button buttonStudent;
    public Spinner spinnerCourse,spinnerOrganization;
    public Button buttonTutor;
    public Button buttonRegistration;
    public TextView editTextName;
    public RadioGroup rgGender;
    public String gender;
    public String email ;
    public String course ;
    public String institution ;
    public RadioButton Male,Female;

 DatabaseReference dbRef;
//    List<RegistrationInformation> registrationInfoList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    RegistrationInformation regInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextName = findViewById(R.id.editTextName);
        rgGender = findViewById(R.id.radioGroupGender);
        Male = findViewById(R.id.radioButtonMale);
        Female = findViewById(R.id.radioButtonFemale);

         email = getIntent().getStringExtra("Email");

        spinnerOrganization = findViewById(R.id.spinnerOrganization);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        buttonStudent = findViewById(R.id.buttonStudent);
        buttonTutor = findViewById(R.id.buttonTutor);
        buttonRegistration = findViewById(R.id.buttonRegistration);

//      course = spinnerCourse.getOnItemSelectedListener().toString();

//        String text = spinnerCourse.getSelectedItem().toString();
        spinnerOrganization.setOnItemSelectedListener(this);
        spinnerCourse.setOnItemSelectedListener(this);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(Male.isChecked())
                {
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }


            }
        });

        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCourse.setEnabled(true);

            }
        });

        buttonTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCourse.setEnabled(false);
            }
        });
buttonRegistration.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        DatabaseReference myTutor = database.getReference("tutors");
        DatabaseReference myStudent = database.getReference("student");
        if(spinnerCourse.isEnabled()== true){
            String id  = myTutor.push().getKey();
            String fUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            RegistrationInformation regInfo = new RegistrationInformation(id,editTextName.getText().toString(),email,gender,course,institution);
            myTutor.child(id).setValue(regInfo);

        }
        else{

            String id  = myStudent.push().getKey();
            RegistrationInformation regInfo = new RegistrationInformation(id,editTextName.getText().toString(),email,gender,course,"Nil");
            myStudent.child(id).setValue(regInfo);
        }

        Intent i = new Intent(Registration.this, Search.class);
        startActivity(i);

    }
});



//        myTutor.setValue("Trisha");
//        myTutor.child("boyfriend").setValue("aishdeep");
//        myTutor.child("girlfriend").setValue("trisha");
//
//        myStudent.child("boyfriend").setValue("aishdeep");
//        myStudent.child("girlfriend").setValue("trisha");

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        InfoFragment fragment = new InfoFragment();
//        fragmentTransaction.add(R.id.fragment, fragment);
//        fragmentTransaction.commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
if(adapterView == spinnerCourse){
        course = adapterView.getItemAtPosition(i).toString();}
else if(adapterView == spinnerOrganization)
        institution = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
