package com.example.dmitry.note;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;
public class CreatePrNote extends AppCompatActivity {
    private NotesDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pr_note);
        mDbHelper = new NotesDbHelper(this);
    }
    private void insertGuest() {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        EditText edit_name=(EditText) findViewById(R.id.name);
        EditText edit_text=(EditText) findViewById(R.id.text);
        String name=edit_name.getText().toString();
        String text=edit_text.getText().toString();
        // а информация о госте является значениями ключей

        ContentValues values = new ContentValues();
        values.put(NoteContract.MNote.COLUMN_NAME, name);
        values.put(NoteContract.MNote.COLUMN_TEXT, text);
        values.put(NoteContract.MNote.COLUMN_LABEL, "private");

        long newRowId = db.insert(NoteContract.MNote.TABLE_NAME, null, values);
    }

    public void create_pr_note(View view) {
        insertGuest();
        startActivity(new Intent(getApplicationContext(),Private_list.class));
    }
}
