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
        setContentView(R.layout.activity_splash); // se define el xml del splash

        getSupportActionBar().hide();  // se oculta la barra de tareas

        TextView tv_titulo = (TextView) findViewById(R.id.titulo_splash); // se busca el título en el xml

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacion_titulo); // se carga la animación del título
        tv_titulo.startAnimation(anim); // se lanza la animación del título

        ImageView tv_imagen = (ImageView) findViewById(R.id.imagen_splash); // se busca la imagen en el xml
        Animation anim_imagen = AnimationUtils.loadAnimation(this, R.anim.animacion_imagen); // se carga la animación de la imagen
        tv_imagen.startAnimation(anim_imagen); // se lanza la animación del título

        anim.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Se pasa a la actividad principal
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
