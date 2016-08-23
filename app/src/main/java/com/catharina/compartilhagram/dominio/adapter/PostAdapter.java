package com.catharina.compartilhagram.dominio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.catharina.compartilhagram.R;
import com.catharina.compartilhagram.dominio.dao.DAO;
import com.catharina.compartilhagram.dominio.modelo.Post;

import java.util.List;

import io.realm.Realm;

/**
 * Created by catharina on 22/08/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostVieWolder>{

    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public PostVieWolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_post, parent, false);
        PostVieWolder postVieWolder = new PostVieWolder(context, view);
        return postVieWolder;
    }

    @Override
    public void onBindViewHolder(PostVieWolder holder, int position) {
        Post post = posts.get(position);
        holder.textAutor.setText(post.getAutor());
        holder.textDescricao.setText(post.getDescricao());
        holder.textLikes.setText(post.getCurtidas()+"");
        holder.textData.setText(post.getDataString());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostVieWolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Context context;

        TextView textAutor;
        TextView textData;
        TextView textDescricao;
        TextView textLikes;
        ImageButton curtir;

        private Realm realm;
        private DAO dao;

        public PostVieWolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            this.textAutor = (TextView) itemView.findViewById(R.id.textTituloPost);
            this.textData = (TextView) itemView.findViewById(R.id.textData);
            this.textDescricao = (TextView) itemView.findViewById(R.id.textDescricao);
            this.textLikes = (TextView) itemView.findViewById(R.id.textLikes);
            this.curtir = (ImageButton) itemView.findViewById(R.id.curtir);
            this.curtir.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            realm = Realm.getDefaultInstance();
            dao = DAO.getInstance(realm);
            realm.beginTransaction();
                Post post = posts.get(getAdapterPosition());
                int curtidas = Integer.parseInt(post.getCurtidas()) + 1;
                post.setCurtidas(String.valueOf(curtidas));
            realm.commitTransaction();
            notifyDataSetChanged();
        }
    }
}
