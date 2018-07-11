package com.example.dmitry.note;

/**
 * Created by Dmitry on 15.12.2016.
 */

import android.view.View;


public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);

}
