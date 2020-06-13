package com.example.hhhelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Ticket> mTicketList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        View ticketView;
        ImageView ticketImage;
        TextView ticketName;
        TextView ticketBonus;
        TextView ticketDDL;
        public ViewHolder(View view){
            super(view);
            ticketView = view;
            ticketImage = (ImageView) view.findViewById(R.id.ticket_sender);
            ticketName = (TextView) view.findViewById(R.id.ticket_name);
            ticketBonus = (TextView) view.findViewById(R.id.ticket_bonus);
            ticketDDL = (TextView) view.findViewById(R.id.ticket_ddl);
        }
    }
    public RecyclerViewAdapter(ArrayList<Ticket> ticketList){
        mTicketList = ticketList;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ticketView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Ticket ticket = mTicketList.get(position);
                String uid = ticket.getUid();
                Intent intent = new Intent(v.getContext(),DetailsActivity.class);
                intent.putExtra("ticketUID",uid);
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Ticket ticket = mTicketList.get(position);
        //holder.ticketImage.setImageResource(ticket.getImageId());
        holder.ticketName.setText(ticket.getName());
        holder.ticketBonus.setText(ticket.getBonus());
        holder.ticketDDL.setText(ticket.getDeadline().toString());
        Bitmap bitmap = null;
        try{
            byte[] bitmapByte  = Base64.decode(ticket.getSenderImageBase64(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapByte,0,bitmapByte.length);
            holder.ticketImage.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount(){
        return mTicketList.size();
    }
}
