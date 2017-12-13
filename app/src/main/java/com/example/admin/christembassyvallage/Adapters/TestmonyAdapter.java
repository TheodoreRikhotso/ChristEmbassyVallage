package com.example.admin.christembassyvallage.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.admin.christembassyvallage.R;
import com.example.admin.christembassyvallage.model.Testimony;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 13-Dec-17.
 */

public class TestmonyAdapter extends RecyclerView.Adapter<TestmonyAdapter.MyViewHolder> {


        private Activity context;
        private List<Testimony> userItemPojos;
        private Activity applicationContext;
        private boolean check =false;

        public TestmonyAdapter(Activity context, List<Testimony> userItemPojos) {
            this.context = context;
            this.userItemPojos = userItemPojos;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.testimony_model,null);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Testimony ann = userItemPojos.get(position);

            holder.tvAnnViewTitle.setText(ann.getName());
            holder.tvAnnViewDesc.setText(ann.getDescripion());

            Glide.with(context).load(ann.getUrlImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.circleImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.circleImageView.setImageDrawable(circularBitmapDrawable);
                }
            });

//            Glide.with(ProfileActivity.this).load(imageUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(ivEditProfile) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(ProfileActivity.this.getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    ivEditProfile.setImageDrawable(circularBitmapDrawable);
//                }
//            });
//        holder.tvAnnViewCurrentDay.setText(ann.getPostedTime()+"\n"+ann.getPostedDate());
//        holder.tvAnnViewDate.setText(ann.getTime()+"  "+ann.getEndDate());
//
//        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Sansation-Bold.ttf");
//        holder.tvAnnViewTitle.setTypeface(custom_font);

        }

        @Override
        public int getItemCount() {
            return (null != userItemPojos ? userItemPojos.size() : 0);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvAnnViewTitle,tvAnnViewDesc,tvAnnViewCurrentDay,tvAnnViewDate;
            LinearLayout llUserItem;
            ImageView ivStatusColor;
            CircleImageView circleImageView;



            public MyViewHolder(View itemView) {
                super(itemView);
                tvAnnViewTitle= itemView.findViewById(R.id.tvTestmonyName);
                tvAnnViewDesc = itemView.findViewById(R.id.tvDescrption);
                circleImageView = itemView.findViewById(R.id.circleImageView);

//            tvAnnViewCurrentDay = itemView.findViewById(R.id.tvAnnViewCurrentDay);
//                backgroungView = itemView.findViewById(R.id.backgroungView);

//            tvAnnViewDate = itemView.findViewById(R.id.tvAnnViewDate);




            }
        }


}
