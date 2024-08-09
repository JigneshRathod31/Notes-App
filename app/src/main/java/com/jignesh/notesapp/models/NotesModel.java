package com.jignesh.notesapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_tbl")
public class NotesModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "note_title")
    public String noteTitle;

    @ColumnInfo(name = "note_sub_title")
    public String noteSubTitle;

    @ColumnInfo(name = "note_body")
    public String noteBody;

    @ColumnInfo(name = "note_date")
    public String noteDate;

    @ColumnInfo(name = "note_priority")
    public String notePriority;

    // Constructors
    public NotesModel(){}

    public NotesModel(String noteTitle, String noteSubTitle, String noteBody, String noteDate, String notePriority) {
        this.noteTitle = noteTitle;
        this.noteSubTitle = noteSubTitle;
        this.noteBody = noteBody;
        this.noteDate = noteDate;
        this.notePriority = notePriority;
    }

    public NotesModel(int id, String noteTitle, String noteSubTitle, String noteBody, String noteDate, String notePriority) {
        this.id = id;
        this.noteTitle = noteTitle;
        this.noteSubTitle = noteSubTitle;
        this.noteBody = noteBody;
        this.noteDate = noteDate;
        this.notePriority = notePriority;
    }
}
