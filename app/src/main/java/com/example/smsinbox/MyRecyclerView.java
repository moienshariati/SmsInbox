package com.example.smsinbox;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.MyViewHolder> {
    private ArrayList<SMSData> smsList;


    public MyRecyclerView(ArrayList<SMSData> smsList) {
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //test

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_custom_simple_list,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SMSData currentSMS = smsList.get(position);
//
//        Uri uri = Uri.parse("content://sms/");
//        ContentResolver contentResolver = getContentResolver();
//        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
//        String id= cursor.getString(cursor.getColumnIndexOrThrow("_id"));

        holder.tv_sms_from.setText(currentSMS.getNumber());
        holder.tv_sms_body.setText(currentSMS.getBody());
//        holder.itemView.getTag(Integer.parseInt(id));
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }
    public SMSData getSMSAt(int position) {
        return smsList.get(position);
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView tv_sms_from,tv_sms_body;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_sms_from=itemView.findViewById(R.id.tv_sms_from);
            tv_sms_body=itemView.findViewById(R.id.tv_sms_body);
        }

    }


    }
