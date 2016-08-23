package com.catharina.compartilhagram;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.catharina.compartilhagram.dominio.adapter.PostAdapter;
import com.catharina.compartilhagram.dominio.dao.DAO;
import com.catharina.compartilhagram.dominio.modelo.Post;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private Realm realm;
    private DAO dao;
    private RealmResults<Post> posts;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        realm = Realm.getDefaultInstance();
        dao = DAO.getInstance(realm);

        posts = dao.getPostAll();
        adapter = new PostAdapter(getContext(), posts);

        recyclerView = (RecyclerView) view.findViewById(R.id.listaPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                realm.beginTransaction();
                posts.get(viewHolder.getAdapterPosition()).deleteFromRealm();
                realm.commitTransaction();
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onResume() {
        adapter = new PostAdapter(getContext(), dao.getPostAll());
        recyclerView.setAdapter(adapter);
        super.onResume();
    }
}
