package com.example.lizandro_fbu_insta;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lizandro_fbu_insta.model.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{
    private Context context;
    private List<Post> posts;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int postion) {
        final Post post = posts.get(postion);
        holder.bind(post);
        holder.imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post selctionPost = post;
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("post", selctionPost);
                intent.putExtra("user", selctionPost.getUser());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewHandle;
        private ImageView imageViewImage;
        private TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHandle = itemView.findViewById(R.id.textViewHandle);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
           // itemView.setOnClickListener(this);
        }
        public void bind(Post post) {
            textViewHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(imageViewImage);
            }
            textViewDescription.setText(post.getDescription());
        }
/**
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(context, "This is my message "+position, Toast.LENGTH_LONG).show();
        }
    }*/
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
}
