package com.example.dmitry.note;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmitry.note.data.NoteContract;
import com.example.dmitry.note.data.NotesDbHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dmitry on 25.01.2017.
 */

public class Adapters_notes extends RecyclerView.Adapter<Adapters_notes.ViewHolder>  {
    public Context context;
    public List<Note> noteList;
    int height;
    static final int PAGE_COUNT = 2;
    PagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;


    public Adapters_notes ( List<Note> noteList,Context context){
        this.context=context;
    this.noteList=noteList;
        }


@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
    return new ViewHolder(itemView);
        }

@Override
public void onBindViewHolder(final ViewHolder holder, int position) {
    Note note_ob = noteList.get(position);
   if (!note_ob.name.equals("")) {
        holder.text_name.setText(note_ob.name);

    } else {
        holder.text_name.setGravity(Gravity.NO_GRAVITY);
        holder.text_name.setText(note_ob.text);
    }


    holder.setClickListener(new ItemClickListener() {
        @Override
        public void onClick(View view, int position, boolean isLongClick) {
            Note obj = noteList.get(position);

            if(!isLongClick){
            Intent i = new Intent(context, One_note.class);
            i.putExtra("name", obj.name);
            i.putExtra("text", obj.text);
            i.putExtra("id", obj.id);
            i.putExtra("label", obj.label);
            view.getContext().startActivity(i);
        }
        else delete(obj.id,position);}
    });

}





@Override
public int getItemCount() {
        return noteList.size();
        }


class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {
    private ItemClickListener clickListener;
   public TextView text_name;
    public TextView text_delete;
   public LinearLayout delete;
    public TextView cancel;
    public LinearLayout linearLayout;



    public ViewHolder(View itemView) {
        super(itemView);
      text_name = (TextView) itemView.findViewById(R.id.name_of_note);
        linearLayout=(LinearLayout) itemView.findViewById(R.id.layout_item);
        text_delete=(TextView) itemView.findViewById(R.id.delete_item);
        cancel=(TextView) itemView.findViewById(R.id.cancel_item);
        delete=(LinearLayout)itemView.findViewById(R.id.delete_layout);

        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    @Override
    public void onClick(View view) {
        clickListener.onClick(view, getPosition(), false);
    }
    @Override
    public boolean onLongClick(View view) {
        clickListener.onClick(view, getPosition(), true);
        return true;
    }

}
    public void addItem(Note note) {
        noteList.add(note);
        notifyItemInserted(noteList.size());
    }

    public void removeItem(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteList.size());
    }


    public void delete(int id,int pos) {


            NotesDbHelper mDbHelper = new NotesDbHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            db.delete(NoteContract.MNote.TABLE_NAME,
                    "_id = ?",
                    new String[] {Integer.toString(id)});
        removeItem(pos);




    }

}


