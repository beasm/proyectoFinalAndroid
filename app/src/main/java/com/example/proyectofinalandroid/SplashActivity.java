package com.example.proyectofinalandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        TextView tv_titulo = (TextView) findViewById(R.id.titulo_splash);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacion);
        tv_titulo.startAnimation(anim);

        ImageView tv_imagen = (ImageView) findViewById(R.id.imagen_splash);
        Animation anim_imagen = AnimationUtils.loadAnimation(this, R.anim.animacion_imagen);
        tv_imagen.startAnimation(anim_imagen);

        anim.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
