package com.example.admin.christembassyvallage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.admin.christembassyvallage.model.Testimony;

public class ReadMoreActivity extends AppCompatActivity {

    private TextView tvAnnViewTitle,tvAnnViewDesc;
    private ImageView circleImageView,ivLike;
    private Testimony test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);

        tvAnnViewTitle= (TextView) findViewById(R.id.tvTestmonyNameMore);
        tvAnnViewDesc =(TextView)findViewById(R.id.tvDescrptionMore);
        circleImageView =(ImageView)findViewById(R.id.circleImageViewMore);
        ivLike= (ImageView)findViewById(R.id.ivLikeMore);
        Intent intent = getIntent();
        test = (Testimony) intent.getSerializableExtra("select");


        tvAnnViewTitle.setText(test.getName());
        tvAnnViewDesc.setText(test.getDescripion());
        Glide.with(this).load(test.getUrlImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(circleImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
              circleImageView.setImageDrawable(circularBitmapDrawable);
            }
        });


    }
}
