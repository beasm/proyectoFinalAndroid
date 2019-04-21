package com.example.mistareas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistareas.db.ControladorDB;

public class MainActivity extends AppCompatActivity {

    private ControladorDB db;
    private ArrayAdapter<String> adapter;
    private ListView listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ControladorDB(this);
        listaTareas = findViewById(R.id.listaTareas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actualizarUI();
    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nuevaTarea:
                final EditText cajaTexto = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Nueva Tarea")
                        .setMessage("Qué quieres hacer a continuación?")
                        .setView(cajaTexto)
                        .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tarea = cajaTexto.getText().toString();
                                db.addTarea(tarea);
                                actualizarUI();
                                callToast("Tarea añadida");
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actualizarUI() {
        if (db.numeroRegistro() == 0) {
            listaTareas.setAdapter(null);
        } else {
            adapter = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.tarea_titulo, db.obtenerTareas());
            listaTareas.setAdapter(adapter);
        }
    }

    public void borrarTarea(View view) {
        View parent = (View) view.getParent();
        TextView textView = (TextView) parent.findViewById(R.id.tarea_titulo);
        String tarea = textView.getText().toString();
        db.borrarTarea(tarea);
        actualizarUI();
        callToast("Tarea realizada");

    }

    public void editarTarea(View view) {
        View parent = (View) view.getParent();
        TextView textView = (TextView) parent.findViewById(R.id.tarea_titulo);
        final String oldTarea = textView.getText().toString();

        final EditText cajaTexto = new EditText(this);
        cajaTexto.setText(oldTarea);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Modificar Tarea")
                .setMessage("Modifica tu tarea")
                .setView(cajaTexto)
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTarea = cajaTexto.getText().toString();
                        db.editarTarea(newTarea, oldTarea);
                        actualizarUI();
                        callToast("Tarea modificada");
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();


    }

    private void callToast(String mensaje) {

        Toast toast2 = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView) layout.findViewById(R.id.txtMensaje);
        txtMsg.setText(mensaje);
        toast2.setDuration(Toast.LENGTH_SHORT);
        toast2.setView(layout);
        toast2.show();
    }
}
