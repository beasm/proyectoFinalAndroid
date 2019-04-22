package com.example.proyectofinalandroid.noticias;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
    private FloatingActionButton fab_noticias;
    private EditText input_noticias;
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public FragmentNoticias() {
        // Required empty public constructor
    }

    private void displayNoticias() {

        FirebaseListOptions<Noticia> options = new FirebaseListOptions.Builder<Noticia>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("news"), Noticia.class)
                .setLayout(R.layout.noticias)
                .build();
        adapter = new FirebaseListAdapter<Noticia>(options) {
            @Override
            protected void populateView(View v, Noticia model, int position) {
                // Get references to the views of message.xml
                TextView noticiasContenido = (TextView)v.findViewById(R.id.noticias_contenido);
                TextView noticiasTitulo = (TextView)v.findViewById(R.id.noticias_titulo);
//                TextView noticiasTime = (TextView)v.findViewById(R.id.noticias_time);
//                TextView noticiasUrl = (TextView)v.findViewById(R.id.noticias_url);

                // Set their text
                noticiasContenido.setText(model.getNoticiasContenido());
                noticiasTitulo.setText(model.getNoticiasTitulo());
//                noticiasUrl.setText(model.getNoticiasUrl());

                // Format the date before showing it
//                noticiasTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getNoticiasTime()));
            }

        };
        listOfNoticias.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);
//        fab_noticias = view.findViewById(R.id.fab_noticias);
//        input_noticias = view.findViewById(R.id.input_noticias);
        listOfNoticias = view.findViewById(R.id.list_of_noticias);
        displayNoticias();

//        fab_noticias.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!input_noticias.getText().toString().trim().equals("")) {
//                    FirebaseDatabase.getInstance()
//                            .getReference("news")
//                            .push()
//                            .setValue(new Noticia(input_noticias.getText().toString(),
//                                    FirebaseAuth.getInstance()
//                                            .getCurrentUser()
//                                            .getDisplayName())
//                            );
//                    input_noticias.setText("");
//                }
//            }
//        });

        listOfNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference itemRef = adapter.getRef(position);
                itemRef.child("noticiasUrl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                        mContext.startActivity(browserIntent);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
