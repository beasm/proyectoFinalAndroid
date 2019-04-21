package com.example.mistareas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.mistareas.dbuser.ControladorDB_User;

public class LoginActivity extends AppCompatActivity {

    private ControladorDB_User db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new ControladorDB_User(this);
        getSupportActionBar().hide();
    }

    public void crearCuenta(View view) {
        Intent intent = new Intent(this, CreateLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        TextInputEditText tiet_usuario = (TextInputEditText) findViewById(R.id.usuario);
        TextInputEditText tiet_password = (TextInputEditText) findViewById(R.id.password);

        if (db.validarLogin(tiet_usuario.getText().toString(), tiet_password.getText().toString())) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
