package com.example.proyectofinalandroid.contactar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

public class FragmentContactar extends Fragment {

    private String chats = "foro";
    private FirebaseListAdapter<ChatMessage> adapter;
    private ListView listOfMessages;
    private FloatingActionButton fab;
    private EditText input;

    private OnFragmentInteractionListener mListener;

    public FragmentContactar() {
        // Required empty public constructor
    }

    public void setChats(String chats) {
        this.chats = chats;
    }

    private void displayChatMessages() {
        if (chats.equals("foro")) {
            adapter = new TodosForos(this, chats);
        } else {
            adapter = new MessageAdapterForo(this, chats);
        }
        listOfMessages.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        if (chats.equals("foro")) {
            view = inflater.inflate(R.layout.fragment_foro, container, false);
            listOfMessages = view.findViewById(R.id.list_of_messages);
            displayChatMessages();
        } else {
            view = inflater.inflate(R.layout.fragment_contactar, container, false);
            fab = view.findViewById(R.id.fab);
            input = view.findViewById(R.id.input);
            listOfMessages = view.findViewById(R.id.list_of_messages);
            displayChatMessages();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!input.getText().toString().trim().equals("")) {
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

        listOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference itemRef = adapter.getRef(position);
                itemRef.child("messageUser").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        FragmentContactar fragment = new FragmentContactar();
                        FragmentTransaction ft = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.content_frame, fragment);
                        fragment.setChats(value);
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
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
