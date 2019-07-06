package com.example.proyectofinalandroid.contactar;
import android.view.View;
import android.widget.TextView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TodosForos extends FirebaseListAdapter<ChatMessage> {

    private FragmentForo activity;

    /**
     * Constructor inicializando el Constructor FirebaseListAdapter
     *
     * @param activity
     * @param chats
     */
    public TodosForos(FragmentForo activity, String chats) {
        super(new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(chats), ChatMessage.class)
                .setLayout(R.layout.foros)
                .build());
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        // obtenemos la referencia del texto de foros.xml
        TextView nombreForo = (TextView) v.findViewById(R.id.nombre_foro);
        nombreForo.setText(model.getMessageUser()); // asignamos valor

    }


}
