package com.example.proyectofinalandroid.eventos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class FragmentEventos extends Fragment {
    private FirebaseListAdapter<Eventos> adapter;
    private ListView listOfEventos;

    /**
     *  Constructor vacío es necesitado
     */
    public FragmentEventos() {
    }

    /**
     * Mostramos las noticias guardados en firebase
     */
    private void displayEventos() {
        // opciones que usaremos para llamar a firebase
        FirebaseListOptions<Eventos> options = new FirebaseListOptions.Builder<Eventos>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("eventos"), Eventos.class)
                .setLayout(R.layout.eventos)
                .build();
        // llamamos a firebase
        adapter = new FirebaseListAdapter<Eventos>(options) {
            @Override
            protected void populateView(View v, Eventos model, int position) {
                // obtenemos las referencias de la vista eventos.xml
                TextView eventosContenido = (TextView)v.findViewById(R.id.eventos_contenido);
                TextView eventosTitulo = (TextView)v.findViewById(R.id.eventos_titulo);
                TextView eventosTime = (TextView)v.findViewById(R.id.eventos_time);

                // Asignamos los valores obtenido de firebase
                eventosContenido.setText(model.getEventosContenido());
                eventosTitulo.setText(model.getEventosTitulo());
                eventosTime.setText(DateFormat.format("EEE, dd MMM HH:mm",
                        model.getEventosTime()));
            }

        };
        // actualizamos la lista con los resultados
        listOfEventos.setAdapter(adapter);
    }

    /**
     * Inicializamos y cargamos la página al crearse la vista
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
        // Obtenemos la referencia de la vista fragment_eventos
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        // Obtenemos la referencia de la lista de los eventos
        listOfEventos = view.findViewById(R.id.list_of_eventos);

        displayEventos(); // llamamos al método para mostrar la info de firebase

        // activamos un escuchador los eventos de on click de los componentes de la listas
        listOfEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // referencia de la posición del elemento de la lista
                DatabaseReference itemRef = adapter.getRef(position);

                // obtenemos la URL del evento en google maps y se lanza en el navegador
                itemRef.child("eventosGeo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class); // obtenemos el valor
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(value));  // preparamos para lazar la actividad de la URL
                        startActivity(intent); // lazamos la URL
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
     * método del context el Fragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * método empezar el Fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * método parar el Fragment
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
