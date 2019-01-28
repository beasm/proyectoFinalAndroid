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
//        Toast toast = Toast.makeText(this, "Funcionalidad no implementada", Toast.LENGTH_LONG);
//        toast.show();
        TextInputEditText tiet_usuario = (TextInputEditText) findViewById(R.id.usuario);
        TextInputEditText tiet_password = (TextInputEditText) findViewById(R.id.password);
        String user = tiet_usuario.getText().toString();
        String pwd = tiet_password.getText().toString();
        if (user.isEmpty() && pwd.isEmpty()) {
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
        } else {
            db.addTarea(user,pwd);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void login(View view) {
        TextInputEditText tiet_usuario = (TextInputEditText) findViewById(R.id.usuario);
        TextInputEditText tiet_password = (TextInputEditText) findViewById(R.id.password);

        if (db.validarLogin(tiet_usuario.getText().toString(), tiet_password.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
