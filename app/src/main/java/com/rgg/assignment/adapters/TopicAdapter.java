package com.rgg.assignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rgg.assignment.R;
import com.rgg.assignment.model.Topic;

import java.util.List;


public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private Context mContext;
    private List<Topic> topicList;


    public TopicAdapter(Context mContext, List<Topic> topicList) {
        this.mContext = mContext;
        this.topicList = topicList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(topicList.get(position));
    }

    @Override
    public int getItemCount() {
        return topicList != null ? topicList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private TextView title;

        @Nullable
        private TextView genre;

        @Nullable
        private TextView author;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            genre = itemView.findViewById(R.id.genre);
            author = itemView.findViewById(R.id.author);
        }


        public void bind(Topic book) {
            if (title != null) title.setText(book.getTitle());
            if (genre != null) genre.setText(book.getGenre());
            if (author != null) author.setText(book.getAuthor());
        }
    }


}
