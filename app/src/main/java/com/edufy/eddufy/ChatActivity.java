package com.edufy.eddufy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public EditText text_send;
    public Button btn_Send;
    public TextView username;
    FirebaseUser fUser;
    Intent intent;
    MessageAdapter messageAdapter;
    List<MessageInfo> mChats;
    RecyclerView recycler_View;
    public String fUserString;
    public String tutorNameString;
    public String tutoridString;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        text_send = findViewById(R.id.text_send);
        btn_Send = findViewById(R.id.btn_Send);
        username = findViewById(R.id.username);
        recycler_View = findViewById(R.id.recycler_View);
        recycler_View.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recycler_View.setLayoutManager(linearLayoutManager);


        intent = getIntent();
        Bundle extras = getIntent().getExtras();

     fUserString = extras.getString("uid");
        tutorNameString = extras.getString("tutorName");

       tutoridString = extras.getString("tutorid");

        username.setText(tutorNameString);

        createNotificationChannel();


       btn_Send.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

               String msg = text_send.getText().toString();
               if(!msg.equals("")){
                   sendMessage(fUserString,tutoridString,msg);

               }else{
                   Toast.makeText(ChatActivity.this, "You Cannot Send Empty Message", Toast.LENGTH_SHORT).show();
               }
               text_send.setText("");
           }
       });


       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors").child(tutoridString);
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               RegistrationInformation user = dataSnapshot.getValue(RegistrationInformation.class);
               readMessage(fUserString,tutoridString);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }

    private void sendNotification() {

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        mNotificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());


    }


    public void  createNotificationChannel(){
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
//CREATE NOTIFICATION CHANNEL
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Test Notification Channel",NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from test notification channel");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }    }


    private NotificationCompat.Builder getNotificationBuilder(){

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent notificationPendingintent = PendingIntent.getActivity(
                this,NOTIFICATION_ID,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //instane of our notification builder
        NotificationCompat.Builder notifyBuillder = new NotificationCompat.Builder(
                this,
                PRIMARY_CHANNEL_ID)//channel id
                .setContentTitle("Map Location UPdated")
                .setContentText("")
//                .setSmallIcon(R.drawable.downloadimg)
                .setContentIntent(notificationPendingintent)
                .setAutoCancel(true);

        return notifyBuillder;

    }


    private void sendMessage (String sender , String receiver , String message){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        databaseReference.child("Chats").push().setValue(hashMap);

    }

    private void readMessage (final String myId , final String userId){
        mChats = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChats.clear();

                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {

                    MessageInfo chat = chatSnapshot.getValue(MessageInfo.class);

                    Log.e("Id Testuseid:::", String.valueOf(chat.message));


                    if (chat.getReceiver().equals(tutoridString) && chat.getSender().equals(fUserString) ||chat.getReceiver().equals(fUserString) && chat.getSender().equals(tutoridString) ) {
                        mChats.add(chat);
                    }

                    messageAdapter = new MessageAdapter(ChatActivity.this, mChats);
                    recycler_View.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
