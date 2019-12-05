package com.edufy.eddufy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static  final int MSG_TYPE_RIGHT = 1;
    public static  final int MSG_TYPE_LEFT = 0;

    FirebaseUser fUser, fUser2;
    private List<MessageInfo> mChat;
    private Context mContext;

    public MessageAdapter(Context mContext,List<MessageInfo> mChat){
        this.mContext = mContext;
        this.mChat = mChat;
    }
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_layout_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.message_layout, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        MessageInfo chat = mChat.get(position);
        holder.showMessage.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showMessage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessage);



        }
    }

    @Override
    public int getItemViewType(int position) {

        fUser = FirebaseAuth.getInstance().getCurrentUser();


        if(mChat.get(position).getSender().equals(fUser.getUid()))
//        if(mChat.get(position).getSender().equals("bfADRrp881OpPCr57I5YnvNOHpu2")){
        {


            return MSG_TYPE_RIGHT;

        }

        else
        return MSG_TYPE_LEFT;
    }
}
