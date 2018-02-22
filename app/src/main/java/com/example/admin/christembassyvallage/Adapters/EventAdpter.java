package com.example.admin.christembassyvallage.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.christembassyvallage.R;
import com.example.admin.christembassyvallage.model.EventPojo;

import java.util.List;

/**
 * Created by Admin on 01-Feb-18.
 */

public class EventAdpter extends RecyclerView.Adapter<EventAdpter.MyViewHolder> {
        private Activity context;
        private List<EventPojo> events;
        private Activity applicationContext;
        private boolean check =false;

    public EventAdpter(Activity context, List< EventPojo > events) {
            this.context = context;
            this.events = events;
        }


        @Override
        public EventAdpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_model,null);

            EventAdpter.MyViewHolder myViewHolder = new EventAdpter.MyViewHolder(view);
            return myViewHolder;
        }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EventPojo ann = events.get(position);

        holder.tvTitle.setText(ann.getTitle());
        holder.tvDescription.setText(ann.getDescription());
        holder.tvTime.setText(ann.getTime());
        String date =ann.getEnddate();
        String[] splited = date.split("\\s+");
        holder.tvEventDates.setText(splited[0]+" "+splited[1]);
//
    }



        @Override
        public int getItemCount() {
            return (null != events ? events.size() : 0);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle,tvTime,tvDescription,tvEventDates;
            LinearLayout llUserItem;
            ImageView ivStatusColor;
            View backgroungView;



            public MyViewHolder(View itemView) {
                super(itemView);
                tvTitle= itemView.findViewById(R.id.tvEventsTitle);
                tvTime = itemView.findViewById(R.id.tvEventsTime);
                tvEventDates =itemView.findViewById(R.id.tvEventDates);
//

                tvDescription = itemView.findViewById(R.id.tvEventsDescription);




            }
        }
}
