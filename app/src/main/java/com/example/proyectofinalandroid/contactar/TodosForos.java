package com.example.proyectofinalandroid.contactar;
import android.view.View;
import android.widget.TextView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TodosForos extends FirebaseListAdapter<ChatMessage> {

    private FragmentForo activity;

    public TodosForos(FragmentForo activity, String chats) {
        super(new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(chats), ChatMessage.class)
                .setLayout(R.layout.foros)
                .build());
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
//        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView nombreForo = (TextView) v.findViewById(R.id.nombre_foro);
//        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

//        messageText.setText(model.getMessageText());
        nombreForo.setText(model.getMessageUser());

        // Format the date before showing it
//        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }


}
