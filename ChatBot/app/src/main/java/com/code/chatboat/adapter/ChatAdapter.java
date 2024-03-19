package com.code.chatboat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.chatboat.R;
import com.code.chatboat.model.Message;
import com.code.chatboat.model.MessageType;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<Message> messages;
    private String currentUser;

    public ChatAdapter(List<Message> messages, String currentUser) {
        this.messages = messages;
        this.currentUser = currentUser;
    }

    public void setList(List<Message> messages){
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == MessageType.SENT.ordinal()) {
            view = inflater.inflate(R.layout.first, parent, false);
        } else {
            view = inflater.inflate(R.layout.second, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUser)) {
            return MessageType.SENT.ordinal();
        } else {
            return MessageType.RECEIVED.ordinal();
        }
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.text_view_message);
        }

        public void bind(Message message) {
            textViewMessage.setText(message.getContent());
        }
    }
}
