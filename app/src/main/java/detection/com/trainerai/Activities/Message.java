package detection.com.trainerai.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import detection.com.trainerai.Models.ChatFunctions;
import detection.com.trainerai.R;
import detection.com.trainerai.utilities.DataProcessor;

public class Message extends AppCompatActivity {

    ImageView send;
    EditText writemsg;
    private FirebaseAuth firebaseAuth;
    private TextView user, message, time, users, messages, times;
    RelativeLayout rls, rlr;
    private FirebaseRecyclerAdapter<ChatFunctions, MessageHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        send = findViewById(R.id.but_send_msg);
        writemsg = findViewById(R.id.et_writemsg);
        firebaseAuth = FirebaseAuth.getInstance();
        final RecyclerView recyclerView = findViewById(R.id.lv_msg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        ListView readMessageList = findViewById(R.id.lv_msg);
        final String name = DataProcessor.getStr("name");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Chats")
                        .push()
                        .setValue
                                (new ChatFunctions(writemsg.getText().toString().trim()
                                        , name));
                writemsg.setText("");
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Chats").limitToLast(7);
        FirebaseRecyclerOptions<ChatFunctions> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<ChatFunctions>()
                .setQuery(query, ChatFunctions.class)
                .build();


        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatFunctions, MessageHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MessageHolder blogPostHolder, int position, @NonNull ChatFunctions blogPost) {
                blogPostHolder.setBlogPost(blogPost);
            }

            @Override
            public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_recieved, parent, false);

                return new MessageHolder(view);
            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

//     if (model.getMessageUser().equals(name)) {
//        rlr.setVisibility(View.GONE);
//        users.setText(model.getMessageUser());
//        messages.setText(model.getMessageText());
//        times.setText(DateFormat.format("dd-MMM-yyyy --  h:mm a", model.getMessageTime()));
//    } else {
//        rls.setVisibility(View.GONE);
//        user.setText(model.getMessageUser());
//        message.setText(model.getMessageText());
//        time.setText(DateFormat.format("dd-MMM-yyyy --  h:mm a", model.getMessageTime()));
//    }

    private class MessageHolder extends RecyclerView.ViewHolder {

        MessageHolder(View view) {
            super(view);
            user = view.findViewById(R.id.text_message_name);
            message = view.findViewById(R.id.text_message_body);
            time = view.findViewById(R.id.text_message_time);
            users = view.findViewById(R.id.text_message_name_S);
            messages = view.findViewById(R.id.text_message_body_S);
            times = view.findViewById(R.id.text_message_time_S);
            rlr = view.findViewById(R.id.rl_receiver);
            rls = view.findViewById(R.id.rl_sender);
        }

        void setBlogPost(ChatFunctions model) {

            if (model.getMessageUser().equals(DataProcessor.getStr("name"))) {
                rlr.setVisibility(View.GONE);
                users.setText(model.getMessageUser());
                messages.setText(model.getMessageText());
                times.setText(DateFormat.format("dd-MMM-yyyy --  h:mm a", model.getMessageTime()));
            } else {
                rls.setVisibility(View.GONE);
                user.setText("You");
                message.setText(model.getMessageText());
                time.setText(DateFormat.format("dd-MMM-yyyy --  h:mm a", model.getMessageTime()));
            }

//           String userId = chatFunctions.getMessageUser();
//            user.setText(userId);
//            String body = chatFunctions.getMessageText();
//            message.setText(body);
//            time.setText(DateFormat.format("dd-MMM-yyyy --  h:mm a", chatFunctions.getMessageTime()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.stopListening();
        }
    }
}
