package com.example.proyectofinalandroid.contactar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.example.proyectofinalandroid.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {

    private FragmentContactar activity;

    public MessageAdapter(FragmentContactar activity) {
        super(new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("chats"), ChatMessage.class)
                .setLayout(R.layout.message)
                .build());
        this.activity = activity;
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        TextView messageText = (TextView) v.findViewById(R.id.message_text);
        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
    }

//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        ChatMessage chatMessage = getItem(position);
//        if (chatMessage.getMessageUserId().equals(activity.getLoggedInUserName()))
//            view = activity.getLayoutInflater().inflate(R.layout.item_out_message, viewGroup, false);
//        else
//            view = activity.getLayoutInflater().inflate(R.layout.item_in_message, viewGroup, false);
//
//        //generating view
//        populateView(view, chatMessage, position);
//
//        return view;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        // return the total number of view types. this value should never change
//        // at runtime
//        return 2;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        // return a value between 0 and (getViewTypeCount - 1)
//        return position % 2;
//    }
}
