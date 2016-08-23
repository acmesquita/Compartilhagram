package com.catharina.compartilhagram.dominio.dao;

import com.catharina.compartilhagram.dominio.modelo.Post;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by catharina on 22/08/16.
 */
public class DAO {

    private Realm realm;
    private static DAO INSTANCE;

    private DAO(Realm realm) {
        this.realm = realm;
    }

    public static DAO getInstance(Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new DAO(realm);
        }
        return INSTANCE;
    }


    public void savePost(final Post post){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(post);
            }
        });
    }

    public Post getPostById(String id){
        Post post = realm.where(Post.class).equalTo("id", id).findFirst();
        return post;
    }

    public RealmResults<Post> getPostByAutor(String nome){
        return realm.where(Post.class).contains("autor", nome, Case.INSENSITIVE).findAll();
    }

    public RealmResults<Post> getPostAll(){
        return realm.where(Post.class).findAllSorted("dataPost", Sort.DESCENDING);
    }

    public void removeContatoById(String id){
        final Post post = this.getPostById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(post != null){
                    post.deleteFromRealm();
                }
            }
        });
    }
}
