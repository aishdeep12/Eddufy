package com.edufy.eddufy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends ArrayAdapter {
    Context context;
    public Button connectButton;
    public String uid;


ArrayList<RegistrationInformation> fireBaseFetch;
//    ArrayList<FireBaseFetch> fireBaseFetch;
    public InfoAdapter(Activity context, ArrayList<RegistrationInformation> fireBaseFetch, String uid) {

        super(context,R.layout.listview,fireBaseFetch);

        this.context = context;
        this.uid = uid;
        this.fireBaseFetch = fireBaseFetch;

    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        RegistrationInformation fireBaseFetchVariable = fireBaseFetch.get(position);

          View  listViewItem = LayoutInflater.from(context).inflate(R.layout.listview, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewCourse = listViewItem.findViewById(R.id.textViewCourse);
        ImageView imageViewImage = listViewItem.findViewById(R.id.imageViewImage);
       connectButton = listViewItem.findViewById(R.id.coneectButton);

        textViewName.setText(fireBaseFetchVariable.name);
        textViewCourse.setText(fireBaseFetchVariable.course);
        imageViewImage.setImageResource(R.drawable.mystudent);


connectButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("uid",uid);
        context.startActivity(intent);

    }
});
        return listViewItem;

    }


}
