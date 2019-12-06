package com.edufy.eddufy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class InfoAdapter extends ArrayAdapter {
    Context context;
    public Button connectButton;
    public String uid ;
    public String tutorId;
    public String tutorName;
    public int positions;
    RegistrationInformation firebasevariable2;


ArrayList<RegistrationInformation> fireBaseFetch;
//    ArrayList<FireBaseFetch> fireBaseFetch;
    public InfoAdapter(Activity context, ArrayList<RegistrationInformation> fireBaseFetch, String uid) {

        super(context,R.layout.listview,fireBaseFetch);

        this.context = context;
        this.uid = uid;
        this.fireBaseFetch = fireBaseFetch;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        final RegistrationInformation fireBaseFetchVariable = fireBaseFetch.get(position);



          convertView = LayoutInflater.from(context).inflate(R.layout.listview, null, true);
//
//     if (!uid.equals(fireBaseFetchVariable.userId)) {

           // convertView = LayoutInflater.from(context).inflate(R.layout.listview, null, true);
            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewCourse = convertView.findViewById(R.id.textViewCourse);
            ImageView imageViewImage = convertView.findViewById(R.id.imageViewImage);
            connectButton = convertView.findViewById(R.id.coneectButton);

            textViewName.setText(fireBaseFetchVariable.name);
            textViewCourse.setText(fireBaseFetchVariable.course);
            imageViewImage.setImageResource(R.drawable.mystudent);

            firebasevariable2 = fireBaseFetchVariable;


        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(context,TutorInfo.class);

                intent.putExtra("name",fireBaseFetchVariable.name);
                intent.putExtra("email",fireBaseFetchVariable.email);
                intent.putExtra("course",fireBaseFetchVariable.course);
                intent.putExtra("institution",fireBaseFetchVariable.institution);
                context.startActivity(intent);
                return false;
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();

                ListView listView = (ListView) parentRow.getParent();

                final int position = listView.getPositionForView(parentRow);


                final RegistrationInformation fireBaseFetchVariable = fireBaseFetch.get(position);


                    tutorId = fireBaseFetchVariable.userId;
                tutorName = fireBaseFetchVariable.name;

                    System.out.println(tutorId + "In adaptor tutor");
                    System.out.println(uid + "In adaptor uid");

                    Intent intent = new Intent(connectButton.getContext(), ChatActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("tutorid", tutorId);
                    intent.putExtra("tutorName", tutorName);
                    context.startActivity(intent);






            }
        });
            return convertView;

        }


}
