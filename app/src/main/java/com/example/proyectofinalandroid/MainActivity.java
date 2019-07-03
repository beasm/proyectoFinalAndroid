package com.example.proyectofinalandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyectofinalandroid.descarga.FragmentDescargas;
import com.example.proyectofinalandroid.eventos.FragmentEventos;
import com.example.proyectofinalandroid.contactar.FragmentForo;
import com.example.proyectofinalandroid.noticias.FragmentNoticias;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentEventos.OnFragmentInteractionListener,
        FragmentDescargas.OnFragmentInteractionListener,
        FragmentForo.OnFragmentInteractionListener,
        FragmentNoticias.OnFragmentInteractionListener {

    private static final int SIGN_IN_REQUEST_CODE = 200;
    private static final short REQUEST_CODE = 6545;
    private Fragment lastFragment;
    private String lastTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // se define el xml de la actividad
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // se busca el toolbar en el xml

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // se busca drawer_layout en el xml
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // se define la barra de tareas de la navegación
        drawer.addDrawerListener(toggle); // se añade el escuchador de eventos
        toggle.syncState(); // se sincroniza

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //se busca la navegación en el xml
        navigationView.setNavigationItemSelectedListener(this); // se asigna el escuchador

        if (FirebaseAuth.getInstance().getCurrentUser() == null) { // si el usuario no esta logeado
            // Se inicia la activida de iniciar sesión o el registro
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else { // si el usuario esta logeado

            // Muestra el mensaje de Bienvenido
            Toast.makeText(this,
                    "Bienvenido " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // se carga la pagina de Eventos
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new FragmentEventos());
            ft.commit();
            getSupportActionBar().setTitle("Eventos");
        }
    }

    /**
     * metodo que comprueba si los permisos para escribir en la memoria
     */
    private void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) { // si no tiene los permisos

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE); // pide los permisos de escritura
        }
    }

    /**
     * metodo que comprueba si los permisos se han concedido
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            // Si se cancela la solicitud, el array estara vacio.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // si se concede los permisos
                Toast.makeText(this, "Gracias, permisos concedidos", Toast.LENGTH_LONG).show();
            } else { // si se deniega el permiso
                Toast.makeText(this, "Por favor, proporciona permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * metodo que controla el boton de la navegación
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * metodo agrega elementos a la barra de opciones
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout, menu);
        return true;
    }

    /**
     * metodo gestiona los evento de seleccion de las opciones
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) { // si el elemento seleccionado es sign out
            AuthUI.getInstance().signOut(this) // firebase deslogea al usuario
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this,
                                    "Desconectado.",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // cierra la actividad
                            finish();
                        }
                    });
        }
        if (item.getItemId() == android.R.id.home) {
                if (lastFragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, lastFragment);
                    ft.commit();

                    if (getSupportActionBar() != null) { // si no es null establecer el título de la barra de herramientas
                        getSupportActionBar().setTitle(lastTitle);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Maneja la navegacion del menu
     *
     * @param item MenuItem
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        String title = "";

        if (id == R.id.nav_eventos) { // se selecciona eventos
            fragment = new FragmentEventos();
            title = "Eventos";
        } else if (id == R.id.nav_noticias) { // se selecciona noticias
            fragment = new FragmentNoticias();
            title = "Noticias";
        } else if (id == R.id.nav_descargas) { // se selecciona descargas
            fragment = new FragmentDescargas();
            title = "Descargas";
        } else if (id == R.id.nav_foro) { // se selecciona foro
            fragment = new FragmentForo();
            title = "Foro";
        }

        if (fragment != null) { // si no es null
            lastFragment = fragment;
            lastTitle = title;
            // Se cambia el contenido del fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if (getSupportActionBar() != null) { // si no es null establecer el título de la barra de herramientas
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout); // se busca drawer_layout en el xml
        drawer.closeDrawer(GravityCompat.START); // se cierra la navegacion
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // si se logeo el usuario
                // se muestra el mensaje
                Toast.makeText(this,
                        "Ha iniciado sesión correctamente. Bienvenido!",
                        Toast.LENGTH_LONG)
                        .show();
                // Se lanza el fragment FragmentEventos
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new FragmentEventos());
                ft.commit();
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Eventos");
                }
                checkSelfPermission();
            } else { // si hubo un problema
                Toast.makeText(this,
                        "No pudimos iniciar sesión. Inténtalo de nuevo más tarde.",
                        Toast.LENGTH_LONG)
                        .show();

                // Cierra la app
                finish();
            }
        }

    }
}
