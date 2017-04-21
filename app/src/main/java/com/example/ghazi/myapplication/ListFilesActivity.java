package com.example.ghazi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghazi on 17/07/2016.
 */
public class ListFilesActivity extends ActionBarActivity {

    private Context context = this;
    private File currentFile = null;
    private ListView liste = null;
    private List<File> listeFile = new ArrayList<File>();
    private List<File> listeSearch = new ArrayList<File>();
    private SearchView search = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_file_layout);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));


        search = (SearchView) findViewById(R.id.search);
        liste = (ListView) findViewById(R.id.filesList);





        currentFile = Environment.getDataDirectory();
        currentFile = currentFile.getParentFile();

remplirListe(currentFile);




        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File f = (File) parent.getItemAtPosition(position);

                if(f.isDirectory()) {
                    listeFile.clear();
                    search.setQuery("",false);
                    currentFile = f;
                    remplirListe(currentFile);
                }else{

                    //return file
                    Intent intent = new Intent();
                    intent.putExtra("file1",f.getName());
                    intent.putExtra("file2",f.getAbsolutePath());

                    ListFilesActivity.this.setResult(1,intent);
                    ListFilesActivity.this.finish();

                }

            }
        });


search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if(!s.toString().equals("")) {
            listeSearch.clear();

            for (File f : listeFile) {

                if (f.getName().toUpperCase().startsWith(s.toString().toUpperCase()))
                    listeSearch.add(f);

            }


            FileAdapter fileAdapter = new FileAdapter(ListFilesActivity.this, listeSearch);
            liste.setAdapter(fileAdapter);

        }else{

            FileAdapter adapter = new FileAdapter(ListFilesActivity.this,listeFile);
            liste.setAdapter(adapter);

        }


        return false;
    }
});





    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(KeyEvent.KEYCODE_BACK == keyCode){

            if(currentFile.getParent() != null){
                listeFile.clear();
                search.setQuery("",false);
                currentFile = currentFile.getParentFile();
                remplirListe(currentFile);
                return true;

            }else{

                return super.onKeyDown(keyCode, event);
            }


        }
        return super.onKeyDown(keyCode, event);
    }



    private void remplirListe(File file){


        if(file != null) {


            File[] files = file.listFiles();


                if (files == null) {

                    Toast.makeText(context, "Dossier vide", Toast.LENGTH_SHORT).show();

                   FileAdapter adapter = new FileAdapter(this,listeFile);
                    liste.setAdapter(adapter);

                } else {

                    if (files.length == 0) {
                        Toast.makeText(context, "Dossier vide", Toast.LENGTH_SHORT).show();

                        FileAdapter adapter = new FileAdapter(this,listeFile);
                        liste.setAdapter(adapter);
                    }else {




                        for (File f : files)
                            listeFile.add(f);


                       FileAdapter adapter = new FileAdapter(this,listeFile);
                        liste.setAdapter(adapter);
                    }


                }

        }

        }







}
