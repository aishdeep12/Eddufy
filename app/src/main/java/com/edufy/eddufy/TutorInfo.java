package com.edufy.eddufy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorInfo extends AppCompatActivity {

    TextView textViewName , textViewEmail, textViewCourse,textViewInstitution;
    String name, email,course ,institution;
Button sendMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_info);

        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewCourse = findViewById(R.id.textViewCourse);
        textViewInstitution = findViewById(R.id.textViewInstitution);
        sendMail = findViewById(R.id.button_send);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        email = extras.getString("email");
        course = extras.getString("course");
        institution = extras.getString("institution");

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewInstitution.setText(institution);
        textViewCourse.setText(course);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                startActivity(Intent.createChooser(emailIntent, null));
            }
        });

    }
}
