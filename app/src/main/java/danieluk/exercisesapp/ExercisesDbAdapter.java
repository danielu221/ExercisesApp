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
        public static final String KEY_DATE = "exercise_date";


        private static final String TAG = "ExercisesDbAdapter";
        private DatabaseHelper mDbHelper;
        private SQLiteDatabase mDb;

        private static final String DATABASE_NAME = "Exercises";
        private static final String SQLITE_TABLE = "Exercise";
        private static final int DATABASE_VERSION = 7;

        private final Context mCtx;

        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                        KEY_NAME + "," +
                        KEY_SERIES +"," +
                        KEY_REPS +"," +
                        KEY_WEIGHTS +"," +
                        KEY_NOTES +"," +
                        KEY_DATE +
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

    public long createExercise(String name, int series,int reps,int weights,String notes,String date) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SERIES, series);
        initialValues.put(KEY_REPS, reps);
        initialValues.put(KEY_WEIGHTS, weights);
        initialValues.put(KEY_NOTES, notes);
        initialValues.put(KEY_DATE, date);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllExercises() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchAllExercises() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                         KEY_NAME, KEY_SERIES,KEY_REPS,KEY_WEIGHTS,KEY_NOTES,KEY_DATE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // metoda do testów
    public void insertSomeExercises() {

        createExercise("a",1,15,0,"Ostatnia seria byla niepelna","2017-01-03");
        createExercise("b",3,10,30,"Z kazda seria zwiekszalem ciezar o 10kg","2017-06-03");
        createExercise("d",1,15,0,"Ostatnia seria byla niepelna","2017-12-30");
        createExercise("c",3,10,30,"Z kazda seria zwiekszalem ciezar o 10kg","2017-12-03");


    }

    //
    public boolean deleteRowWithId(int id){
        return mDb.delete(SQLITE_TABLE,KEY_ROWID+"="+Integer.toString(id),null)>0;
    }



}
