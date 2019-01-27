package com.example.mistareas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }

    public void crearCuenta(View view) {
        Toast toast = Toast.makeText(this, "Funcionalidad no implementada", Toast.LENGTH_LONG);
        toast.show();
    }

    public void login(View view) {
        TextInputEditText tiet_usuario = (TextInputEditText) findViewById(R.id.usuario);
        TextInputEditText tiet_password = (TextInputEditText) findViewById(R.id.password);

        if(tiet_usuario.getText().toString().equalsIgnoreCase("IT")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast toast = Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
