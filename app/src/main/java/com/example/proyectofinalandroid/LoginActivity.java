package com.example.proyectofinalandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        String user = tiet_usuario.getText().toString();
        String pwd = tiet_password.getText().toString();
        if (user.isEmpty() || pwd.isEmpty()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Iniciar Sesión")
                    .setMessage("Introduce tu email y contraseña en la ventana principal y vuelve a pulsar este botón")
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            dialog.show();
        } else {
            AuthCredential credential = EmailAuthProvider.getCredential(user, pwd);
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
