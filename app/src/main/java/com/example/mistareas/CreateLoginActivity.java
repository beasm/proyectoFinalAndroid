package com.example.mistareas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mistareas.dbuser.ControladorDB_User;

public class CreateLoginActivity extends AppCompatActivity {

    private ControladorDB_User db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_login);
        db = new ControladorDB_User(this);
        getSupportActionBar().setTitle("Registro de usuarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void crearCuenta(View view) {
        TextInputEditText tiet_usuario = (TextInputEditText) findViewById(R.id.create_login_usuario);
        TextInputEditText tiet_password = (TextInputEditText) findViewById(R.id.create_login_password);
        String user = tiet_usuario.getText().toString();
        String pwd = tiet_password.getText().toString();
        if (user.isEmpty() || pwd.isEmpty()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Registro")
                    .setMessage("Introduce tu usuario y contraseña en la ventana principal y vuelve a pulsar este botón")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();
            dialog.show();
        } else if (db.existeLogin(user)) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Registro")
                    .setMessage("El usuario introducido ya existe prueba otro usuario")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();
            dialog.show();
        } else {
            db.addTarea(user,pwd);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }


}
