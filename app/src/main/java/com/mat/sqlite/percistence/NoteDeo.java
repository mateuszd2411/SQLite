package com.mat.sqlite.percistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mat.sqlite.models.Note;

import java.util.List;

@Dao
public interface NoteDeo {

    @Insert
    long[] insertNotes(Note... notes);          /// ... is []

    @Query("SELECT * FROM notes")           ///SQL
    LiveData<List<Note>> getNotes();

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);

}
