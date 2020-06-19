package com.rutkovski.chatapp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    private static final int TYPE_MY_MESSAGE = 0;
    private static final int TYPE_OTHER_MESSAGE = 1;

    private List<Message> messages;
    private Context context;

    public MessagesAdapter(Context context) {
        this.messages = new ArrayList<>();
        this.context = context;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_MY_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layaut_item_my_message, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layaut_item_other_message, parent, false);

        }
        return new MessageViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (author!=null&&author.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("author","Anonim"))){
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        String textOfMessage = message.getTextOfMessage();
        String urlToMessage = message.getImageUrl();
        holder.textViewAuthor.setText(author);

        if (urlToMessage == null||urlToMessage.isEmpty()){
            holder.imageViewImageUrl.setVisibility(View.GONE);
        }else {
            holder.imageViewImageUrl.setVisibility(View.VISIBLE);
        }
        holder.textViewAuthor.setText(author);

        if (textOfMessage !=null&&!textOfMessage.isEmpty()){
            holder.textViewMessage.setVisibility(View.VISIBLE);
            holder.textViewMessage.setText(textOfMessage);
        }else {
            holder.textViewMessage.setVisibility(View.GONE);
        }


        if (urlToMessage!= null && !urlToMessage.isEmpty()){
            Picasso.get().load(urlToMessage).into(holder.imageViewImageUrl);
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAuthor;
        TextView textViewMessage;
        private ImageView imageViewImageUrl;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            imageViewImageUrl = itemView.findViewById(R.id.imageViewImage);
        }
    }

}

