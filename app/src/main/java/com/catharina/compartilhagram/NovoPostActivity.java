package com.catharina.compartilhagram;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.catharina.compartilhagram.dominio.dao.DAO;
import com.catharina.compartilhagram.dominio.modelo.Post;

import java.util.Date;

import io.realm.Realm;

public class NovoPostActivity extends AppCompatActivity{

    private EditText edtAutor;
    private EditText edtDescricao;

    private Post post;

    private Realm realm;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_post);

        realm = Realm.getDefaultInstance();
        dao = DAO.getInstance(realm);

        edtAutor = (EditText) findViewById(R.id.edtTitulo);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String idpost = bundle.getString("IDPOST");
            if(idpost != null) {
                post = dao.getPostById(idpost);
                edtAutor.setText(post.getAutor());
                edtDescricao.setText(post.getDescricao());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_post:
                salvar();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        if(this.post != null){
            realm.beginTransaction();
            post.setAutor(edtAutor.getText().toString());
            post.setDescricao(edtDescricao.getText().toString());
            post.setDataPost(new Date());
            realm.commitTransaction();
        }
        else {
            Post post = new Post();
            post.setAutor(edtAutor.getText().toString());
            post.setDescricao(edtDescricao.getText().toString());
            post.setDataPost(new Date());

            dao.savePost(post);
        }
    }
}
