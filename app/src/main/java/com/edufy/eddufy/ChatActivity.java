package com.edufy.eddufy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
       // final String userId = intent.getStringExtra("name");
//       fUser = FirebaseAuth.getInstance().getCurrentUser();
//         fUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle extras = getIntent().getExtras();
        fUserString = extras.getString("uid");

       btn_Send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String msg = text_send.getText().toString();
               if(!msg.equals("")){
                   sendMessage("bfADRrp881OpPCr57I5YnvNOHpu2","bpgvI1n4tkOavEb6edUgEibWQQn1",msg);

               }else{
                   Toast.makeText(ChatActivity.this, "You Cannot Send Empty Message", Toast.LENGTH_SHORT).show();
               }
               text_send.setText("");
           }
       });
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors").child("bpgvI1n4tkOavEb6edUgEibWQQn1");
       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               RegistrationInformation user = dataSnapshot.getValue(RegistrationInformation.class);
               readMessage("bfADRrp881OpPCr57I5YnvNOHpu2","bpgvI1n4tkOavEb6edUgEibWQQn1");

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


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



//                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId))
                   if(chat.getReceiver()=="bpgvI1n4tkOavEb6edUgEibWQQn1" && chat.getSender() == "bfADRrp881OpPCr57I5YnvNOHpu2"){
                        mChats.add(chat);
                        System.out.println(mChats);

                    }
                    messageAdapter = new MessageAdapter(ChatActivity.this,mChats);
                   recycler_View.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}