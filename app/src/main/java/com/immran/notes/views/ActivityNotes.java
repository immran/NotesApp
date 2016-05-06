package com.immran.notes.views;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.immran.notes.R;
import com.immran.notes.model.Notes;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by immran on 04/05/2016.
 */
public class ActivityNotes extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab;

    EditText etTitle, etDesc;

    String title, note;
    long time;

    boolean editingNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        toolbar = (Toolbar) findViewById(R.id.addnote_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);

        getSupportActionBar().setTitle("Add new note");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        etTitle = (EditText) findViewById(R.id.addnote_title);
        etDesc = (EditText) findViewById(R.id.addnote_desc);

        fab = (FloatingActionButton) findViewById(R.id.addnote_fab);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        final Realm realm= Realm.getInstance(realmConfiguration);

        //  handle intent

//        editingNote = getIntent() != null;
        editingNote = getIntent().getBooleanExtra("isEditing", false);
        if (editingNote) {
            title = getIntent().getStringExtra("note_title");
            note = getIntent().getStringExtra("note");
            time = getIntent().getLongExtra("note_time", 0);

            etTitle.setText(title);
            etDesc.setText(note);

        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add note to DB

                final String newTitle = etTitle.getText().toString();
                String newDesc = etDesc.getText().toString();
                long newTime = System.currentTimeMillis();


                /**
                 * TODO: Check if note exists before saving
                 */
                if (!editingNote) {
                    Log.d("Note", "saving");
                    realm.beginTransaction();

                    Notes note = realm.createObject(Notes.class);
                    note.setTitle(newTitle);
                    note.setDescription(newDesc);
                    note.setTime(newTime);

                    realm.commitTransaction();


                } else {
                    Log.d("Note", "updating");

                    /*final List<Notes> notes = Notes.find(Notes.class, "title = ?", title);
                    if (notes.size() > 0) {

                        Notes note = notes.get(0);
                        Log.d("got note", "note: " + note.title);
                        note.title = newTitle;
                        note.description = newDesc;
                        note.time = newTime;

                        note.save();

                    }*/

                    realm.beginTransaction();
                    Notes realmResults = realm.where(Notes.class).equalTo("title",title).findFirst();
                    realmResults.setTitle(newTitle);

                    realm.commitTransaction();

                }

                finish();


            }
        });


    }
}
