package com.example.dmitry.note;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;

import java.util.ArrayList;
import java.util.List;

public class Private_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NotesDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onAttachedToWindow();

        mDbHelper = new NotesDbHelper(this);
        initViews();
        displayDatabaseInfo();
    }
    public void add_pr(View view) {
        startActivity(new Intent(getApplicationContext(),CreatePrNote.class));
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(getApplicationContext(),Main_list.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);

    }
    private void initViews() {
        List<Note> list_of_all_note = displayDatabaseInfo();
        List<Note> list_of_private_note = new ArrayList<>();
        for(int i=0;i<list_of_all_note.size();i++){
            if(list_of_all_note.get(i).label.equals("private"))
                list_of_private_note.add((list_of_all_note.get(i)));
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapters_notes adapter = new Adapters_notes(list_of_private_note, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


    private List<Note> displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Note> list_of_project = new ArrayList<>();

        String[] projection = {
                NoteContract.MNote._ID,
                NoteContract.MNote.COLUMN_NAME,
                NoteContract.MNote.COLUMN_TEXT,
                NoteContract.MNote.COLUMN_LABEL,
                NoteContract.MNote.COLUMN_PASSWORD };

        // Делаем запрос
        Cursor cursor = db.query(
                NoteContract.MNote.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {
            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(NoteContract.MNote._ID);
            int nameColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_NAME);
            int textColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_TEXT);
            int labelColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_LABEL);
            int passwordColumnIndex = cursor.getColumnIndex(NoteContract.MNote.COLUMN_PASSWORD);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int ID = cursor.getInt(idColumnIndex);
                String Name = cursor.getString(nameColumnIndex);
                String Text= cursor.getString(textColumnIndex);
                String Label = cursor.getString(labelColumnIndex);
                int Password= cursor.getInt(passwordColumnIndex);

             /*   displayTextView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCity + " - " +
                        currentGender + " - " +
                        currentAge));       */

                list_of_project.add(new Note(ID, Name, Text, Password, Label));

            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return list_of_project;
    }


    public void changePassword(View view) {
        startActivity(new Intent(getApplicationContext(),ChangePassword.class));

    }
}