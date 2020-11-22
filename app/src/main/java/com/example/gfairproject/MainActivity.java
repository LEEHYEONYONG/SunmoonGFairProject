package com.example.gfairproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //Animation animation;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.activity_splash);
        /*
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(MainActivity.this,ImportantActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        linearLayout.startAnimation(animation);
        */

        /*
        try {
            Thread.sleep(3000); //대기 초 설정
            startActivity(new Intent(MainActivity.this, ImportantActivity.class));
            finish();
        } catch (Exception e) {
        }

         */
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(new Intent(MainActivity.this, ImportantActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}