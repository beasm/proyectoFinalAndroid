package com.example.mistareas;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

//        Typeface tf_miFuente = Typeface.createFromAsset(getAssets(),"fuente.ttf");
        TextView tv_titulo = (TextView) findViewById(R.id.titulo);
//        tv_titulo.setTypeface(tf_miFuente);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.animacion);
        tv_titulo.startAnimation(anim);

        anim.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
