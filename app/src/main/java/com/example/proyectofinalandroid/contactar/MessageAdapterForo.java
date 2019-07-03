package com.example.proyectofinalandroid.contactar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MessageAdapterForo extends FirebaseListAdapter<ChatMessage> {

    private FragmentForo activity;

    public MessageAdapterForo(FragmentForo activity, String chats) {
        super(new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(chats), ChatMessage.class)
                .setLayout(R.layout.message)
                .build());
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        // obtenemos las referencias de la vista message.xml
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        // Asignamos los valores obtenido de firebase
        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }


}
