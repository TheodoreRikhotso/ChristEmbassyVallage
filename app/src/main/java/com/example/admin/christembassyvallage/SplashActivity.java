package com.example.admin.christembassyvallage;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ImageView ivLogo;
    private Animation animation;
    private LinearLayout allView;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 90000;
    long animationDuration =1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        ivLogo =(ImageView)findViewById(R.id.ivLogo);
        allView =(LinearLayout)findViewById(R.id.allView);


         //animation = AnimationUtils.loadAnimation(this,R.anim.fac);

       // ivLogo.setAnimation(animation);

        ObjectAnimator animator = ObjectAnimator.ofFloat(ivLogo, "translationX", 0, 75, 0);
        animator.setInterpolator(new EasingInterpolator(Ease.ELASTIC_IN_OUT));
        animator.setStartDelay(500);
        animator.setDuration(1500);
        animator.start();
//


    }

    public void handleA(View view)
    {
//        ObjectAnimator objectAnimatorx = ObjectAnimator.ofFloat(ivLogo,"x",420f);
//        ObjectAnimator objectAnimatory = ObjectAnimator.ofFloat(ivLogo,"y",200f);

//        objectAnimatorx.setDuration(animationDuration);
//
//        AnimatorSet animationSet = new AnimatorSet();
//        animationSet.playTogether(objectAnimatory,objectAnimatorx);
//
//
//        animationSet.start();

    }
    @Override
    public void onAnimationStart(Animation animation) {
        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(this, "END", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Toast.makeText(this, "RE", Toast.LENGTH_SHORT).show();
    }
}
