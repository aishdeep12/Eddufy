package com.edufy.eddufy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class FeedsAdapter extends ArrayAdapter {
    Context context;
    public Button chatButton;
    public String uid ;
    public String posterId ;
    public String userEmail;
    public String userpost;
    PostInfo firebasevariable2;


    ArrayList<PostInfo> fireBaseFetch;
    //    ArrayList<FireBaseFetch> fireBaseFetch;
    public FeedsAdapter(Activity context, ArrayList<PostInfo> fireBaseFetch) {

        super(context,R.layout.listview,fireBaseFetch);

        this.context = context;
        this.uid = uid;
        this.fireBaseFetch = fireBaseFetch;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        final PostInfo fireBaseFetchVariable = fireBaseFetch.get(position);




        convertView = LayoutInflater.from(context).inflate(R.layout.feeds, null, true);

        TextView textViewName = convertView.findViewById(R.id.user_name);
        TextView textViewQuestion = convertView.findViewById(R.id.user_question);

        chatButton = convertView.findViewById(R.id.chat_button);

        textViewName.setText(fireBaseFetchVariable.userEmail);
        textViewQuestion.setText(fireBaseFetchVariable.post);

        firebasevariable2 = fireBaseFetchVariable;



        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentRow = (View) view.getParent();

                ListView listView = (ListView) parentRow.getParent();

                final int position = listView.getPositionForView(parentRow);


                final PostInfo fireBaseFetchVariable = fireBaseFetch.get(position);


                userEmail = fireBaseFetchVariable.userEmail;
                userpost = fireBaseFetchVariable.post;
                posterId = fireBaseFetchVariable.userId;


                Intent intent = new Intent(chatButton.getContext(), ChatActivity.class);
                intent.putExtra("uid", uid);
                intent.putExtra("tutorid", posterId);
                intent.putExtra("tutorName", userEmail);
                context.startActivity(intent);






            }
        });
        return convertView;

    }


}
