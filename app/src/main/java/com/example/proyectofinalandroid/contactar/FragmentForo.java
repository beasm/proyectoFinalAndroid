package com.example.proyectofinalandroid.contactar;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentForo extends Fragment {

    private String chats = "foro";
    private FirebaseListAdapter<ChatMessage> adapter;
    private ListView listOfMessages;
    private FloatingActionButton boton_enviar_mensaje;
    private EditText input;

    /**
     *  Constructor vacío es necesitado
     */
    public FragmentForo() {
    }

    /**
     * Setter para poder cambiar el chat
     *
     * @param chats
     */
    public void setChats(String chats) {
        this.chats = chats;
    }

    /**
     * Mostramos los foros guardados en firebase
     */
    private void displayChatMessages() {
        if (chats.equals("foro")) { // si chats es foro, al cargar la página es el valor por defecto
            adapter = new TodosForos(this, chats); // cargamos la lista de todos los foros
        } else { // si no cargamos el foro seleccionado
            adapter = new MessageAdapterForo(this, chats);
        }
        // actualizamos la lista con los resultados
        listOfMessages.setAdapter(adapter);
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
        // Declaramos la vista
        View view;
        if (chats.equals("foro")) { // si chats es foro

            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Obtenemos la referencia de la vista fragment_foro
            view = inflater.inflate(R.layout.fragment_foro, container, false);
            // Obtenemos la referencia de la lista de los eventos
            listOfMessages = view.findViewById(R.id.list_of_messages);
            // llamamos al método para mostrar la info de firebase
            displayChatMessages();

            // activamos un escuchador los eventos de on click de los componentes de la listas
            listOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // referencia de la posición del elemento de la lista
                    DatabaseReference itemRef = adapter.getRef(position);

                    // obtenemos el nombre del foto al que vamos acceder y se lanza Fragment
                    itemRef.child("messageUser").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // obtenemos el valor seleccionado y modificamos el valor de la variable chats
                            String value = dataSnapshot.getValue(String.class);

                            // Se lanza el fragment FragmentForo con el nuevo valor de chats
                            FragmentForo fragment = new FragmentForo();
                            fragment.setChats(value);
                            FragmentTransaction ft = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.commit();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                }
            });

        } else { // si no es foro el valor
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Obtenemos la referencia de la vista fragment_contactar
            view = inflater.inflate(R.layout.fragment_contactar, container, false);

            // Obtenemos la referencias del botón, de la entrada de texto y la lista
            boton_enviar_mensaje = view.findViewById(R.id.boton_enviar_mensaje);
            input = view.findViewById(R.id.input);
            listOfMessages = view.findViewById(R.id.list_of_messages);
            listOfMessages.setOnItemClickListener(null);

            // llamamos al método para mostrar la info de firebase
            displayChatMessages();

            // activamos un escuchador los eventos de on click del botón
            boton_enviar_mensaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!input.getText().toString().trim().equals("")) { // si no esta vacío

                        // guardamos el mensaje en firebase
                        FirebaseDatabase.getInstance()
                                .getReference(chats)
                                .push()
                                .setValue(new ChatMessage(input.getText().toString(),
                                        FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getDisplayName())
                                );
                        input.setText("");
                    }
                }
            });
        }
        return view;
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
     * para permitir una interacción entre ellos. Ejemplo implements FragmentEventos.OnFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
