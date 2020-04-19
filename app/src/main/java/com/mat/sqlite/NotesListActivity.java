package com.mat.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mat.sqlite.adapters.NotesRecyclerAdapter;
import com.mat.sqlite.models.Note;
import com.mat.sqlite.percistence.NoteRepository;
import com.mat.sqlite.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteListener,
        View.OnClickListener {

    private static final String TAG = "TAG";

    // Ui components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);

        initRecyclerView();
        retrieveNotes();
//        insertFakeList();


        Log.d(TAG, "onCreate: thread +" +  Thread.currentThread().getName());

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Notes");

    }

    private void retrieveNotes(){
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNotes.size() > 0){
                    mNotes.clear();
                }
                if (notes != null){
                    mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClisk(int position) {
        Log.d(TAG, "onNoteClisk: clicked");

        Intent intent = new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note",mNotes.get(position));
        startActivity(intent);
        
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback  itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
