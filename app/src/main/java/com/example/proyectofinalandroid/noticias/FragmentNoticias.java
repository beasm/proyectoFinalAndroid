package com.example.proyectofinalandroid.noticias;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentNoticias extends Fragment {
    private FirebaseListAdapter<Noticia> adapter;
    private ListView listOfNoticias;
    private Context mContext;

    /**
     *  Contructor vacio es necesitado
     */
    public FragmentNoticias() {
    }

    /**
     * Mostramos las noticias guardados en firebase
     */
    private void displayNoticias() {
        // opciones que usaremos para llamar a firebase
        FirebaseListOptions<Noticia> options = new FirebaseListOptions.Builder<Noticia>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("news"), Noticia.class)
                .setLayout(R.layout.noticias)
                .build();
        // llamamos a firebase
        adapter = new FirebaseListAdapter<Noticia>(options) {
            @Override
            protected void populateView(View v, Noticia model, int position) {
                // obtenemos las referencias de la vista noticias.xml
                TextView noticiasContenido = (TextView)v.findViewById(R.id.noticias_contenido);
                TextView noticiasTitulo = (TextView)v.findViewById(R.id.noticias_titulo);
                ImageView imgAA  = v.findViewById(R.id.imagen_noticia);

                // Asignamos los valores obtenido de firebase
                noticiasContenido.setText(model.getNoticiasContenido());
                noticiasTitulo.setText(model.getNoticiasTitulo());

                // llamamos a la clase TareaAsyncTask para obtener la imagen en segundo plano
                TareaAsyncTask tareasynctask = new TareaAsyncTask(imgAA);
                tareasynctask.execute(model.getNoticiasImagen());
            }

        };
        // actualizamos la lista con los resultados
        listOfNoticias.setAdapter(adapter);
    }

    /**
     * Inicializamos y cargamos la pagina al crearse la vista
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Obtenemos la referencia de la vista fragment_noticias
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        // Obtenemos la referencia de la lista de las noticias
        listOfNoticias = view.findViewById(R.id.list_of_noticias);

        displayNoticias(); // llamamos al metodo para muestrar la info de firebase

        // activamos un escuchador los eventos de on click de los componentes de la listas
        listOfNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // referencia de la posicion del elemento de la lista
                DatabaseReference itemRef = adapter.getRef(position);

                // obtenemos la URL de la noticia y se lanza en el navegador
                itemRef.child("noticiasUrl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class); // obtenemos el valor
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value)); // preparamos para lazar la actividad de la URL
                        mContext.startActivity(browserIntent); // lazamos la URL
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        return view;
    }

    /**
     * Se inicializa el mContext con context
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Metodo empezar el Fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * Metodo parar el Fragment
     */
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * Esta interfaz debe ser implementada por actividades que contengan Fragment
     * para permitir que una interacción.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
