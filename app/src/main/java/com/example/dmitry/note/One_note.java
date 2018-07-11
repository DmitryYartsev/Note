package com.example.dmitry.note;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;

import java.util.ArrayList;
import java.util.List;

public class One_note extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int id;
    String t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText text_name = (EditText) findViewById(R.id.name);
        EditText text_date = (EditText) findViewById(R.id.text);
        id = getIntent().getIntExtra("id", 100);
        t1=getIntent().getStringExtra("name");
        t2=getIntent().getStringExtra("text");
        text_name.setText(getIntent().getStringExtra("name"));
        text_date.setText(getIntent().getStringExtra("text"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final Button btn_save = (Button) findViewById(R.id.save_chan);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        text_name.getBackground().setColorFilter(getResources().getColor(R.color.colorEditTextLine), PorterDuff.Mode.SRC_IN);
        text_date.getBackground().setColorFilter(getResources().getColor(R.color.colorEditTextLine), PorterDuff.Mode.SRC_IN);

        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/nord.otf");

        text_name.setTypeface(custom_font2);
        text_date.setTypeface(custom_font2);

        text_name.addTextChangedListener(new TextWatcher() {

                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 btn_save.setVisibility(View.VISIBLE);
                                             }

                                             public void afterTextChanged(Editable s) {


                                             }
                                         }
        );


        text_date.addTextChangedListener(new TextWatcher() {

                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 btn_save.setVisibility(View.VISIBLE);
                                             }

                                             public void afterTextChanged(Editable s) {


                                             }
                                         }
        );


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        String label=getIntent().getStringExtra("label");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(label.equals("free")) {

            startActivity(new Intent(this, Main_list.class));
        }
        else startActivity(new Intent(this, Private_list.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_note, menu);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void enterToPrivate(View view) {
        String password;
        SharedPreferences sharedPreferences = getSharedPreferences("password",
                Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password", "e");
        if (!password.equals("e")) {
            startActivity(new Intent(this, Control.class));
        } else {
            startActivity(new Intent(this, CreatePassword.class));
        }

    }

    public void saveChan(View v) {
        EditText text_name = (EditText) findViewById(R.id.name);
        EditText text_date = (EditText) findViewById(R.id.text);
        String name = text_name.getText().toString().trim();
        String text = text_date.getText().toString().trim();
        t1=name;
        t2=text;
        NotesDbHelper mDbHelper = new NotesDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteContract.MNote.COLUMN_NAME, name);
        values.put(NoteContract.MNote.COLUMN_TEXT, text);
        db.update(NoteContract.MNote.TABLE_NAME,
                values,
                "_id = ?",
                new String[]{Integer.toString(id)});
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        final Button btn_save = (Button) findViewById(R.id.save_chan);
        btn_save.setVisibility(View.GONE);
        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();


    }

    public void share(View v) {
        int id_row=getIntent().getIntExtra("id",0);
        Log.i("expanenta", id_row+"");
        String message=t1+"\n"+t2;


        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        try {
            startActivity(share);
        } catch (Exception e) {
            Log.i("expanenta", e.getMessage());
        }
    }


}