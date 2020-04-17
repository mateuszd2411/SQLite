package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mat.sqlite.adapters.NotesRecyclerAdapter;
import com.mat.sqlite.models.Note;
import com.mat.sqlite.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener{

    private static final String TAG = "TAG";

    // Ui components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        mRecyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakeList();

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notes");

    }

    private void insertFakeList() {
        for (int i = 0; i < 100; i++){
            Note note = new Note();
            note.setTitle("title # " + i);
            note.setContent("content # " + i);
            note.setTimestamp("Jan 2020");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClisk(int position) {
//        mNotes.get(position);
//        Intent intent = new Intent(this,NewActivity.class);
//        startActivity(intent);
    }
}
