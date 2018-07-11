package com.example.dmitry.note.data;
import android.provider.BaseColumns;
/**
 * Created by Dmitry on 25.01.2017.
 */

public class NoteContract {
    private NoteContract() {
    };

    public static final class MNote implements BaseColumns{
        public final static String TABLE_NAME = "tnotes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_TEXT = "text";
        public final static String COLUMN_LABEL = "label";
        public final static String COLUMN_PASSWORD = "password";
    }
}
