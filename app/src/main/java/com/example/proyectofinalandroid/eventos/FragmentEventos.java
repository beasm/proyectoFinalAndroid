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
     *  Constructor vacío
     */
    public FragmentEventos() {
    }

    /**
     * Mostramos las noticias guardados en firebase
     */
    private void displayEventos() {
        // Opciones que se usan para llamar a firebase
        FirebaseListOptions<Eventos> options = new FirebaseListOptions.Builder<Eventos>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("eventos"), Eventos.class)
                .setLayout(R.layout.eventos)
                .build();
        // Se llama a firebase
        adapter = new FirebaseListAdapter<Eventos>(options) {
            @Override
            protected void populateView(View v, Eventos model, int position) {
                // Se obtienen las referencias de la vista eventos.xml
                TextView eventosContenido = (TextView)v.findViewById(R.id.eventos_contenido);
                TextView eventosTitulo = (TextView)v.findViewById(R.id.eventos_titulo);
                TextView eventosTime = (TextView)v.findViewById(R.id.eventos_time);

                // Se asignan los valores obtenidos de firebase
                eventosContenido.setText(model.getEventosContenido());
                eventosTitulo.setText(model.getEventosTitulo());
                eventosTime.setText(DateFormat.format("EEE, dd MMM HH:mm",
                        model.getEventosTime()));
            }

        };
        // Se actualiza la lista con los resultados
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

        // Se activa un escuchador los eventos de on click de los componentes de la listas
        listOfEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Referencia de la posición del elemento de la lista
                DatabaseReference itemRef = adapter.getRef(position);

                // Obtenemos la URL del evento en google maps y se lanza en el navegador
                itemRef.child("eventosGeo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class); // Obtenemos el valor
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(value));  // Preparamos para lazar la actividad de la URL
                        startActivity(intent); // Lanzamos la URL
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
     * Método del context el Fragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Método empezar el Fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /**
     * Método parar el Fragment
     */
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * Esta interfaz debe ser implementada por actividades que contengan Fragment
     * para permitir una interacción entre ellos. Ejemplo implements FragmentEventos.OnFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
