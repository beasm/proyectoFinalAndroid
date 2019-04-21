package com.example.mistareas.Eventos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mistareas.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentEventos extends Fragment {
    private FirebaseListAdapter<Eventos> adapter;
    private ListView listOfEventos;
//    private FloatingActionButton fab_eventos;
//    private EditText input_eventos;
    private Context mContext;

    private FragmentEventos.OnFragmentInteractionListener mListener;

    public FragmentEventos() {
        // Required empty public constructor
    }

    private void displayEventos() {

        FirebaseListOptions<Eventos> options = new FirebaseListOptions.Builder<Eventos>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("eventos"), Eventos.class)
                .setLayout(R.layout.eventos)
                .build();
        adapter = new FirebaseListAdapter<Eventos>(options) {
            @Override
            protected void populateView(View v, Eventos model, int position) {
                // Get references to the views of message.xml
                TextView eventosContenido = (TextView)v.findViewById(R.id.eventos_contenido);
                TextView eventosTitulo = (TextView)v.findViewById(R.id.eventos_titulo);
                TextView eventosTime = (TextView)v.findViewById(R.id.eventos_time);
//                TextView eventosUrl = (TextView)v.findViewById(R.id.eventos_url);

                // Set their text
                eventosContenido.setText(model.getEventosContenido());
                eventosTitulo.setText(model.getEventosTitulo());
//                eventosUrl.setText(model.getEventosUrl());

                // Format the date before showing it
                eventosTime.setText(DateFormat.format("EEE, dd MMM HH:mm",//"dd-MM-yyyy (HH:mm:ss)",
                        model.getEventosTime()));
            }

        };
        listOfEventos.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
//        fab_eventos = view.findViewById(R.id.fab_eventos);
//        input_eventos = view.findViewById(R.id.input_eventos);
        listOfEventos = view.findViewById(R.id.list_of_eventos);
        displayEventos();

//        fab_eventos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!input_eventos.getText().toString().trim().equals("")) {
//                    FirebaseDatabase.getInstance()
//                            .getReference("eventos")
//                            .push()
//                            .setValue(new Eventos(input_eventos.getText().toString(),
//                                    FirebaseAuth.getInstance()
//                                            .getCurrentUser()
//                                            .getDisplayName())
//                            );
//                    input_eventos.setText("");
//                }
//            }
//        });

        listOfEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference itemRef = adapter.getRef(position);
                itemRef.child("eventosGeo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // TODO: google maps
                        String value = dataSnapshot.getValue(String.class);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        Intent intent = new Intent( this, GeofenceTrasitionService.class);
                        intent.setData(Uri.parse(value));
                        startActivity(intent);
//                        String value = dataSnapshot.getValue(String.class);
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
//                        mContext.startActivity(browserIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentEventos.OnFragmentInteractionListener) {
            mListener = (FragmentEventos.OnFragmentInteractionListener) context;
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
