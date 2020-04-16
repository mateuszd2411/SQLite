package com.mat.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mat.sqlite.adapters.NotesRecyclerAdapter;
import com.mat.sqlite.models.Note;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

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
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }
}
