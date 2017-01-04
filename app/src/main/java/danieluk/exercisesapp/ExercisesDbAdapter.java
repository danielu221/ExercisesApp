package danieluk.exercisesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Danielu on 2017-01-03.
 */

public class ExercisesDbAdapter{
        public static final String KEY_ROWID = "_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_SERIES = "series";
        public static final String KEY_REPS = "reps";
        public static final String KEY_WEIGHTS = "weights";
        public static final String KEY_NOTES = "notes";


        private static final String TAG = "ExercisesDbAdapter";
        private DatabaseHelper mDbHelper;
        private SQLiteDatabase mDb;

        private static final String DATABASE_NAME = "Exercises";
        private static final String SQLITE_TABLE = "Exercise";
        private static final int DATABASE_VERSION = 5;

        private final Context mCtx;

        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                        KEY_NAME + "," +
                        KEY_SERIES +"," +
                        KEY_REPS +"," +
                        KEY_WEIGHTS +"," +
                        KEY_NOTES +
                        ");";

private static class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w(TAG, DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
        onCreate(db);
    }
}

    public ExercisesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ExercisesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createExercise(String name, int series,int reps,int weights,String notes) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SERIES, series);
        initialValues.put(KEY_REPS, reps);
        initialValues.put(KEY_WEIGHTS, weights);
        initialValues.put(KEY_NOTES, notes);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllExercises() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }
/*
    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_NAME, KEY_SERIES},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_NAME, KEY_SERIES},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }*/

    public Cursor fetchAllExercises() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                         KEY_NAME, KEY_SERIES,KEY_REPS,KEY_WEIGHTS,KEY_NOTES},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeExercises() {

        createExercise("Podnoszenie sztangielek na barki",1,15,0,"Ostatnia seria byla niepelna");
        createExercise("Przysiady ze sztanga",3,10,30,"Z kazda seria zwiekszalem ciezar o 10kg");
        createExercise("Podnoszenie sztangielek na barki",1,15,0,"Ostatnia seria byla niepelna");
        createExercise("Przysiady ze sztanga",3,10,30,"Z kazda seria zwiekszalem ciezar o 10kg");


    }

}
