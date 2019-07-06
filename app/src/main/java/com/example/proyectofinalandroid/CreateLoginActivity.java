package com.example.proyectofinalandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_login);
        getSupportActionBar().setTitle("Registro de usuarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
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
                    .setMessage("Introduce tu email y contraseña en la ventana principal y vuelve a pulsar este botón")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            dialog.show();
        } else {
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(user, pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        }
                    });
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Registro fallido", Toast.LENGTH_LONG);
            toast.show();
        }
    }


}
