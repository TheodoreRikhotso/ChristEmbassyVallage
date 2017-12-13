package com.example.admin.christembassyvallage.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.christembassyvallage.R;
import com.example.admin.christembassyvallage.model.Announcement;

import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 11-Dec-17.
 */

public class AnnouncementAdapter  extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {
    private Activity context;
    private List<Announcement> userItemPojos;
    private Activity applicationContext;
    private boolean check =false;

    public AnnouncementAdapter(Activity context, List<Announcement> userItemPojos) {
        this.context = context;
        this.userItemPojos = userItemPojos;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.announment_model,null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Announcement ann = userItemPojos.get(position);

        holder.tvAnnViewTitle.setText(ann.getTitle());
        holder.tvAnnViewDesc.setText(ann.getDesrciption());
//        holder.tvAnnViewCurrentDay.setText(ann.getPostedTime()+"\n"+ann.getPostedDate());

//
//        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Sansation-Bold.ttf");
//        holder.tvAnnViewTitle.setTypeface(custom_font);
        holder.tvAnnViewTitle.setTextColor(Color.BLACK);

        Random rnd = new Random();
        String date =ann.getStartDate();


        String[] animals = date.split(" ");
        int animalIndex = 1;

            holder.tvAnnViewDate.setText(animals[0]+"\n"+animals[1]+"\n"+animals[2]);

    }

    @Override
    public int getItemCount() {
        return (null != userItemPojos ? userItemPojos.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAnnViewTitle,tvAnnViewDesc,tvAnnViewCurrentDay,tvAnnViewDate;
        LinearLayout llUserItem;
        ImageView ivStatusColor;
        View backgroungView;



        public MyViewHolder(View itemView) {
            super(itemView);
            tvAnnViewTitle= itemView.findViewById(R.id.tvAnnViewTitle);
            tvAnnViewDesc = itemView.findViewById(R.id.tvAnnViewDesc);
//            tvAnnViewCurrentDay = itemView.findViewById(R.id.tvAnnViewCurrentDay);
           // backgroungView = itemView.findViewById(R.id.backgroungView);

               tvAnnViewDate = itemView.findViewById(R.id.tvAnnViewDate);




        }
    }
}
