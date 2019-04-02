package com.yondev.yaumiyah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                YoYo.with(Techniques.FadeOut).duration(700).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator arg0) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationRepeat(Animator arg0) {
                        // TODO Auto-generated method stub
                    }
                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        // TODO Auto-generated method stub
                        findViewById(R.id.logo1).setVisibility(View.GONE);
                        YoYo.with(Techniques.FadeIn).duration(700).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator arg0) {
                                // TODO Auto-generated method stub
                                findViewById(R.id.logo2).setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onAnimationRepeat(Animator arg0) {
                                // TODO Auto-generated method stub
                            }
                            @Override
                            public void onAnimationEnd(Animator arg0) {
                                // TODO Auto-generated method stub
                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(SplashActivity.this, LandingPageActivity.class);
                                        startActivity(intent);
                                        finish();
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                }, 2000);
                            }

                            @Override
                            public void onAnimationCancel(Animator arg0) {
                                // TODO Auto-generated method stub


                            }
                        }).playOn(findViewById(R.id.logo2));
                    }

                    @Override
                    public void onAnimationCancel(Animator arg0) {
                        // TODO Auto-generated method stub


                    }
                }).playOn(findViewById(R.id.logo1));
            }
        }, 2000);
    }


}
