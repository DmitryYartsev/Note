package com.example.dmitry.note.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dmitry on 25.01.2017.
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    public NotesDbHelper(Context context) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String DATABASE_NAME = "tnotes.db";
    private static final int DATABASE_VERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TNOTES_TABLE = "CREATE TABLE " + NoteContract.MNote.TABLE_NAME + " ("
                + NoteContract.MNote._ID + " INTEGER PRIMARY KEY , "
                + NoteContract.MNote.COLUMN_NAME + " TEXT, "
                + NoteContract.MNote.COLUMN_TEXT+ " TEXT, "
                + NoteContract.MNote.COLUMN_LABEL + " TEXT, "
                + NoteContract.MNote.COLUMN_PASSWORD + " INTEGER);";

        db.execSQL(SQL_CREATE_TNOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
